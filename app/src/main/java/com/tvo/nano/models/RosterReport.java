package com.tvo.nano.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 24-Apr-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RosterReport {
    @JsonProperty("reportmonth")
    private String reportmonth;
    @JsonProperty("reportpath")
    private String reportpath;

    public String getReportmonth() {
        return reportmonth;
    }

    public void setReportmonth(String reportmonth) {
        this.reportmonth = reportmonth;
    }

    public String getReportpath() {
        return reportpath;
    }

    public void setReportpath(String reportpath) {
        this.reportpath = reportpath;
    }
}
