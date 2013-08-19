package com.xuan.lx.xplayer.activity;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import com.xuan.lx.xplayer.R;
import com.xuan.lx.xplayer.util.LrcProcess;
import com.xuan.lx.xplayer.util.LrcView;
import com.xuan.lx.xplayer.util.Music;
import com.xuan.lx.xplayer.util.MusicData;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MusicService extends Service implements Runnable {
	private MediaPlayer player;
	private List<Music> lists;
	public static volatile int currentId = -1; // 当前正在播放的id
	public static volatile Boolean isRun = true;
	public LrcProcess mLrcProcess;
	public LrcView mLrcView;
	public static int playing_id = 0;
	public static Boolean playing = false;//是否还在播放
	static Random random = new Random(10);
	public volatile static Boolean isPlay = false;
	public volatile static Boolean ringBeforState = false;
	public static int id = 1;
	public static Boolean replaying = false;//重新播放
	public static int playWay = Constants.LOOP;//列表循环;单曲循环 ;随机播放
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		lists = MusicData.getMusicData(getApplicationContext());
		
		random = new Random(lists.size()-1);
		// 注册更新进度条广播事件
		SeekBarBroadcastReceiver receiver = new SeekBarBroadcastReceiver();
		IntentFilter filter = new IntentFilter("com.xuan.lx.xplayer.seekBar");
		this.registerReceiver(receiver, filter);
		//启动歌曲进度线程
		new Thread(this).start();
		super.onCreate();
		//电话状态监听
		TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telManager.listen(mobliePhoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	
	public PhoneStateListener mobliePhoneStateListener = new  PhoneStateListener() {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE: // 无任何状态时 
				if( MusicService.ringBeforState ){
					MusicActivity.imageBtnPlay.setImageResource(R.drawable.pause1);
					MusicService.isPlay = true;
					Intent intent = new Intent();
					// 设置service 参数
					intent.putExtra("play", Constants.PLAYING);
					intent.putExtra("id", MusicService.currentId);
					onStart(intent,0);
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK: // 接起电话时 
				break;
			case TelephonyManager.CALL_STATE_RINGING: // 电话进来时 
				if(MusicService.isPlay){
					MusicService.ringBeforState = true;
					MusicActivity.imageBtnPlay.setImageResource(R.drawable.play1);
					MusicService.isPlay = false;
					Intent intent = new Intent();
					// 设置service 参数
					intent.putExtra("play", Constants.PUASE);
					intent.putExtra("id", MusicService.currentId);
					onStart(intent,0);
				}				
				break;
			default:
				break;
			}

		}

	};

	/**
	 * 拖动进度条更新到此时播放的位置 broadcastReceiver
	 * @author xuan.lx
	 *
	 */
	private class SeekBarBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int seekBarPosition = intent.getIntExtra("seekBarPosition", 0);
			player.seekTo(seekBarPosition * player.getDuration() / 100);
			if(null == player)
				player.start();
		}

	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while ( true ) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			if (null != player) {
				if( isRun ){
					try {
						//获取当前进度
						int position = player.getCurrentPosition();
						//获取总时间
						int total = player.getDuration();
						Intent intent = new Intent("com.xuan.lx.xplayer.progress");
						intent.putExtra("position", position);
						intent.putExtra("total", total);
						//发送广播消息
						sendBroadcast(intent);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	 
	/**
	 * @author xuan.lx
	 * 重写onStart 启动播放
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		int play = intent.getIntExtra("play", Constants.PLAYING);
		currentId = intent.getIntExtra("id", 1);
		isRun = true;
		switch(play){
		case Constants.PLAYING:
			if (player != null) {
				player.start();
			} else {
				playMusic(currentId);
			}
			break;
		case Constants.PUASE:
			if (null != player) {
				player.pause();
			} 
			break;
		case Constants.STOP:
			if (null != player) {
				isRun = false;
				player.stop();
			} 
			break;
		case Constants.NEXT:
			playMusic(currentId);
			break;
		case Constants.PREVIOUS:
			playMusic(currentId); 
			break;
		case Constants.REPLAYING:
			playMusic(currentId);
			break;
			
		}
		BaseActivity.adapter.setSelectItem(MusicService.currentId);
		BaseActivity.adapter.notifyDataSetInvalidated();
	}
	
	private void playMusic(int id) {
		//如果存在则释放,重新开始播放
		if (null != player) {
			try {
				isRun = false;
				Thread.sleep(200);
				player.reset();
				player.release();
				player = null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		if (id >= lists.size()) {
			currentId = lists.size() - 1;
		} else if (id < 0) {
			currentId = 0;
		}
		Music m = lists.get(currentId);
		String url = m.getUrl();
		player = new MediaPlayer();
		player.reset();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			player.setDataSource(url);
			player.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		player.start();
		isRun = true;
		
		//当一首播放完后
		player.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				Intent intent = new Intent("com.xuan.lx.xplayer.completion");
				switch(MusicService.playWay){
					case Constants.LOOP:
						currentId ++;
						if (currentId >= lists.size()) {
							currentId = 0;
						} else if ( currentId < 0 ) {
							currentId = lists.size() -1;
						}
						playMusic(currentId);
						sendBroadcast(intent);
						break;
					case Constants.CIRCLE:
						playMusic(currentId);
						sendBroadcast(intent);
						break;
					case Constants.RANDOM:
						currentId = random.nextInt(lists.size());
						playMusic(currentId);
						sendBroadcast(intent);
						break;
				}
 			}
		});
		//当出现错误的时候
		player.setOnErrorListener(new OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				if (null != player) {
					player.release();
					player = null;
				}
				Music m = lists.get(currentId);
				String url = m.getUrl();
				player = new MediaPlayer();
				player.reset();
				player.setAudioStreamType(AudioManager.STREAM_MUSIC);
				try {
					player.setDataSource(url);
					player.prepare();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				player.start();
				return false;
			}
		});
	}
	
	/**
	 * 当服务销毁时
	 * @author xuan.lx
	 */
	public void onDestroy() {
		isRun = false;
		if (null != player) {
			player.reset();
			player.release();
			player = null;
		}
    }
	
}
