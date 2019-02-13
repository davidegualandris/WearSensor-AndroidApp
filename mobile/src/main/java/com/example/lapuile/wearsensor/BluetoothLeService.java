package com.example.lapuile.wearsensor;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothHealth;
import android.bluetooth.BluetoothHealthAppConfiguration;
import android.bluetooth.BluetoothHealthCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothLeService extends Service {

    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;


    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_BONDED =
            "com.example.bluetooth.le.ACTION_GATT_BONDED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";
    public final static String EXTRA_DES = "DES";

    public final static UUID UUID_HEART_RATE_MEASUREMENT =
            UUID.fromString(DefineGattAttributes.HEART_RATE_MEASUREMENT);
    public final static UUID UUID_BATTERY_LEVEL = UUID.fromString(DefineGattAttributes.BATTERY_LEVEL);
    public final static UUID UUID_BLOOD_PRESSURE = UUID.fromString(DefineGattAttributes.BLOODY_PRESSURE);
    public final static UUID UUID_BODY_SENSOR_LOCATION = UUID.fromString(DefineGattAttributes.BODY_SENSOR_LOCATION);
    private String mServiceName;
    private String value;

    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;


            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());
                /*
            } else if(gatt.getDevice().BOND_BONDED == newState){
                intentAction = ACTION_GATT_BONDED;
                mConnectionState = STATE_CONNECTED;
                Log.i(TAG, "BOOOOOOOONDED");
                broadcastUpdate(intentAction);

                */

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
        }


        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };


    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);

        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml

        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {

            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.d(TAG, String.format("Received heart rate: %d", heartRate));

            intent.putExtra(EXTRA_DATA, String.valueOf(heartRate) + " bpm");
            intent.putExtra(EXTRA_DES, getString(R.string.Heart_Rate_Char_Measurement));

        } else if (UUID_BODY_SENSOR_LOCATION.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();

            int format = BluetoothGattCharacteristic.FORMAT_UINT8;
            final int codeBody = characteristic.getIntValue(format, 0);
            switch (codeBody) {
                case 0:
                    intent.putExtra(EXTRA_DATA, "Other");
                    break;
                case 1:
                    intent.putExtra(EXTRA_DATA, "Chest");
                    break;
                case 2:
                    intent.putExtra(EXTRA_DATA, "Wrist");
                    break;
                case 3:
                    intent.putExtra(EXTRA_DATA, "Finger");
                    break;
                case 4:
                    intent.putExtra(EXTRA_DATA, "Hand");
                    break;
                case 5:
                    intent.putExtra(EXTRA_DATA, "Ear Lobe");
                    break;
                case 6:
                    intent.putExtra(EXTRA_DATA, "Foot");
                    break;
            }
            intent.putExtra(EXTRA_DES, getString(R.string.body_location_description));

        } else if (UUID_BATTERY_LEVEL.equals(characteristic.getUuid())) {
            //int flag = characteristic.getProperties();
            // int format = -1;
            //if ((flag & 0x01) != 0) {
            int format = BluetoothGattCharacteristic.FORMAT_UINT8;
            Log.d(TAG, "battery format UINT8.");

            final int batteryLevel = characteristic.getIntValue(format, 0);
            Log.d(TAG, String.format("Received battery level: %d", batteryLevel));
            intent.putExtra(EXTRA_DATA, String.valueOf(batteryLevel) + " %");
            intent.putExtra(EXTRA_DES, getString(R.string.Battery_level));

        } else if (UUID_BLOOD_PRESSURE.equals(characteristic.getUuid())) {

            int flag = characteristic.getProperties();
            int format = -1;
            format = BluetoothGattCharacteristic.FORMAT_UINT8;
            final int bloodPressure = characteristic.getIntValue(format, 0);
            if ((flag & 0x01) == 1) {

                intent.putExtra(EXTRA_DATA, String.valueOf(bloodPressure) + " kPa");
            } else if ((flag & 0x01) == 0)
                intent.putExtra(EXTRA_DATA, String.valueOf(bloodPressure) + " mmHg");


        } else {
            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for (byte byteChar : data)
                    stringBuilder.append(String.format("%02X ", byteChar));
                intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
            }
        }
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to Bluetoot
//        hAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     * @return Return true if the connection is initiated successfully. The connection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                Log.d(TAG, "Connect");
                return true;
            } else {
                Log.d(TAG, "NOTConnect");
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            device.createBond();
        }
        */
        if (device == null) {
            Log.i(TAG, "Device not found.  Unable to connect.");
            return false;
        }

        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;

    }


    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled        If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

        // This is specific to Heart Rate Measurement.
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(DefineGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }

        if (UUID_BATTERY_LEVEL.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(DefineGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            if (descriptor != null) {
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mBluetoothGatt.writeDescriptor(descriptor);
            }
        }

        if (UUID_BLOOD_PRESSURE.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(DefineGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            if (descriptor != null) {
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mBluetoothGatt.writeDescriptor(descriptor);
            }
        }
    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }
}

