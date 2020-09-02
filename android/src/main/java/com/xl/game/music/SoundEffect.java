package com.xl.game.music;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;
import java.util.HashMap;
//import com.xl.game.tool.Logcat;


public class SoundEffect {


    SoundPool sp; // 声明SoundPool的引用
    HashMap<Integer, Integer> hm; // 声明一个HashMap来存放声音文件
    int currStreamId;// 当前正播放的streamId，每播放一次


    // 重写onCreate方法

    //super.onCreate(savedInstanceState);
    //setContentView(R.layout.main); // 设置layout
    //initSoundPool(); // 初始化声音池的方法
    //Button b1 = (Button) this.findViewById(R.id.Button01); // 获取播放按钮
    //1.setOnClickListener // 为播放按钮添加监听器

    //playSound(1, 0); // 播放1号声音资源，且播放一次


    // 初始化声音池的方法
    public void initSoundPool() {
        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0); // 创建SoundPool对象
        hm = new HashMap<Integer, Integer>(); // 创建HashMap对象
        //hm.put(1, sp.load(this, R.raw.musictest, 1)); // 加载声音文件musictest并且设置为1号声音放入hm中
    }

    public void initSoundPool(int tick) {
        sp = new SoundPool(tick, AudioManager.STREAM_MUSIC, 0); // 创建SoundPool对象
        hm = new HashMap<Integer, Integer>(); // 创建HashMap对象
        //hm.put(1, sp.load(this, R.raw.musictest, 1)); // 加载声音文件musictest并且设置为1号声音放入hm中
    }

    //设置声音的id，为播放做准备
    public void setSound(Context context, int r, int id) {
        hm.put(id, sp.load(context, r, 1));
    }

    public void setSound(Context context, String assetfile, int id) {
        AssetManager asset = context.getResources().getAssets();
        AssetFileDescriptor afd;
        try {
            afd = asset.openFd(assetfile);
            hm.put(id, sp.load(afd, 0));

        } catch (IOException e) {
            e.printStackTrace();
            //Logcat.e("打开音频文件异常");
        }

        ;

    }

    // 播放声音的方法，返回播放id
    public int playSound(Context context, int id, int loop) { // 获取AudioManager引用
        AudioManager am = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);
        // 获取当前音量
        float streamVolumeCurrent = am
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        // 获取系统最大音量
        float streamVolumeMax = am
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 计算得到播放音量
        float volume = streamVolumeCurrent / streamVolumeMax;
        // 调用SoundPool的play方法来播放声音文件
        return currStreamId = sp.play(hm.get(id), volume, volume, 1, loop, 1.0f);
    }

    //停止播放音乐，id为playSound的返回值
    public void stopSound(int id) {
        sp.stop(id);
    }


}
