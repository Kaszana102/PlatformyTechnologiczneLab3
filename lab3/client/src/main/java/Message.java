
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kubaw
 */

public class Message implements Serializable {

    @Override
    public String toString() {
        return "Message{" + "number=" + number + ", content=" + content + '}';
    }
    private int number;
    private String content;
    
    public Message(int number, String content){
        this.number=number;
        this.content=content;
    }
    
    public void SetNumber(int number){
        this.number=number;
    }
    
    public void SetContent(String content){
        this.content=content;
    }
    
    public int GetNumber(){
        return this.number;
    }
    
    public String GetContent(){
        return this.content;
    }
    
}
