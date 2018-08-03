package com.pakpobox.cleanpro.ui.scanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;

import com.google.zxing.Result;
import com.google.zxing.client.android.AutoScannerView;
import com.google.zxing.client.android.BaseCaptureActivity;
import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.utils.StatusBarUtil;
import com.pakpobox.cleanpro.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QRCodeScanActivity extends BaseCaptureActivity {

    private static final String TAG = "QRCodeScanActivity";
    @BindView(R.id.qrcode_scan_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.qrcode_scan_preview)
    SurfaceView surfaceView;
    @BindView(R.id.qrcode_scan_autoscanner_view)
    AutoScannerView autoScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);
        ButterKnife.bind(this);
        StatusBarUtil.transparencyBar(this);

        StatusBarUtil.setHeight(this, mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoScannerView.setCameraManager(cameraManager);
    }


    @Override
    public SurfaceView getSurfaceView() {
        return (surfaceView == null) ? (SurfaceView) findViewById(R.id.qrcode_scan_preview) : surfaceView;
    }

    @Override
    public void dealDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        playBeepSoundAndVibrate(true, false);
//        ToastUtils.showToast(this, rawResult.getText());
//        对此次扫描结果不满意可以调用
//        reScan();

        Intent intent = getIntent();
        intent.putExtra("scan_result", rawResult.getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}
