package com.jay.iot.smartconstruction.comm;

import android.util.Log;

/**
 * Created by H151136 on 12/19/2017.
 */

public class CsspMessage {
    public static final String SPLIT = ",";
    public static final String FRAME_HEAD = ">>";
    public static final String PROTOCOL_ID = "cssp";

    public static final int DEVICE_SOURCE = 0;
    public static final int DEVICE_TARGET = 1;

    public static final int OPERATION_NONE = 0;//"none";
    public static final int OPERATION_GET = 1;//"get";
    public static final int OPERATION_SET = 2;//"set";

    public static final String OPERATION_ARRAY[] = {
            "none",
            "get",
            "set"
    };

    public static final String FRAME_END = "\n";

    public static final String TYPE_POWER = "power";
    public static final String TYPE_BATTERY = "battery";
    public static final String TYPE_VERSION = "ver";
    public static final String TYPE_BSTATION = "bstation";
    public static final String TYPE_HBEAT = "hbeat";
    public static final String TYPE_ERR = "err";
    public static final String TYPE_ALMSTA = "almsta";
    public static final String TYPE_USELIST = "uselist";
    public static final String TYPE_USENODESTA = "usenodesta";
    public static final String TYPE_CAL_TRSD = "cal&trst";
    public static final String TYPE_CALMAC = "calmac";
    public static final String TYPE_MATCHLIST = "matchlist";
    public static final String TYPE_SENSORID = "sensorid";
    public static final String TYPE_RAW = "raw";
    public static final String TYPE_CAL_CON = "cal.con";
    public static final String TYPE_PERALARM = "peralarm";
    public static final String TYPE_FIN = "fin";

    public static final String[] TYPE_ARRAY = {
            TYPE_POWER,
            TYPE_BATTERY,
            TYPE_VERSION,
            TYPE_BSTATION,
            TYPE_HBEAT,
            TYPE_ERR,
            TYPE_ALMSTA,
            TYPE_USELIST,
            TYPE_USENODESTA,
            TYPE_CAL_TRSD,
            TYPE_CALMAC,
            TYPE_MATCHLIST,
            TYPE_SENSORID,
            TYPE_RAW,
            TYPE_CAL_CON,
            TYPE_PERALARM,
            TYPE_FIN,
    };

    public static final int TYPE_POWER_INDEX = 0;
    public static final int TYPE_BATTERY_INDEX = 1;
    public static final int TYPE_VERSION_INDEX = 2;
    public static final int TYPE_BSTATION_INDEX = 3;
    public static final int TYPE_HBEAT_INDEX = 4;
    public static final int TYPE_ERR_INDEX = 5;
    public static final int TYPE_ALMSTA_INDEX = 6;
    public static final int TYPE_USELIST_INDEX = 7;
    public static final int TYPE_USENODESTA_INDEX = 8;
    public static final int TYPE_CAL_TRSD_INDEX = 9;
    public static final int TYPE_CALMAC_INDEX = 10;
    public static final int TYPE_MATCHLIST_INDEX = 11;
    public static final int TYPE_SENSORID_INDEX = 12;
    public static final int TYPE_RAW_INDEX = 13;
    public static final int TYPE_CAL_CON_INDEX = 14;
    public static final int TYPE_PERALARM_INDEX = 15;
    public static final int TYPE_FIN_INDEX = 16;

    public static final String RSP_OK = "ok";
    public static final String RSP_ERROR = "error";
    public static final String RSP_REJECT = "reject";

    private int mSyncId;
    private int mDevice0;
    private String mDevice1;
    private String mDevice2;
    private int mType;
    private int mOperation;
    private int mNumber;
    private String[] mData;
    private int mChecksum;

    public int getSyncId() {
        return mSyncId;
    }

    public void setSyncId(int id) {
        mSyncId = id;
    }

    public int getDevice0() {
        return mDevice0;
    }

    public void setDevice0(int device0) {
        mDevice0 = device0;
    }

    public String getDevice1() {
        return mDevice1;
    }

    public void setDevice1(String device1) {
        mDevice1 = device1;
    }

    public String getDevice2() {
        return mDevice2;
    }

    public void setDevice2(String device2) {
        mDevice2 = device2;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getOperation() {
        return mOperation;
    }

    public void setOperation(int operation) {
        mOperation = operation;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int num) {
        mNumber = num;
    }

    public String[] getData() {
        return mData;
    }

    public void setData(String[] data) {
        mData = data;
    }

    public int getChecksum() {
        return mChecksum;
    }

    public void setChecksum(int cs) {
        mChecksum = cs;
    }

    private int calcCheckSum(String data, int len) {
        int cs = 0x00;
        byte[] bytes = data.getBytes();

        for (int i = 0; i < len; i++) {
            cs = cs ^ (char) bytes[i];
        }
        return cs;
    }

    public String makeString() {
        String value = "";
        value += "" + FRAME_HEAD + PROTOCOL_ID + SPLIT;
        value += "" + getSyncId() + SPLIT;
        value += "" + getDevice0() + SPLIT;
        value += "" + getDevice1() + SPLIT;
        value += "" + getDevice2() + SPLIT;
        value += "" + TYPE_ARRAY[getType()] + SPLIT;
        value += "" + OPERATION_ARRAY[getOperation()] + SPLIT;
        value += "" + getNumber() + SPLIT;
        for (String data : mData) {
            value += "" + data + SPLIT;
        }
        int sum = calcCheckSum(value, value.length());
        String sum_fmt = String.format("%02x", sum);
        value += "" + sum_fmt + FRAME_END;

        return value;
    }

    public static class Builder {

    }
}