/* Do nothing. */ //}
                /*
            }

            sendMessage(STATUS_READ_DATA_DONE, 0);
        }
    }

    private class WriteThread extends Thread {
        private ParcelFileDescriptor mFd;

        public WriteThread(ParcelFileDescriptor fd) {
            super();
            mFd = fd;
        }

        @Override
        public void run() {
            FileOutputStream fos = new FileOutputStream(mFd.getFileDescriptor());
            final byte data_AR[] = new byte[] {         (byte) 0xE3, (byte) 0x00,
                    (byte) 0x00, (byte) 0x2C,
                    (byte) 0x00, (byte) 0x00,
                    (byte) 0x50, (byte) 0x79,
                    (byte) 0x00, (byte) 0x26,
                    (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x80, (byte) 0x00,
                    (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x08,  //bt add for phone, can be automate in the future
                    (byte) 0x3C, (byte) 0x5A, (byte) 0x37, (byte) 0xFF,
                    (byte) 0xFE, (byte) 0x95, (byte) 0xEE, (byte) 0xE3,
                    (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            final byte data_DR[] = new byte[] {         (byte) 0xE7, (byte) 0x00,
                    (byte) 0x00, (byte) 0x12,
                    (byte) 0x00, (byte) 0x10,
                    (byte) 0x00, (byte) 0x24,
                    (byte) 0x02, (byte) 0x01,
                    (byte) 0x00, (byte) 0x0A,
                    (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x0D, (byte) 0x1D,
                    (byte) 0x00, (byte) 0x00 };

            final byte get_MDS[] = new byte[] {         (byte) 0xE7, (byte) 0x00,
                    (byte) 0x00, (byte) 0x0E,
                    (byte) 0x00, (byte) 0x0C,
                    (byte) 0x00, (byte) 0x24,
                    (byte) 0x01, (byte) 0x03,
                    (byte) 0x00, (byte) 0x06,
                    (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00 };

            final byte data_RR[] = new byte[] {         (byte) 0xE5, (byte) 0x00,
                    (byte) 0x00, (byte) 0x02,
                    (byte) 0x00, (byte) 0x00 };

//            final byte data_RRQ[] = new byte[] {        (byte) 0xE4, (byte) 0x00,
//                                                        (byte) 0x00, (byte) 0x02,
//                                                        (byte) 0x00, (byte) 0x00 };
//
//            final byte data_ABORT[] = new byte[] {      (byte) 0xE6, (byte) 0x00,
//                                                        (byte) 0x00, (byte) 0x02,
//                                                        (byte) 0x00, (byte) 0x00 };
            try {
                Log.i(TAG, String.valueOf(count));
                if (count == 1)
                {
                    fos.write(data_AR);
                    Log.i(TAG, "Association Responded!");
                }
                else if (count == 2)
                {
                    fos.write(get_MDS);
                    Log.i(TAG, "Get MDS object attributes!");
                }
                else if (count == 3)
                {
                    fos.write(data_DR);
                    Log.i(TAG, "Data Responsed!");
                }
                else if (count == 4)
                {
                    fos.write(data_RR);
                    Log.i(TAG, "Data Released!");
                }
            } catch(IOException ioe) {}
        }

    }
}


*/