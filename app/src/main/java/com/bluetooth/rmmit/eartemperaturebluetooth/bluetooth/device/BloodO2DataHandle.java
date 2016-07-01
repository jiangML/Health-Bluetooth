package com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device;


import com.bluetooth.rmmit.eartemperaturebluetooth.bean.BloodO2Bean;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.BluetoothUtil;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.IBlueToothMessageCallBack;
import com.bluetooth.rmmit.eartemperaturebluetooth.utils.ULog;

/**
 * 血氧返回数据处理
 *
 * @author fenghui
 *
 */
public class BloodO2DataHandle extends AbstractDeviceDataHandle {

	private BloodO2Bean bloodO2Bean;

	public BloodO2DataHandle(IBlueToothMessageCallBack callback) {
		super(callback);
		this.deviceType = BluetoothUtil.DEVICETYPE_TIZHONGCHENG;
		this.TAG = "BloodO2DataHandle";
		bloodO2Bean = new BloodO2Bean();
	}

	@Override
	public void handlerData(String data) {
		ULog.i(TAG, "receive data : " + data);
		if (super.isTheSameData(data)) {
			return;
		}
		String[] datas = data.split(" ");

		//解析血氧数据
		if(datas.length == 6) {
			if(Integer.parseInt(datas[4]) > 0) {
				//血氧
				bloodO2Bean.setBloodO2Data(Integer.parseInt(datas[3]));
				//脉搏率
				bloodO2Bean.setPulseData(Integer.parseInt(datas[4]));
				//返回数据
				this.notifyDataToUser(bloodO2Bean);
			}
		}
	}

	@Override
	public void onDeviceConnected() {
		ULog.i(TAG, "bloodo2 device connected.");
		bloodO2Bean.setBloodO2Data(0);
		bloodO2Bean.setPulseData(0);
		this.notifyDataToUser(bloodO2Bean);

		//AA5504B10000B5
		final byte[] datas = {(byte)0xaa, (byte)0x55, (byte)0x04, (byte)0xb1, 0x00, 0x00, (byte)0xb5};

		new Thread() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sendDataToDevice(datas);
			};
		}.start();

	}
}