package com.example.binderclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val TAG = "Client"

    var iRemoteService: IRemoteService? = null
    var iBinderService: IBinder? = null
    var binderProxy: BinderProxy? = null

    val binderConn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            Log.e(TAG, "binder onServiceConnected")
            iBinder?.run {
                iBinderService = this
                binderProxy = BinderProxy(this)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e(TAG, "binder onServiceDisconnected")
            iBinderService = null
        }
    }

    val aidlConn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            Log.e(TAG, "aidl onServiceConnected")
            iBinder?.run {
                iRemoteService = IRemoteService.Stub.asInterface(this)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e(TAG, "aidl onServiceDisconnected")
            iRemoteService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pkg = "com.example.binderservice"
        val action = "android.intent.action.bind.binder"
        launcherBinderService()
        launcherAIDLService()

        findViewById<View>(R.id.normalActionTv).setOnClickListener {
            normalBinderInvoke()
            Toast.makeText(this, "发送中", Toast.LENGTH_LONG).show()
        }

        findViewById<View>(R.id.aidlActionTv).setOnClickListener {
            aidlInvoke()
            Toast.makeText(this, "发送中", Toast.LENGTH_LONG).show()
        }
    }

    fun launcherBinderService(){
        val intent = Intent(MainActivity@ this, ServerBinderService::class.java)
        bindService(intent, binderConn, BIND_AUTO_CREATE)
        Log.e("Client", "Binder Service Launcher")
    }

    fun launcherAIDLService(){
        val intent = Intent(MainActivity@ this, ServerAIDLService::class.java)
        bindService(intent, aidlConn, BIND_AUTO_CREATE)
        Log.e("Client", "AIDL Service Launcher")
    }

    fun normalBinderInvoke() {
        binderProxy?.apply {
            invokeMethod0()
            Log.e(TAG, "binder invoke")
            val result: Int = invokeMethod1()
            Log.e(TAG, "binder result = $result")
        }
    }

    fun aidlInvoke(){
        iRemoteService?.run {
            method0()
            Log.e(TAG, "aidl invoke")
            val result=method1(1,2)
            Log.e(TAG, "aidl result = $result")
        }
    }

}