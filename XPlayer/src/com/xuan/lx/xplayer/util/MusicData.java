package com.xuan.lx.xplayer.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

public class MusicData {
	static String TAG = "MusicData";

	/**
	 * 搜索媒体文件
	 * 
	 * @param context
	 * @return
	 */
	public static List<Music> getMusicData(Context context) {
		List<Music> musicList = new ArrayList<Music>();
		ContentResolver cr = context.getContentResolver();
		if (cr != null) {
			// 搜索媒体文件
			Cursor cursor = cr.query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
					null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
			if (null == cursor) {
				return null;
			}
			if (cursor.moveToFirst()) {
				do {
					Music m = new Music();
					String title = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.TITLE));
					String singer = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.ARTIST));

					/*try {
						title = new String(title.getBytes("gb2312"), "gb2312");
					} catch (UnsupportedEncodingException e) {
						Log.e("MusicData UnsupportedEncodingException",
								"title:" + title + " singer:" + singer);
						e.printStackTrace();
					}
*/
					title = changeStringEncode(title);
					singer = changeStringEncode(singer);
					Log.i("MusicData", "title:" + title + " singer:" + singer);
					if ("<unknown>".equals(singer)) {
						singer = "未知";
					}
					String album = changeStringEncode(cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
					long size = cursor.getLong(cursor
							.getColumnIndex(MediaStore.Audio.Media.SIZE));
					long time = cursor.getLong(cursor
							.getColumnIndex(MediaStore.Audio.Media.DURATION));
					String url = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.DATA));
					String name = cursor
							.getString(cursor
									.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
					String sbr = name.substring(name.length() - 3,
							name.length());
					if (sbr.equals("mp3")) {
						m.setTitle(title);
						m.setSinger(singer);
						m.setAlbum(album);
						m.setSize(size);
						m.setTime(time);
						m.setUrl(url);
						m.setName(name);
						musicList.add(m);
					}
				} while (cursor.moveToNext());
			}
		}
		return musicList;

	}

	/**
	 * 将long时间格式化
	 * 
	 * @param time
	 * @return
	 */
	public static String toTime(int time) {

		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		return String.format("%02d:%02d", minute, second);
	}

	public static String changeStringEncode(String content) {
		if(content == null || content.equals("")){
			return null;
		}
		if (java.nio.charset.Charset.forName("GB2312").newEncoder()
				.canEncode(content)) {
		} else if (java.nio.charset.Charset.forName("ISO-8859-1").newEncoder()
				.canEncode(content)) {
			try {
				content = new String(content.getBytes("ISO-8859-1"), "GBK");
			}

			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} else if (java.nio.charset.Charset.forName("GBK").newEncoder()
				.canEncode(content)) {
		} else if (java.nio.charset.Charset.forName("UTF-8").newEncoder()
				.canEncode(content)) {
			try {
				content = new String(content.getBytes("UTF-8"), "GBK");
			}

			catch (UnsupportedEncodingException e) {
				e.printStackTrace();

			}
		} else if (java.nio.charset.Charset.forName("UTF-16").newEncoder()
				.canEncode(content)) {
		}
		return content;

	}

}
