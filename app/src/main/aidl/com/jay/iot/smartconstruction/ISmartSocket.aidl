// ISmartSocket.aidl
package com.jay.iot.smartconstruction;

// Declare any non-default types here with import statements

interface ISmartSocket {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    boolean sendPowerStatus(String dev, int status);
    void connectServer();
}
