package com.bluetooth.rmmit.eartemperaturebluetooth.bluetooth;

/**
 * 蓝牙消息回调类
 * @author fenghui
 *
 */
public interface IBlueToothMessageCallBack {
	/**
	 * 接收到数据
	 */
	void onReceiveMessage(Object obj);

	/**
	 * 断开连接
	 * @return
	 */
	int onDisConnected();

	/**
	 * 写入数据
	 * @param bs
	 */
	void writeData(byte[] bs);
}