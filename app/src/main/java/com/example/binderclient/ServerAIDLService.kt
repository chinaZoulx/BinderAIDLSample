package com.example.binderclient

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.os.Process
import android.util.Log

class ServerAIDLService : Service() {

    val TAG="Service"
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    private val binder = object : IRemoteService.Stub() {
        override fun getPid(): Int {
            return Process.myPid()
        }

        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String
        ) {
            // Does nothing
        }

        override fun method0() {
//            Thread.sleep(5000)
            Log.e(TAG, "AIDL "+Process.myPid().toString() + "  " + Process.myTid() + "  " + Process.myUid() + "  " + "method0");
        }

        override fun method1(a: Int, b: Int): Int {
            Log.e(TAG, "AIDL "+Process.myPid().toString() + "  " + Process.myTid() + "  " + Process.myUid() + "  " + "method1" + "  " + a + " " + b);
//            Thread.sleep(10000)
            return a + b
        }
    }
}