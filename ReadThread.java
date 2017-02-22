import java.net.*;
import java.io.*;

/**
* Vincent Messenger, Thomas Back, Leia Trice
* 
*/
public class ReadThread extends Thread{
   private Socket read_socket;  // client socket
   private int port_num = Node.send_port;
   
   public ReadThread() throws IOException{
       super();  // call super constructor
   }
   
   @Override
   public void run() {

        // create stuffs
        DatagramPacket out;
        String temp;
        String outMessage;
       
       // try to create io streams
       try(
           DatagramSocket read_socket = new DatagramSocket(port_num);
               )
       {
            // check if user passed in IP to connect to, connect to self if not
            if (Node.next_node == ""){
                Node.next_node = InetAddress.getLocalHost().getHostAddress().toString();
            }
            else {
                // format message and send join request
                outMessage = "J";
                out = new DatagramPacket(outMessage.getBytes(), outMessage.getBytes().length,
                    InetAddress.getByName(Node.next_node), port_num);
                read_socket.send(out);
            }
            
            // start receiving packets
            while(true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // receive packet
                read_socket.receive(packet);
                buffer = packet.getData();
                
                // Message protocol handling switch statement
                // TODO: format strings printed by System.out to user
                String message = new String(buffer);
                char messageType = message.charAt(0);
                switch (messageType) {

                    case 'J':
                        // keeps the old next node to send later
                        temp = Node.next_node;

                        // set next_node to joining node and send connected message to that node
                        Node.next_node = packet.getAddress().getHostName().toString();
                        outMessage = String.format("C %s", temp);
                        out = new DatagramPacket(outMessage.getBytes(), outMessage.getBytes().length,
                            InetAddress.getByName(Node.next_node), port_num);
                        read_socket.send(out);

                        break;

                    case 'C':
                        // get the substring of the message that contains the node's new next ip and set it to next
                        Node.next_node = message.substring(2);

                        // print message to user and send message on that the new user has joined the chat
                        outMessage = String.format("M %s %s has joined the chat!!!", InetAddress.getLocalHost().getHostAddress(), Node.user_name);
                        String[] splitOutMessage = outMessage.split(" ");
                        print_subArray(splitOutMessage,2);

                        // send packet
                        out = new DatagramPacket(outMessage.getBytes(),outMessage.getBytes().length,
                            InetAddress.getByName(Node.next_node), port_num);
                        read_socket.send(out);
                        
                        break;

                    case 'L':
                        String[] splitLeaveMessage = message.split(" ");

                        // check message was not sent from the next ip and continue sending
                        if(splitLeaveMessage[1].equals(Node.next_node) == false)
                        {
                            // print message
                            print_subArray(splitLeaveMessage,3);

                            // send message
                            out = new DatagramPacket(message.getBytes(),message.getBytes().length,
                                InetAddress.getByName(Node.next_node), port_num);
                            read_socket.send(out);
                        }

                        // message received at node that sent it, remove node from p2pchat by setting it's next to an empty string 
                        else if(splitLeaveMessage[1].equals(InetAddress.getLocalHost().getHostAddress()) == true)
                        {
                            Node.next_node = "";
                        }

                        // message was sent from the next ip, so set next to it's next
                        else
                        {
                            temp = Node.next_node;
                            Node.next_node = splitLeaveMessage[2];

                            // print message
                            print_subArray(splitLeaveMessage,3);

                            // send packet
                            out = new DatagramPacket(message.getBytes(),message.getBytes().length,
                                InetAddress.getByName(temp), port_num);
                            read_socket.send(out);
                        }

                        break;

                    case 'M':
                        String[] splitMessage = message.split(" ");

                        // check message was not sent from the local ip and continue sending
                        if(splitMessage[1].equals(InetAddress.getLocalHost().getHostAddress().toString()) == false)
                        {
                            print_subArray(splitMessage,2);
                            out = new DatagramPacket(message.getBytes(),message.getBytes().length,
                                InetAddress.getByName(Node.next_node), port_num);
                            read_socket.send(out);
                        }

                        break; 
                    }

                // this code sets the next_node value to the IP that sent packet
                //Node.next_node = packet.getAddress().getHostName().toString();
                //System.out.println("next_node: " + Node.next_node);
                
                // send packet back
                //read_socket.send(packet);
            }
       
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    void print_subArray(String[] stringsToPrint, int index){
        for(int i = index; i<stringsToPrint.length; i++)
        {
            System.out.print(stringsToPrint[i] + " ");
        }
        
        System.out.println("\n");
    }
}