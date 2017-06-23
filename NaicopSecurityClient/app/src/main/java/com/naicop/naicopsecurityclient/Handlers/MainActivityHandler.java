package com.naicop.naicopsecurityclient.Handlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.naicop.naicopsecurityclient.Activities.LoginActivity;
import com.naicop.naicopsecurityclient.Activities.MainActivity;
import com.naicop.naicopsecurityclient.Config.Config;
import com.naicop.naicopsecurityclient.ServerCalls.ScanQr;

/**
 * Created by Martin on 21/06/2017.
 */

public class MainActivityHandler {

    private MainActivity activity;

    public MainActivityHandler(MainActivity activity){
        this.activity= activity;
    }

    public void ValidateQrCode(String code){
        new ScanQr(activity, code);
    }
}
