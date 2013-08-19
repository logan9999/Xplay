package com.xuan.lx.xplayer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import com.xuan.lx.xplayer.R;
import com.xuan.lx.xplayer.activity.Constants;
import com.xuan.lx.xplayer.activity.MusicActivity;
import com.xuan.lx.xplayer.activity.MusicService;

public class Menus {
	public static Activity activity;
	
	 
	public static void setAcitivty(Activity activity){
		Menus.activity = activity;
	}
	public static void setMenus(Menu menu) {
		menu.add(Constants.idGroup1,Constants.M_SCAN ,Constants.orderMenuItem1,R.string.M_SCAN).setIcon(Constants.menu_image_array[0]);
		menu.add(Constants.idGroup1,Constants.M_SKIN ,Constants.orderMenuItem2, R.string.M_SKIN).setIcon(Constants.menu_image_array[1]);
		menu.add(Constants.idGroup1,Constants.M_SET ,Constants.orderMenuItem3, R.string.M_SET).setIcon(Constants.menu_image_array[2]);
		menu.add(Constants.idGroup1,Constants.M_EXIT ,Constants.orderMenuItem4, R.string.M_EXIT).setIcon(Constants.menu_image_array[3]);
		menu.setGroupCheckable(Constants.idGroup1, true, true);
	}
	
	public static void closeApp(){
		try {
			Intent intent = new Intent(activity,MusicService.class);
			activity.stopService(intent); 
			activity.finish();
			android.os.Process.killProcess(android.os.Process.myPid());// 关闭进程
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
