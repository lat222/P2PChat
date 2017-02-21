import java.net.*;
import java.io.*;
import static java.lang.System.in;

/**
* Vincent Messenger, Thomas Back, Leia Trice
* 
*/
public class WriteThread extends Thread{
   private Socket write_socket;  // client socket
   private int port_num = 8081;
   
   public WriteThread() throws IOException{
       super();  // call super constructor
   }
   
   @Override
   public void run() {

        // create stuffs
        byte[] buffer = new byte[1024];
        DatagramPacket packet = null;
        String inString;

       
        // try to create io streams
        try(
            DatagramSocket write_socket = new DatagramSocket(port_num);
                )
        {
                System.out.println("hi");
                BufferedReader stdin =  new BufferedReader(new InputStreamReader(System.in));

                // start listening for keyboard input
                while((inString = stdin.readLine()) != null){
                    packet = new DatagramPacket(inString.getBytes(), inString.getBytes().length, InetAddress.getByName(Node.next_node), 8080);
                    write_socket.send(packet);
                }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
}