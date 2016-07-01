package com.bluetooth.rmmit.eartemperaturebluetooth.bean;

/**
 * Created by Administrator on 2016/6/28.
 * 耳温枪
 */
public class EarTemperatureBean {
    /**
     * 体温值
     *
     */
    private float temperData;

    /**
     * 单位
     */
    private String temperUnitText = "摄氏度";

    /**
     * 是否测量中数据
     */
    private boolean isTestingData = true;

    public float getTemperData() {
        return temperData;
    }

    public void setTemperData(float temperData) {
        this.temperData = temperData;
    }

    public String getTemperUnitText() {
        return temperUnitText;
    }

    public void setTemperUnitText(String temperUnitText) {
        this.temperUnitText = temperUnitText;
    }

    public boolean isTestingData() {
        return isTestingData;
    }

    public void setTestingData(boolean isTestingData) {
        this.isTestingData = isTestingData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EarTemperature,");
        sb.append("isTestingData : ");
        sb.append(this.isTestingData);

        sb.append(" ,temperData:");
        sb.append(this.temperData);
        sb.append(this.temperUnitText);

        return sb.toString();
    }
}
