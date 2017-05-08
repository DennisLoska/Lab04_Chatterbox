import java.io.DataInputStream;
import java.io.IOException;

/*
* Because we want to be able to receive messages at any time we moved all of the InputStream
* logic to a new Thread that continously runs in the background
*/
public class Listener extends Thread {
  private DataInputStream inputStream;

  Listener(DataInputStream inputStream) {
    this.inputStream = inputStream;
  }

  @Override
  public void run() {
    while (inputStream != null || !Thread.currentThread().isInterrupted()) {
      try {
        try {
          System.out.println(inputStream.readUTF()); // receives & prints Server messages
        } catch (IOException ioe) {
          ioe.printStackTrace();
          Thread.sleep(2000); // delay before retrying to avoid exhaustion
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt(); // sends signal to stop the Thread
      }
    }
  }
}