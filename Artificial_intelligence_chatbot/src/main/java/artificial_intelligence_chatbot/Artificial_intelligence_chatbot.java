package artificial_intelligence_chatbot;
import java.util.*;

public class Artificial_intelligence_chatbot    
{
  
 public static void main(String[] args)
   {
         ChatBot bot=new ChatBot();
       
         try(Scanner in=new Scanner(System.in))
       {
         System.out.println("Chat-Learner (console)-type 'exit' to quit.");
         while(true)
           {
             System.out.print("You: ");
             String user =in.nextLine().trim();
             
             
             if(user.equalsIgnoreCase("exit")||user.equalsIgnoreCase("bye"))
             {
                 System.out.println("Bot: Good bye = Keep Coding");
                 break;
             }
             
             
             if(user.toLowerCase().startsWith("learn:"))
             {
                 String rest= user.substring("learn:".length()).trim(); 
                 
                 String[] parts= rest.split("\\|",2);
                 if(parts.length==2)
                 {
                     String question = parts[0].trim();
                     String answer = parts[1].trim();
                     bot.learn(question, answer);
                     System.out.println("Bot: got it! i learned a new answer for"+ question);
                 }
                 
                 else
                 {
                     System.out.println("Bot: please use format: learn: question | answer");
                 }
                 continue;
             }
             
             
             String reply = bot.getResponse(user);
             System.out.println("Bot: "+reply);
           }
       }
   } 
}



