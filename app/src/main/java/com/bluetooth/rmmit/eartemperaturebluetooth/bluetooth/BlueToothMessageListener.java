package com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth;

import java.util.UUID;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;

import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device.AbstractDeviceDataHandle;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device.BloodO2DataHandle;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device.BloodPressureDataHandle;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device.EarTemperatureDataHandle;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device.ThreeInOneDataHandle;
import com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth.device.WeightDataHandle;
import com.bluetooth.rmmit.eartemperaturebluetooth.utils.ULog;


/**
 * 蓝牙消息监听类
 * @author fenghui
 *
 */
@SuppressLint("NewApi")
public class BlueToothMessageListener {
	//tag
	private static final String TAG = "BlueToothMessageListener";

	//activity
	private Activity targetActivity;
	//回调对象
	private IBlueToothMessageCallBack btMsgCallBack;
	//是否扫描
	private boolean mScanning = false;

	//adapter
	private BluetoothAdapter mBluetoothAdapter;
	//当前测量设备类型
	private int currDeviceType = -1;
	//当前连接设备
	private BluetoothDevice currConnectDevice = null;
	private BluetoothGatt mBluetoothGatt;

	//数据处理
	private AbstractDeviceDataHandle deviceDataHandler;

	//蓝牙设备监听端口
	String[] deviceReadNotifyUUIDs = null;
	int deviceReadNotifyIndex = 0;
	BluetoothGattService btService = null;

	private int deviceType=-1; //指定连接的蓝牙类型 分别有耳温枪 体重秤 血压计


	public BlueToothMessageListener(Activity activity, IBlueToothMessageCallBack msgCallBack) {
		this.targetActivity = activity;
		this.btMsgCallBack = msgCallBack;
	}

	public void  setDeviceType(int deviceType)
	{
		this.deviceType=deviceType;
	}



	/**
	 * 开始监听蓝牙消息
	 * @return 0成功，其他失败
	 */
	public int startListenerMessage() {

		// 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
		if (!targetActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
			return BluetoothUtil.START_DEVICE_CODE_UNSUPPORTBLE;
		}

		// 初始化 Bluetooth adapter, 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上和版本)
		BluetoothManager bluetoothManager = (BluetoothManager) targetActivity.getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		// 检查设备上是否支持蓝牙
		if (mBluetoothAdapter == null) {
			return BluetoothUtil.START_DEVICE_CODE_UNSUPPORTBT;
		}

		// 如果蓝牙没有打开，则直接打开
		if(!mBluetoothAdapter.isEnabled()) {
			mBluetoothAdapter.enable();
		}

		scanLeDevice(true);


		return BluetoothUtil.START_DEVICE_CODE_SUCCESS;
	}

	/**
	 * 关闭蓝牙消息监听
	 */
	public void stopListenerMessage() {
		if(mScanning) {
			scanLeDevice(false);
		}
		currConnectDevice = null;
		if(currConnectDevice != null) {
			//mBluetoothGatt.close();
			mBluetoothGatt = null;
		}
	}

	@SuppressWarnings("unused")
	private void restartListenerMessage() {
		stopListenerMessage();
		startListenerMessage();
	}

	//扫描设备
	private void scanLeDevice(boolean enable) {
		if (enable) {
			mScanning = true;
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
	}

	// Device scan callback.
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
			ULog.i(TAG, "BluetoothDevice  name=" + device.getName() + " address=" + device.getAddress());
			String name = device.getName();

			currDeviceType = BluetoothUtil.getDeviceTypeByDeviceName(name);
			if(currDeviceType > -1) {
				ULog.i(TAG,"=========扫描到倍泰蓝牙设备==========");
				if(currDeviceType == BluetoothUtil.DEVICETYPE_ERWENQIANG&&deviceType==BluetoothUtil.DEVICETYPE_ERWENQIANG) {
					deviceDataHandler = new EarTemperatureDataHandle(btMsgCallBack);
				} else if(currDeviceType == BluetoothUtil.DEVICETYPE_TIZHONGCHENG&&deviceType==BluetoothUtil.DEVICETYPE_TIZHONGCHENG) {
					deviceDataHandler = new WeightDataHandle(btMsgCallBack);
				} else if(currDeviceType == BluetoothUtil.DEVICETYPE_XUEYAJI&&deviceType==BluetoothUtil.DEVICETYPE_XUEYAJI){
					deviceDataHandler = new BloodPressureDataHandle(btMsgCallBack);
				} else if(currDeviceType == BluetoothUtil.DEVICETYPE_XUEYANGYI&&deviceType==BluetoothUtil.DEVICETYPE_XUEYANGYI) {
					deviceDataHandler = new BloodO2DataHandle(btMsgCallBack);
				} else if(currDeviceType == BluetoothUtil.DEVICETYPE_THREEINONE&&deviceType==BluetoothUtil.DEVICETYPE_THREEINONE) {
					deviceDataHandler = new ThreeInOneDataHandle(btMsgCallBack);
				}else {
					//deviceDataHandler = new WeightDataHandle(btMsgCallBack);
					ULog.i(TAG,"----指定连接的蓝牙设备不正确-----");
				}

				currConnectDevice = device;
				mBluetoothGatt = device.connectGatt(targetActivity, false, mGattCallback);
				if (mScanning) {
					scanLeDevice(false);
				}
			}else{
				ULog.i(TAG,"------没有扫描到相关蓝牙设备------");
			}
			//这里需要创建不同设备的具体数据处理对象
		}
	};

	//连接回调信息
	private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
			ULog.i(TAG, "onConnectionStateChange, status:" + status + ",new status:" + newState);
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				//连接状态改变为连接成功
				mBluetoothGatt.discoverServices();
				ULog.i("tag","连接成功");
			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				ULog.i("tag","连接失败");
				//连接状态改变为未连接
				new Thread() {
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(!mScanning) {
							scanLeDevice(true);
						}
					};
				}.start();
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				//搜寻设备完毕，写入数据特征到设备中

				if (mBluetoothAdapter == null || mBluetoothGatt == null) {
					ULog.e(TAG, "BluetoothAdapter not initialized");
					return;
				}
				btService = mBluetoothGatt.getService(UUID.fromString(BluetoothUtil.getDeviceServiceUUID(currDeviceType)));
				if(btService == null) {
					ULog.e(TAG, "service is null.");
					return;
				}

				//btService.getCharacteristic(uuid)


				//数据通知uuid列表
				deviceReadNotifyUUIDs = BluetoothUtil.getReadUUIDsByDeviceType(currDeviceType);
				deviceReadNotifyIndex = 0;


