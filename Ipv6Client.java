/**
 * Created by Narvik on 5/2/17.
 */
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Ipv6Client {

    public static void main(String[] args) {

        try (Socket socket = new Socket("codebank.xyz", 38004)) {

            // Shows we are connected to the server
            System.out.println("Connected to server.");

            InputStream getIS = socket.getInputStream();
            OutputStream outStream = socket.getOutputStream();

            byte[] IPv6packet ;
            byte[] destAddr ;
            byte[] massage = new byte[4];
            short length;
            int dataLength = 2;


            for (int k = 0 ; k < 12 ; k++) {

                length = (short)(40 + dataLength);
                IPv6packet = new byte[length];
                System.out.println("Data Length: " + dataLength);

                IPv6packet[0]= 0b01100000;  // Version + HLen
                IPv6packet[1]= 0;           // Traffic Class
                IPv6packet[2]= 0;           // 1st half of length
                IPv6packet[3]= 0;           // 2nd half of length

                IPv6packet[4]= (byte)((dataLength & 0xFF00) >> 8); // 1st 8 bits of Payload Length
                IPv6packet[5]= (byte)(dataLength & 0x00FF);        // 2nd 8 bits of Payload Length
                IPv6packet[6]= (byte)17;    // Next Header
                IPv6packet[7]= (byte)20;    // Hop Limit

                // Source Address
                IPv6packet[8]= 0;
                IPv6packet[9]= 0;
                IPv6packet[10]= 0;
                IPv6packet[11]= 0;
                IPv6packet[12]= 0;
                IPv6packet[13]= 0;
                IPv6packet[14]= 0;
                IPv6packet[15]= 0;
                IPv6packet[16]= 0;
                IPv6packet[17]= 0;
                IPv6packet[18]= (byte)0xFF;//////////
                IPv6packet[19]= (byte)0xFF;////////
                IPv6packet[20]= (byte)192;
                IPv6packet[21]= (byte)168;
                IPv6packet[22]= (byte)1;
                IPv6packet[23]= (byte)64;

                // Server IP, Destination Address
                IPv6packet[24]= 0;
                IPv6packet[25]= 0;
                IPv6packet[26]= 0;
                IPv6packet[27]= 0;
                IPv6packet[28]= 0;
                IPv6packet[29]= 0;
                IPv6packet[30]= 0;
                IPv6packet[31]= 0;
                IPv6packet[32]= 0;
                IPv6packet[33]= 0;
                IPv6packet[34]= (byte)0xFF;
                IPv6packet[35]= (byte)0xFF;
                destAddr  = socket.getInetAddress().getAddress();
                for (int i = 0,j = 36 ; i<destAddr.length; i++, j++){
                    IPv6packet[j] = destAddr[i] ;
                }

                // Send data to the server
                for (int i = 0; i< length; i++) {
                    outStream.write(IPv6packet[i]);
                }

                // Read respond from the server
                getIS.read(massage);
                System.out.print("Response: 0x");
                for(byte e: massage) {
                    System.out.printf("%X", e);
                }
                System.out.println("\n");
                dataLength *= 2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}