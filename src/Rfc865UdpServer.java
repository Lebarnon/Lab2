import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Rfc865UdpServer {

    public static void main(String[] argv) {

        // 1. Open UDP socket at well-known port (17)
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(17);
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        while (true) {
            try {
                // 2. Listen for UDP request from client
                byte[] buf = new byte[512];  // Adjust buffer size if needed
                DatagramPacket request = new DatagramPacket(buf, buf.length);
                socket.receive(request);

                // Get a quote. This could come from a predefined list or a file.
                String quote = "Your Quote of the Day here";

                // 3. Send UDP reply to client
                byte[] replyBuf = quote.getBytes();
                DatagramPacket reply = new DatagramPacket(
                        replyBuf, 
                        replyBuf.length, 
                        request.getAddress(), 
                        request.getPort()
                );
                socket.send(reply);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
