
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kubaw
 */
public class Main_client {
    public static void main(String[] args) throws IOException{        
        //Message mess=new Message(-1,"succes");
        boolean pracujemy=true;
        Scanner scanner=new Scanner(System.in);
        ObjectOutputStream oos;
        ObjectInputStream ois;
        String text;
        int number;
        
        while(pracujemy){
            try (Socket client = new Socket("localhost", 9797)) {                   
                try (OutputStream os = client.getOutputStream();                    
                    InputStream is = client.getInputStream()){ 
                    BufferedOutputStream otp_buffer=new BufferedOutputStream(os);  
                    //ObjectOutputStream oos=new ObjectOutputStream(otp_buffer);  
                    //ObjectOutputStream oos=new ObjectOutputStream(os);                                
                    

                    BufferedInputStream in_buffer=new BufferedInputStream(is);                                         
                    //ObjectInputStream ois = new ObjectInputStream(in_buffer); 

                    oos = new ObjectOutputStream(otp_buffer);                                                                               
                    ois = new ObjectInputStream(in_buffer); 
                    try{
                        text=(String)ois.readObject();
                        
                        if(text.equals("ready")){
                            System.out.println("got ready signal");
                            number=scanner.nextInt();
                            oos.writeObject(number);
                            oos.flush();
                            System.out.println("Number sent");
                            
                            text=(String)ois.readObject();
                            if(text.equals("ready for messages")){
                                System.out.println("got ready for messages signal");
                                while(number-- > 0){
                                    Message mess=new Message(-1,"succes");
                                    mess.SetNumber(scanner.nextInt());
                                    mess.SetContent(scanner.nextLine());                                   

                                    oos.writeObject(mess);
                                    oos.flush();
                                    System.out.println("wysłano "+mess.toString());
                                }
                                while(pracujemy){//czekanie nie wiadomość końcową
                                    if(is.available()>0){
                                        text=(String)ois.readObject();
                                        if(text.equals("finished")){
                                            System.out.println("Task ended succesfully");
                                            pracujemy=false;
                                        }
                                        else{
                                            System.out.println("Task failed");
                                        }
                                    }
                                }
                            }
                            else{
                                System.out.println("didn't get ready for messages signal");
                            }
                        }
                        else{
                            System.out.println("didn't get ready signal");
                        }
                    }catch(ClassNotFoundException ex){
                        
                    }
                    
                }
            }catch (IOException ex) {
            System.err.println(ex);
            }           
        }                
    }
}

