package com.bluetooth.rmmit.eartemperaturebluetooth.activity;

import android.app.backup.BackupDataInputStream;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bluetooth.rmmit.eartemperaturebluetooth.R;
import com.bluetooth.rmmit.eartemperaturebluetooth.bean.BloodO2Bean;
import com.bluetooth.rmmit.eartemperaturebluetooth.bean.BloodPressureBean;
import com.bluetooth.rmmit.eartemperaturebluetooth.bean.EarTemperatureBean;
import com.bluetooth.rmmit.eartemperaturebluetooth.bean.ThreeInOneBean;
import com.bluetooth.rmmit.eartemperaturebluetooth.bean.WeightBean;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.BlueToothMessageListener;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.IBlueToothMessageCallBack;
import com.bluetooth.rmmit.eartemperaturebluetooth.utils.ULog;

public class MainActivity extends BaseActivity {

    private String TAG="MainActivity";

    private Button btn_scan;
    private Button btn_stop;

    private BlueToothMessageListener blueToothMessageListener;
    private IBlueToothMessageCallBack callBack=new IBlueToothMessageCallBack() {
        @Override
        public void onReceiveMessage(Object data) {
            if(data instanceof WeightBean) {
                ULog.i(TAG, "receive data type is weight.");
            } else if(data instanceof EarTemperatureBean) {
                ULog.i(TAG, "receive data type is earTemperature.");
                 ULog.i("tag", "ear: "+((EarTemperatureBean)(data)).toString());
            } else if(data instanceof BloodPressureBean) {
                ULog.i(TAG, "receive data type is bloodPressure.");
            } else if(data instanceof BloodO2Bean) {
                ULog.i(TAG, "receive data type is bloodO2.");
            } else if(data instanceof ThreeInOneBean) {
                ULog.i(TAG, "receive data type is threeinone.");
            } else {
                ULog.e(TAG, "receive undefined type.");
            }
        }

        @Override
        public int onDisConnected() {
            blueToothMessageListener.stopListenerMessage();
            blueToothMessageListener = new BlueToothMessageListener(MainActivity.this, callBack);
            blueToothMessageListener.startListenerMessage();
            return 0;
        }

        @Override
        public void writeData(byte[] bs) {
            final byte[] sendData = bs;
            new Thread() {
                public void run() {
                    for(int i = 0; i < 2; i++) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ULog.i("tag","发送关机命令 writeData-->"+String.valueOf(sendData));
                        blueToothMessageListener.writeLlsAlertLevel(sendData);
                    }
                }
            }.start();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_scan=(Button)findViewById(R.id.btn_scan);
        btn_stop=(Button)findViewById(R.id.btn_stop);

        blueToothMessageListener=new BlueToothMessageListener(this,callBack);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ULog.i(TAG,"开始扫描....");
               // blueToothMessageListener.startListenerMessage();
                showLoadDialog("正在加载...");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        disMissLoadDialog();
                    }
                },3000);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ULog.i(TAG, "结束扫描...");
               // blueToothMessageListener.stopListenerMessage();
                disMissLoadDialog();
            }
        });
    }

    private Handler handler=new Handler();



}
