package com.xuan.lx.xplayer.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.xuan.lx.xplayer.R;
import com.xuan.lx.xplayer.adapter.TabMenu;

public class MenuActivity extends Activity {
	View menuView;
	/** 菜单图片 **/
	int[] menu_image_array = { R.drawable.menu_account,
			R.drawable.menu_settings, R.drawable.menu_quit };
	/** 菜单文字 **/
	String[] menu_name_array = { "隐藏", "设置", "退出" };

	TabMenu tabMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//menuView = View.inflate(this, R.layout.view_menu, null);
		this.setContentView(R.layout.view_menu);
		TabMenu.MenuBodyAdapter bodyAdapter = new TabMenu.MenuBodyAdapter(this,
				menu_image_array,menu_name_array);
		tabMenu = new TabMenu(this, new BodyClickEvent(),bodyAdapter);// 出现与消失的动画
		
		tabMenu.update();
		tabMenu.setBodyAdapter(bodyAdapter);

	}

	class BodyClickEvent implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			tabMenu.setBodySelect(arg2, Color.GRAY);
		}

	}
	 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");// 必须创建一项
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 拦截MENU事件，显示自己的菜单
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (tabMenu != null) {
			if (tabMenu.isShowing()){
				tabMenu.dismiss();
			}else {
				tabMenu.showAtLocation(findViewById(R.id.LinearLayout01),
						Gravity.BOTTOM, 0, 0);
			}
		}
		// 返回为true 则显示系统menu
		return false;
	}
	

}
