import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;  

public class Rfc865UdpClient {

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            // Create a socket
            socket = new DatagramSocket();

            // Get the address of the server
            InetAddress serverAddress = InetAddress.getByName("SWLAB2-C.scse.ntu.edu.sg");  // or replace "localhost" with your server's IP

            // Send an empty packet to the server at the QOTD port 17
            
            byte[] buf = "Lim Shao Zhe Lenson, TCCA, 172.121.146.141".getBytes("UTF-8");
            DatagramPacket request = new DatagramPacket(buf, buf.length, serverAddress, 17);
            socket.send(request);

            // Receive the response
            byte[] replyBuf = new byte[512];
            DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length);
            socket.receive(reply);

            // Print the quote
            String quote = new String(reply.getData(), 0, reply.getLength());
            System.out.println("Quote of the Day: " + quote);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
