package com.tvo.nano.json;

import com.tvo.nano.models.CurrentMonthData;
import com.tvo.nano.models.PreviousMonthData;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Son on 05-May-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSON_SalesReport {
    @JsonProperty("CurrentMonthData")
    private ArrayList<CurrentMonthData> arrCurrentMonthData;
    @JsonProperty("PreviousMonthData")
    private ArrayList<PreviousMonthData> arrPreviousMonthData;

    public ArrayList<CurrentMonthData> getArrCurrentMonthData() {
        return arrCurrentMonthData;
    }

    public void setArrCurrentMonthData(ArrayList<CurrentMonthData> arrCurrentMonthData) {
        this.arrCurrentMonthData = arrCurrentMonthData;
    }

    public ArrayList<PreviousMonthData> getArrPreviousMonthData() {
        return arrPreviousMonthData;
    }

    public void setArrPreviousMonthData(ArrayList<PreviousMonthData> arrPreviousMonthData) {
        this.arrPreviousMonthData = arrPreviousMonthData;
    }
}
