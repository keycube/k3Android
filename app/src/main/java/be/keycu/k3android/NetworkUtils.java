package be.keycu.k3android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

class NetworkUtils {

    private static String KEY_IPADDRESS = "key_ip_address";

    public static class SendDataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... serverInfo) {
            try {
                InetAddress serverAddress = InetAddress.getByName(serverInfo[0]);
                // range between 49152–65535 for dynamic, private and ephemeral ports
                Socket socket = new Socket(serverAddress, 55555);
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(serverInfo[1]);
                socket.close();
            } catch (Exception ignored) {

            }
            return null;
        }
    }

    static String getIpAddressPref(Context context) {
        String value = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getString(KEY_IPADDRESS, null);
        }
        return value;
    }

    static void setIpAddressPref(Context context, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null && !TextUtils.isEmpty(value)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(KEY_IPADDRESS, value);
            editor.apply();
        }
    }
}
