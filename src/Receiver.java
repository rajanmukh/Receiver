
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Istrac
 */
public class Receiver {

    private boolean connected;
    byte[] EMPTY;
    private byte[] buf;
    byte[] lengthbuf;
    Socket sock;
    DataInputStream dis;
    String ipaddr;
    int port;

    public Receiver(String ipaddr,int port) {
        EMPTY = new byte[0];
        buf = new byte[2048];
        lengthbuf = new byte[4];
        this.ipaddr=ipaddr;
        this.port = port;
        //connect
        trytoconnect();
        
    }

    public byte[] receive() {

        try {
            if (!connected) {
                return EMPTY;
            }
            while (true) {// match txID byte0 & 1 and protocol ID byte 2&3
                byte byte0 = dis.readByte();
                if (byte0 != 35) {
                    continue;
                }
                byte byte1 = dis.readByte();
                if (byte1 != 35) {
                    continue;
                }
                byte byte2 = dis.readByte();
                if (byte2 != 35) {
                    continue;
                }
                byte byte3 = dis.readByte();
                if (byte3 == 35) {
                    break;
                }
            }

            dis.readFully(buf, 4, 6);//junk
            dis.readFully(lengthbuf, 0, 4);
            System.arraycopy(lengthbuf, 0, buf, 9, 4);
            String valueOf = new String(lengthbuf);
            int len = Integer.parseInt(valueOf);
            dis.readFully(buf, 14, len - 14);
            return buf;
        } catch (IOException ex) {
            connected = false;
            trytoconnect();
            return EMPTY;
        }
    }

    private void trytoconnect() {
        while (true) {
            
            try {
                sock = new Socket(ipaddr, port);
                dis = new DataInputStream(sock.getInputStream());
                connected = true;
                break;
            } catch (Exception ex) {
                try {
                    sock.close();
                    connected = false;
                } catch (Exception ex1) {
                }
            }
            if(!connected){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void close(){
        try {
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args){
        Receiver receiver = new Receiver("127.0.0.1",6006);
    }

}
