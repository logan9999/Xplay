package com.xuan.lx.xplayer.activity;

import java.util.List;
import com.xuan.lx.xplayer.R;
import com.xuan.lx.xplayer.R.drawable;
import com.xuan.lx.xplayer.adapter.MusicAdapter;
import com.xuan.lx.xplayer.util.Music;
import com.xuan.lx.xplayer.util.MusicData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 初始化音乐列表
 * @author xuan.lx
 *
 */
public class ListActivity extends BaseActivity{
	private ListView listView; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		convertView = LayoutInflater.from(this).inflate(R.layout.listmusic, null);
		convertView.setBackgroundResource(drawable.player_bg2);
		setContentView(convertView);
		listView = (ListView) this.findViewById(R.id.listAllMusic);
		//获取媒体文件
		List<Music> listMusic = MusicData.getMusicData(getApplicationContext());
		if(listMusic != null && listMusic.size() > 0){
			adapter = new MusicAdapter(this, listMusic);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(ItemClick);
		}
		
	}
	 
	OnItemClickListener ItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			adapter.setSelectItem(position);
			adapter.notifyDataSetInvalidated();
			Intent intent = new Intent(ListActivity.this,
					MusicActivity.class);
			intent.putExtra("id", position);
			startActivity(intent);
		}
	};
	@Override
	protected void onStart(){
		super.onStart();
		BaseActivity.adapter.setSelectItem(MusicService.currentId);
		BaseActivity.adapter.notifyDataSetInvalidated();
	}
	
}
