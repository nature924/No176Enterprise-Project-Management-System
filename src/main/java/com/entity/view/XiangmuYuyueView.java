package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.XiangmuYuyueEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 项目申报
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("xiangmu_yuyue")
public class XiangmuYuyueView extends XiangmuYuyueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表
	/**
	* 报名状态的值
	*/
	@ColumnInfo(comment="报名状态的字典表值",type="varchar(200)")
	private String xiangmuYuyueYesnoValue;

	//级联表 项目
		/**
		* 项目名称
		*/

		@ColumnInfo(comment="项目名称",type="varchar(200)")
		private String xiangmuName;
		/**
		* 项目编号
		*/

		@ColumnInfo(comment="项目编号",type="varchar(200)")
		private String xiangmuUuidNumber;
		/**
		* 项目照片
		*/

		@ColumnInfo(comment="项目照片",type="varchar(200)")
		private String xiangmuPhoto;
		/**
		* 项目类型
		*/
		@ColumnInfo(comment="项目类型",type="int(11)")
		private Integer xiangmuTypes;
			/**
			* 项目类型的值
			*/
			@ColumnInfo(comment="项目类型的字典表值",type="varchar(200)")
			private String xiangmuValue;
		/**
		* 项目文件
		*/

		@ColumnInfo(comment="项目文件",type="varchar(200)")
		private String xiangmuFile;
		/**
		* 项目介绍
		*/

		@ColumnInfo(comment="项目介绍",type="longtext")
		private String xiangmuContent;
		/**
		* 逻辑删除
		*/

		@ColumnInfo(comment="逻辑删除",type="int(11)")
		private Integer xiangmuDelete;
	//级联表 用户
		/**
		* 用户姓名
		*/

		@ColumnInfo(comment="用户姓名",type="varchar(200)")
		private String yonghuName;
		/**
		* 用户手机号
		*/

		@ColumnInfo(comment="用户手机号",type="varchar(200)")
		private String yonghuPhone;
		/**
		* 用户身份证号
		*/

		@ColumnInfo(comment="用户身份证号",type="varchar(200)")
		private String yonghuIdNumber;
		/**
		* 用户头像
		*/

		@ColumnInfo(comment="用户头像",type="varchar(200)")
		private String yonghuPhoto;
		/**
		* 用户邮箱
		*/

		@ColumnInfo(comment="用户邮箱",type="varchar(200)")
		private String yonghuEmail;



	public XiangmuYuyueView() {

	}

	public XiangmuYuyueView(XiangmuYuyueEntity xiangmuYuyueEntity) {
		try {
			BeanUtils.copyProperties(this, xiangmuYuyueEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//当前表的
	/**
	* 获取： 报名状态的值
	*/
	public String getXiangmuYuyueYesnoValue() {
		return xiangmuYuyueYesnoValue;
	}
	/**
	* 设置： 报名状态的值
	*/
	public void setXiangmuYuyueYesnoValue(String xiangmuYuyueYesnoValue) {
		this.xiangmuYuyueYesnoValue = xiangmuYuyueYesnoValue;
	}


	//级联表的get和set 项目

		/**
		* 获取： 项目名称
		*/
		public String getXiangmuName() {
			return xiangmuName;
		}
		/**
		* 设置： 项目名称
		*/
		public void setXiangmuName(String xiangmuName) {
			this.xiangmuName = xiangmuName;
		}

		/**
		* 获取： 项目编号
		*/
		public String getXiangmuUuidNumber() {
			return xiangmuUuidNumber;
		}
		/**
		* 设置： 项目编号
		*/
		public void setXiangmuUuidNumber(String xiangmuUuidNumber) {
			this.xiangmuUuidNumber = xiangmuUuidNumber;
		}

		/**
		* 获取： 项目照片
		*/
		public String getXiangmuPhoto() {
			return xiangmuPhoto;
		}
		/**
		* 设置： 项目照片
		*/
		public void setXiangmuPhoto(String xiangmuPhoto) {
			this.xiangmuPhoto = xiangmuPhoto;
		}
		/**
		* 获取： 项目类型
		*/
		public Integer getXiangmuTypes() {
			return xiangmuTypes;
		}
		/**
		* 设置： 项目类型
		*/
		public void setXiangmuTypes(Integer xiangmuTypes) {
			this.xiangmuTypes = xiangmuTypes;
		}


			/**
			* 获取： 项目类型的值
			*/
			public String getXiangmuValue() {
				return xiangmuValue;
			}
			/**
			* 设置： 项目类型的值
			*/
			public void setXiangmuValue(String xiangmuValue) {
				this.xiangmuValue = xiangmuValue;
			}

		/**
		* 获取： 项目文件
		*/
		public String getXiangmuFile() {
			return xiangmuFile;
		}
		/**
		* 设置： 项目文件
		*/
		public void setXiangmuFile(String xiangmuFile) {
			this.xiangmuFile = xiangmuFile;
		}

		/**
		* 获取： 项目介绍
		*/
		public String getXiangmuContent() {
			return xiangmuContent;
		}
		/**
		* 设置： 项目介绍
		*/
		public void setXiangmuContent(String xiangmuContent) {
			this.xiangmuContent = xiangmuContent;
		}

		/**
		* 获取： 逻辑删除
		*/
		public Integer getXiangmuDelete() {
			return xiangmuDelete;
		}
		/**
		* 设置： 逻辑删除
		*/
		public void setXiangmuDelete(Integer xiangmuDelete) {
			this.xiangmuDelete = xiangmuDelete;
		}
	//级联表的get和set 用户

		/**
		* 获取： 用户姓名
		*/
		public String getYonghuName() {
			return yonghuName;
		}
		/**
		* 设置： 用户姓名
		*/
		public void setYonghuName(String yonghuName) {
			this.yonghuName = yonghuName;
		}

		/**
		* 获取： 用户手机号
		*/
		public String getYonghuPhone() {
			return yonghuPhone;
		}
		/**
		* 设置： 用户手机号
		*/
		public void setYonghuPhone(String yonghuPhone) {
			this.yonghuPhone = yonghuPhone;
		}

		/**
		* 获取： 用户身份证号
		*/
		public String getYonghuIdNumber() {
			return yonghuIdNumber;
		}
		/**
		* 设置： 用户身份证号
		*/
		public void setYonghuIdNumber(String yonghuIdNumber) {
			this.yonghuIdNumber = yonghuIdNumber;
		}

		/**
		* 获取： 用户头像
		*/
		public String getYonghuPhoto() {
			return yonghuPhoto;
		}
		/**
		* 设置： 用户头像
		*/
		public void setYonghuPhoto(String yonghuPhoto) {
			this.yonghuPhoto = yonghuPhoto;
		}

		/**
		* 获取： 用户邮箱
		*/
		public String getYonghuEmail() {
			return yonghuEmail;
		}
		/**
		* 设置： 用户邮箱
		*/
		public void setYonghuEmail(String yonghuEmail) {
			this.yonghuEmail = yonghuEmail;
		}


	@Override
	public String toString() {
		return "XiangmuYuyueView{" +
			", xiangmuYuyueYesnoValue=" + xiangmuYuyueYesnoValue +
			", xiangmuName=" + xiangmuName +
			", xiangmuUuidNumber=" + xiangmuUuidNumber +
			", xiangmuPhoto=" + xiangmuPhoto +
			", xiangmuFile=" + xiangmuFile +
			", xiangmuContent=" + xiangmuContent +
			", xiangmuDelete=" + xiangmuDelete +
			", yonghuName=" + yonghuName +
			", yonghuPhone=" + yonghuPhone +
			", yonghuIdNumber=" + yonghuIdNumber +
			", yonghuPhoto=" + yonghuPhoto +
			", yonghuEmail=" + yonghuEmail +
			"} " + super.toString();
	}
}
