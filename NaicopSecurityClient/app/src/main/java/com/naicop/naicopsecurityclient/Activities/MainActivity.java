package com.naicop.naicopsecurityclient.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.naicop.naicopsecurityclient.Config.Constants;
import com.naicop.naicopsecurityclient.Config.Permissions;
import com.naicop.naicopsecurityclient.Handlers.MainActivityHandler;
import com.naicop.naicopsecurityclient.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, ActivityCompat.OnRequestPermissionsResultCallback{

    private ZXingScannerView scannerView;
    private TextView scanButton;
    private MainActivityHandler handler;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new MainActivityHandler(this);
        setContentView(R.layout.activity_main);
        findViewsById();
        setButtonColor();
        setBehaviour();
    }

    private void findViewsById(){
        scanButton = (TextView)findViewById(R.id.scanButton);
    }

    private void setButtonColor(){
        GradientDrawable loginShape = (GradientDrawable)scanButton.getBackground();
        loginShape.setColor(Color.parseColor("#51b7dd"));
    }

    private void setBehaviour(){
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QrScanner();
            }
        });
    }

    private void QrScanner(){
        if(Permissions.cameraPermissionEnabled(this)){
            scannerView = new ZXingScannerView(this);
            setContentView(scannerView);
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        }else{
            Permissions.requestCameraPermission(this);
        }

    }

    @Override
    public void onPause(){
        super.onPause();
        if(scannerView!= null)
            scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        handler.ValidateQrCode(result.getText().toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                    scannerView.setResultHandler(this);
                    scannerView.startCamera();
                } else {
                    finish();
                }
            }

        }
    }
}

