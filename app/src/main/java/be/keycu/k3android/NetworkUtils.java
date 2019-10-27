package be.keycu.k3android;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class NetworkUtils {

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
}
