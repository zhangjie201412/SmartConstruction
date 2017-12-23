package com.jay.iot.smartconstruction.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jay.iot.smartconstruction.ISmartSocket;
import com.jay.iot.smartconstruction.comm.CsspMessage;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketClientSendingDelegate;
import com.vilyever.socketclient.helper.SocketPacket;
import com.vilyever.socketclient.helper.SocketPacketHelper;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.vilyever.socketclient.util.CharsetUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by H151136 on 12/14/2017.
 */

public class SmartSocketService extends Service {
    private final String TAG = getClass().getName();
    private SocketClient mSocketClient;
    private ClientDelegate mClientDelegate;
    private int mId;

    private ISmartSocket.Stub mBinder = new ISmartSocket.Stub() {
        @Override
        public boolean sendPowerStatus(String dev, int status) throws RemoteException {
            return SmartSocketService.this.sendPowerStatus(dev, status);
        }

        @Override
        public void connectServer() throws RemoteException {
            mSocketClient.connect();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        initSocketClient();
        mId = 0;
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseSocketClient();
        Log.d(TAG, "onDestory");
    }

    private void initSocketClient() {
        mSocketClient = new SocketClient();//new SocketClient("gz01.cssiot.com.cn", 8777);
        mSocketClient.getAddress().setRemoteIP("61.177.48.122");
        mSocketClient.getAddress().setRemotePort("8685");
        mSocketClient.getAddress().setConnectionTimeout(20 * 1000);
        mSocketClient.setCharsetName(CharsetUtil.UTF_8);
        mSocketClient.getSocketPacketHelper().setReadStrategy(SocketPacketHelper.ReadStrategy.AutoReadToTrailer);
        mSocketClient.getSocketPacketHelper().setReceiveTrailerData(new byte[]{0x0a});
        mClientDelegate = new ClientDelegate();
        mSocketClient.registerSocketClientDelegate(mClientDelegate);
    }

    private void releaseSocketClient() {
        mSocketClient.removeSocketClientDelegate(mClientDelegate);
        mSocketClient.disconnect();
    }

    private class ClientDelegate implements SocketClientDelegate {
        @Override
        public void onConnected(SocketClient client) {
            Log.d(TAG, "++onConnected++");
        }

        @Override
        public void onDisconnected(SocketClient client) {
            Log.d(TAG, "++onDisconnected++");
        }

        @Override
        public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
//            mLock.lock();
            try {
                String responseMsg = responsePacket.getMessage();
                Log.d(TAG, "++Response: " + responseMsg + "++");
//                mCondition.signal();
            } finally {
//                mLock.unlock();
            }
        }
    }

    private void sendMessage(CsspMessage message) {
        Log.d(TAG, "==> " + message.makeString());
        mSocketClient.sendString(message.makeString());
    }

    private boolean sendPowerStatus(String dev, int status) {
        boolean ret = false;

        CsspMessage message = new CsspMessage();
        message.setSyncId(mId);
        message.setDevice0(1);
        message.setDevice1(dev);
        message.setDevice2("0");
        String data = "";
        if (status == 0) {
            data = "0";
        } else {
            data = "100";
        }
        message.setType(CsspMessage.TYPE_POWER_INDEX);
        message.setOperation(CsspMessage.OPERATION_NONE);
        message.setNumber(1);
        message.setData(new String[]{data});
        sendMessage(message);

        return ret;
    }
}
