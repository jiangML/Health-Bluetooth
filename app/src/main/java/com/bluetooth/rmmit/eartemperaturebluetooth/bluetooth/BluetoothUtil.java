package com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth;

import com.bluetooth.rmmit.eartemperaturebluetooth.utils.ULog;

/**
 * ble工具类
 * @author fenghui
 *
 */
public class BluetoothUtil {

	//初始化监听消息返回结果
	public static final int START_DEVICE_CODE_SUCCESS = 0;
	public static final int START_DEVICE_CODE_UNSUPPORTBLE = -1;
	public static final int START_DEVICE_CODE_UNSUPPORTBT = -2;
	public static final int START_DEVICE_CODE_ERROR = -3;


	//设备类型
	public final static int DEVICETYPE_ERWENQIANG = 0;
	public final static int DEVICETYPE_XUEYAJI = 1;
	public final static int DEVICETYPE_TIZHONGCHENG = 2;
	public final static int DEVICETYPE_XUETANGYI = 3;
	public final static int DEVICETYPE_XUEYANGYI = 4;
	public final static int DEVICETYPE_THREEINONE = 5;

	//设备标识
	public final static String DEVICENAMETAGS_ERWENQIANG = "e-Thermometer,CSR-SP";
	public final static String DEVICENAMETAGS_TIZHONGCHENG = "eBody-Scale,eBody-Fat-Scale";
	public final static String DEVICENAMETAGS_XUEYAJI = "eBlood-Pressure";
	public final static String DEVICENAMETAGS_XUETANGYI = "";
	public final static String DEVICENAMETAGS_XUEYANGYI = "iChoice";
	public final static String DEVICENAMETAGS_THREEINONE = "BeneCheck";

	//设备关机命令
	public final static byte[][] DEVICESHUTDOWNCOMMD = {
			{(byte)0xfd, (byte)0x30},
			{(byte)0xfd, (byte)0x30},
			{(byte)0xfd, (byte)0x30},
			{(byte) 0xFF, (byte) 0xFD, 0x02, 0x03}
	};

	//设备Service uuid
	public final static String[] DEVICE_SERVICE_UUID = {
			"0000fff0-0000-1000-8000-00805f9b34fb",
			"0000fff0-0000-1000-8000-00805f9b34fb",
			"0000fff0-0000-1000-8000-00805f9b34fb",
			"0000ffe0-0000-1000-8000-00805f9b34fb",
			"ba11f08c-5f14-0b0d-1080-007cbe2387d2",
			""
	};

	//设备读数据uuid
	public final static String[][] READ_CHARACTERISTICSUUID = {
			{"0000fff4-0000-1000-8000-00805f9b34fb"},
			{"0000fff4-0000-1000-8000-00805f9b34fb"},
			{"0000fff4-0000-1000-8000-00805f9b34fb"},
			{"0000ffe1-0000-1000-8000-00805f9b34fb"},
			{
					"0000cd01-0000-1000-8000-00805f9b34fb",
					"0000cd02-0000-1000-8000-00805f9b34fb",
					"0000cd03-0000-1000-8000-00805f9b34fb",
					"0000cd04-0000-1000-8000-00805f9b34fb"
			},
			{"00001808-0000-1000-8000-00805f9b34fb"}
	};

	//设备写数据uuid
	public final static String[] WRITE_CHARACTERISTICSUUID = {
			"0000fff3-0000-1000-8000-00805f9b34fb",
			"0000fff3-0000-1000-8000-00805f9b34fb",
			"0000fff3-0000-1000-8000-00805f9b34fb",
			"0000ffe1-0000-1000-8000-00805f9b34fb",
			"0000cd20-0000-1000-8000-00805f9b34fb",
			""
	};

	//
	public final static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

	/**
	 * 根据设备名称查询设备类型
	 * @param deviceName
	 * @return
	 */
	public static int getDeviceTypeByDeviceName(String deviceName) {
		String[] deviceNames = {
				DEVICENAMETAGS_ERWENQIANG,
				DEVICENAMETAGS_XUEYAJI,
				DEVICENAMETAGS_TIZHONGCHENG,
				DEVICENAMETAGS_XUETANGYI,
				DEVICENAMETAGS_XUEYANGYI,
				DEVICENAMETAGS_THREEINONE
		};
		deviceName = deviceName.toLowerCase();
		ULog.i("tag","name: "+deviceName);
		for(int i = 0; i < deviceNames.length; i++) {
//			if(deviceNames[i].toLowerCase().indexOf(deviceName) > -1) {
//				return i;
//			}
			if (deviceNames[i].toLowerCase().contains(deviceName))
			{
				ULog.i("tag","index: "+i);
				return i;
			}

		}
		return -1;
	}


	/**
	 * 返回设备数据读取uuid
	 * @param deviceType
	 * @return
	 */
	public static String[] getReadUUIDsByDeviceType(int deviceType) {
		return READ_CHARACTERISTICSUUID[deviceType];
	}

	/**
	 * 返回设备数据写入uuid
	 * @param deviceType
	 * @return
	 */
	public static String getWriteUUIDByDeviceType(int deviceType) {
		return WRITE_CHARACTERISTICSUUID[deviceType];
	}

	/**
	 * 返回设备service uuid
	 * @param deviceType
	 * @return
	 */
	public static String getDeviceServiceUUID(int deviceType) {
		return DEVICE_SERVICE_UUID[deviceType];
	}
}