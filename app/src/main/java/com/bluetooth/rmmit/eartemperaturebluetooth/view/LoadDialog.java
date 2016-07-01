package com.bluetooth.rmmit.eartemperaturebluetooth.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bluetooth.rmmit.eartemperaturebluetooth.R;

/**
 * Created by Administrator on 2016/7/1.
 * 等待加载对话框
 */
public class LoadDialog extends Dialog {

    /**
     * 对话框宽度
     */
    private int width=180;

    /**
     * 对话框高
     */
    private int height=120;

    /**
     * 对话框布局ID
     */
    private int contentViewId;

    /**
     * 对话框加载提示信息
     */
    private TextView tv_msg;

    /**
     * 对话框加载提示信息资源ID
     */
    private int msgResId;

    /**
     * 对话框加载提示信息
     */
    private String msg;

    public int getContentViewId() {
        return contentViewId;
    }

    public void setContentViewId(int contentViewId) {
        this.contentViewId = contentViewId;
    }

    public LoadDialog(Context context) {
        super(context);
    }

    public LoadDialog(Context context, int themeResId,int layId) {
        super(context, themeResId);
        init(context, themeResId, width, height,layId);
    }
    public LoadDialog(Context context, int themeResId,int w,int h,int layId) {
        super(context, themeResId);
        init(context, themeResId, w, h,layId);
    }


    public LoadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void  init(Context context, int themeResId,int w,int h,int layId)
    {
        setContentView(layId);
        Window window=getWindow();
        WindowManager.LayoutParams params=window.getAttributes();
        params.width=dip2px(w);
        params.height=dip2px(h);
        params.gravity= Gravity.CENTER;
        window.setAttributes(params);
        tv_msg=(TextView)window.findViewById(R.id.tv_msg);

    }


    private int dip2px(int dip)
    {
        return (int)(dip*getDensity()+0.5);
    }

    private float getDensity()
    {
        return getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * 设置提示信息
     * @param msg
     */
    public void setMsg(String msg)
    {
        this.msg=msg;
        msgResId=0;
        setMsg();
    }

    /**
     *   设置提示信息
     */
    public void setMsg(int msgResId)
    {
        this.msgResId=msgResId;
        msg=null;
        setMsg();
    }

    private void setMsg()
    {
        if(msg!=null&&!"".equals(msg))
        {
            tv_msg.setText(msg);
        }else if(msgResId!=0)
        {
            tv_msg.setText(msgResId);
        }else{
            tv_msg.setText("");
        }
    }


}
