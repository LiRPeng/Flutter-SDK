import 'dart:async';

import 'package:agora_rtc_engine/src/base.dart';
import 'package:flutter/services.dart';

import 'agora_rtc_engine.dart';

class RtcChannel {
  static const MethodChannel _channel =
      const MethodChannel('agora_rtc_channel');
  static const EventChannel _eventChannel =
      const EventChannel('agora_rtc_channel_event_channel');

  static StreamSubscription<dynamic> _sink;

  static void Function(String channelId, int warn) onChannelWarning;
  static void Function(String channelId, int error) onChannelError;
  static void Function(String channelId, int uid, int elapsed)
      onJoinChannelSuccess;
  static void Function(String channelId, int uid, int elapsed)
      onRejoinChannelSuccess;
  static void Function(String channelId, RtcStats stats) onLeaveChannel;
  static void Function(String channelId, int oldRole, int newRole)
      onClientRoleChanged;
  static void Function(String channelId, int uid, int elapsed) onUserJoined;
  static void Function(String channelId, int uid, int reason) onUserOffline;
  static void Function(String channelId, int state, int reaso)
      onConnectionStateChanged;
  static void Function(String channelId) onConnectionLost;
  static void Function(String channelId, String token)
      onTokenPrivilegeWillExpire;
  static void Function(String channelId) onRequestToken;
  static void Function(String channelId, RtcStats stats) onRtcStats;
  static void Function(String channelId, int uid, int txQuality, int rxQuality)
      onNetworkQuality;
  static void Function(String channelId, RemoteVideoStats stats)
      onRemoteVideoStats;
  static void Function(String channelId, RemoteAudioStats) onRemoteAudioStats;
  static void Function(
          String channelId, int uid, int state, int reason, int elapsed)
      onRemoteAudioStateChanged;
  static void Function(String channelId, int uid) onActiveSpeaker;
  static void Function(
          String channelId, int uid, int width, int height, int rotation)
      onVideoSizeChanged;
  static void Function(
          String channelId, int uid, int state, int reason, int elapsed)
      onRemoteVideoStateChanged;
  static void Function(String channelId, int state, int code)
      onChannelMediaRelayStateChanged;
  static void Function(String channelId, int code) onChannelMediaRelayEvent;
  static void Function(String channelId, String url, int state, int errCode)
      onRtmpStreamingStateChanged;
  static void Function(String channelId) onTranscodingUpdated;
  static void Function(String channelId, String url, int uid, int status)
      onStreamInjectedStatus;
  static void Function(String channelId, int uid, bool isFallbackOrRecover)
      onRemoteSubscribeFallbackToAudioOnly;

  static Future<void> setRtcChannelEventHandler(String channelId) async {
    await _channel
        .invokeMethod("setRtcChannelEventHandler", {"channelId": channelId});
  }

  static Future<void> destroy() async {
    await _channel.invokeMethod("destroy");
  }

  static Future<void> destroyChannelById(String channelId) async {
    await _channel.invokeMethod("destroyChannelById", {"channelId": channelId});
  }

  static Future<int> getConnectionState(String channelId) async {
    return _channel
        .invokeMethod("getConnectionState", {"channelId": channelId});
  }

  static Future<int> joinChannel(
      String channelId,
      String token,
      String optionalInfo,
      int optionalUid,
      bool autoSubscribeAudio,
      bool autoSubscribeVideo) async {
    return _channel.invokeMethod("joinChannel", {
      "channelId": channelId,
      "token": token,
      "optionalInfo": optionalInfo,
      "optionalUid": optionalUid,
      "autoSubscribeAudio": autoSubscribeAudio,
      "autoSubscribeVideo": autoSubscribeVideo
    });
  }

