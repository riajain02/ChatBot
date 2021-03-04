import java.util.Scanner;

public class Bot
{
    public static void main(String args[])
    {
        System.out.println("\nWelcome to ChatBot!");
        System.out.println("Ask me anything by typing below. Type 'QUIT' to end the program.\n");
        Scanner sc = new Scanner(System.in);
        System.out.print("Ask ChatBot: ");
        String input = sc.nextLine();
        while(!input.equals("QUIT"))
        {
            System.out.print("Ask ChatBot: ");
            input = sc.nextLine();
        }
        System.out.println("\nChatbot program has been quit.\n");
    }
}