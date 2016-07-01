package com.bluetooth.rmmit.eartemperaturebluetooth.bean;

/**
 * Created by Administrator on 2016/6/28.
 */
public class BloodPressureBean {
    /**
     * 收缩压
     */
    private int sbpData;

    /**
     * 舒张压
     */
    private int dbpData;

    /**
     * 脉搏
     */
    private int pulseData;

    /**
     * 收缩压单位
     */
    private String sbpUnitText = "毫米汞柱";

    /**
     * 舒张压单位
     */
    private String dbpUnitText = "毫米汞柱";

    /**
     * 脉搏单位
     */
    private String pulseUnitText = "次/分钟";

    /**
     * 是否测量中数据
     */
    private boolean isTestingData = true;

    public int getSbpData() {
        return sbpData;
    }

    public void setSbpData(int sbpData) {
        this.sbpData = sbpData;
    }

    public int getDbpData() {
        return dbpData;
    }

    public void setDbpData(int dbpData) {
        this.dbpData = dbpData;
    }

    public int getPulseData() {
        return pulseData;
    }

    public void setPulseData(int pulseData) {
        this.pulseData = pulseData;
    }

    public String getSbpUnitText() {
        return sbpUnitText;
    }

    public void setSbpUnitText(String sbpUnitText) {
        this.sbpUnitText = sbpUnitText;
    }

    public String getDbpUnitText() {
        return dbpUnitText;
    }

    public void setDbpUnitText(String dbpUnitText) {
        this.dbpUnitText = dbpUnitText;
    }

    public String getPulseUnitText() {
        return pulseUnitText;
    }

    public void setPulseUnitText(String pulseUnitText) {
        this.pulseUnitText = pulseUnitText;
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
        sb.append("BloodPressure,");
        sb.append("isTestingData : ");
        sb.append(this.isTestingData);

        sb.append(" ,sbpData:");
        sb.append(this.sbpData);
        sb.append(this.sbpUnitText);

        sb.append(" ,dbpData:");
        sb.append(this.dbpData);
        sb.append(this.dbpUnitText);

        sb.append(" ,pulseData:");
        sb.append(this.pulseData);
        sb.append(this.pulseUnitText);

        return sb.toString();
    }
}
