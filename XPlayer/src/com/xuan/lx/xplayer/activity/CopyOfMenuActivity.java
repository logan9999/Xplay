package com.xuan.lx.xplayer.activity;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.xuan.lx.xplayer.R;

public class CopyOfMenuActivity extends Activity {
	AlertDialog menuDialog;// menu菜单Dialog
	GridView menuGrid;
	View menuView;
	final private int menuSettings = Menu.FIRST;
	private static final int REQ_SYSTEM_SETTINGS = 0;
	/** 菜单图片 **/
	int[] menu_image_array = { R.drawable.menu_account,
			R.drawable.menu_settings, R.drawable.menu_quit };
	/** 菜单文字 **/
	String[] menu_name_array = { "隐藏", "设置", "退出" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menuView = View.inflate(this, R.layout.gridview_menu, null);

		// 创建AlertDialog
		menuDialog = new AlertDialog.Builder(this).create();
		menuDialog.setView(menuView);
		menuDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU)// 监听按键
					dialog.dismiss();
				return false;
			}
		});
		menuGrid = (GridView) menuView.findViewById(R.id.gridview);
		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
		/** 监听menu选项 **/
		menuGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				switch (0) {
				case 0:

					break;
				case 1:

					break;
				case 2:

					break;

				}

			}
		});

	}

	private SimpleAdapter getMenuAdapter(String[] menuNameArray,
			int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
				R.layout.item_menu, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });
		return simperAdapter;
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
	   if (menuDialog == null) {
	    menuDialog = new AlertDialog.Builder(this).setView(menuView).show();
	   } else {
	    menuDialog.show();
	   }
	   return false;// 返回为true 则显示系统menu
	}

}
