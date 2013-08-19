package com.xuan.lx.xplayer.adapter;

import java.util.List;

import com.xuan.lx.xplayer.R;
import com.xuan.lx.xplayer.R.color;
import com.xuan.lx.xplayer.util.Music;
import com.xuan.lx.xplayer.util.MusicData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter{
	String Tag = "MusicAdapter";
	private List<Music> listMusic;
    private Context context;
    private int  selectItem = -1;
    public MusicAdapter(Context context,List<Music> listMusic){
    	this.context = context;
    	this.listMusic = listMusic;
    }
    
    public  void setSelectItem(int selectItem) {  
        this.selectItem = selectItem;  
    }  
    
    public int getSelectItem(){
    	return selectItem;
    }
    
    public void setListItem(List<Music> listMusic){
		this.listMusic = listMusic;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMusic.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listMusic.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		//设置layout
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.music_item, null);
			holder.textMusicName = (TextView) convertView.findViewById(R.id.music_item_name);
			holder.textSinger = (TextView) convertView.findViewById(R.id.music_item_singer);
			holder.textTime = (TextView) convertView.findViewById(R.id.music_item_time);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//获取music信息
		Music m = listMusic.get(position);
		//设置要显示的Text
		holder.textMusicName.setText(m.getTitle());
		holder.textSinger.setText(m.getSinger());
		holder.textTime.setText(MusicData.toTime((int)m.getTime()));
		if( position == this.getSelectItem() ){
			convertView.setBackgroundColor(color.gray);
		}else{
			convertView.setBackgroundColor(color.transparent);
			convertView.getBackground().setAlpha(120);
		} 
		    
		return convertView;
	}
	static class ViewHolder { 
		TextView textMusicName ;
		TextView textSinger ;
		TextView textTime ;
	}

}
