import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Rfc865TcpServer {

    public static void main(String[] argv) {

        // 1. Open TCP socket at well-known port
        ServerSocket parentSocket = null;
        try {
            parentSocket = new ServerSocket(17);  // Quote of the Day Protocol port
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        while (true) {
            try {
                // 2. Listen to establish TCP connection with client
                Socket childSocket = parentSocket.accept();

                // 3. Create new thread to handle client connection
                ClientHandler client = new ClientHandler(childSocket);
                Thread thread = new Thread(client);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // 4. Receive TCP request from client (Optional for QOTD protocol)
            InputStream is = socket.getInputStream();
            byte[] requestBuf = new byte[512];
            int bytesRead = is.read(requestBuf);
            String request = new String(requestBuf, 0, bytesRead);
            System.out.println("Received request: " + request);  // Log the request (optional)

            // 5. Send TCP reply to client
            String quote = "Your Quote of the Day here";
            OutputStream os = socket.getOutputStream();
            os.write(quote.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
