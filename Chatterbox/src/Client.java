import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {

  private Socket client;
  private DataInputStream in;
  private DataOutputStream out;
  public static int id = 0;

  public Client() {
    id++;
  }

  public static void main(String[] args) {
    Thread client = new Client();
    client.run();
  }

  public void run() {
    System.out.println("Establishing connection to Server. hold tight...");
    try {
      client = new Socket("localhost", 8080);
      System.out.println("Successfully connected. " + client + "\n---------------------------------");

      // init socket streams
      in = new DataInputStream(client.getInputStream());
      out = new DataOutputStream(client.getOutputStream());

      Listener listener = new Listener(in);
      listener.start(); // background thread

      Scanner sc = new Scanner(System.in);

      while (!client.isClosed()) {
        //System.out.print("> ");
        String input = sc.nextLine();
        out.writeUTF(input); // send to Socket
        if (input.equals("quit")) {
          break;
        }
      }
      listener.interrupt();
      synchronized (listener) {
        try {
          listener.wait(); // waiting for background listener to stop
          System.out.println("\nDONE");
        } catch (InterruptedException ie) {
          System.out.println("\nFAILED\n" + ie);
        }
      }
      client.close();

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public void quit() {

  }
}
