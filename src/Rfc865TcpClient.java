import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Rfc865TcpClient {

    public static void main(String[] argv) {

        // 1. Establish TCP connection with the server
        Socket socket = null;
        try {
            socket = new Socket("localhost", 17);  // replace "localhost" with your server's IP and 17 with the port number
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            // 2. Send TCP request to server
            // Since QOTD protocol doesn't require a specific request message,
            // you can send an empty message or any message to get the quote
            OutputStream os = socket.getOutputStream();
            os.write("GIVE ME A QUOTE\n".getBytes());
            os.flush();  // Ensure the data is sent out

            // 3. Receive TCP reply from server
            InputStream is = socket.getInputStream();
            byte[] replyBuf = new byte[512];  // adjust buffer size if needed
            int bytesRead = is.read(replyBuf);
            String reply = new String(replyBuf, 0, bytesRead);
            System.out.println("Quote of the Day: " + reply);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();  // Close the socket
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
