import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private ServerSocket serverSocket;
  private Socket server;
  private DataInputStream in;
  private DataOutputStream out;
  public boolean isRunning;

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


      // Code
      isRunning = true;
      in = new DataInputStream(server.getInputStream());
      out = new DataOutputStream(server.getOutputStream());

      //output.writeUTF("Hallo");

      while (isRunning) {
        String inputString = in.readUTF();

        out.writeUTF(inputString);
        //sendToClients( inputString );

        if (inputString.equals("quit")) {
          isRunning = false;
        } else {
          System.out.println(inputString);
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
