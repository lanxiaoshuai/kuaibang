package com.witkey.witkeyhelp.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.witkey.witkeyhelp.MyAPP;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ReceiveMsgService extends Service {
	// // 实时监听网络状态改变
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				Timer timer = new Timer();
				timer.schedule(new QunXTask(context), new Date());
			}
		}
	};

	public interface GetConnectState {
		public void GetState(boolean isConnected); // 网络状态改变之后，通过此接口的实例通知当前网络的状态，此接口在Activity中注入实例对象
	}

	private GetConnectState onGetConnectState;

	public void setOnGetConnectState(GetConnectState onGetConnectState) {
		this.onGetConnectState = onGetConnectState;
	}

	private Binder binder = new MyBinder();
	private boolean isContected = true;

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {// 注册广播
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); // 添加接收网络连接状态改变的Action
		Log.e("mylog", "注册广播");
		registerReceiver(mReceiver, mFilter);
	}

	public class QunXTask extends TimerTask {
		private Context context;

		public QunXTask(Context context) {
			this.context = context;
		}

		@Override
		public void run() {
			if (isNetworkConnected(context) || isWifiConnected(context)) {
				isContected = true;
			} else {
				isContected = false;
			}
			if (onGetConnectState != null) {
				onGetConnectState.GetState(isContected); // 通知网络状态改变
				MyAPP.getInstance().setNetContect(isContected);
				Log.i("llx", "通知网络状态改变:" + isContected);
			}
		}
	}

	/*
	 * 判断是3G否有网络连接
	 */
	private boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}	
		return false;
	}

	/*
	 * 判断是否有wifi连接
	 */
	private boolean isWifiConnected(Context context) {
		if (context != null) {
			Context c=context.getApplicationContext();
			WifiManager manager=(WifiManager) c.getSystemService(Context.WIFI_SERVICE);
			if(manager!=null)
			{
				return manager.isWifiEnabled();
			}
		}
		return false;
	}

	public class MyBinder extends Binder {
		public ReceiveMsgService getService() {
			return ReceiveMsgService.this;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver); // 删除广播
	}
}