  static Future<int> joinWithUserAccount(
      String channelId,
      String token,
      String userAccount,
      bool autoSubscribeAudio,
      bool autoSubscribeVideo) async {
    return _channel.invokeMethod("joinWithUserAccount", {
      "channelId": channelId,
      "token": token,
      "userAccount": userAccount,
      "autoSubscribeAudio": autoSubscribeAudio,
      "autoSubscribeVideo": autoSubscribeVideo
    });
  }

  static Future<int> leaveChannel(
      String channelId) async {
    return _channel.invokeMethod("leaveChannel", {
      "channelId": channelId,
    });
  }

  static Future<int> publish(
      String channelId) async {
    return _channel.invokeMethod("publish", {
      "channelId": channelId,
    });
  }

  static Future<int> unPublish(
      String channelId) async {
    return _channel.invokeMethod("unPublish", {
      "channelId": channelId,
    });
  }

  static Future<int> renewToken(
      String channelId,
      String token) async {
    return _channel.invokeMethod("renewToken", {
      "channelId": channelId,
      "token": token,
    });
  }

  static Future<int> registerMediaMetadataObserver(
      String channelId,
      int type) async {
    return _channel.invokeMethod("registerMediaMetadataObserver", {
      "channelId": channelId,
      "type": type,
    });
  }

  static Future<int> setClientRole(
      String channelId,
      int role) async {
    return _channel.invokeMethod("setClientRole", {
      "channelId": channelId,
      "role": role,
    });
  }

  static Future<int> setRemoteUserPriority(
      String channelId,
      int uid,
      int userPriority) async {
    return _channel.invokeMethod("setRemoteUserPriority", {
      "channelId": channelId,
      "uid": uid,
      "userPriority": userPriority,
    });
  }

  static Future<int> setRemoteVoicePosition(
      String channelId,
      int uid,
      double pan,
      int gain) async {
    return _channel.invokeMethod("setRemoteVoicePosition", {
      "channelId": channelId,
      "uid": uid,
      "pan": pan,
      "gain": gain,
    });
  }

  static Future<int> setRemoteRenderMode(
      String channelId,
      int uid,
      int mode,
      int mirrorMode) async {
    return _channel.invokeMethod("setRemoteRenderMode", {
      "channelId": channelId,
      "uid": uid,
      "mode": mode,
      "mirrorMode": mirrorMode,
    });
  }

  static Future<int> setDefaultMuteAllRemoteAudioStreams(
      String channelId,
      bool muted,
      ) async {
    return _channel.invokeMethod("setDefaultMuteAllRemoteAudioStreams", {
      "channelId": channelId,
      "muted": muted,
    });
  }

  static Future<int> setDefaultMuteAllRemoteVideoStreams(
      String channelId,
      bool muted,
      ) async {
    return _channel.invokeMethod("setDefaultMuteAllRemoteVideoStreams", {
      "channelId": channelId,
      "muted": muted,
    });
  }

  static Future<int> muteAllRemoteAudioStreams(
      String channelId,
      bool muted,
      ) async {
    return _channel.invokeMethod("muteAllRemoteAudioStreams", {
      "channelId": channelId,
      "muted": muted,
    });
  }

  static Future<int> muteAllRemoteVideoStreams(
      String channelId,
      bool muted,
      ) async {
    return _channel.invokeMethod("muteAllRemoteVideoStreams", {
      "channelId": channelId,
      "muted": muted,
    });
  }

  static Future<int> adjustUserPlaybackSignalVolume(
      String channelId,
      int uid,
      int volume
      ) async {
    return _channel.invokeMethod("muteAllRemoteVideoStreams", {
      "channelId": channelId,
      "uid": uid,
      "volume": volume
    });
  }

  static Future<int> muteRemoteAudioStream(
      String channelId,
      int uid,
      bool muted
      ) async {
    return _channel.invokeMethod("muteRemoteAudioStream", {
      "channelId": channelId,
      "uid": uid,
      "muted": muted
    });
  }

