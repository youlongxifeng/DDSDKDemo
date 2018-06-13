package com.dd.sdk.tools;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.tools
 * @class describe
 * @time 2018/6/11 18:36
 * @change
 * @class describe
 */

import android.util.Log;
public class LogUtils {
    private static String sTag = "LogUtils";
    private static boolean sIsDebug = false;
    private static boolean sIsTrace = false;

    public LogUtils() {
    }

    public static void init(String tag, boolean isDebug, boolean isTrace) {
        if(tag != null) {
            sTag = tag;
        }

        sIsDebug = isDebug;
        sIsTrace = isTrace;
    }

    public static void i(String msg) {
        if(sIsDebug) {
            StackTraceElement[] list = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            if(sIsTrace) {
                sb.append("[");
                if(list.length > 3) {
                    sb.append(list[3].getFileName() + "_" + list[3].getMethodName() + "_" + list[3].getLineNumber() + "_");
                }

                if(list.length > 4) {
                    sb.append(list[4].getFileName() + "_" + list[4].getMethodName() + "_" + list[4].getLineNumber() + "_");
                }

                if(list.length > 5) {
                    sb.append(list[5].getFileName() + "_" + list[5].getMethodName() + "_" + list[5].getLineNumber());
                }

                sb.append("] ");
            }

            Log.i(sTag, sb.toString() + msg);
        }

    }

    public static void i(String tag, String msg) {
        if(sIsDebug) {
            StackTraceElement[] list = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            if(list.length > 3) {
                sb.append(list[3].getFileName() + "#" + list[3].getMethodName() + "(" + list[3].getLineNumber() + ")_");
            }

            if(list.length > 4) {
                sb.append(list[4].getFileName() + "#" + list[4].getMethodName() + "(" + list[4].getLineNumber() + ")_");
            }

            if(list.length > 5) {
                sb.append(list[5].getFileName() + "#" + list[5].getMethodName() + "(" + list[5].getLineNumber() + ")");
            }

            sb.append("]");
            Log.i(sTag + "_" + tag, sb.toString() + msg);
        }

    }

    public static void w(String msg) {
        if(sIsDebug) {
            StackTraceElement[] list = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            if(sIsTrace) {
                sb.append("[");
                if(list.length > 3) {
                    sb.append(list[3].getFileName() + "_" + list[3].getMethodName() + "_" + list[3].getLineNumber() + "_");
                }

                if(list.length > 4) {
                    sb.append(list[4].getFileName() + "_" + list[4].getMethodName() + "_" + list[4].getLineNumber() + "_");
                }

                if(list.length > 5) {
                    sb.append(list[5].getFileName() + "_" + list[5].getMethodName() + "_" + list[5].getLineNumber());
                }

                sb.append("] ");
            }

            Log.w(sTag, sb.toString() + msg);
        }

    }

    public static void w(String tag, String msg) {
        if(sIsDebug) {
            StackTraceElement[] list = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            if(list.length > 3) {
                sb.append(list[3].getFileName() + "#" + list[3].getMethodName() + "(" + list[3].getLineNumber() + ")_");
            }

            if(list.length > 4) {
                sb.append(list[4].getFileName() + "#" + list[4].getMethodName() + "(" + list[4].getLineNumber() + ")_");
            }

            if(list.length > 5) {
                sb.append(list[5].getFileName() + "#" + list[5].getMethodName() + "(" + list[5].getLineNumber() + ")");
            }

            sb.append("]");
            Log.w(sTag + "_" + tag, sb.toString() + msg);
        }

    }

    public static void e(String msg) {
        if(sIsDebug) {
            StackTraceElement[] list = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            if(sIsTrace) {
                sb.append("[");
                if(list.length > 3) {
                    sb.append(list[3].getFileName() + "_" + list[3].getMethodName() + "_" + list[3].getLineNumber() + "_");
                }

                if(list.length > 4) {
                    sb.append(list[4].getFileName() + "_" + list[4].getMethodName() + "_" + list[4].getLineNumber() + "_");
                }

                if(list.length > 5) {
                    sb.append(list[5].getFileName() + "_" + list[5].getMethodName() + "_" + list[5].getLineNumber());
                }

                sb.append("] ");
            }

            Log.e(sTag, sb.toString() + msg);
        }

    }

    public static void e(String tag, String msg) {
        if(sIsDebug) {
            StackTraceElement[] list = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            if(list.length > 3) {
                sb.append(list[3].getFileName() + "#" + list[3].getMethodName() + "(" + list[3].getLineNumber() + ")_");
            }

            if(list.length > 4) {
                sb.append(list[4].getFileName() + "#" + list[4].getMethodName() + "(" + list[4].getLineNumber() + ")_");
            }

            if(list.length > 5) {
                sb.append(list[5].getFileName() + "#" + list[5].getMethodName() + "(" + list[5].getLineNumber() + ")");
            }

            sb.append("]");
            Log.e(sTag + "_" + tag, sb.toString() + msg);
        }

    }

    public static void print(String msg) {
        StackTraceElement[] list = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        if(sIsTrace) {
            sb.append("[");
            if(list.length > 3) {
                sb.append(list[3].getFileName() + "#" + list[3].getMethodName() + "(" + list[3].getLineNumber() + ")");
            }

            if(list.length > 4) {
                sb.append(list[4].getFileName() + "#" + list[4].getMethodName() + "(" + list[4].getLineNumber() + ")");
            }

            if(list.length > 5) {
                sb.append(list[5].getFileName() + "#" + list[5].getMethodName() + "(" + list[5].getLineNumber() + ")");
            }

            sb.append("] ");
        }

        Log.w(sTag + "_print", sb.toString() + msg);
    }

    public static void i(String tag, String msg, boolean isDebug) {
        if(isDebug && sIsDebug) {
            Log.i(tag, msg);
        }

    }

    public static void saveLog(String tag, String msg) {
        if(sIsDebug) {
            StackTraceElement[] list = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            if(list.length > 3) {
                sb.append(list[3].getFileName() + "#" + list[3].getMethodName() + "(" + list[3].getLineNumber() + ")_");
            }

            if(list.length > 4) {
                sb.append(list[4].getFileName() + "#" + list[4].getMethodName() + "(" + list[4].getLineNumber() + ")_");
            }

            if(list.length > 5) {
                sb.append(list[5].getFileName() + "#" + list[5].getMethodName() + "(" + list[5].getLineNumber() + ")");
            }

            sb.append("]");
            (new StringBuilder()).append(sTag).append("_").append(tag).append("/t").append(sb.toString()).append(msg).toString();
        }

    }
}
