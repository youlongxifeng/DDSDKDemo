// ICommandManagerAidlInterface.aidl
package com.dd.sdk.service;

// Declare any non-default types here with import statements
import com.dd.sdk.service.IOnCommandListener;
interface ICommandManager {
    void onMessageResponse(  String msg) ;
    void onServiceStatusConnectChanged(   int statusCode);
    void registerListener(  IOnCommandListener listener);
    void unRegisterListener(  IOnCommandListener listener);
}
