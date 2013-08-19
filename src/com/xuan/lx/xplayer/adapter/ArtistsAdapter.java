package com.xuan.lx.xplayer.adapter;

import java.util.List;

import com.xuan.lx.xplayer.R;
import com.xuan.lx.xplayer.util.Music;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArtistsAdapter extends BaseAdapter{

	private List<Music> listMusic;
	
    private Context context;
    
    public ArtistsAdapter(Context context,List<Music> listMusic){
    	this.context = context;
    	this.listMusic = listMusic;
    }
	
	@Override
	public int getCount() {
		return listMusic.size();
	}

	@Override
	public Object getItem(int position) {
		return listMusic.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.music_item, null);
		}
		Music m = listMusic.get(position);
		TextView textMusicName = (TextView) convertView.findViewById(R.id.music_item_name);
		textMusicName.setText(m.getSinger());
		TextView textMusicSinger = (TextView) convertView.findViewById(R.id.music_item_singer);
		textMusicSinger.setText(m.getAlbum());
		return convertView;
	}

}
