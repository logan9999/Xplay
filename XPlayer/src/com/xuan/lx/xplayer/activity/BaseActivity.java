package com.xuan.lx.xplayer.activity;

import com.xuan.lx.xplayer.adapter.Menus;
import com.xuan.lx.xplayer.adapter.MusicAdapter;
import com.xuan.lx.xplayer.adapter.TabMenu;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class BaseActivity extends Activity {
	static MusicAdapter adapter = null;
	View convertView;
	TabMenu tabMenu;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Menus.setAcitivty(this);
		Menus.setMenus(menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case (Constants.M_SCAN):
			break;
		case (Constants.M_SKIN):
			break;
		case (Constants.M_SET):
			Intent mIntent = new Intent();
			mIntent.setClass(this, MyPreference.class);
			startActivity(mIntent);
			break;
		case (Constants.M_EXIT):
			Menus.closeApp();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
