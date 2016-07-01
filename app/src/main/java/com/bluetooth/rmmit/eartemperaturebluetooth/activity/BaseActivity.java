package com.bluetooth.rmmit.eartemperaturebluetooth.activity;

import android.support.v7.app.AppCompatActivity;

import com.bluetooth.rmmit.eartemperaturebluetooth.R;
import com.bluetooth.rmmit.eartemperaturebluetooth.view.LoadDialog;

/**
 * Created by Administrator on 2016/7/1.
 */
public class BaseActivity extends AppCompatActivity {

    protected LoadDialog loadDialog;

    /**
     * 显示加载对话框
     * @param msg
     */
    protected void showLoadDialog(String msg)
    {
        if(loadDialog==null)
        {
            loadDialog=new LoadDialog(this, R.style.Dialogthem,R.layout.load_dialog);
            loadDialog.setCanceledOnTouchOutside(false);
        }
        loadDialog.setMsg(msg);
        loadDialog.show();
    }

    /**
     * 关闭加载对话框
     */
    protected void disMissLoadDialog()
    {
        if(loadDialog!=null)
            loadDialog.dismiss();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        disMissLoadDialog();
    }



}
