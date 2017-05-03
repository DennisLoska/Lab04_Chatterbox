import java.io.IOException;

/**
 * ChatterSystem
 * should launch Server & test Client by default
 */
public class ChatterSystem {

  public static void main(String[] args) throws IOException {
    ChatterSystem cs = new ChatterSystem();
    System.out.println("ChatterSystem startup...");
    cs.setUp();
  }

  public void setUp() throws IOException {
    Server server = new Server();
    server.start();
    Client client = new Client();
    while (server.isRunning) {
      System.out.println("waiting for input");
    }
  }
}
