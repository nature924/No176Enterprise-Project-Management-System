package com.entity.model;

import com.entity.XiangmuYuyueEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 项目申报
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class XiangmuYuyueModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 报名编号
     */
    private String xiangmuYuyueUuidNumber;


    /**
     * 项目
     */
    private Integer xiangmuId;


    /**
     * 用户
     */
    private Integer yonghuId;


    /**
     * 申报理由
     */
    private String xiangmuYuyueText;


    /**
     * 附件
     */
    private String xiangmuFile;


    /**
     * 报名状态
     */
    private Integer xiangmuYuyueYesnoTypes;


    /**
     * 审核回复
     */
    private String xiangmuYuyueYesnoText;


    /**
     * 审核时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date xiangmuYuyueShenheTime;


    /**
     * 项目申报时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 创建时间 show3 listShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：报名编号
	 */
    public String getXiangmuYuyueUuidNumber() {
        return xiangmuYuyueUuidNumber;
    }


    /**
	 * 设置：报名编号
	 */
    public void setXiangmuYuyueUuidNumber(String xiangmuYuyueUuidNumber) {
        this.xiangmuYuyueUuidNumber = xiangmuYuyueUuidNumber;
    }
    /**
	 * 获取：项目
	 */
    public Integer getXiangmuId() {
        return xiangmuId;
    }


    /**
	 * 设置：项目
	 */
    public void setXiangmuId(Integer xiangmuId) {
        this.xiangmuId = xiangmuId;
    }
    /**
	 * 获取：用户
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }


    /**
	 * 设置：用户
	 */
    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 获取：申报理由
	 */
    public String getXiangmuYuyueText() {
        return xiangmuYuyueText;
    }


    /**
	 * 设置：申报理由
	 */
    public void setXiangmuYuyueText(String xiangmuYuyueText) {
        this.xiangmuYuyueText = xiangmuYuyueText;
    }
    /**
	 * 获取：附件
	 */
    public String getXiangmuFile() {
        return xiangmuFile;
    }


    /**
	 * 设置：附件
	 */
    public void setXiangmuFile(String xiangmuFile) {
        this.xiangmuFile = xiangmuFile;
    }
    /**
	 * 获取：报名状态
	 */
    public Integer getXiangmuYuyueYesnoTypes() {
        return xiangmuYuyueYesnoTypes;
    }


    /**
	 * 设置：报名状态
	 */
    public void setXiangmuYuyueYesnoTypes(Integer xiangmuYuyueYesnoTypes) {
        this.xiangmuYuyueYesnoTypes = xiangmuYuyueYesnoTypes;
    }
    /**
	 * 获取：审核回复
	 */
    public String getXiangmuYuyueYesnoText() {
        return xiangmuYuyueYesnoText;
    }


    /**
	 * 设置：审核回复
	 */
    public void setXiangmuYuyueYesnoText(String xiangmuYuyueYesnoText) {
        this.xiangmuYuyueYesnoText = xiangmuYuyueYesnoText;
    }
    /**
	 * 获取：审核时间
	 */
    public Date getXiangmuYuyueShenheTime() {
        return xiangmuYuyueShenheTime;
    }


    /**
	 * 设置：审核时间
	 */
    public void setXiangmuYuyueShenheTime(Date xiangmuYuyueShenheTime) {
        this.xiangmuYuyueShenheTime = xiangmuYuyueShenheTime;
    }
    /**
	 * 获取：项目申报时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 设置：项目申报时间
	 */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：创建时间 show3 listShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间 show3 listShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
