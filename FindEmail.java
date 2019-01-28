// Julia Sato, November 14 2018
// Created for APCS in the Cloud9 IDE

import java.io.*;

public class FindEmail{

    static int emailCount = 0; // initializes the total number of emails
    static String line = ""; // initializes the string that holds the entirety of the line being analyzed
    
    public static void main(String args[]) throws Exception{
        
        BufferedReader fin;
        fin = new BufferedReader(new FileReader("file.txt")); // states file being read
        
        while((line != null)){ // keeps looping until end of file
            
            line = fin.readLine(); // reads next line
            if (line != null) emailTime(); // run the email check unless line is empty (end of file)
            
        }
        
        System.out.println("Total emails: " + emailCount);
        
    }
    
    public static void emailTime(){
        
        int start = 0; // holds the starting index of the word being assessed
        int end = 0; // holds the ending index ''
        
        for(int i = 0; i < line.length(); i++){ // loops through entire line of file
            
            if(line.substring(i,i + 1).equals(" ")){ // looks for spaces to discern beginnings and ends of words
                
                start = end; // once it finds a space, move the indexes up by one to select next word
                end = i + 1; // accounts for exclusivity of substring
                analyzeString(line.substring(start,end - 1)); // checks word in line for email
                
            }
            
        } 
        
        if(line.length() != 0) analyzeString(line.substring(end)); // accounts for last word in line to compensate for where for loop ends
        
    }
    
    public static void analyzeString(String str){ // checks to see if the string is an email or not
        
        if(str.substring(str.length() - 1).equals(".")){ // deletes any periods after words
            str = str.substring(0, str.length() - 1);
        }
        
        if(str.indexOf('@') != -1){ // if there is an @ in the string, it is an email
            System.out.println(str); // prints the email
            emailCount++; // adds to total email count
        }
        
    }
    
}
