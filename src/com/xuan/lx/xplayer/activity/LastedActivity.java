package com.xuan.lx.xplayer.activity;

import com.xuan.lx.xplayer.R;
import com.xuan.lx.xplayer.R.drawable;
import com.xuan.lx.xplayer.adapter.LastedAdapter;
import com.xuan.lx.xplayer.util.MusicData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LastedActivity  extends BaseActivity{
	private ListView lastedListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		convertView = LayoutInflater.from(this).inflate(R.layout.songs, null);
		convertView.setBackgroundResource(drawable.xim);
		setContentView(convertView);
		
		lastedListView = (ListView) this.findViewById(R.id.lastedListView);
		LastedAdapter adapter = new LastedAdapter(this,MusicData.getMusicData(this));
		lastedListView.setAdapter(adapter);
		lastedListView.setOnItemClickListener(new OnItemClickListener(){
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(LastedActivity.this,MusicActivity.class);
				intent.putExtra("id", position);
				startActivity(intent);
			}
			
		});
	}

}
