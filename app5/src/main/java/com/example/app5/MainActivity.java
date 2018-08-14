package com.example.app5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // 必须异步调用

    }

    Runnable payRunnable = new Runnable() {

        @Override
        public void run() {
            PayTask alipay = new PayTask(MainActivity.this);

//                                String result = alipay.payV2(orderInfo,true);

            Map<String, String> result = alipay.payV2("alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017121300703777&biz_content=%7B%22out_trade_no%22%3A%22180806202856073%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%221.0%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fwww.univstar.com%2Fv1%2Fm%2Falipay%2FnotifyUrl&sign=G%2FFYnlS0qdOxDx6yS7SMCNIiw7KWDoL0%2FDpuOvGFk%2BY%2Bc1P%2BupY7Dd9hxcA3lD5Q972024QkFXNUoq7HLi4QMyT9S21BXg5yxrumsLi23PfxzaC9%2BIDGqfCpooVwgpPmtGaCW0Q4qYWKGmrVD4Hii1adFSotnIQIIWV4OY1HKHzZPxo12XQzhU7P0y57S6V6ZavzzQHb5OlfonaOi10Ze9SSu%2FGLWmTTipOVqiIH%2BZ%2F3YCRn%2FH%2B4j44fdO9WQt%2BeCcG4lwtpuIgcBU9Gpm%2B37ZPddrExBJhtNRoXsKDhjSmBzTgG%2BnyCdL07Fjv8IFQDzVKa6DQDrM%2FHkm%2BJi3ZR3Q%3D%3D&sign_type=RSA2&timestamp=2018-08-06+20%3A29%3A18&version=1.0", true);

            Message msg = new Message();
            msg.what = 1;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    };
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent();


                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private void initView() {
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Thread payThread = new Thread(payRunnable);
                payThread.start();
                break;
        }
    }
}
