/*
 * Copyright (c) 2010 北京数字政通科技股份有限公司
 * 版权所有
 *
 * 修改标识：史先方20100702
 * 修改描述：创建
 */
package com.alarmclock.yhkr.alarmalock.webService;

import android.os.Environment;

import java.io.File;

/**
 * 静态类SysConfig操作系统配置. 提供了读取配置内容和保存等功能.
 * 
 * @version 0.2 20140314
 * @author liweijun
 */
public class SysConfig {

	/** 日志打印的Tag,便于过滤日志. */
	private static final String TAG = "[SysConfig]";

	/** 上报多媒体图片的水印: 空或“no”=无水印，date=拍照日期，pic=图片，其它不为空时=文字. */
	private static String photoWaterMark = "no";
	
	/** 上报多媒体图片的默认最大边长度, 单位为像素. */
	private static int photoWidtHeigth = 640;
	
	/** 上报多媒体图片的大小限制, 单位KB. */
	private static int photoLimitSize = 1024;
	
	/** 相机拍照存放路径. */
	private static String picRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "DCIM/Camera/";


	public static String AccessKeyId=  "LTAIEhAH00ppRBQZ";

	public static String AccessKeySecret="hkJrBD233RUivJujMxV2lmAIbthkx7";

	public static String photoBuketName="xiaoyujiankan";
	public static String photoBuketEndPoint="http://oss-cn-shanghai.aliyuncs.com";

	public static String videoBuketName="xiaoyuvideo";
	public static String videoBuketEndPoint="http://oss-cn-hangzhou.aliyuncs.com";

	public static String token="LTAIEhAH00ppRBQZ";

	public static String MEDIADIR="MultiMediaPost";

	public static double  widthHeighScale=1.0;
	/**
	 * @return the photoWaterMark
	 */
	public static String getPhotoWaterMark() {
		return photoWaterMark;
	}

	/**
	 * @param photoWaterMark the photoWaterMark to set
	 */
	public static void setPhotoWaterMark(String photoWaterMark) {
		SysConfig.photoWaterMark = photoWaterMark;
	}

	/**
	 * @return the photoWidtHeigth
	 */
	public static int getPhotoWidtHeigth() {
		return photoWidtHeigth;
	}

	/**
	 * @param photoWidtHeigth the photoWidtHeigth to set
	 */
	public static void setPhotoWidtHeigth(int photoWidtHeigth) {
		SysConfig.photoWidtHeigth = photoWidtHeigth;
	}

	/**
	 * @return the picRoot
	 */
	public static String getPicRoot() {
		return picRoot;
	}

	/**
	 * @param picRoot the picRoot to set
	 */
	public static void setPicRoot(String picRoot) {
		SysConfig.picRoot = picRoot;
	}

	/**
	 * @return the photoLimitSize
	 */
	public static int getPhotoLimitSize() {
		return photoLimitSize;
	}

	/**
	 * @param photoLimitSize the photoLimitSize to set
	 */
	public static void setPhotoLimitSize(int photoLimitSize) {
		SysConfig.photoLimitSize = photoLimitSize;
	}

	public static String getSDCardPath(){
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	public static String getMediaPath(){
		String path=getSDCardPath()+ "/"+MEDIADIR+"/";
	    if(!new File(path).exists()){
			new File(path).mkdir();
		}
		return path;
	}




	
	
	
	
	
}
