package artificial_intelligence_chatbot;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

public class ChatBot
{
 private static final String Learned ="learned.txt";
 private final Map<String, String> learned = new HashMap<>();
    
 private final Map<String, java.util.List<String>> intentKeywords= new HashMap<>();
 
 private final Map<String,String> intentResponses = new HashMap<>();
  public ChatBot()
   { 
    LoadLearnedFromFile();
 
     //Greeting intent
     intentKeywords.put("greeting",java.util.Arrays.asList("hello","hii","hey","good morning","good evening"));
     intentResponses.put("greeting","Hello! how can i help you today");
    
     // Bot info intent
     intentKeywords.put("bot_info", Arrays.asList("what is your name","who are you"));
     intentResponses.put("bot_info","hii there, I am candis, your java learning chatbot.");
    
     //Simple Ok Intent
     intentKeywords.put("ack",java.util.Arrays.asList("ok","okay"));
     intentResponses.put("ack","yess");
    
     //Work/capability intent
     intentKeywords.put("work", Arrays.asList("what is your work","what can you do","things you can do"));
     intentResponses.put("work","Right now I am in my developing phase, but I can answer some pre-stored questions and help you practise Java!");
     
       
     //Help intent
     intentKeywords.put("help",java.util.Arrays.asList("help","show options","what can i ask"));
     intentResponses.put("help","You can say things like 'what is your work', 'what is your name', or just greet me.");
   } 
 
   public void learn(String question, String answer)
       {
           String q = question.toLowerCase().trim().replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", " ");
           learned.put(q,answer);
           appendLearnedToFile(question, answer);   
       }
         
   private void appendLearnedToFile(String question, String answer)
       {
         try (java.io.FileWriter fw = new java.io.FileWriter(Learned, true);
        java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
        java.io.PrintWriter out = new java.io.PrintWriter(bw))
           {
             out.println(question + "|" + answer);
           }   
           catch (Exception e)
           { 
             System.out.println("Bot: could not save learned data: " + e.getMessage());
           }
     }
   private void LoadLearnedFromFile()
   {
        java.io.File file = new java.io.File(Learned);
        if (!file.exists())
           {
            return; // nothing learned yet
           }

         try (java.util.Scanner fileScanner = new java.util.Scanner(file)) 
         {
          while (fileScanner.hasNextLine()) 
         { 
             String line = fileScanner.nextLine().trim();
             if (line.isEmpty()) continue;

             String[] parts = line.split("\\|", 2);
             if (parts.length == 2) 
               {
                 String question = parts[0].trim().toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "")     .replaceAll("\\s+", " ");
                 String answer = parts[1].trim();
                 learned.put(question, answer);
               }
            }
        }  
        catch (Exception e) 
       {
        System.out.println("Bot: could not load learned data: " + e.getMessage());
       }
    }

   private java.util.Set<String> tokenize(String text)
   {
       String cleaned =text.toLowerCase().trim().replaceAll("[^a-zA-Z0-9 ]","").replaceAll("\\s+"," ");
       java.util.Set<String> tokens= new java.util.HashSet<>();
        if (!cleaned.isEmpty()) 
       {
            for (String w : cleaned.split(" ")) 
           {
                if (!w.isEmpty()) 
               {
                 tokens.add(w);
               }
           }
       }
        return tokens;
   }

 public String getResponse(String input)
{
    String s = input.toLowerCase().trim().replaceAll("[^a-zA-Z0-9 ]","").replaceAll("\\s+"," ");
  
    
    if (input.toLowerCase().startsWith("learn:")) 
    {
        String rest = input.substring("learn:".length()).trim();
        String[] parts = rest.split("\\|", 2);
        if (parts.length == 2) 
        {
            String question = parts[0].trim();
            String answer   = parts[1].trim();
            learn(question, answer);
            return "Got it! I learned something new. Ask me again to check.";
        } else
        {
            return "Format: learn: question | answer";
        }
    }
    
    
    for (String q : learned.keySet())
    {
        if (s.contains(q))
        {
            return learned.get(q);
        }
    }

    java.util.Set<String> inputTokens = tokenize(s);

    String bestIntent = null;
    int bestscore = 0;

    for (String intent : intentKeywords.keySet())
    {
        int bestForThisIntent = 0;

    
        for (String example : intentKeywords.get(intent))
        {
            java.util.Set<String> exampletokens = tokenize(example);
            int overlap = 0;

            for (String token : inputTokens)
            {
                if (exampletokens.contains(token))
                {
                    overlap++;
                }
            }

            if (overlap > bestForThisIntent)
            {
                bestForThisIntent = overlap;
            }
        }

        if (bestForThisIntent > bestscore)
        {
            bestscore = bestForThisIntent;
            bestIntent = intent;
        }
    }

    if (bestscore > 0 && bestIntent != null)
    {
        return intentResponses.get(bestIntent);
    }

    
    if (s.matches(".*\\bhow are you\\b.*"))
    {
        return "I'm just code, but thanks for asking - ready to learn!";
    }

    // 4) fallback
    return "Sorry, I don't understand yet. Could you rephrase or try a different question?";
}
}
