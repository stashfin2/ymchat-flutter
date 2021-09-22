package com.yellow.ai.ymchat_flutter;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** YmchatFlutterPlugin */
public class YmchatFlutterPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel methodChannel;

  private EventChannel ymEventChannel;

  private EventChannel ymCloseBotEventChannel;

  private YmChatService ymChatService;

  private Context flutterContext;

  private EventChannel.EventSink eventSink;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "com.yellow.ai.ymchat");
    methodChannel.setMethodCallHandler(this);
    this.flutterContext = flutterPluginBinding.getApplicationContext();

    ymEventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "YMChatEvent");
    ymCloseBotEventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "YMBotCloseEvent");

    ymChatService = new YmChatService(ymEventChannel, ymCloseBotEventChannel);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case "setBotId":
        setBotId(call, result);
        break;
      case "setDeviceToken":
        setDeviceToken(call, result);
        break;
      case "setEnableSpeech":
        setEnableSpeech(call, result);
        break;
      case "setEnableHistory":
        setEnableHistory(call, result);
        break;
      case "setAuthenticationToken":
        setAuthenticationToken(call, result);
        break;
      case "showCloseButton":
        showCloseButton(call, result);
        break;
      case "setCustomURL":
        setCustomURL(call, result);
        break;
      case "setPayload":
        setPayload(call, result);
        break;
      case "startChatbot":
        startChatbot(call, result);
        break;
      case "closeBot":
        closeBot(result);
        break;
    }
  }

  public void setBotId(MethodCall call, Result result) {
    String botId = call.argument("botId");
    ymChatService.setBotId(botId);
    result.success(true);
  }

  public void startChatbot(MethodCall call, Result result) {
    ymChatService.startChatbot(flutterContext);
    result.success(true);
  }

  public void closeBot(Result result) {
    ymChatService.closeBot();
    result.success(true);
  }

  public void setDeviceToken(MethodCall call, Result result) {
    String deviceToken = call.argument("deviceToken");
    ymChatService.setDeviceToken(deviceToken);
    result.success(true);
  }

  public void setEnableSpeech(MethodCall call, Result result) {
    Boolean shouldEnableSpeech = call.argument("shouldEnableSpeech");
    ymChatService.setEnableSpeech(shouldEnableSpeech);
    result.success(true);
  }

  public void setEnableHistory(MethodCall call, Result result) {
    Boolean shouldEnableHistory = call.argument("shouldEnableHistory");
    ymChatService.setEnableHistory(shouldEnableHistory);
    result.success(true);
  }

  public void setAuthenticationToken(MethodCall call, Result result) {
    String authenticationToken = call.argument("authenticationToken");
    ymChatService.setAuthenticationToken(authenticationToken);
    result.success(true);
  }

  public void showCloseButton(MethodCall call, Result result) {
    Boolean shouldShowCloseButton = call.argument("shouldEnableCloseButton");
    ymChatService.showCloseButton(shouldShowCloseButton);
    result.success(true);
  }

  public void setCustomURL(MethodCall call, Result result) {
    String customURL = call.argument("customURL");
    ymChatService.customBaseUrl(customURL);
    result.success(true);
  }

  public void setPayload(MethodCall call, Result result) {
    HashMap<String, Object> payload = call.argument("payload");
    ymChatService.setPayload(payload);
    result.success(true);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
  }
}