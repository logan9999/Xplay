package com.xuan.lx.xplayer.activity;

import android.view.Menu;

import com.xuan.lx.xplayer.R;

/**
 * 定义相关播放状态
 * @author xuan.lx
 *
 */
public class Constants {
	/**
	 * 播放的状态
	 */
	//接着播放
	public static final int PLAYING = 0;
	//重新播放
	public static final int REPLAYING = 1;
    //暂停
	public static final int PUASE = 2;
	//下一首
	public static final int NEXT = 3;
	//上一首
	public static final int PREVIOUS = 4;
	
	//停止
	public static final int STOP = 5;
	
	/**
	 * 播放的方式
	 */
    //循环播放 
	public static final int LOOP = 0;
	//单曲循环
	public static final int CIRCLE = 1;
	//随机播放
	public static final int RANDOM = 2;
	
	/** 菜单图片 **/
	public static final int[] menu_image_array = { R.drawable.ic_menu_scan_default,
		R.drawable.ic_menu_skin_default,R.drawable.ic_menu_setting_default, R.drawable.ic_menu_exit_default};
	/** 菜单文字 **/
	public static final String[] menu_name_array = { "隐藏","背景", "设置", "退出" };
	
	
	/* 独一无二的menu选项identifier，用以识别事件 */
	public static final  int M_SCAN = Menu.FIRST;
	public static final  int M_SKIN = Menu.FIRST + 1;
	public static final  int M_SET = Menu.FIRST + 2;
	public static final  int M_EXIT = Menu.FIRST + 3;
	
	/* menu组ID */
	public static final  int idGroup1 = 0;
	/* menuItemID */
	public static final  int orderMenuItem1 = Menu.NONE;
	public static final  int orderMenuItem2 = Menu.NONE + 1;
	public static final  int orderMenuItem3 = Menu.NONE + 2;
	public static final  int orderMenuItem4 = Menu.NONE + 3;
	//
	public static boolean isListenerSensor = true;
	
}
