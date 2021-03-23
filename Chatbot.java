import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.lang.Override;
import java.lang.Thread;

public class Chatbot extends JFrame {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField chatBox;
    private JScrollPane scroll;
    private Border border;
    
    public static void main(String args[])
    {
        new Chatbot();
    }

    public Chatbot() {
        frame = new JFrame("Chatbot");
        chatArea = new JTextArea(2,540);
        chatBox = new JTextField();
        scroll = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        border = BorderFactory.createLineBorder(Color.BLUE, 1);

        chatArea.setSize(540,400);
        chatArea.setLocation(2,2);
        chatBox.setSize(540,30);
        chatBox.setLocation(2,18);
        chatBox.setBorder(border);
        frame.setResizable(false);
        frame.setSize(600, 600);
        frame.add(chatBox);
        frame.add(scroll);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        bot("Hello! I am a chatbot that specializes in AI in literature. Ask me anything by typing below. Type \"QUIT\" to end the program.\n\n");
        chatArea.append("Conversation:\n");
        chatBox.setText("");

        chatBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String gtext = chatBox.getText();
                chatArea.append("User: " + gtext + "\n");
                chatBox.setText("");
                if (gtext.equals("QUIT")) {
                    sleep(500);
                    System.exit(0);
                }
                String category = "";
                try {
                    category = BotResponse.findCategory(gtext);
                    System.out.println(category);
                }
                catch (Exception e) {
                    System.out.println("Exception thrown.");
                }
                String response = respond(category);
                bot(response);
            }
        });
    }    

    private void bot(String string)
    {
        chatArea.append("Bot: " + string + "\n");
    }

    private void sleep(int x)
    {
        try {
            Thread.sleep(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String respond(String category)
    {
        String[] greetings = {"Hi.", "Hello.", "Hi, how can I help you?"};
        String[] conversationContinue = {"Is there anything else I can help you with?"};
        String[] bookInquiry = {"There are many books written about AI. I can recommend some, if you ask me to do so.", "There are many science fiction novels that you can read.", "Artificial intelligence is often a common topic in science fiction literature.",
    "Many popular authors, including Isaac Asimov and William Gibson, have written novels on artificial intelligence.", "Long before modern computing achieved advanced implementations of AI, the concept was fairly commonly found in literature."};
        String[] bookRecommendationStart = {"Here's one: ", "Try reading ", "A good read is ", "A popular one is ", "Perhaps explore "};
        String[] bookRecommendationName = {"The Veldt, by Ray Bradbury (1950)", "Robot Dreams, by Isaac Asimov (1986)", "Robbie, by Isaac Asimov (1940)", "1984, by Geroge Orwell (1949)", "Idoru, by William Gibson (1996)", "Lightless, by C.A. Higgins (2015)"};
        String[] bookTrend = {"Many books show AI as a positive thing that can make life convenient.", "There are many authors who portray technology as an asset to humankind.", "Some authors believed that AI would have the potential to reflect human emotion.", "AI can be a positive because it can automate tedious tasks.", "Artificial intelligence can positively impact society by saving human resources or completing dangerous tasks.",
    "Many authors believe AI will pose a threat to humankind in the future.", "Some books warn that if we are not careful, AI can take over our lives in the future.", "Some authors argue against the concept of merging societal, technological, and cultural trends altogether.", "Some books communicate that technology is too invasive and intimate and leaves no room for privacy.", "In their books, some authors pose the idea that to create an entity that can think for itself might destory the human race altogether."};
        String[] authorInquirySent = {"One common author is ", "I would recommend novels written by ", "One author who is popular in SciFi literature is ", "Someone who has some popular AI-related publications is ", "It might be nice to read novels written by "};
        String[] authorInquiryName = {"Ray Bradbury.", "Isaac Asimov.", "George Orwell.", "William Gibson.", "C.A. Higgins.", "Louisa Hall.", "Karel Capek.", "Dennis E. Taylor.", "E.M. Foner.", "John C. Wright.", "Daniel H. Wilson.", "Daniel Suarez.", "Martha Wells.", "Neal Stephenson.", "Robert A. Heinlein.", "Harlan Ellison.", "Iain M. Banks.", "Rudy Rucker.", "Dan Simmons.", "Arthur C. Clarke."};
        String[] aiInquiry = {"Artificial intelligence, or AI, is the ability of a computer to complete tasks that require human-like intelligence.", "AI, which stands for Artificial Intelligence, is the concept of machines exhibiting dynamic intelligence, which was coined in 1955.", "The concept of artificial intelligence (AI) is not new, but its implementations are becoming increasingly advanced in the modern era due to the development of new computers capable of high-performance computing."};
        String[] botInquiry = {"I am a bot, an example of artificial intelligence. You can ask me anything you want!", "I am a chatbot that uses natural language processing (NLP) techniques to respond to you as best as I can.", "I am a chatbot. I specialize in Artificial Intelligence in literature and science fiction."};
        String[] conversationComplete = {"Goodbye. ", "Bye. ", "See you next time. "};

        if (category.equals("greeting")) return greetings[(int) (Math.random()*greetings.length)];
        else if (category.equals("conversation-continue")) return conversationContinue[(int) (Math.random()*conversationContinue.length)];
        else if (category.equals("book-inquiry")) return bookInquiry[(int) (Math.random()*bookInquiry.length)];
        else if (category.equals("book-recommendation")) return bookRecommendationStart[(int) (Math.random()*bookRecommendationStart.length)] + bookRecommendationName[(int) (Math.random()*bookRecommendationName.length)];
        else if (category.equals("book-trend")) return bookTrend[(int) (Math.random()*bookTrend.length)];
        else if (category.equals("author-inquiry")) return authorInquirySent[(int) (Math.random()*authorInquirySent.length)] + authorInquiryName[(int) (Math.random()*authorInquiryName.length)];
        else if (category.equals("ai-inquiry")) return aiInquiry[(int) (Math.random()*aiInquiry.length)];
        else if (category.equals("bot-inquiry")) return botInquiry[(int) (Math.random()*botInquiry.length)];
        else if (category.equals("conversation-complete")) return conversationComplete[(int) (Math.random()*conversationComplete.length)] + "You may type 'QUIT' to end this conversation.";        
        else return "Sorry, I don't understand. Perhaps try rephrasing your statement.";
    }
}