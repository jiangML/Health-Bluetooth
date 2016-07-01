package com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device;


import com.bluetooth.rmmit.eartemperaturebluetooth.bean.ThreeInOneBean;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.BluetoothUtil;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.IBlueToothMessageCallBack;
import com.bluetooth.rmmit.eartemperaturebluetooth.utils.ULog;

/**
 * 三合一(血糖、尿酸、胆固醇)返回数据处理
 */
public class ThreeInOneDataHandle extends AbstractDeviceDataHandle {

	private ThreeInOneBean threeInOneBean;

	public ThreeInOneDataHandle(IBlueToothMessageCallBack callback) {
		super(callback);
		this.deviceType = BluetoothUtil.DEVICETYPE_THREEINONE;
		this.TAG = "ThreeInOneDataHandle";
		threeInOneBean = new ThreeInOneBean();
	}

	@Override
	public void handlerData(String data) {
		ULog.i(TAG, "receive data : " + data);
		if (super.isTheSameData(data)) {
			return;
		}
		String[] datas = data.split(" ");

		// 解析三合一数据
		if(datas.length != 13) {
			ULog.e(TAG, "datas length is not 13.");
			return;
		}

		String val1 = Integer.toHexString(Integer.parseInt(datas[10]));
		String val2 = Integer.toHexString(Integer.parseInt(datas[11]));

		String value = val2.substring(1) + val1;
		ULog.i(TAG, "16 Hex value is:" + value);

		float valFloat = Integer.parseInt(value, 16);
		valFloat = Math.round(valFloat / 10);
		//测量类型
		if(datas[1].equals("65")) {        //GLUC  血糖
			threeInOneBean.setBloodSugerData((valFloat / 10) + "");
		} else if(datas[1].equals("81")) { //UA    尿液
			threeInOneBean.setUricAcidData("0." + (int)valFloat);
		} else if(datas[1].equals("81")) { //    胆固醇
			//TODO
		}

		threeInOneBean.setTestingData(false);
		this.notifyDataToUser(threeInOneBean);
	}

	@Override
	public void onDeviceConnected() {
		ULog.i(TAG, "three in one device connected.");
		threeInOneBean.setBloodSugerData("0");
		threeInOneBean.setCholesterolData("0");
		threeInOneBean.setUricAcidData("0");
		this.notifyDataToUser(threeInOneBean);
	}
}