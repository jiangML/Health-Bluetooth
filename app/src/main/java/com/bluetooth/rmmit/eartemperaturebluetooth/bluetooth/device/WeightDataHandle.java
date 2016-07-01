package com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device;


import com.bluetooth.rmmit.eartemperaturebluetooth.bean.WeightBean;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.BluetoothUtil;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.IBlueToothMessageCallBack;
import com.bluetooth.rmmit.eartemperaturebluetooth.utils.ULog;

/**
 * 体重秤返回数据处理
 */
public class WeightDataHandle extends AbstractDeviceDataHandle {

	private WeightBean weightBean;
	private byte[] shutdownDatas = {(byte)0xfd, (byte)0x30};

	public WeightDataHandle(IBlueToothMessageCallBack callback) {
		super(callback);
		this.deviceType = BluetoothUtil.DEVICETYPE_TIZHONGCHENG;
		this.TAG = "WeightDataHandle";
		weightBean = new WeightBean();
	}

	@Override
	public void handlerData(String data) {
		ULog.i(TAG, "receive data : " + data);
		if (super.isTheSameData(data)) {
			return;
		}
		String[] datas = data.split(" ");

		// 解析体重数据
		String weightGao = Integer.toHexString(Integer.parseInt(datas[1]));
		String weightDi = Integer.toHexString(Integer.parseInt(datas[2]));
		if (Integer.parseInt(weightDi, 16) <= 16) {
			weightDi = "0" + weightDi;
		}
		String weight = weightGao.substring(1, 2) + weightDi;
		float weightValue = Integer.parseInt(weight, 16);
		weightValue = weightValue / 10;

		weightBean.setWeightData(weightValue);

		//测量中数据
//		if(datas.length <= 9) {
//			byte[] bodyBytes = {(byte)0xfd, 0x53, 0x00, 0x00, 0x50, 0x18, (byte)0xb2};
//			this.sendDataToDevice(bodyBytes);
//			return;
//		}

		//结果值
		if(datas[0].equals("255")) {
			weightBean.setTestingData(false);
			//发送关机命令
			this.sendDataToDevice(shutdownDatas);
		} else {
			//测量中值
			weightBean.setTestingData(true);
		}

		this.notifyDataToUser(weightBean);
	}

	@Override
	public void onDeviceConnected() {
		ULog.i(TAG, "weight device connected.");
		weightBean.setWeightData(0f);
		this.notifyDataToUser(weightBean);
	}
}