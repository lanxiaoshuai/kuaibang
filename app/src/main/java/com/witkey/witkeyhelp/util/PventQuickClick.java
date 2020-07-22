package com.witkey.witkeyhelp.util;

import android.util.Log;

/**
 * 连续点击
 * Title: PventQuickClick.java
 * @author wangshijie
 * 2015-12-26
 * @version 1.0
 */
public class PventQuickClick {

	private static long lastClickTime = 0L;
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 700) {
			return true;
		}
		lastClickTime = time;

		return false;
	}

	public static boolean isLastFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 500) {
			return true;
		}
		lastClickTime = time;

		return false;
	}

	public static boolean isHomeFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 2000) {
			return true;
		}
		lastClickTime = time;

		return false;
	}
}
