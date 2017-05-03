import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public boolean isRunning;
  private ServerSocket serverSocket;
  private Socket server;
  private DataInputStream in;
  private DataOutputStream out;

  public Server() {

  }

  public static void main(String[] args) {
    Server server = new Server();
    server.start();
  }

  /**
   *
   */
  public void start() {
    try {
      serverSocket = new ServerSocket(8080);
      serverSocket.setSoTimeout(20000);
      server = serverSocket.accept();

      printConnectionDetail();

      // start listening for streams
      isRunning = true;
      in = new DataInputStream(server.getInputStream());
      out = new DataOutputStream(server.getOutputStream());

      out.writeUTF("Hello there, this is the Server !");

      while (isRunning) {
        String inputString = in.readUTF();
        System.out.println(inputString); // local log

        if (inputString.equals("quit")) {
          out.writeUTF("bye");
          isRunning = false;
        } else {
          out.writeUTF(inputString); // response to Client
        }
      }

      server.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void printConnectionDetail() {
    System.out.println("accepted connection from : " + server.getLocalAddress() + ", port: " + server.getPort());
  }
}
