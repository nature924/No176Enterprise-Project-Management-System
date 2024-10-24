
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 项目申报
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/xiangmuYuyue")
public class XiangmuYuyueController {
    private static final Logger logger = LoggerFactory.getLogger(XiangmuYuyueController.class);

    private static final String TABLE_NAME = "xiangmuYuyue";

    @Autowired
    private XiangmuYuyueService xiangmuYuyueService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private CaozuorizhiService caozuorizhiService;//操作日志
    @Autowired
    private DictionaryService dictionaryService;//字典
    @Autowired
    private ForumService forumService;//论坛
    @Autowired
    private GonggaoService gonggaoService;//公告
    @Autowired
    private XiangmuService xiangmuService;//项目
    @Autowired
    private YonghuService yonghuService;//用户
    @Autowired
    private UsersService usersService;//管理员


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = xiangmuYuyueService.queryPage(params);

        //字典表数据转换
        List<XiangmuYuyueView> list =(List<XiangmuYuyueView>)page.getList();
        for(XiangmuYuyueView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        caozuorizhiService.insertCaozuorizhi(String.valueOf(request.getSession().getAttribute("role")),TABLE_NAME,String.valueOf(request.getSession().getAttribute("username")),"列表查询",list.toString());
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        XiangmuYuyueEntity xiangmuYuyue = xiangmuYuyueService.selectById(id);
        if(xiangmuYuyue !=null){
            //entity转view
            XiangmuYuyueView view = new XiangmuYuyueView();
            BeanUtils.copyProperties( xiangmuYuyue , view );//把实体数据重构到view中
            //级联表 项目
            //级联表
            XiangmuEntity xiangmu = xiangmuService.selectById(xiangmuYuyue.getXiangmuId());
            if(xiangmu != null){
            BeanUtils.copyProperties( xiangmu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setXiangmuId(xiangmu.getId());
            }
            //级联表 用户
            //级联表
            YonghuEntity yonghu = yonghuService.selectById(xiangmuYuyue.getYonghuId());
            if(yonghu != null){
            BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setYonghuId(yonghu.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
    caozuorizhiService.insertCaozuorizhi(String.valueOf(request.getSession().getAttribute("role")),TABLE_NAME,String.valueOf(request.getSession().getAttribute("username")),"单条数据查看",view.toString());
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody XiangmuYuyueEntity xiangmuYuyue, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,xiangmuYuyue:{}",this.getClass().getName(),xiangmuYuyue.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("用户".equals(role))
            xiangmuYuyue.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<XiangmuYuyueEntity> queryWrapper = new EntityWrapper<XiangmuYuyueEntity>()
            .eq("xiangmu_id", xiangmuYuyue.getXiangmuId())
            .eq("yonghu_id", xiangmuYuyue.getYonghuId())
            .in("xiangmu_yuyue_yesno_types", new Integer[]{1,2})
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XiangmuYuyueEntity xiangmuYuyueEntity = xiangmuYuyueService.selectOne(queryWrapper);
        if(xiangmuYuyueEntity==null){
            xiangmuYuyue.setXiangmuYuyueYesnoTypes(1);
            xiangmuYuyue.setInsertTime(new Date());
            xiangmuYuyue.setCreateTime(new Date());
            xiangmuYuyueService.insert(xiangmuYuyue);
            caozuorizhiService.insertCaozuorizhi(String.valueOf(request.getSession().getAttribute("role")),TABLE_NAME,String.valueOf(request.getSession().getAttribute("username")),"新增",xiangmuYuyue.toString());
            return R.ok();
        }else {
            if(xiangmuYuyueEntity.getXiangmuYuyueYesnoTypes()==1)
                return R.error(511,"有相同的待审核的数据");
            else if(xiangmuYuyueEntity.getXiangmuYuyueYesnoTypes()==2)
                return R.error(511,"有相同的审核通过的数据");
            else
                return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody XiangmuYuyueEntity xiangmuYuyue, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,xiangmuYuyue:{}",this.getClass().getName(),xiangmuYuyue.toString());
        XiangmuYuyueEntity oldXiangmuYuyueEntity = xiangmuYuyueService.selectById(xiangmuYuyue.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            xiangmuYuyue.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        if("".equals(xiangmuYuyue.getXiangmuFile()) || "null".equals(xiangmuYuyue.getXiangmuFile())){
                xiangmuYuyue.setXiangmuFile(null);
        }

            xiangmuYuyueService.updateById(xiangmuYuyue);//根据id更新
            List<String> strings = caozuorizhiService.clazzDiff(xiangmuYuyue, oldXiangmuYuyueEntity, request,new String[]{"updateTime"});
            caozuorizhiService.insertCaozuorizhi(String.valueOf(request.getSession().getAttribute("role")),TABLE_NAME,String.valueOf(request.getSession().getAttribute("username")),"修改",strings.toString());
            return R.ok();
    }


    /**
    * 审核
    */
    @RequestMapping("/shenhe")
    public R shenhe(@RequestBody XiangmuYuyueEntity xiangmuYuyueEntity, HttpServletRequest request){
        logger.debug("shenhe方法:,,Controller:{},,xiangmuYuyueEntity:{}",this.getClass().getName(),xiangmuYuyueEntity.toString());

        XiangmuYuyueEntity oldXiangmuYuyue = xiangmuYuyueService.selectById(xiangmuYuyueEntity.getId());//查询原先数据

//        if(xiangmuYuyueEntity.getXiangmuYuyueYesnoTypes() == 2){//通过
//            xiangmuYuyueEntity.setXiangmuYuyueTypes();
//        }else if(xiangmuYuyueEntity.getXiangmuYuyueYesnoTypes() == 3){//拒绝
//            xiangmuYuyueEntity.setXiangmuYuyueTypes();
//        }
        xiangmuYuyueEntity.setXiangmuYuyueShenheTime(new Date());//审核时间
        xiangmuYuyueService.updateById(xiangmuYuyueEntity);//审核

        caozuorizhiService.insertCaozuorizhi(String.valueOf(request.getSession().getAttribute("role")),TABLE_NAME,String.valueOf(request.getSession().getAttribute("username")),"审核数据","审核"+oldXiangmuYuyue+"数据,审核结果是"+(xiangmuYuyueEntity.getXiangmuYuyueYesnoTypes()==2?"通过":"拒绝"));
        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<XiangmuYuyueEntity> oldXiangmuYuyueList =xiangmuYuyueService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        xiangmuYuyueService.deleteBatchIds(Arrays.asList(ids));

        caozuorizhiService.insertCaozuorizhi(String.valueOf(request.getSession().getAttribute("role")),TABLE_NAME,String.valueOf(request.getSession().getAttribute("username")),"删除",oldXiangmuYuyueList.toString());
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //.eq("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        try {
            List<XiangmuYuyueEntity> xiangmuYuyueList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            XiangmuYuyueEntity xiangmuYuyueEntity = new XiangmuYuyueEntity();
//                            xiangmuYuyueEntity.setXiangmuYuyueUuidNumber(data.get(0));                    //报名编号 要改的
//                            xiangmuYuyueEntity.setXiangmuId(Integer.valueOf(data.get(0)));   //项目 要改的
//                            xiangmuYuyueEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            xiangmuYuyueEntity.setXiangmuYuyueText(data.get(0));                    //申报理由 要改的
//                            xiangmuYuyueEntity.setXiangmuFile(data.get(0));                    //附件 要改的
//                            xiangmuYuyueEntity.setXiangmuYuyueYesnoTypes(Integer.valueOf(data.get(0)));   //报名状态 要改的
//                            xiangmuYuyueEntity.setXiangmuYuyueYesnoText(data.get(0));                    //审核回复 要改的
//                            xiangmuYuyueEntity.setXiangmuYuyueShenheTime(sdf.parse(data.get(0)));          //审核时间 要改的
//                            xiangmuYuyueEntity.setInsertTime(date);//时间
//                            xiangmuYuyueEntity.setCreateTime(date);//时间
                            xiangmuYuyueList.add(xiangmuYuyueEntity);


                            //把要查询是否重复的字段放入map中
                                //报名编号
                                if(seachFields.containsKey("xiangmuYuyueUuidNumber")){
                                    List<String> xiangmuYuyueUuidNumber = seachFields.get("xiangmuYuyueUuidNumber");
                                    xiangmuYuyueUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> xiangmuYuyueUuidNumber = new ArrayList<>();
                                    xiangmuYuyueUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("xiangmuYuyueUuidNumber",xiangmuYuyueUuidNumber);
                                }
                        }

                        //查询是否重复
                         //报名编号
                        List<XiangmuYuyueEntity> xiangmuYuyueEntities_xiangmuYuyueUuidNumber = xiangmuYuyueService.selectList(new EntityWrapper<XiangmuYuyueEntity>().in("xiangmu_yuyue_uuid_number", seachFields.get("xiangmuYuyueUuidNumber")));
                        if(xiangmuYuyueEntities_xiangmuYuyueUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(XiangmuYuyueEntity s:xiangmuYuyueEntities_xiangmuYuyueUuidNumber){
                                repeatFields.add(s.getXiangmuYuyueUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [报名编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        xiangmuYuyueService.insertBatch(xiangmuYuyueList);
                        caozuorizhiService.insertCaozuorizhi(String.valueOf(request.getSession().getAttribute("role")),TABLE_NAME,String.valueOf(request.getSession().getAttribute("username")),"批量新增",xiangmuYuyueList.toString());
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }




    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = xiangmuYuyueService.queryPage(params);

        //字典表数据转换
        List<XiangmuYuyueView> list =(List<XiangmuYuyueView>)page.getList();
        for(XiangmuYuyueView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        caozuorizhiService.insertCaozuorizhi(String.valueOf(request.getSession().getAttribute("role")),TABLE_NAME,String.valueOf(request.getSession().getAttribute("username")),"列表查询",list.toString());
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        XiangmuYuyueEntity xiangmuYuyue = xiangmuYuyueService.selectById(id);
            if(xiangmuYuyue !=null){


                //entity转view
                XiangmuYuyueView view = new XiangmuYuyueView();
                BeanUtils.copyProperties( xiangmuYuyue , view );//把实体数据重构到view中

                //级联表
                    XiangmuEntity xiangmu = xiangmuService.selectById(xiangmuYuyue.getXiangmuId());
                if(xiangmu != null){
                    BeanUtils.copyProperties( xiangmu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setXiangmuId(xiangmu.getId());
                }
                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(xiangmuYuyue.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                    caozuorizhiService.insertCaozuorizhi(String.valueOf(request.getSession().getAttribute("role")),TABLE_NAME,String.valueOf(request.getSession().getAttribute("username")),"单条数据查看",view.toString());
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody XiangmuYuyueEntity xiangmuYuyue, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,xiangmuYuyue:{}",this.getClass().getName(),xiangmuYuyue.toString());
        Wrapper<XiangmuYuyueEntity> queryWrapper = new EntityWrapper<XiangmuYuyueEntity>()
            .eq("xiangmu_yuyue_uuid_number", xiangmuYuyue.getXiangmuYuyueUuidNumber())
            .eq("xiangmu_id", xiangmuYuyue.getXiangmuId())
            .eq("yonghu_id", xiangmuYuyue.getYonghuId())
            .eq("xiangmu_yuyue_text", xiangmuYuyue.getXiangmuYuyueText())
            .in("xiangmu_yuyue_yesno_types", new Integer[]{1,2})
            .eq("xiangmu_yuyue_yesno_text", xiangmuYuyue.getXiangmuYuyueYesnoText())
//            .notIn("xiangmu_yuyue_types", new Integer[]{102})
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XiangmuYuyueEntity xiangmuYuyueEntity = xiangmuYuyueService.selectOne(queryWrapper);
        if(xiangmuYuyueEntity==null){
            xiangmuYuyue.setXiangmuYuyueYesnoTypes(1);
            xiangmuYuyue.setInsertTime(new Date());
            xiangmuYuyue.setCreateTime(new Date());
        xiangmuYuyueService.insert(xiangmuYuyue);

            caozuorizhiService.insertCaozuorizhi(String.valueOf(request.getSession().getAttribute("role")),TABLE_NAME,String.valueOf(request.getSession().getAttribute("username")),"前台新增",xiangmuYuyue.toString());
            return R.ok();
        }else {
            if(xiangmuYuyueEntity.getXiangmuYuyueYesnoTypes()==1)
                return R.error(511,"有相同的待审核的数据");
            else if(xiangmuYuyueEntity.getXiangmuYuyueYesnoTypes()==2)
                return R.error(511,"有相同的审核通过的数据");
            else
                return R.error(511,"表中有相同数据");
        }
    }

}

