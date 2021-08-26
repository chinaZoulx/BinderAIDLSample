package com.example.binderclient

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.os.Process
import android.util.Log

class ServerBinderService : Service() {

    companion object {
        val TAG = "Service"
    }

    override fun onBind(intent: Intent?): IBinder {
        return Stub()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * Binder服务端
     */
    class Stub : Binder() {

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            data.enforceInterface(BinderProxy.DESCRIPTOR)
            when (code) {
                BinderProxy.TRANSACTION_method0 -> {
                    method0()
                    reply?.writeNoException()
                    return true
                }
                BinderProxy.TRANSACTION_method1 -> {
                    var a = data.readInt()
                    var b = data.readInt()
                    val result = method1(a, b)
                    reply?.run {
                        writeNoException()
                        writeInt(result)
                    }
                    return true
                }
            }
            return super.onTransact(code, data, reply, flags)
        }

        fun method0() {
//            Thread.sleep(5000)
            Log.e(
                TAG,
                "binder "+Process.myPid()
                    .toString() + "  " + Process.myTid() + "  " + Process.myUid() + "  " + "method0"
            );
        }

        fun method1(a: Int, b: Int): Int {
            Log.e(
                TAG,
                "binder "+Process.myPid()
                    .toString() + "  " + Process.myTid() + "  " + Process.myUid() + "  " + "method1" + "  " + a + " " + b
            );
//            Thread.sleep(10000)
            return a + b
        }
    }
}