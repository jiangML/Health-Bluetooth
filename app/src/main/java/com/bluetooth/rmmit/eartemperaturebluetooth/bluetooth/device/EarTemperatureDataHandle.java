package com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device;


import com.bluetooth.rmmit.eartemperaturebluetooth.bean.EarTemperatureBean;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.BluetoothUtil;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.IBlueToothMessageCallBack;
import com.bluetooth.rmmit.eartemperaturebluetooth.utils.ULog;

/**
 * 耳温枪返回数据处理
 *
 * @author fenghui
 *
 */
public class EarTemperatureDataHandle extends AbstractDeviceDataHandle {

	private EarTemperatureBean earTemperatureDataBean;
	private byte[] shutdownDatas = {(byte)0xfd, (byte)0x30};

	public EarTemperatureDataHandle(IBlueToothMessageCallBack callback) {
		super(callback);
		this.deviceType = BluetoothUtil.DEVICETYPE_ERWENQIANG;
		this.TAG = "EarTemperatureDataHandle";
		earTemperatureDataBean = new EarTemperatureBean();
	}

	@Override
	public void handlerData(String data) {
		ULog.i(TAG, "receive data : " + data);
		if (super.isTheSameData(data)) {
			return;
		}
		String[] datas = data.split(" ");

		// 解析耳温枪数据

		// 解析数据
		if (datas.length > 3) {
			String earFE = Integer.toHexString(Integer.parseInt(datas[0]));
			if (earFE.equals("ff")) {
				String earGao = Integer.toHexString(Integer.parseInt(datas[2]));
				String earDi = Integer.toHexString(Integer.parseInt(datas[3]));
				String ear = earGao + earDi;
				float earValue = Integer.parseInt(ear, 16);
				earTemperatureDataBean.setTestingData(false);
				earTemperatureDataBean.setTemperData(earValue / 10);
				this.notifyDataToUser(earTemperatureDataBean);
				//发送关机命令
				this.sendDataToDevice(shutdownDatas);
			} else if (earFE.equals("ee")) {
				String Error = Integer.toHexString(Integer.parseInt(datas[1]));
				ULog.e(TAG, "temp error,code is : " + Error);
			}
		}
	}

	@Override
	public void onDeviceConnected() {
		ULog.i(TAG, "weight device connected.");
		earTemperatureDataBean.setTemperData(0f);
		this.notifyDataToUser(earTemperatureDataBean);
	}
}