  static Future<int> muteRemoteVideoStream(
      String channelId,
      int uid,
      bool muted
      ) async {
    return _channel.invokeMethod("muteRemoteVideoStream", {
      "channelId": channelId,
      "uid": uid,
      "muted": muted
    });
  }

  static Future<int> setRemoteVideoStreamType(
      String channelId,
      int uid,
      int streamType
      ) async {
    return _channel.invokeMethod("setRemoteVideoStreamType", {
      "channelId": channelId,
      "uid": uid,
      "streamType": streamType
    });
  }

  static Future<int> setRemoteDefaultVideoStreamType(
      String channelId,
      int streamType
      ) async {
    return _channel.invokeMethod("setRemoteDefaultVideoStreamType", {
      "channelId": channelId,
      "streamType": streamType
    });
  }

  static Future<int> createDataStream(
      String channelId,
      bool reliable,
      bool ordered
      ) async {
    return _channel.invokeMethod("createDataStream", {
      "channelId": channelId,
      "reliable": reliable,
      "ordered": ordered
    });
  }

  static Future<int> addPublishStreamUrl(
      String channelId,
      String url,
      bool transcodingEnabled
      ) async {
    return _channel.invokeMethod("addPublishStreamUrl", {
      "channelId": channelId,
      "url": url,
      "transcodingEnabled": transcodingEnabled
    });
  }

  static Future<int> removePublishStreamUrl(
      String channelId,
      String url,
      ) async {
    return _channel.invokeMethod("removePublishStreamUrl", {
      "channelId": channelId,
      "url": url,
    });
  }

  /// Sets the video layout and audio settings for CDN live. (CDN live only.)
  static Future<int> setLiveTranscoding(AgoraLiveTranscoding config) async {
    return _channel
        .invokeMethod('setLiveTranscoding', {"transcoding": config.toJson()});
  }

  static Future<void> startChannelMediaRelay(
      AgoraChannelMediaRelayConfiguration config) async {
    await _channel
        .invokeMethod('startChannelMediaRelay', {"config": config.toJson()});
  }

  static Future<void> stopChannelMediaRelay() async {
    await _channel.invokeMethod('stopChannelMediaRelay');
  }

  /// updateChannelMediaRelay
  static Future<void> updateChannelMediaRelay(
      AgoraChannelMediaRelayConfiguration config) async {
    await _channel
        .invokeMethod('updateChannelMediaRelay', {"config": config.toJson()});
  }

  static void addEventChannelHandler() async {
    _sink = _eventChannel.receiveBroadcastStream().listen(_eventListener);
  }

  static void removeEventChannelHandler() async {
    await _sink.cancel();
  }

