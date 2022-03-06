package S01;

import java.util.Scanner;

public class ConsoleApplication {
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter username");

        String userName = myObj.nextLine();  // Read user input
        System.out.println("Username is: " + userName);  // Output user input

        System.out.println("Press Enter To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }
}
