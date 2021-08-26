package com.example.binderclient

import android.os.IBinder
import android.os.Parcel
import android.os.Process
import android.util.Log
import java.lang.Exception

class BinderProxy(var remote: IBinder) {
    companion object {
        val DESCRIPTOR = "MyBinder"
        val TRANSACTION_method0 = IBinder.FIRST_CALL_TRANSACTION + 0
        val TRANSACTION_method1 = IBinder.FIRST_CALL_TRANSACTION + 1
    }

    fun invokeMethod0() {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()

        try {
            data.writeInterfaceToken(DESCRIPTOR)
            remote.run {
                transact(TRANSACTION_method0, data, reply, 0)
                reply.readException()
            }
        } catch (O_O: Exception) {
            O_O.printStackTrace()
        } finally {
            data.recycle()
            reply.recycle()
        }
        Log.e(
            "Client",
            Process.myPid()
                .toString() + "  " + Process.myTid() + "  " + Process.myUid() + "  " + "method0"
        );

    }

    fun invokeMethod1(): Int {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        var result = -1
        try {
            data.writeInterfaceToken(DESCRIPTOR)
            data.writeInt(1)
            data.writeInt(2)
            remote.run {
                transact(TRANSACTION_method1, data, reply, 0)//flags ：0（同步调用，服务端不应该做耗时操作） 1（异步调用）
                reply.readException()
            }
            result = reply.readInt()
        } catch (O_O: Exception) {
            O_O.printStackTrace()
        } finally {
            data.recycle()
            reply.recycle()
        }
        Log.e(
            "Client",
            Process.myPid()
                .toString() + "  " + Process.myTid() + "  " + Process.myUid() + "  " + "method1"
        );
        return result
    }
}