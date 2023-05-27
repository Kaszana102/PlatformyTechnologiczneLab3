
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kubaw
 */
public class Main_host {
    public static void main(String[] args) throws ClassNotFoundException{
        InputStream input;
        int command=-1;
        byte[] buffer=new byte[100];
        boolean pracujemy=true,keepDialog=true;
        boolean odbieramyObiekty=false;
        boolean odbieramyLiczbe=false;
        Message mess=new Message(-1,"");
        
        try (ServerSocket server = new ServerSocket(9797)) {
            System.out.println("utworzono");        
            while(pracujemy){                    
                //sprawdź czy jest nowe połączenie
                try (Socket socket = server.accept()) {   
                    keepDialog=true;
                    System.out.println("Somebody connected");
                    //utwórz nowy wątek, który go rozpatrzy
                    new Thread(new Workman(socket)).run();                                                                        
                    
                }
            }
        }catch (IOException ex) {
            System.err.println(ex);
        }
        
        
        
        System.out.println("Server end");        
    }
}
