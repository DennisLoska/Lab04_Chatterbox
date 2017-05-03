import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Thread {

  private Socket client;
  private DataInputStream in;
  private DataOutputStream out;
  //public static int id = 0;

  public Client() {
    //id++;
  }

  public static void main(String[] args) {
    Thread client = new Client();
    client.run();
  }

  public void run() {

    try {
      client = new Socket("localhost", 8080);

      in = new DataInputStream(client.getInputStream());
      out = new DataOutputStream(client.getOutputStream());

      Scanner sc = new Scanner(System.in);

      while (!client.isClosed()) {
        System.out.println("Chatbot: say something..");
        System.out.print("> ");
        //System.out.println(in.readUTF()); // funktioniert nicht
        //out.writeUTF(scanner.nextLine());
        System.out.println("response: " + sc.nextLine() + "\n");
      }

      client.close();

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
