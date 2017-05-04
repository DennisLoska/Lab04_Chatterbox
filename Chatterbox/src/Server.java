import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server implements Runnable {
  private boolean isRunning;
  private ServerSocket serverSocket;
  private Socket server;
  private DataInputStream in;
  private DataOutputStream out;
  private Thread thread;
  private static final int timeout = 20000;

  public Server(int port) {
    try {
      serverSocket = new ServerSocket(port);
      if (timeout > 0)
        serverSocket.setSoTimeout(timeout);
      System.out.println("Server running on port " + port);
      start();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Server server = new Server(8080);
  }

  public void run() {
    while (thread != null && !serverSocket.isClosed()) {
      System.out.print("Waiting for client... ");
      try {
        server = serverSocket.accept(); // establish client connection
        System.out.println("SUCCESS");
        printConnectionDetail();
        open();
        isRunning = true;
        out.writeUTF("Hello there, this is the Server !");
        while (isRunning) {
          String inputString = in.readUTF();
          System.out.println(inputString); // local log
          out.writeUTF(inputString); // echo response to client
          if (inputString.equals("quit")) isRunning = false;
        }
        close();
      } catch (SocketTimeoutException ste) {
        System.out.println("FAILED");
        System.out.println(ste);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  public void start() {
    if (thread == null) {
      thread = new Thread(this);
      thread.start();
    }
  }

  public void stop() {
    if (thread != null) {
      thread.interrupt();
      thread = null;
    }
  }

  public void open() {
    try {
      in = new DataInputStream(server.getInputStream());
      out = new DataOutputStream(server.getOutputStream());
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public void close() {
    try {
      in.close();
      out.close();
      server.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void printConnectionDetail() {
    System.out.println("accepted connection from : " + server.getLocalAddress() + ", port: " + server.getPort());
  }
}
