package e.hanglungdemo.ui.bean;

public class TraceBean {
    /** 时间 */
     String acceptTime;
    /** 描述 */
     String acceptStation;

    public TraceBean() {
    }

    public TraceBean(String acceptTime, String acceptStation) {
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }


}
