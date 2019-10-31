package be.keycu.k3android;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class BluetoothUtils extends Thread {

    private static final String TAG = BluetoothUtils.class.getSimpleName();

    final static int RECEIVE_MESSAGE = 1;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (for now, we must edit this line)
    private static final String MAC_ADDRESS = "00:18:E4:40:00:06"; // Keycube Yellow

    private BluetoothAdapter mBluetoothAdapter;

    private Handler mHandler;
    private Context mContext;

    private InputStream mmInStream;


    public BluetoothUtils(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(MAC_ADDRESS);

        // Two things are needed to make a connection:
        // A MAC address, which we got above.
        // A Service ID or UUID.  In this case we are using the UUID for SPP.
        BluetoothSocket mBluetoothSocket = null;
        try {
            mBluetoothSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        mBluetoothAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        try {
            assert mBluetoothSocket != null;
            mBluetoothSocket.connect();
        } catch (IOException e) {
            try {
                mBluetoothSocket.close();
            } catch (IOException e2) {
                errorExit("In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Get the input stream
        try {
            mmInStream = mBluetoothSocket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if (mBluetoothAdapter == null) {
            errorExit("Bluetooth not support");
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                //Prompt user to turn on Bluetooth
                // Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // mContext.startActivityForResult(enableBtIntent, 1);
                errorExit("Request enable Bluetooth");
            }
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, MY_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    private void errorExit(String message){
        Toast.makeText(mContext, "Fatal Error - " + message, Toast.LENGTH_LONG).show();
        // finish();
    }

    public void run() {

        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                byte[] buffer = new byte[32];  // buffer store for the stream
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                // Get number of bytes and message in "buffer"
                mHandler.obtainMessage(RECEIVE_MESSAGE, bytes, -1, buffer).sendToTarget(); // Send to message queue Handler
            } catch (IOException e) {
                break;
            }
        }
    }
}
