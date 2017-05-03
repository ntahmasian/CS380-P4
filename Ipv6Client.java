/**
 * Created by Narvik on 5/2/17.
 */
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.io.BufferedReader;

public class Ipv6Client {

    public static void main(String[] args) {

        try (Socket socket = new Socket("codebank.xyz", 38004)) {

            // Shows we are connected to the server
            System.out.println("Connected to server.");

            InputStream getIS = socket.getInputStream();
            OutputStream outStream = socket.getOutputStream();
            InputStreamReader readIS = new InputStreamReader(getIS, "UTF-8");
            BufferedReader serverMassage = new BufferedReader(readIS);


            byte[] IPv6packet ;
            byte[] destAddr ;
            short cs, length;
            int dataLength = 2;



            for (int k = 0 ; k < 12 ; k++) {

                length = (short) (20 + dataLength);
                IPv6packet = new byte[length];
                System.out.println("Data Length: " + dataLength);

            }







        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static short checksum(byte[] b){
        int sum = 0, i = 0;

        while(i < b.length-1) {
            sum += ((b[i]<<8 & 0xFF00) | (b[i+1] & 0xFF));

            if((sum & 0xFFFF0000) > 0) {
                sum &= 0xFFFF;
                sum++;
            }
            i += 2;
        }

        if((b.length)%2 == 1) {
            sum += ((b[i]<<8) & 0xFF00);

            // Wrap around, carry occurred
            if((sum & 0xFFFF0000) > 0) {
                sum &= 0xFFFF;
                sum++;
            }
        }

        // Return the 1's complement
        return (short) ~(sum & 0xFFFF);
    }
}