  static void _eventListener(dynamic event) {
    final Map<dynamic, dynamic> map = event;
    switch (map['event']) {
      case "onChannelWarning":
        if (onChannelWarning != null) {
          onChannelWarning(map["channelId"], map["warn"]);
        }
        break;
      case "onChannelError":
        if (onChannelError != null) {
          onChannelError(map["channelId"], map["error"]);
        }
        break;
      case "onJoinChannelSuccess":
        if (onJoinChannelSuccess != null) {
          onJoinChannelSuccess(map["channelId"], map["uid"], map["elapsed"]);
        }
        break;
      case "onRejoinChannelSuccess":
        if (onRejoinChannelSuccess != null) {
          onRejoinChannelSuccess(map["channelId"], map["uid"], map["elapsed"]);
        }
        break;
      case "onLeaveChannel":
        if (onLeaveChannel != null) {
          RtcStats stats = RtcStats.fromJson(map['stats']);
          onLeaveChannel(map["channelId"], stats);
        }
        break;
      case "onClientRoleChanged":
        if (onClientRoleChanged != null) {
          onClientRoleChanged(map["channelId"], map["oldRole"], map["newRole"]);
        }
        break;
      case "onUserJoined":
        if (onUserJoined != null) {
          onUserJoined(map["channelId"], map["uid"], map["elapsed"]);
        }
        break;
      case "onUserOffline":
        if (onUserOffline != null) {
          onUserOffline(map["channelId"], map["uid"], map["reason"]);
        }
        break;
      case "onConnectionStateChanged":
        if (onConnectionStateChanged != null) {
          onConnectionStateChanged(
              map["channelId"], map["state"], map["reason"]);
        }
        break;
      case "onConnectionLost":
        if (onConnectionLost != null) {
          onConnectionLost(map["channelId"]);
        }
        break;
      case "onTokenPrivilegeWillExpire":
        if (onTokenPrivilegeWillExpire != null) {
          onTokenPrivilegeWillExpire(map["channelId"], map["token"]);
        }
        break;
      case "onRequestToken":
        if (onRequestToken != null) {
          onRequestToken(map["channelId"]);
        }
        break;
      case "onRtcStats":
        if (onRtcStats != null) {
          RtcStats stats = RtcStats.fromJson(map["stats"]);
          onRtcStats(map["channelId"], stats);
        }
        break;
      case "onNetworkQuality":
        if (onNetworkQuality != null) {
          onNetworkQuality(
              map["channelId"], map["uid"], map["txQuality"], map["rxQuality"]);
        }
        break;
      case "onRemoteVideoStats":
        if (onRemoteVideoStats != null) {
          RemoteVideoStats stats = RemoteVideoStats.fromJson(map["stats"]);
          onRemoteVideoStats(map["channelId"], stats);
        }
        break;
      case "onRemoteAudioStats":
        if (onRemoteAudioStats != null) {
          RemoteAudioStats stats = RemoteAudioStats.fromJson(map["stats"]);
          onRemoteAudioStats(map["channelId"], stats);
        }
        break;
      case "onRemoteAudioStateChanged":
        if (onRemoteAudioStateChanged != null) {
          onRemoteAudioStateChanged(map["channelId"], map["uid"], map["state"],
              map["reason"], map["elapsed"]);
        }
        break;
      case "onActiveSpeaker":
        if (onActiveSpeaker != null) {
          onActiveSpeaker(map["channelId"], map["uid"]);
        }
        break;
      case "onVideoSizeChanged":
        if (onVideoSizeChanged != null) {
          onVideoSizeChanged(map["channelId"], map["uid"], map["width"],
              map["height"], map["rotation"]);
        }
        break;

      case "onRemoteVideoStateChanged":
        if (onRemoteVideoStateChanged != null) {
          onRemoteVideoStateChanged(map["channelId"], map["uid"], map["state"],
              map["reason"], map["elapsed"]);
        }
        break;

      case "onChannelMediaRelayStateChanged":
        if (onChannelMediaRelayStateChanged != null) {
          onChannelMediaRelayStateChanged(
              map["channelId"], map["state"], map["code"]);
        }
        break;

      case "onChannelMediaRelayEvent":
        if (onChannelMediaRelayEvent != null) {
          onChannelMediaRelayEvent(map["channelId"], map["code"]);
        }
        break;
      case "onRtmpStreamingStateChanged":
        if (onRtmpStreamingStateChanged != null) {
          onRtmpStreamingStateChanged(
              map["channelId"], map["url"], map["state"], map["errCode"]);
        }
        break;
      case "onTranscodingUpdated":
        if (onTranscodingUpdated != null) {
          onTranscodingUpdated(map["channelId"]);
        }
        break;
      case "onStreamInjectedStatus":
        if (onStreamInjectedStatus != null) {
          onStreamInjectedStatus(
              map["channelId"], map["url"], map["uid"], map["status"]);
        }
        break;
      case "onRemoteSubscribeFallbackToAudioOnly":
        if (onRemoteSubscribeFallbackToAudioOnly != null) {
          onRemoteSubscribeFallbackToAudioOnly(
              map["channelId"], map["uid"], map["isFallbackOrRecover"]);
        }
        break;
    }
  }
}
