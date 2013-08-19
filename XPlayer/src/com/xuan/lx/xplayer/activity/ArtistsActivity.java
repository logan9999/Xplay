package com.xuan.lx.xplayer.activity;

import com.xuan.lx.xplayer.R;
import com.xuan.lx.xplayer.R.drawable;
import com.xuan.lx.xplayer.adapter.ArtistsAdapter;
import com.xuan.lx.xplayer.util.MusicData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ArtistsActivity  extends BaseActivity{
	private ListView artistListView;
	View convertView ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	convertView = LayoutInflater.from(this).inflate(R.layout.artist, null);
	convertView.setBackgroundResource(drawable.qianshou);
	setContentView(convertView);
	
	artistListView=(ListView) this.findViewById(R.id.artistListView);
	ArtistsAdapter adapter=new ArtistsAdapter(this, MusicData.getMusicData(this));
	artistListView.setAdapter(adapter);
	artistListView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(ArtistsActivity.this,
					MusicActivity.class);
			intent.putExtra("id", arg2);
			startActivity(intent);
		}
	});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		return super.onOptionsItemSelected(item);
	}
	
}
