package com.group8.view;

//The view receives messages and also has boolean indicators for the client to get
//strings from the view. The view handles new input using the logic in runConsoleView().

//Client will continuously check the view for new messages that the user inputs

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConsoleView implements View{

  public boolean newInputTyped = false; //set to true after user presses enter with new message
                                        //would need to have method to set newInputTyped to false from controller?
                                        //that method would also have to clear all old strings
  public Queue<String> newMessages = new ConcurrentLinkedQueue<String>();


  public ConsoleView()
  {
    //when constructing new view, might want to show previous conversation in here?
    //could pass hashmap<EachMessage, user> for display in view
    
    runConsoleView();
  }
  
  public void runConsoleView()
  {
    Scanner scanner = new Scanner(System.in); //read keyboard input on console, enter to send
    String readString = scanner.nextLine(); 
    while(readString!=null)
    {
        System.out.println(readString);
        newMessages.add(readString); 
        newInputTyped = true;
        if(readString.equals(""))
            System.out.println("Read Enter Key.");
        if(scanner.hasNextLine())
        {
            readString = scanner.nextLine();
        }
        else
            readString = null;
    }
  }
  
  public void reset()
  {
    //set newInputTyped to false
    //clear queue
  }

  public void displayMessage(String msg){
    System.out.println(msg);
  }

  public void displayInfo(String info){
    System.out.println(info);
  }
  
  
}

