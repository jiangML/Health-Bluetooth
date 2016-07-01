package com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device;

import com.bluetooth.rmmit.eartemperaturebluetooth.bean.BloodPressureBean;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.BluetoothUtil;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.IBlueToothMessageCallBack;
import com.bluetooth.rmmit.eartemperaturebluetooth.utils.ULog;

import java.util.Calendar;


/**
* 血压计返回数据处理
*/
public class BloodPressureDataHandle extends AbstractDeviceDataHandle {

	private BloodPressureBean bloodPressureBean;
	private byte[] shutdownDatas = {(byte) 0xFF, (byte) 0xFD, 0x02, 0x03};

	public BloodPressureDataHandle(IBlueToothMessageCallBack callback) {
		super(callback);
		this.deviceType = BluetoothUtil.DEVICETYPE_XUEYAJI;
		this.TAG = "BloodPressureDataHandle";
		bloodPressureBean = new BloodPressureBean();
	}

	@Override
	public void handlerData(String data) {
		ULog.i(TAG, "receive data : " + data);
		if (super.isTheSameData(data)) {
			return;
		}
		String[] datas = data.split(" ");

		// 解析血压数据
		int dia = Integer.parseInt(datas[1]);

		if (dia == 254) {         // 错误时
			ULog.e(TAG, "error, value is : " + dia);
		} else {
			// 接收过来的数据分两种数据：一种为测量中数据（短数据），一种为结果数据（长数据），两种数据长度不同，
			if (data.length() > 10) {
				// 该数据为结果数据
				bloodPressureBean.setSbpData(Integer.parseInt(datas[2]));
				bloodPressureBean.setDbpData(Integer.parseInt(datas[4]));
				bloodPressureBean.setPulseData(Integer.parseInt(datas[8]));

				//发送关机命令
				this.sendDataToDevice(shutdownDatas);
				//发送消息到用户端
				this.notifyDataToUser(bloodPressureBean);
			}
		}
	}

	@Override
	public void onDeviceConnected() {
		ULog.i(TAG, "weight device connected.");
		this.notifyDataToUser(bloodPressureBean);

		//设备连接成功后，发送时间信息到设备
		Calendar cale = Calendar.getInstance();
		byte year = (byte)(cale.get(Calendar.YEAR) % 100);
		byte month = (byte)(cale.get(Calendar.MONTH) + 1);
		byte date = (byte)cale.get(Calendar.DATE);
		byte hour = (byte)cale.get(Calendar.HOUR_OF_DAY);
		byte mil = (byte)cale.get(Calendar.MINUTE);
		byte seconds = (byte)cale.get(Calendar.SECOND);
		byte[] setTimeBytes = {(byte)0xff, (byte)0xfd, 0x01, year, month, date, hour, mil, seconds};
		this.sendDataToDevice(setTimeBytes);
	}
}