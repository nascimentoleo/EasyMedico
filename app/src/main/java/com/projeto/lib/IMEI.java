package com.projeto.lib;

import android.content.Context;
import android.telephony.TelephonyManager;

public class IMEI {

	// CAPTURANDO NUMERO DE SERIE DO CELULAR:
	public static String numCel(Context ctx) {
		String IMEI = "";

		TelephonyManager telephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		IMEI = telephonyManager.getDeviceId();

		return IMEI;
	}

}
