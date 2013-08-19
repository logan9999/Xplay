package com.xuan.lx.xplayer.activity;

import com.xuan.lx.xplayer.R;
import com.xuan.lx.xplayer.R.drawable;
import com.xuan.lx.xplayer.adapter.AlbumsAdapter;
import com.xuan.lx.xplayer.util.MusicData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AlbumsActivity  extends BaseActivity{
	private ListView albumtListView;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		convertView = LayoutInflater.from(this).inflate(R.layout.albums, null);
		convertView.setBackgroundResource(drawable.yanshi);
		setContentView(convertView);
		
		albumtListView = (ListView) this.findViewById(R.id.albumListView);
		AlbumsAdapter adapter = new AlbumsAdapter(this,MusicData.getMusicData(this)); 
		albumtListView.setAdapter(adapter);
		albumtListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(AlbumsActivity.this,
						MusicActivity.class);
				intent.putExtra("id", position);
				startActivity(intent);
			}});
	}
}
