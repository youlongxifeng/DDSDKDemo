// IOnNewBookArrivedListener.aidl
package com.dd.sdk.service;

// Declare any non-default types here with import statements
interface IOnCommandListener {
      void onMessageResponse(in  String msg);

      void onServiceStatusConnectChanged(in int statusCode);
}
