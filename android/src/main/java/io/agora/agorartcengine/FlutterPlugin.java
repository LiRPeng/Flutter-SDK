package io.agora.agorartcengine;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.video.AgoraImage;
import io.agora.rtc.video.BeautyOptions;
import io.agora.rtc.video.VideoEncoderConfiguration;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class FlutterPlugin {
    PluginRegistry.Registrar mRegistrar;
    Handler mEventHandler = new Handler(Looper.getMainLooper());
    EventChannel.EventSink sink;

    Context getActiveContext() {
        return (mRegistrar.activity() != null) ? mRegistrar.activity() : mRegistrar.context();
    }

    VideoEncoderConfiguration.ORIENTATION_MODE orientationFromValue(int value) {
        switch (value) {
            case 0:
                return VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE;
            case 1:
                return VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_LANDSCAPE;
            case 2:
                return VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT;
            default:
                return VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE;
        }
    }

    AgoraImage createAgoraImage(Map<String, Object> options) {
        AgoraImage image = new AgoraImage();
        image.url = (String) options.get("url");
        image.height = (int) options.get("height");
        image.width = (int) options.get("width");
        image.x = (int) options.get("x");
        image.y = (int) options.get("y");
        return image;
    }

    HashMap<String, Object> mapFromRect(Rect rect) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("x", rect.left);
        map.put("y", rect.top);
        map.put("width", rect.width());
        map.put("height", rect.height());
        return map;
    }

    HashMap<String, Object> mapFromLocalVideoStats(IRtcEngineEventHandler.LocalVideoStats stats) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sentBitrate", stats.sentBitrate);
        map.put("sentFrameRate", stats.sentFrameRate);
        map.put("encoderOutputFrameRate", stats.encoderOutputFrameRate);
        map.put("rendererOutputFrameRate", stats.rendererOutputFrameRate);
        map.put("sentTargetBitrate", stats.targetBitrate);
        map.put("sentTargetFrameRate", stats.targetFrameRate);
        map.put("qualityAdaptIndication", stats.targetBitrate);
        map.put("encodedBitrate", stats.targetBitrate);
        map.put("encodedFrameWidth", stats.targetBitrate);
        map.put("encodedFrameHeight", stats.encodedFrameHeight);
        map.put("encodedFrameCount", stats.encodedFrameCount);
        map.put("codecType", stats.codecType);
        return map;
    }

    HashMap<String, Object> mapFromLocalAudioStats(IRtcEngineEventHandler.LocalAudioStats stats) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("numChannels", stats.numChannels);
        map.put("sentSampleRate", stats.sentSampleRate);
        map.put("sentBitrate", stats.sentBitrate);
        return map;
    }


    HashMap<String, Object> mapFromRemoteAudioStats(IRtcEngineEventHandler.RemoteAudioStats stats) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", stats.uid);
        map.put("quality", stats.quality);
        map.put("networkTransportDelay", stats.networkTransportDelay);
        map.put("jitterBufferDelay", stats.jitterBufferDelay);
        map.put("audioLossRate", stats.audioLossRate);
        map.put("numChannels", stats.numChannels);
        map.put("receivedSampleRate", stats.receivedSampleRate);
        map.put("receivedBitrate", stats.receivedBitrate);
        map.put("totalFrozenTime", stats.totalFrozenTime);
        map.put("frozenRate", stats.frozenRate);
        return map;
    }

    ArrayList<HashMap<String, Object>> arrayFromSpeakers(IRtcEngineEventHandler.AudioVolumeInfo[] speakers) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();

        for (IRtcEngineEventHandler.AudioVolumeInfo info : speakers) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("uid", info.uid);
            map.put("volume", info.volume);
            map.put("vad", info.vad);
            map.put("channelId", info.channelId);
            list.add(map);
        }

        return list;
    }

    HashMap<String, Object> mapFromRemoteVideoStats(IRtcEngineEventHandler.RemoteVideoStats stats) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", stats.uid);
        map.put("width", stats.width);
        map.put("height", stats.height);
        map.put("receivedBitrate", stats.receivedBitrate);
        map.put("decoderOutputFrameRate", stats.decoderOutputFrameRate);
        map.put("rendererOutputFrameRate", stats.rendererOutputFrameRate);
        map.put("packetLossRate", stats.packetLossRate);
        map.put("rxStreamType", stats.rxStreamType);
        map.put("totalFrozenTime", stats.totalFrozenTime);
        map.put("frozenRate", stats.frozenRate);
        return map;
    }

    HashMap<String, Object> mapFromStats(IRtcEngineEventHandler.RtcStats stats) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("totalDuration", stats.totalDuration);
        map.put("txBytes", stats.txBytes);
        map.put("rxBytes", stats.rxBytes);
        map.put("txAudioBytes", stats.txAudioBytes);
        map.put("txVideoBytes", stats.txVideoBytes);
        map.put("rxAudioBytes", stats.rxAudioBytes);
        map.put("rxVideoBytes", stats.rxVideoBytes);
        map.put("txKBitrate", stats.txKBitRate);
        map.put("rxKBitrate", stats.rxKBitRate);
        map.put("txAudioKBitrate", stats.txAudioKBitRate);
        map.put("rxAudioKBitrate", stats.rxAudioKBitRate);
        map.put("txVideoKBitrate", stats.txVideoKBitRate);
        map.put("rxVideoKBitrate", stats.rxVideoKBitRate);
        map.put("lastmileDelay", stats.lastmileDelay);
        map.put("txPacketLossRate", stats.txPacketLossRate);
        map.put("rxPacketLossRate", stats.rxPacketLossRate);
        map.put("users", stats.users);
        map.put("cpuAppUsage", stats.cpuAppUsage);
        map.put("cpuTotalUsage", stats.cpuTotalUsage);
        map.put("gatewayRtt", stats.gatewayRtt);
        map.put("memoryAppUsageRatio", stats.memoryAppUsageRatio);
        map.put("memoryTotalUsageRatio", stats.memoryTotalUsageRatio);
        map.put("memoryAppUsageInKbytes", stats.memoryAppUsageInKbytes);
        return map;
    }


    BeautyOptions beautyOptionsFromMap(HashMap<String, Object> map) {
        BeautyOptions options = new BeautyOptions();
        options.lighteningContrastLevel =
                ((Double) (map.get("lighteningContrastLevel"))).intValue();
        options.lighteningLevel = ((Double) (map.get("lighteningLevel"))).floatValue();
        options.smoothnessLevel = ((Double) (map.get("smoothnessLevel"))).floatValue();
        options.rednessLevel = ((Double) (map.get("rednessLevel"))).floatValue();
        return options;
    }

    VideoEncoderConfiguration videoEncoderConfigurationFromMap(HashMap<String, Object> map) {
        int width = (int) (map.get("width"));
        int height = (int) (map.get("height"));
        int frameRate = (int) (map.get("frameRate"));
        int bitrate = (int) (map.get("bitrate"));
        int minBitrate = (int) (map.get("minBitrate"));
        int orientationMode = (int) (map.get("orientationMode"));
        int mirrorMode = (int) (map.get("mirrorMode"));

        VideoEncoderConfiguration configuration = new VideoEncoderConfiguration();
        configuration.dimensions = new VideoEncoderConfiguration.VideoDimensions(width, height);
        configuration.frameRate = frameRate;
        configuration.bitrate = bitrate;
        configuration.minBitrate = minBitrate;
        configuration.orientationMode = orientationFromValue(orientationMode);
        configuration.mirrorMode = mirrorMode;

        return configuration;
    }

    void sendEvent(final String eventName, final HashMap map) {
        map.put("event", eventName);
        mEventHandler.post(new Runnable() {
            @Override
            public void run() {
                if (sink != null) {
                    sink.success(map);
                }
            }
        });
    }

    static class MethodResultWrapper implements MethodChannel.Result {
        private MethodChannel.Result mResult;
        private Handler mHandler;

        MethodResultWrapper(MethodChannel.Result result, Handler handler) {
            this.mResult = result;
            this.mHandler = handler;
        }

        @Override
        public void success(final Object result) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResult.success(result);
                }
            });
        }

        @Override
        public void error(final String errorCode, final String errorMessage,
                          final Object errorDetails) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResult.error(errorCode, errorMessage, errorDetails);
                }
            });
        }

        @Override
        public void notImplemented() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResult.notImplemented();
                }
            });
        }
    }

}
