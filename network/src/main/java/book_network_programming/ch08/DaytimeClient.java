package book_network_programming.ch08;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class DaytimeClient {
  public void getTime() {
    try (Socket sock = new Socket("time.nist.gov", 13)) {
      // Setting a timeout on the socket means that each read from or write to the socket will take
      // at most a certain number of milliseconds.
      sock.setSoTimeout(15000);
      InputStream in = sock.getInputStream();
      InputStreamReader reader = new InputStreamReader(in, "ASCII");
      StringBuilder time = new StringBuilder();
      for (int c = reader.read(); c != -1; c = reader.read()) {
        time.append((char) c);
      }
      System.out.println(time);
    } catch (IOException e) {
      System.err.println("Could not connect to time.nist.gov");
    }
  }
}
