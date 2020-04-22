package io.agora.agorartcengine;


import android.content.Context;

import java.util.HashMap;
import java.util.Set;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcChannel;
import io.agora.rtc.RtcEngine;

public class AgoraRtc {
    private static AgoraRtc mInstance;
    private RtcEngine mRtcEngine;
    private HashMap<String, RtcChannel> mRtcChannels = new HashMap<>();

    public static AgoraRtc newInstance() {
        if (mInstance == null) {
            synchronized (AgoraRtc.class) {
                if (mInstance == null) {
                    mInstance = new AgoraRtc();
                }
            }
        }
        return mInstance;
    }

    private AgoraRtc() {
    }

    void create(Context context, String appId, IRtcEngineEventHandler mRtcEventHandler) {
        try {
            mRtcEngine = RtcEngine.create(context, appId, mRtcEventHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void destroy() {
        Set<String> keys = mRtcChannels.keySet();
        for (String key : keys) {
            RtcChannel rtcChannel = mRtcChannels.get(key);
            if (rtcChannel != null) {
                rtcChannel.destroy();
            }
        }
        RtcEngine.destroy();
    }

    RtcEngine rtcEngine() {
        return mRtcEngine;
    }

    RtcChannel getRtcChannel(String channelId) {
        Set<String> keys = mRtcChannels.keySet();
        for (String key : keys) {
            return mRtcChannels.get(key);
        }
        return null;
    }

    void addRtcChannel(String chennelId, RtcChannel rtcChannel) {
        mRtcChannels.put(chennelId, rtcChannel);
    }

    void removeRtcChannel(String channel){
        RtcChannel rtcChannel=mRtcChannels.get(channel);
        if (rtcChannel!=null){
            rtcChannel.destroy();
        }
        mRtcChannels.remove(channel);
    }

    void destroyRtcChannel(){
        Set<String> keys = mRtcChannels.keySet();
        for (String key : keys) {
            RtcChannel rtcChannel = mRtcChannels.get(key);
            if (rtcChannel != null) {
                rtcChannel.destroy();
            }
        }
        mRtcChannels.clear();
    }

    HashMap<String, RtcChannel> getRtcChannels() {
        return mRtcChannels;
    }

}