//				for(String item : deviceReadNotifyUUIDs) {
//					BluetoothGattCharacteristic characteristic = btService.getCharacteristic(UUID.fromString(item));
//					if(characteristic != null) {
//						mBluetoothGatt.setCharacteristicNotification(characteristic, true);
//						BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(BluetoothUtil.CLIENT_CHARACTERISTIC_CONFIG));
//						if(descriptor != null) {
//							descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//							mBluetoothGatt.writeDescriptor(descriptor);
//						}
//						mBluetoothGatt.readCharacteristic(characteristic);
//					}
//				}
//				deviceDataHandler.onDeviceConnected();

				BluetoothGattCharacteristic characteristic = btService.getCharacteristic(UUID.fromString(deviceReadNotifyUUIDs[0]));
				if(characteristic != null) {
					mBluetoothGatt.setCharacteristicNotification(characteristic, true);
					BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(BluetoothUtil.CLIENT_CHARACTERISTIC_CONFIG));
					if(descriptor != null) {
						descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
						mBluetoothGatt.writeDescriptor(descriptor);
					}
					mBluetoothGatt.readCharacteristic(characteristic);
				}

			} else {
				ULog.w(TAG, "onServicesDiscovered received: " + status);
			}
		}


		public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor gattDescriptor, int status) {
			if(deviceReadNotifyUUIDs.length - 1 == deviceReadNotifyIndex) {
				//这里通知前端，设备连接成功
				deviceDataHandler.onDeviceConnected();
			} else {
				deviceReadNotifyIndex++;
				BluetoothGattCharacteristic characteristic = btService.getCharacteristic(UUID.fromString(deviceReadNotifyUUIDs[deviceReadNotifyIndex]));
				if(characteristic != null) {
					mBluetoothGatt.setCharacteristicNotification(characteristic, true);
					BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(BluetoothUtil.CLIENT_CHARACTERISTIC_CONFIG));
					if(descriptor != null) {
						descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
						mBluetoothGatt.writeDescriptor(descriptor);
					}
					mBluetoothGatt.readCharacteristic(characteristic);
				}
			}
		};

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
			//读取到数据
			ULog.i(TAG, "onCharacteristicRead");
			if (status == BluetoothGatt.GATT_SUCCESS) {
				ULog.i("TAG","onCharacteristicRead-->"+ characteristic.getValue().toString());
				//broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
			}
		}

		/**
		 * 返回数据。
		 */
		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
			// 数据
			String str = "";
			for (int i = 0; i < characteristic.getValue().length; i++) {
				str = str + (characteristic.getValue()[i] & 0xff) + " ";
			}
			ULog.i(TAG, str);
			deviceDataHandler.handlerData(str);
		}
	};

	/**
	 * 写入数据到设备
	 * @param bb
	 */
	public void writeLlsAlertLevel(byte[] bb) {
		BluetoothGattService linkLossService = mBluetoothGatt.getService(UUID.fromString(BluetoothUtil.getDeviceServiceUUID(currDeviceType)));
		if (linkLossService == null) {
			ULog.e(TAG, "link loss Alert service not found!");
			return;
		}
		BluetoothGattCharacteristic alertLevel = linkLossService.getCharacteristic(UUID.fromString(BluetoothUtil.getWriteUUIDByDeviceType(currDeviceType)));

		if (alertLevel == null) {
			ULog.e(TAG, "link loss Alert Level charateristic not found!");
			return;
		}

		boolean status = false;
		int storedLevel = alertLevel.getWriteType();
		ULog.d(TAG, "storedLevel() - storedLevel=" + storedLevel);

		byte[] aa={(byte)0xfd,(byte)0x29};

		alertLevel.setValue(aa);


		alertLevel.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
		status = mBluetoothGatt.writeCharacteristic(alertLevel);
	//	ULog.i("tag","发送关机命令结果："+status);
		ULog.i("tag","发送数据接收成功命令："+status);
		ULog.d(TAG, "writeLlsAlertLevel() - status=" + status);
		mBluetoothGatt.disconnect();
		stopListenerMessage();
	}
} 