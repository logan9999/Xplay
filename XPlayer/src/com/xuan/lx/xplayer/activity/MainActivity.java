package com.xuan.lx.xplayer.activity;

import com.xuan.lx.xplayer.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity{
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	        Resources res = getResources(); 
	        TabHost tabHost = getTabHost(); 
	        Intent intent = new Intent().setClass(this, ListActivity.class);
	        TabHost.TabSpec spec = tabHost.newTabSpec("音乐").setIndicator("音乐",
	                          res.getDrawable(R.drawable.item))
	                      .setContent(intent);
	        tabHost.addTab(spec);
	        
	        intent = new Intent().setClass(this, ArtistsActivity.class);
	        spec = tabHost.newTabSpec("艺术家").setIndicator("艺术家",
	                          res.getDrawable(R.drawable.artist))
	                      .setContent(intent);
	        tabHost.addTab(spec);

	        intent = new Intent().setClass(this, AlbumsActivity.class);
	        spec = tabHost.newTabSpec("唱片").setIndicator("唱片",
	                          res.getDrawable(R.drawable.album))
	                      .setContent(intent);
	        tabHost.addTab(spec);
	        intent = new Intent().setClass(this, LastedActivity.class);
	        spec = tabHost.newTabSpec("最近播放").setIndicator("最近播放",
	                          res.getDrawable(R.drawable.album))
	                      .setContent(intent);
	        tabHost.addTab(spec);
	        
	        tabHost.setCurrentTab(0);

	    }

}
