package com.bluetooth.rmmit.eartemperaturebluetooth.bean;

/**
 * Created by Administrator on 2016/6/28.
 * 三合一(血糖、尿酸、总胆固醇)bean
 */
public class ThreeInOneBean {
    /**
     * 血糖
     */
    private String bloodSugerData;

    /**
     * 血糖单位
     */
    private String bloodSugerUnitText = "mmol/l";

    /**
     * 尿酸
     */
    private String uricAcidData;

    /**
     * 尿酸单位
     */
    private String uricAcidUnitText = "??";

    /**
     * 总胆固醇
     */
    private String cholesterolData;

    /**
     * 总胆固醇单位
     */
    private String cholesterolUnitText = "??";

    /**
     * 是否测量中数据
     */
    private boolean isTestingData = true;


    public String getBloodSugerData() {
        return bloodSugerData;
    }



    public void setBloodSugerData(String bloodSugerData) {
        this.bloodSugerData = bloodSugerData;
    }



    public String getUricAcidData() {
        return uricAcidData;
    }



    public void setUricAcidData(String uricAcidData) {
        this.uricAcidData = uricAcidData;
    }




    public String getCholesterolData() {
        return cholesterolData;
    }



    public void setCholesterolData(String cholesterolData) {
        this.cholesterolData = cholesterolData;
    }



    public String getCholesterolUnitText() {
        return cholesterolUnitText;
    }



    public void setCholesterolUnitText(String cholesterolUnitText) {
        this.cholesterolUnitText = cholesterolUnitText;
    }



    public boolean isTestingData() {
        return isTestingData;
    }



    public void setTestingData(boolean isTestingData) {
        this.isTestingData = isTestingData;
    }

    public String getBloodSugerUnitText() {
        return bloodSugerUnitText;
    }



    public void setBloodSugerUnitText(String bloodSugerUnitText) {
        this.bloodSugerUnitText = bloodSugerUnitText;
    }

    public String getUricAcidUnitText() {
        return uricAcidUnitText;
    }



    public void setUricAcidUnitText(String uricAcidUnitText) {
        this.uricAcidUnitText = uricAcidUnitText;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ThreeForOne,");
        sb.append("isTestingData : ");
        sb.append(this.isTestingData);

        sb.append(" ,bloodSugerData:");
        sb.append(this.bloodSugerData);
        sb.append(this.bloodSugerUnitText);

        sb.append(" ,uricAcidData:");
        sb.append(this.uricAcidData);
        sb.append(this.uricAcidUnitText);

        sb.append(" ,cholesterolData:");
        sb.append(this.cholesterolData);
        sb.append(this.cholesterolUnitText);

        return sb.toString();
    }
}
