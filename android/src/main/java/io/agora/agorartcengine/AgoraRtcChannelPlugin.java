package io.agora.agorartcengine;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.agora.rtc.IMetadataObserver;
import io.agora.rtc.IRtcChannelEventHandler;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcChannel;
import io.agora.rtc.live.LiveTranscoding;
import io.agora.rtc.models.ChannelMediaOptions;
import io.agora.rtc.video.ChannelMediaInfo;
import io.agora.rtc.video.ChannelMediaRelayConfiguration;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class AgoraRtcChannelPlugin extends FlutterPlugin implements MethodChannel.MethodCallHandler, EventChannel.StreamHandler {


    public AgoraRtcChannelPlugin(PluginRegistry.Registrar registrar) {
        this.mRegistrar = registrar;
        this.sink = null;
    }


    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        this.sink = events;
    }

    @Override
    public void onCancel(Object arguments) {
        this.sink = null;
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        Context context=getActiveContext();
        switch (call.method){
            case "setRtcChannelEventHandler": {
                String channelId = call.argument("channelId");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    rtcChannel.setRtcChannelEventHandler(mRctChannelEventHandler);
                }
                result.success(null);
            }
            break;
            case "destroy": {
                AgoraRtc.newInstance().destroyRtcChannel();
                result.success(null);
            }
            break;
            case "destroyChannelById": {
                String channelId = call.argument("channelId");
                AgoraRtc.newInstance().removeRtcChannel(channelId);
                result.success(null);
            }
            break;
            case "getConnectionState": {
                String channelId = call.argument("channelId");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                result.success(rtcChannel.getConnectionState());
            }
            break;
            case "joinChannel": {
                String channelId = call.argument("channelId");
                String token = call.argument("token");
                String optionalInfo = call.argument("optionalInfo");
                int optionalUid = call.argument("optionalUid");
                boolean autoSubscribeAudio = call.argument("autoSubscribeAudio");
                boolean autoSubscribeVideo = call.argument("autoSubscribeVideo");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                ChannelMediaOptions mChannelMediaOptions = new ChannelMediaOptions();
                mChannelMediaOptions.autoSubscribeAudio = autoSubscribeAudio;
                mChannelMediaOptions.autoSubscribeVideo = autoSubscribeVideo;
                if (rtcChannel != null) {
                    result.success(rtcChannel.joinChannel(token, optionalInfo, optionalUid, mChannelMediaOptions));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "joinWithUserAccount": {
                String channelId = call.argument("channelId");
                String token = call.argument("token");
                String userAccount = call.argument("userAccount");
                boolean autoSubscribeAudio = call.argument("autoSubscribeAudio");
                boolean autoSubscribeVideo = call.argument("autoSubscribeVideo");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                ChannelMediaOptions mChannelMediaOptions = new ChannelMediaOptions();
                mChannelMediaOptions.autoSubscribeAudio = autoSubscribeAudio;
                mChannelMediaOptions.autoSubscribeVideo = autoSubscribeVideo;
                if (rtcChannel != null) {
                    result.success(rtcChannel.joinChannelWithUserAccount(token, userAccount, mChannelMediaOptions));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "leaveChannel": {
                String channelId = call.argument("channelId");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.leaveChannel());
                } else {
                    result.success(-1);
                }
            }
            break;
            case "publish": {
                String channelId = call.argument("channelId");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.publish());
                } else {
                    result.success(-1);
                }
            }
            break;
            case "unPublish": {
                String channelId = call.argument("channelId");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.unpublish());
                } else {
                    result.success(-1);
                }
            }
            break;
            case "renewToken": {
                String channelId = call.argument("channelId");
                String token = call.argument("token");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.renewToken(token));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "registerMediaMetadataObserver ": {
                String channelId = call.argument("channelId");
                int type = call.argument("type");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.registerMediaMetadataObserver(mIMetadataObserver, type));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "setClientRole": {
                String channelId = call.argument("channelId");
                int role = call.argument("role");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.setClientRole(role));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "setRemoteUserPriority": {
                String channelId = call.argument("channelId");
                int uid = call.argument("uid");
                int userPriority = call.argument("userPriority");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.setRemoteUserPriority(uid, userPriority));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "setRemoteVoicePosition": {
                String channelId = call.argument("channelId");
                int uid = call.argument("uid");
                double pan = call.argument("pan");
                int gain = call.argument("gain");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.setRemoteVoicePosition(uid, pan, gain));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "setRemoteRenderMode": {
                String channelId = call.argument("channelId");
                int uid = call.argument("uid");
                int mode = call.argument("mode");
                int mirrorMode = call.argument("mirrorMode");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.setRemoteRenderMode(uid, mode, mirrorMode));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "setDefaultMuteAllRemoteAudioStreams": {
                String channelId = call.argument("channelId");
                boolean muted = call.argument("muted");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.setDefaultMuteAllRemoteAudioStreams(muted));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "setDefaultMuteAllRemoteVideoStreams": {
                String channelId = call.argument("channelId");
                boolean muted = call.argument("muted");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.setDefaultMuteAllRemoteVideoStreams(muted));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "muteAllRemoteAudioStreams": {
                String channelId = call.argument("channelId");
                boolean muted = call.argument("muted");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.muteAllRemoteAudioStreams(muted));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "muteAllRemoteVideoStreams": {
                String channelId = call.argument("channelId");
                boolean muted = call.argument("muted");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.muteAllRemoteVideoStreams(muted));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "adjustUserPlaybackSignalVolume": {
                String channelId = call.argument("channelId");
                int uid = call.argument("uid");
                int volume = call.argument("volume");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.adjustUserPlaybackSignalVolume(uid, volume));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "muteRemoteAudioStream": {
                String channelId = call.argument("channelId");
                int uid = call.argument("uid");
                boolean muted = call.argument("muted");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.muteRemoteAudioStream(uid, muted));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "muteRemoteVideoStream": {
                String channelId = call.argument("channelId");
                int uid = call.argument("uid");
                boolean muted = call.argument("muted");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.muteRemoteVideoStream(uid, muted));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "setRemoteVideoStreamType": {
                String channelId = call.argument("channelId");
                int uid = call.argument("uid");
                int streamType = call.argument("streamType");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.setRemoteVideoStreamType(uid, streamType));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "setRemoteDefaultVideoStreamType": {
                String channelId = call.argument("channelId");
                int streamType = call.argument("streamType");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.setRemoteDefaultVideoStreamType(streamType));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "createDataStream": {
                String channelId = call.argument("channelId");
                boolean reliable = call.argument("reliable");
                boolean ordered = call.argument("ordered");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.createDataStream(reliable, ordered));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "addPublishStreamUrl": {
                String channelId = call.argument("channelId");
                String url = call.argument("url");
                boolean transcodingEnabled = call.argument("transcodingEnabled");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.addPublishStreamUrl(url, transcodingEnabled));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "removePublishStreamUrl": {
                String channelId = call.argument("channelId");
                String url = call.argument("url");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.removePublishStreamUrl(url));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "setLiveTranscoding": {
                LiveTranscoding transcoding = new LiveTranscoding();
                Map params = call.argument("transcoding");
                if (params.get("width") != null && params.get("height") != null) {
                    transcoding.width = (int) params.get("width");
                    transcoding.height = (int) params.get("height");
                }
                if (params.get("videoBitrate") != null) {
                    transcoding.videoBitrate = (int) params.get("videoBitrate");
                }
                if (params.get("videoFramerate") != null) {
                    transcoding.videoFramerate = (int) params.get("videoFramerate");
                }
                if (params.get("videoGop") != null) {
                    transcoding.videoGop = (int) params.get("videoGop");
                }
                if (params.get("videoCodecProfile") != null) {
                    transcoding.videoCodecProfile = LiveTranscoding.VideoCodecProfileType.values()[(int) params.get("videoCodecProfile")];
                }
                if (params.get("audioCodecProfile") != null) {
                    transcoding.audioCodecProfile = LiveTranscoding.AudioCodecProfileType.values()[(int) params.get("audioCodecProfile")];
                }
                if (params.get("audioSampleRate") != null) {
                    transcoding.audioSampleRate = LiveTranscoding.AudioSampleRateType.values()[(int) params.get("audioSampleRate")];
                }
                if (params.get("watermark") != null) {
                    Map image = (Map) params.get("watermark");
                    Map watermarkMap = new HashMap();
                    watermarkMap.put("url", image.get("url"));
                    watermarkMap.put("x", image.get("x"));
                    watermarkMap.put("y", image.get("y"));
                    watermarkMap.put("width", image.get("width"));
                    watermarkMap.put("height", image.get("height"));
                    transcoding.watermark = this.createAgoraImage(watermarkMap);
                }
                if (params.get("backgroundImage") != null) {
                    Map image = (Map) params.get("backgroundImage");
                    Map backgroundImageMap = new HashMap();
                    backgroundImageMap.put("url", image.get("url"));
                    backgroundImageMap.put("x", image.get("x"));
                    backgroundImageMap.put("y", image.get("y"));
                    backgroundImageMap.put("width", image.get("width"));
                    backgroundImageMap.put("height", image.get("height"));
                    transcoding.backgroundImage = this.createAgoraImage(backgroundImageMap);
                }
                if (params.get("backgroundColor") != null) {
                    transcoding.setBackgroundColor((int) params.get("backgroundColor"));
                }
                if (params.get("audioBitrate") != null) {
                    transcoding.audioBitrate = (int) params.get("audioBitrate");
                }
                if (params.get("audioChannels") != null) {
                    transcoding.audioChannels = (int) params.get("audioChannels");
                }
                if (params.get("transcodingUsers") != null) {
                    ArrayList<LiveTranscoding.TranscodingUser> users = new ArrayList<LiveTranscoding.TranscodingUser>();
                    ArrayList transcodingUsers = (ArrayList) params.get("transcodingUsers");
                    for (int i = 0; i < transcodingUsers.size(); i++) {
                        Map optionUser = (Map) transcodingUsers.get(i);
                        LiveTranscoding.TranscodingUser user = new LiveTranscoding.TranscodingUser();
                        user.uid = (int) optionUser.get("uid");
                        user.x = (int) optionUser.get("x");
                        user.y = (int) optionUser.get("y");
                        user.width = (int) optionUser.get("width");
                        user.height = (int) optionUser.get("height");
                        user.zOrder = (int) optionUser.get("zOrder");
                        user.alpha = (float) optionUser.get("alpha");
                        user.audioChannel = (int) optionUser.get("audioChannel");
                        users.add(user);
                    }
                    transcoding.setUsers(users);
                }
                if (params.get("transcodingExtraInfo") != null) {
                    transcoding.userConfigExtraInfo = (String) params.get("transcodingExtraInfo");
                }
                String channelId = call.argument("channelId");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.setLiveTranscoding(transcoding));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "startChannelMediaRelay": {
                ChannelMediaRelayConfiguration config = new ChannelMediaRelayConfiguration();
                ChannelMediaInfo src = config.getSrcChannelMediaInfo();
                HashMap<String, Object> options = call.argument("config");
                if (options.get("src") != null) {
                    HashMap<String, Object> srcOption = (HashMap<String, Object>) options.get("src");
                    if (srcOption.get("token") != null) {
                        src.token = (String) srcOption.get("token");
                    }
                    if (srcOption.get("channelName") != null) {
                        src.channelName = (String) srcOption.get("channelName");
                    }
                }
                List<HashMap<String, Object>> dstMediaInfo = (List) options.get("channels");
                for (int i = 0; i < dstMediaInfo.size(); i++) {
                    HashMap<String, Object> dst = dstMediaInfo.get(i);
                    String channelName = null;
                    String token = null;
                    Integer uid = 0;
                    if (dst.get("token") != null) {
                        token = token;
                    }
                    if (dst.get("channelName") != null) {
                        channelName = (String) dst.get("channelName");
                    }
                    if (dst.get("uid") != null) {
                        uid = (int) dst.get("uid");
                    }
                    config.setDestChannelInfo(channelName, new ChannelMediaInfo(channelName, token, uid));
                }
                String channelId = call.argument("channelId");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.startChannelMediaRelay(config));
                } else {
                    result.success(-1);
                }
            }
            break;
            case "stopChannelMediaRelay": {
                String channelId = call.argument("channelId");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.stopChannelMediaRelay());
                } else {
                    result.success(-1);
                }
            }
            break;
            case "updateChannelMediaRelay": {
                ChannelMediaRelayConfiguration config = new ChannelMediaRelayConfiguration();
                ChannelMediaInfo src = config.getSrcChannelMediaInfo();
                HashMap<String, Object> options = call.argument("config");
                if (options.get("src") != null) {
                    HashMap<String, Object> srcOption = (HashMap<String, Object>) options.get("src");
                    if (srcOption.get("token") != null) {
                        src.token = (String) srcOption.get("token");
                    }
                    if (srcOption.get("channelName") != null) {
                        src.channelName = (String) srcOption.get("channelName");
                    }
                }
                List<HashMap<String, Object>> dstMediaInfo = (List) options.get("channels");
                for (int i = 0; i < dstMediaInfo.size(); i++) {
                    HashMap<String, Object> dst = dstMediaInfo.get(i);
                    String channelName = null;
                    String token = null;
                    Integer uid = 0;
                    if (dst.get("token") != null) {
                        token = token;
                    }
                    if (dst.get("channelName") != null) {
                        channelName = (String) dst.get("channelName");
                    }
                    if (dst.get("uid") != null) {
                        uid = (int) dst.get("uid");
                    }
                    config.setDestChannelInfo(channelName, new ChannelMediaInfo(channelName, token, uid));
                }
                String channelId = call.argument("channelId");
                RtcChannel rtcChannel = AgoraRtc.newInstance().getRtcChannel(channelId);
                if (rtcChannel != null) {
                    result.success(rtcChannel.updateChannelMediaRelay(config));
                } else {
                    result.success(-1);
                }
            }
            break;

        }
    }

    private final IMetadataObserver mIMetadataObserver = new IMetadataObserver() {

        @Override
        public int getMaxMetadataSize() {
            return 0;
        }

        @Override
        public byte[] onReadyToSendMetadata(long timeStampMs) {
            return new byte[0];
        }

        @Override
        public void onMetadataReceived(byte[] buffer, int uid, long timeStampMs) {

        }
    };

    private final IRtcChannelEventHandler mRctChannelEventHandler = new IRtcChannelEventHandler() {
        @Override
        public void onChannelWarning(RtcChannel rtcChannel, int warn) {
            super.onChannelWarning(rtcChannel, warn);
            HashMap<String, Object> map = new HashMap<>();
            map.put("warn", warn);
            map.put("channelId", rtcChannel.channelId());
            sendEvent("onChannelWarning", map);
        }

        @Override
        public void onChannelError(RtcChannel rtcChannel, int err) {
            super.onChannelError(rtcChannel, err);
            HashMap<String, Object> map = new HashMap<>();
            map.put("error", err);
            map.put("channelId", rtcChannel.channelId());
            sendEvent("onChannelError", map);
        }

        @Override
        public void onJoinChannelSuccess(RtcChannel rtcChannel, int uid, int elapsed) {
            super.onJoinChannelSuccess(rtcChannel, uid, elapsed);
            HashMap<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            map.put("channelId", rtcChannel.channelId());
            map.put("elapsed", elapsed);
            sendEvent("onJoinChannelSuccess", map);
        }

        @Override
        public void onRejoinChannelSuccess(RtcChannel rtcChannel, int uid, int elapsed) {
            super.onRejoinChannelSuccess(rtcChannel, uid, elapsed);
            HashMap<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            map.put("channelId", rtcChannel.channelId());
            map.put("elapsed", elapsed);
            sendEvent("onRejoinChannelSuccess", map);
        }

        @Override
        public void onLeaveChannel(RtcChannel rtcChannel, IRtcEngineEventHandler.RtcStats stats) {
            super.onLeaveChannel(rtcChannel, stats);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("stats", mapFromStats(stats));
            sendEvent("onLeaveChannel", map);
        }

        @Override
        public void onClientRoleChanged(RtcChannel rtcChannel, int oldRole, int newRole) {
            super.onClientRoleChanged(rtcChannel, oldRole, newRole);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("oldRole", oldRole);
            map.put("newRole", newRole);
            sendEvent("onClientRoleChanged", map);
        }

        @Override
        public void onUserJoined(RtcChannel rtcChannel, int uid, int elapsed) {
            super.onUserJoined(rtcChannel, uid, elapsed);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("uid", uid);
            map.put("elapsed", elapsed);
            sendEvent("onUserJoined", map);
        }

        @Override
        public void onUserOffline(RtcChannel rtcChannel, int uid, int reason) {
            super.onUserOffline(rtcChannel, uid, reason);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("uid", uid);
            map.put("reason", reason);
            sendEvent("onUserOffline", map);
        }

        @Override
        public void onConnectionStateChanged(RtcChannel rtcChannel, int state, int reason) {
            super.onConnectionStateChanged(rtcChannel, state, reason);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("state", state);
            map.put("reason", reason);
            sendEvent("onConnectionStateChanged", map);
        }

        @Override
        public void onConnectionLost(RtcChannel rtcChannel) {
            super.onConnectionLost(rtcChannel);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            sendEvent("onConnectionLost", map);
        }

        @Override
        public void onTokenPrivilegeWillExpire(RtcChannel rtcChannel, String token) {
            super.onTokenPrivilegeWillExpire(rtcChannel, token);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("token", token);
            sendEvent("onTokenPrivilegeWillExpire", map);
        }

        @Override
        public void onRequestToken(RtcChannel rtcChannel) {
            super.onRequestToken(rtcChannel);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            sendEvent("onRequestToken", map);
        }

        @Override
        public void onRtcStats(RtcChannel rtcChannel, IRtcEngineEventHandler.RtcStats stats) {
            super.onRtcStats(rtcChannel, stats);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("stats", mapFromStats(stats));
            sendEvent("onRtcStats", map);
        }

        @Override
        public void onNetworkQuality(RtcChannel rtcChannel, int uid, int txQuality, int rxQuality) {
            super.onNetworkQuality(rtcChannel, uid, txQuality, rxQuality);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("uid", uid);
            map.put("txQuality", txQuality);
            map.put("rxQuality", rxQuality);
            sendEvent("onNetworkQuality", map);
        }

        @Override
        public void onRemoteVideoStats(RtcChannel rtcChannel, IRtcEngineEventHandler.RemoteVideoStats stats) {
            super.onRemoteVideoStats(rtcChannel, stats);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("stats", mapFromRemoteVideoStats(stats));
            sendEvent("onRemoteVideoStats", map);
        }

        @Override
        public void onRemoteAudioStats(RtcChannel rtcChannel, IRtcEngineEventHandler.RemoteAudioStats stats) {
            super.onRemoteAudioStats(rtcChannel, stats);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("stats", mapFromRemoteAudioStats(stats));
            sendEvent("onRemoteAudioStats", map);
        }

        @Override
        public void onRemoteAudioStateChanged(RtcChannel rtcChannel, int uid, int state, int reason, int elapsed) {
            super.onRemoteAudioStateChanged(rtcChannel, uid, state, reason, elapsed);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("uid", uid);
            map.put("state", state);
            map.put("reason", reason);
            map.put("elapsed", elapsed);
            sendEvent("onRemoteAudioStateChanged", map);
        }

        @Override
        public void onActiveSpeaker(RtcChannel rtcChannel, int uid) {
            super.onActiveSpeaker(rtcChannel, uid);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("uid", uid);
            sendEvent("onActiveSpeaker", map);
        }

        @Override
        public void onVideoSizeChanged(RtcChannel rtcChannel, int uid, int width, int height, int rotation) {
            super.onVideoSizeChanged(rtcChannel, uid, width, height, rotation);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("uid", uid);
            map.put("width", width);
            map.put("height", height);
            map.put("rotation", rotation);
            sendEvent("onVideoSizeChanged", map);
        }

        @Override
        public void onRemoteVideoStateChanged(RtcChannel rtcChannel, int uid, int state, int reason, int elapsed) {
            super.onRemoteVideoStateChanged(rtcChannel, uid, state, reason, elapsed);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("uid", uid);
            map.put("state", state);
            map.put("reason", reason);
            map.put("elapsed", elapsed);
            sendEvent("onRemoteVideoStateChanged", map);
        }

        @Override
        public void onStreamMessage(RtcChannel rtcChannel, int uid, int streamId, byte[] data) {
            super.onStreamMessage(rtcChannel, uid, streamId, data);
        }

        @Override
        public void onStreamMessageError(RtcChannel rtcChannel, int uid, int streamId, int error, int missed, int cached) {
            super.onStreamMessageError(rtcChannel, uid, streamId, error, missed, cached);
        }

        @Override
        public void onChannelMediaRelayStateChanged(RtcChannel rtcChannel, int state, int code) {
            super.onChannelMediaRelayStateChanged(rtcChannel, state, code);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("state", state);
            map.put("code", code);
            sendEvent("onChannelMediaRelayStateChanged", map);
        }

        @Override
        public void onChannelMediaRelayEvent(RtcChannel rtcChannel, int code) {
            super.onChannelMediaRelayEvent(rtcChannel, code);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("code", code);
            sendEvent("onChannelMediaRelayEvent", map);
        }

        @Override
        public void onRtmpStreamingStateChanged(RtcChannel rtcChannel, String url, int state, int errCode) {
            super.onRtmpStreamingStateChanged(rtcChannel, url, state, errCode);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("url", url);
            map.put("state", state);
            map.put("errCode", errCode);
            sendEvent("onRtmpStreamingStateChanged", map);
        }

        @Override
        public void onTranscodingUpdated(RtcChannel rtcChannel) {
            super.onTranscodingUpdated(rtcChannel);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            sendEvent("onTranscodingUpdated", map);
        }

        @Override
        public void onStreamInjectedStatus(RtcChannel rtcChannel, String url, int uid, int status) {
            super.onStreamInjectedStatus(rtcChannel, url, uid, status);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("url", url);
            map.put("uid", uid);
            map.put("status", status);
            sendEvent("onStreamInjectedStatus", map);
        }

        @Override
        public void onRemoteSubscribeFallbackToAudioOnly(RtcChannel rtcChannel, int uid, boolean isFallbackOrRecover) {
            super.onRemoteSubscribeFallbackToAudioOnly(rtcChannel, uid, isFallbackOrRecover);
            HashMap<String, Object> map = new HashMap<>();
            map.put("channelId", rtcChannel.channelId());
            map.put("isFallbackOrRecover", isFallbackOrRecover);
            map.put("uid", uid);
            sendEvent("onRemoteSubscribeFallbackToAudioOnly", map);
        }
    };


}
