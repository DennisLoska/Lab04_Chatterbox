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

      printWelcome();

      // start listening for streams
      isRunning = true;
      in = new DataInputStream(server.getInputStream());
      out = new DataOutputStream(server.getOutputStream());

      out.writeUTF("Hello there, this is the Server !");

      while (isRunning) {
        String inputString = in.readUTF();

        out.writeUTF(inputString); // response to Client

        if (inputString.equals("quit")) {
          isRunning = false;
        } else {
          System.out.println(inputString); // local log
        }

      }


      server.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void printWelcome() {
    System.out.println("accepted connection from : " + server.getLocalAddress() + ", port: " + server.getPort());
  }
}
