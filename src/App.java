import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class App {
    public static void main(String[] args) throws Exception {
        // open UDP Socket
        String serverAddr = args[0];
        int port = Integer.parseInt(args[1]);
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress ipAddress = InetAddress.getByName(serverAddr);
            socket.connect(ipAddress, port);
        }catch(SocketException e){
            e.printStackTrace();
            System.exit(-1);
        }catch(UnknownHostException e){
            e.printStackTrace();
            System.exit(-1);
        }

        try{
            //send UDP request to server
            byte[] buf = "NAME, GROUP, clientip".getBytes("UTF-8");
            DatagramPacket request = new DatagramPacket(buf, buf.length);
            socket.send(request);

            // recieve UDP reply from server
            byte[] replyBuf = new byte[512];
            DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length);
            socket.receive(reply);

            String quote = new String(replyBuf);
            System.out.println(quote);

            socket.close();
        }catch(IOException e){}
    }
}
