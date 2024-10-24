package com.dao;

import com.entity.XiangmuEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.XiangmuView;

/**
 * 项目 Dao 接口
 *
 * @author 
 */
public interface XiangmuDao extends BaseMapper<XiangmuEntity> {

   List<XiangmuView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
