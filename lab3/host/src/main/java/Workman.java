
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
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
public class Workman implements Runnable{

    public Workman(Socket socket) {
        this.socket = socket;
    }
    Socket socket;
    
    
    @Override
    public void run(){
        Message mess=new Message(-1,"");
        InputStream input;
        OutputStream output;
        ObjectInputStream ois;  
        ObjectOutputStream oos;
        BufferedInputStream input_reader;        
        BufferedOutputStream output_reader;
        System.out.println("New workman");
        boolean keepDialog=true;
        int messNumber=0;
        int readMess=0;
        try{
            OutputStream os = socket.getOutputStream();                    
            InputStream is = socket.getInputStream();        
            BufferedOutputStream otp_buffer=new BufferedOutputStream(os);
            BufferedInputStream in_buffer=new BufferedInputStream(is);                 
            
            oos=new ObjectOutputStream(otp_buffer);            
                    //teraz dziala prawidlowo, lecz tylko pierwszy raz nie dziala
                    //za drugim razem wywoluje 2 razy
            oos.writeObject("ready");
            oos.flush();
            
                             
            System.out.println("Sent ready message");
            ois= new ObjectInputStream(in_buffer);
            
            try{
                messNumber=(int)ois.readObject(); 
                System.out.println("got messNumber");
            }catch(ClassNotFoundException ex){
                System.out.println("MessNumber not found");
            }
            oos.writeObject("ready for messages");
            oos.flush();
            
            while(keepDialog && readMess<messNumber){
                while(is.available()>0){   
                    try{
                        mess=(Message)ois.readObject();   
                        readMess++;
                    }catch(ClassNotFoundException ex){
                        System.out.println("Class not found");
                    }
                    System.out.println(mess); 
                    if(mess.GetNumber()== 0){
                        keepDialog=false;                                  
                    }
                    
                }
                if(socket.isClosed()){
                    keepDialog=false;
                }
            }
            
            oos.writeObject("finished");
            oos.flush();
            System.out.println("Sent finished message");
            
        }catch (IOException  ex){
                
        }
                
        System.out.println("Workman killed");
    }
}