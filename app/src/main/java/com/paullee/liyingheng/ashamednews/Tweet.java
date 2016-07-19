package com.paullee.liyingheng.ashamednews;

import java.io.Serializable;

/**
 * Created by liyingheng on 16/7/15.
 */

public class Tweet implements Serializable {
    private String QID;
    private String UID;
    private String TID;
    private String QIMG;
    private String QVALUE;
    private String QLIKE;
    private String QUNLIKE;
    private String QSHARE;
    private String ISCHECKDE;
    private String USERID;
    private String UNAME;
    private String UHEAD;
    private String TNAME;

    public String getQID() {
        return QID;
    }

    public void setQID(String QID) {
        this.QID = QID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getQIMG() {
        return QIMG;
    }

    public void setQIMG(String QIMG) {
        this.QIMG = QIMG;
    }

    public String getQVALUE() {
        return QVALUE;
    }

    public void setQVALUE(String QVALUE) {
        this.QVALUE = QVALUE;
    }

    public String getQLIKE() {
        return QLIKE;
    }

    public void setQLIKE(String QLIKE) {
        this.QLIKE = QLIKE;
    }

    public String getQUNLIKE() {
        return QUNLIKE;
    }

    public void setQUNLIKE(String QUNLIKE) {
        this.QUNLIKE = QUNLIKE;
    }

    public String getQSHARE() {
        return QSHARE;
    }

    public void setQSHARE(String QSHARE) {
        this.QSHARE = QSHARE;
    }

    public String getISCHECKDE() {
        return ISCHECKDE;
    }

    public void setISCHECKDE(String ISCHECKDE) {
        this.ISCHECKDE = ISCHECKDE;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getUNAME() {
        return UNAME;
    }

    public void setUNAME(String UNAME) {
        this.UNAME = UNAME;
    }

    public String getUHEAD() {
        return UHEAD;
    }

    public void setUHEAD(String UHEAD) {
        this.UHEAD = UHEAD;
    }

    public String getTNAME() {
        return TNAME;
    }

    public void setTNAME(String TNAME) {
        this.TNAME = TNAME;
    }
}
