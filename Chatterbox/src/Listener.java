import java.io.DataInputStream;
import java.io.IOException;

public class Listener extends Thread {
  private static volatile boolean running = true;
  private DataInputStream inputStream;

  Listener(DataInputStream inputStream) {
    this.inputStream = inputStream;
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        try {
          System.out.println(inputStream.readUTF()); // print Server response
        } catch (IOException ioe) {
          ioe.printStackTrace();
          Thread.sleep(2000); // delay before retrying to avoid exhaustion
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}