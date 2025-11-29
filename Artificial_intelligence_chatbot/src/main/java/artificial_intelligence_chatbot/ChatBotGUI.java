
package artificial_intelligence_chatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBotGUI extends JFrame {

    private final ChatBot bot;          
    private final JTextArea chatArea;   
    private final JTextField inputField; 
    private final JButton sendButton; 

    public ChatBotGUI()
    {
        super("Candis - Java Chatbot");

        bot = new ChatBot();


        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16)); 
        
        
        JScrollPane scrollPane = new JScrollPane(chatArea);


        inputField = new JTextField(80);
        sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

    
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

  
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 


        appendBot("Hello! I'm Candis. Type 'help' or 'learn: question | answer' to start.");

        setupActions();
    }

    private void setupActions()
    {
   
        sendButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sendMessage();
            }
        }
        );

   
        inputField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                sendMessage();
            }
        }
        );
    }

    private void sendMessage() 
    {
        String userText = inputField.getText().trim();
        if (userText.isEmpty())
        {
            return;
        }

        appendUser(userText);

        if (userText.equalsIgnoreCase("exit") || userText.equalsIgnoreCase("bye")) {
            appendBot("Good bye - Keep Coding");
            inputField.setText("");
            inputField.setEditable(false);
            sendButton.setEnabled(false);
            return;
        }

        String reply = bot.getResponse(userText);
        appendBot(reply);

        inputField.setText("");
        inputField.requestFocus();
    }

    private void appendUser(String text) 
    {
        chatArea.append("You: " + text + "\n");
    }

    private void appendBot(String text) 
    {
        chatArea.append("Bot: " + text + "\n");
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() 
            {
                new ChatBotGUI().setVisible(true);
            }
        }
        );
    }
}