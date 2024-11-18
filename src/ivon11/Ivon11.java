package ivon11;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Ivon11 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean exit = true;
        do {
            System.out.println("\n---------------------------");
            System.out.println("Welcome to the 4p's Tracker:");
            System.out.println("");
            System.out.println("1. Beneficiary:");
            System.out.println("2. Program:");
            System.out.println("3. Releasing:");
            System.out.println("4. Report:");
            System.out.println("5. Exit:");
            
            int act = 0;

           
            boolean validChoice = false;
            while (!validChoice) {
                System.out.println("Enter Choice (1-5):");
                try {
                    act = sc.nextInt();
                    
                    if (act >= 1 && act <= 5) {
                        validChoice = true; 
                    } else {
                        System.out.println("Invalid choice! Please select a number between 1 to 5.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Only numbers to 1 to 5 !!!!!!.");
                    sc.nextLine(); 
                }
            }

            switch (act) {
                case 1:
                    beneficiary bs = new beneficiary();
                    bs.btransaction();
                    break;
                case 2:
                    program pm = new program();
                    pm.ptransaction();
                    break;
                case 3:
                    releasing rg = new releasing();
                    rg.rtransaction();
                    break;
                case 4:
                    report rt = new report();
                    rt.rtTransaction();
                    break;
                case 5:
                    
                    boolean validExit = false;
                    while (!validExit) {
                        System.out.print("Exit Selected... Type 'yes' to confirm: ");
                        String resp = sc.next();
                        if (resp.equalsIgnoreCase("yes")) {
                            exit = false;
                            validExit = true;
                        } else {
                            System.out.println("Exit canceled. Returning to the menu.");
                            validExit = true; 
                        }
                    }
                    break;
                default:
                 
                    System.out.println("Unexpected error.");
            }
        } while (exit);

        sc.close();
        System.out.println("Thank you for using the 4p's Tracker.");
    }
}