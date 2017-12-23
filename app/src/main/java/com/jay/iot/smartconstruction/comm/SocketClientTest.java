package com.jay.iot.smartconstruction.comm;

import android.support.annotation.NonNull;
import android.util.Log;

import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientAddress;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketClientReceivingDelegate;
import com.vilyever.socketclient.helper.SocketClientSendingDelegate;
import com.vilyever.socketclient.helper.SocketHeartBeatHelper;
import com.vilyever.socketclient.helper.SocketPacket;
import com.vilyever.socketclient.helper.SocketPacketHelper;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.vilyever.socketclient.util.CharsetUtil;
import com.vilyever.socketclient.util.IPUtil;

import java.net.URISyntaxException;

/**
 * Created by H151136 on 12/6/2017.
 */

public class SocketClientTest {
    private static final String TAG = "SocketClientTest";
    private SocketClient mSocketClient;

    public SocketClientTest() {
        mSocketClient = new SocketClient();//new SocketClient("gz01.cssiot.com.cn", 8777);
        mSocketClient.getAddress().setRemoteIP("61.177.48.122");
//        mSocketClient.getAddress().setRemoteIP("192.168.3.63");
        mSocketClient.getAddress().setRemotePort("8777");
        mSocketClient.getAddress().setConnectionTimeout(20 * 1000);
        mSocketClient.setCharsetName(CharsetUtil.UTF_8);
        mSocketClient.getSocketPacketHelper().setReadStrategy(SocketPacketHelper.ReadStrategy.AutoReadToTrailer);
        mSocketClient.getSocketPacketHelper().setReceiveTrailerData(new byte[]{0x0a});
        mSocketClient.registerSocketClientDelegate(new SocketClientDelegate() {
            @Override
            public void onConnected(SocketClient client) {
                Log.d(TAG, "onConnected");
            }

            @Override
            public void onDisconnected(SocketClient client) {
                Log.d(TAG, "onDisonnected");
            }

            @Override
            public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                String responseMsg = responsePacket.getMessage();
                Log.d(TAG, "Server: " + responseMsg);
            }
        });
//        mSocketClient.registerSocketClientReceiveDelegate(new SocketClientReceivingDelegate() {
//            @Override
//            public void onReceivePacketBegin(SocketClient client, SocketResponsePacket packet) {
//            }
//
//            @Override
//            public void onReceivePacketEnd(SocketClient client, SocketResponsePacket packet) {
//                Log.d(TAG, "receive end: " + packet.getMessage());
//            }
//
//            @Override
//            public void onReceivePacketCancel(SocketClient client, SocketResponsePacket packet) {
//
//            }
//
//            @Override
//            public void onReceivingPacketInProgress(SocketClient client, SocketResponsePacket packet, float progress, int receivedLength) {
//            }
//        });
        mSocketClient.registerSocketClientSendingDelegate(new SocketClientSendingDelegate() {
            @Override
            public void onSendPacketBegin(SocketClient client, SocketPacket packet) {
            }

            @Override
            public void onSendPacketEnd(SocketClient client, SocketPacket packet) {
            }

            @Override
            public void onSendPacketCancel(SocketClient client, SocketPacket packet) {

            }

            @Override
            public void onSendingPacketInProgress(SocketClient client, SocketPacket packet, float progress, int sendedLength) {
            }
        });

    }

    public void connect() {
        mSocketClient.connect();
    }

    public void send(String message) {
//        mSocketClient.sendString(message);
        SocketPacket pkg = mSocketClient.sendString(message);
        Log.d(TAG, "-> " + pkg.getMessage());
    }

    public void close() {
        mSocketClient.disconnect();
    }

}
