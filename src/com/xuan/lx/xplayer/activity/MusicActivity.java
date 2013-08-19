package com.xuan.lx.xplayer.activity;

import java.util.List;
import com.xuan.lx.xplayer.R;
import com.xuan.lx.xplayer.util.LrcView;
import com.xuan.lx.xplayer.util.Music;
import com.xuan.lx.xplayer.util.MusicData;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MusicActivity extends Activity implements SensorEventListener {
	private TextView textName;// 歌名
	private TextView textSinger;// 歌手
	private TextView textStartTime;// 开始时间
	private TextView textEndTime;// 结束时间
	private ImageButton imageBtnPrevious;
	public static ImageButton imageBtnPlay;
	private ImageButton imageBtnNext;
	private ImageButton imageBtnLoop;
	public static LrcView lrc_view;
	private SeekBar music_seekBar; // 播放进度
	private AudioManager audioManager;// 音量管理者
	private int maxVolume;// 最大音量
	private int currentVolume;// 当前音量
	private SeekBar seekBarVolume;
	private List<Music> lists;
	private CompletionListner completionListner;
	private ProgressBroadCastReceiver progressReceiver;
	private int btnClicks = 0;
	private SensorManager mManager = null;
	private Sensor mSensor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("MusicActivity", "onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.music);
		textName = (TextView) this.findViewById(R.id.music_name);
		textSinger = (TextView) this.findViewById(R.id.music_singer);
		textStartTime = (TextView) this.findViewById(R.id.music_start_time);
		textEndTime = (TextView) this.findViewById(R.id.music_end_time);
		music_seekBar = (SeekBar) this.findViewById(R.id.music_seekBar);
		imageBtnPrevious = (ImageButton) this.findViewById(R.id.music_previous);
		imageBtnPlay = (ImageButton) this.findViewById(R.id.music_play);
		imageBtnNext = (ImageButton) this.findViewById(R.id.music_next);
		imageBtnLoop = (ImageButton) this.findViewById(R.id.music_loop);
		// 音量bar
		seekBarVolume = (SeekBar) this.findViewById(R.id.music_volume);
		lrc_view = (LrcView) findViewById(R.id.LyricShow);
		// 设置点击监听
		imageBtnPlay.setOnClickListener(new PlayListener());
		imageBtnNext.setOnClickListener(new PlayListener());
		imageBtnPrevious.setOnClickListener(new PlayListener());
		imageBtnLoop.setOnClickListener(new PlayListener());
		// 获取歌曲列表
		lists = MusicData.getMusicData(this);
		// 获取音量服务
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获得最大音量
		currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 获得当前音量

		seekBarVolume.setMax(maxVolume);
		seekBarVolume.setProgress(currentVolume);
		seekBarVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
						progress, AudioManager.FLAG_ALLOW_RINGER_MODES);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

		});

		// 监听拖动进度条更新
		music_seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				seekBar.setProgress(seekBar.getProgress());
				Intent intent = new Intent("com.xuan.lx.xplayer.seekBar");
				intent.putExtra("seekBarPosition", seekBar.getProgress());
				sendBroadcast(intent);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});

		// 注册完成的广播事件
		completionListner = new CompletionListner();
		IntentFilter filter = new IntentFilter("com.xuan.lx.xplayer.completion");
		this.registerReceiver(completionListner, filter);
		
		
		mManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		Constants.isListenerSensor = settings.getBoolean("fling", true);
		
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		Log.e("MusicActivity", "onResume");
		if(Constants.isListenerSensor){
			mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
		}else{
			mManager.unregisterListener(this);
		}
		
	}
	// 重力感应 甩歌代码
	private static final int SHAKE_THRESHOLD = 1300;
	private long lastUpdate = 0;
	private double last_x = 0;
	private double last_y = 4.50;
	private double last_z = 9.50;
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	// 甩屏时切换下一首
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && Constants.isListenerSensor) {
			long curTime = System.currentTimeMillis();
			// 每300毫秒检测一次
			if ((curTime - lastUpdate) > 200) {
				long diffTime = (curTime - lastUpdate);
				lastUpdate = curTime;
				double x = event.values[SensorManager.DATA_X];
				double y = event.values[SensorManager.DATA_Y];
				double z = event.values[SensorManager.DATA_Z];
				float speed = (float) (Math.abs(x + y + z - last_x - last_y
						- last_z)
						/ diffTime * 10000);
				if (speed > SHAKE_THRESHOLD) {
					// 下一首
					synchronized(this){
						int id = MusicService.currentId + 1;
						if (id >= lists.size()) {
							id = 0;
						} else if (id < 0) {
							id = lists.size() - 1;
						}
						Music m = lists.get(id);
						textName.setText(m.getTitle());
						textSinger.setText(m.getSinger());
						textEndTime.setText(MusicData.toTime((int) m.getTime()));
						// 当前正在播放
						if (MusicService.isPlay) {
							imageBtnPlay.setImageResource(R.drawable.pause1);
							setService(id, Constants.NEXT, true);
						} else {// 当前暂停
							imageBtnPlay.setImageResource(R.drawable.play1);
							MusicService.currentId = id;
							setService(id, Constants.STOP, false);
						}
					}
				}
				last_x = x;
				last_y = y;
				last_z = z;
			}
		}

	}

	private class PlayListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == imageBtnPrevious) {
				// 上一首
				int id = MusicService.currentId - 1;
				if (id >= lists.size()) {
					id = 0;
				} else if (id < 0) {
					id = lists.size() - 1;
				}
				Music m = lists.get(id);
				textName.setText(m.getTitle());
				textSinger.setText(m.getSinger());
				textEndTime.setText(MusicData.toTime((int) m.getTime()));
				imageBtnPlay.setImageResource(R.drawable.pause1);
				setService(id, Constants.REPLAYING, true);
			} else if (v == imageBtnPlay) {
				int id = MusicService.currentId;
				// 当前正在播放，则下一个状态为暂停
				if (MusicService.isPlay) {
					imageBtnPlay.setImageResource(R.drawable.play1);
					setService(id, Constants.PUASE, false);
				} else {// 当前暂停，则下一个状态为播放
					imageBtnPlay.setImageResource(R.drawable.pause1);
					setService(id, Constants.PLAYING, true);
				}
			} else if (v == imageBtnNext) {
				// 下一首
				int id = MusicService.currentId + 1;
				if (id >= lists.size()) {
					id = 0;
				} else if (id < 0) {
					id = lists.size() - 1;
				}
				Music m = lists.get(id);
				textName.setText(m.getTitle());
				textSinger.setText(m.getSinger());
				textEndTime.setText(MusicData.toTime((int) m.getTime()));
				imageBtnPlay.setImageResource(R.drawable.pause1);
				setService(id, Constants.REPLAYING, true);
			} else if (v == imageBtnLoop) {
				++btnClicks;
				MusicService.playWay = btnClicks % 3;
				setPlayWay();
			}

		}

	}

	private void setService(int id, int playStatus, Boolean isPlays) {
		MusicService.isPlay = isPlays;
		MusicService.ringBeforState = isPlays;
		Intent intent = new Intent(MusicActivity.this, MusicService.class);
		// 设置service 参数
		intent.putExtra("play", playStatus);
		intent.putExtra("id", id);
		startService(intent);
		
	}

	// 当上一首完成后设置下一首的状态显示
	private class CompletionListner extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Music m = lists.get(MusicService.currentId);
			textName.setText(m.getTitle());
			textSinger.setText(m.getSinger());
			textEndTime.setText(MusicData.toTime((int) m.getTime()));
			imageBtnPlay.setImageResource(R.drawable.pause1);
		}

	}

	/**
	 * 更新进度条 receiver
	 * 
	 * @author xuan.lx
	 * 
	 */
	public class ProgressBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int position = intent.getIntExtra("position", 0);
			int total = intent.getIntExtra("total", 0);
			int progress = position * 100 / total;
			// 更新此时播放的时间
			textStartTime.setText(MusicData.toTime(position));
			// 更新进度条的位置
			music_seekBar.setProgress(progress);
			// 重新画
			music_seekBar.invalidate();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(progressReceiver);
		this.unregisterReceiver(completionListner);
	}

	private void setPlayWay() {

		switch (MusicService.playWay) {
		// 列表循环播放
		case Constants.LOOP:
			imageBtnLoop.setBackgroundResource(R.drawable.play_loop_sel);
			break;
		// 单曲循环
		case Constants.CIRCLE:
			imageBtnLoop.setBackgroundResource(R.drawable.play_loop_spec);
			break;
		// 随机播放
		case Constants.RANDOM:
			imageBtnLoop.setBackgroundResource(R.drawable.play_random_sel);
			break;
		}
	}
	int id = 1;
	@Override
	protected void onStart() {
		super.onStart();
		Log.e("MusicActivity", "onStart");
		// 注册初始化进度条广播事件
		progressReceiver = new ProgressBroadCastReceiver();
		IntentFilter filter = new IntentFilter("com.xuan.lx.xplayer.progress");
		this.registerReceiver(progressReceiver, filter);
		int id = this.getIntent().getIntExtra("id", 1);
		Music m = lists.get(id);
		textName.setText(m.getTitle());
		textSinger.setText(m.getSinger());
		textEndTime.setText(MusicData.toTime((int) m.getTime()));
		setPlayWay();// 设置播放方式
		// 点击不是同一个id，则播放
		if (id != MusicService.currentId) {
			imageBtnPlay.setImageResource(R.drawable.pause1);
			Intent intent = new Intent(MusicActivity.this, MusicService.class);
			intent.putExtra("play", Constants.REPLAYING);
			intent.putExtra("id", id);
			startService(intent);
			MusicService.isPlay = true;
			MusicService.currentId = id;
		} else {
			if (MusicService.isPlay) {
				imageBtnPlay.setImageResource(R.drawable.pause1);
			}
		}

	}
	
	@Override
	protected void onRestart(){
		super.onRestart();
		Log.e("MusicActivity", "onRestart");
		id = MusicService.currentId;
	}

}
