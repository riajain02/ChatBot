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
    private JFrame frame = new JFrame("Chatbot");
    private JTextArea chatArea = new JTextArea(2,540);
    private JTextField chatBox = new JTextField();
    private JScrollPane scroll = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
    public static void main(String args[])
    {
        new Chatbot();
    }

    public Chatbot() {
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
        bot("Hello! Ask me anything by typing below. Type \"QUIT\" to end the program.\n\n");
        chatArea.append("Conversation:\n");
        chatBox.setText("");
        chatBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String gtext = chatBox.getText();
                chatArea.append("User: " + gtext + "\n");
                chatBox.setText("");
                if(gtext.equals("QUIT")) {
                    sleep(500);
                    System.exit(0);
                }
                respond(gtext);
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

    private void respond(String text)
    {
        bot("cool!");
        // sentence detection

        // tokenizing

        // POS tagging

        // lemmatizing

        // categorizing

        // answering
        
    }
}