

package ivon11;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class program {
    
     
     Scanner sc = new Scanner(System.in);

    public void ptransaction() {
        String response = null;
        do {
            System.out.println("\n---------------------------");
            System.out.println("program PANEL:");
            System.out.println("1. Add program:");
            System.out.println("2. View program:");
            System.out.println("3. Update program:");
            System.out.println("4. Delete program:");
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
                    System.out.println("Oops! Please enter a number between 1 to 5.");
                    sc.nextLine();  
                }
            }
            switch (act) {
                case 1:
                   addprogram();
                    break; 
                case 2:
                       viewprogram();  
                    break;
                case 3:viewprogram();
                       updateprogram();
                       viewprogram();
                    break;
                case 4:viewprogram();
                       deleteprogram();
                       viewprogram();                   
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }

          
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("Do you want to continue? (yes/no): ");
                response = sc.next();
                if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")) {
                    validResponse = true;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            }

        } while (response.equalsIgnoreCase("yes"));
    }
    
    
  public void addprogram() {
    Scanner sc = new Scanner(System.in);

    
    String[] validPrograms = {"UNIFAST", "4PS", "MALASAKIT", "TULONG DUNGOG"};

   
    while (true) {
        System.out.println("(UNIFAST, 4PS, MALASAKIT, TULONG DUNGOG)");
        System.out.print("Enter Program Name: ");
        String pname = sc.nextLine().trim();

       
        if (pname.isEmpty()) {
            System.out.println("Error: Program name cannot be empty.");
            continue; 
        }

       
        boolean isValid = false;
        for (String validProgram : validPrograms) {
            if (validProgram.equalsIgnoreCase(pname)) {
                isValid = true;
                break;
            }
        }

        // If the program name is not valid, prompt the user again
        if (!isValid) {
            System.out.println("Error: Invalid program name. Please choose from the following:");
            for (String validProgram : validPrograms) {
                System.out.println(validProgram);
            }
            continue; // Prompt again
        }

        // If the program name is valid, proceed to add it to the database
        String qry = "INSERT INTO tbl_program (p_name) VALUES (?)";
        config conf = new config();
        conf.addRecord(qry, pname);

        // After successful insertion, break out of the loop
        System.out.println("Program '" + pname + "' added successfully.");
        break; // Exit the loop once the program is successfully added
    }
}

   
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);  
            return true; 
        } catch (NumberFormatException e) {
            return false;  
        }
    }


    public void viewprogram() {
        String qry = "SELECT * FROM tbl_program";
        String[] hrds = {"p_id","p_name"};
        String[] clms = {"p_id", "p_name"};
        config conf = new config(); 
        conf.viewRecords(qry, hrds, clms);
    }    
  
  
  
        
    
public void updateprogram() {
    config conf = new config();
    int pid = 0;

    boolean validID = false;
    while (!validID) {
        System.out.print("Enter ID to update: ");
        if (sc.hasNextInt()) { 
            pid = sc.nextInt();
            sc.nextLine(); 
            
          
            if (conf.getSingleValue("SELECT p_id FROM tbl_program WHERE p_id = ?", pid) != 0) {
                validID = true; 
            } else {
                System.out.println("Selected ID doesn't exist! Please enter a valid ID.");
            }
        } else {
            System.out.println("Invalid input! ID to update must be a number.");
            sc.nextLine(); 
        }
    }

   
    String[] validPrograms = {"UNIFAST", "4PS", "MALASAKIT", "TULONG DUNGOG"};
    
    
    String pname = "";
    boolean isValidProgram = false;
    while (!isValidProgram) {
        System.out.println("(UNIFAST, 4PS, MALASAKIT, TULONG DUNGOG)");
        System.out.print("Enter Program Name: ");
        pname = sc.nextLine().trim();  

       

        if (isNumeric(pname)) {
            System.out.println("Error: Program name cannot be a number.");
            continue; 
        }
        isValidProgram = false;  
        for (String validProgram : validPrograms) {
            if (validProgram.equalsIgnoreCase(pname)) {
                isValidProgram = true;
                break;
            }
        }

        if (!isValidProgram) {
            System.out.println("Error: Invalid program name. Please choose from the following:");
            for (String validProgram : validPrograms) {
                System.out.println(validProgram);
            }
            continue; 
        }
    }
    String qry = "UPDATE tbl_program SET p_name = ? WHERE p_id = ?";
    conf.updateRecord(qry, pname, pid); 
    System.out.println("Program updated successfully.");
}



  public void deleteprogram() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

   
    int pid = 0;
    boolean validID = false;
    while (!validID) {
        System.out.print("Enter Id to Delete: ");
        
       
        if (sc.hasNextInt()) {
            pid = sc.nextInt();
           
            if (conf.getSingleValue("SELECT p_id FROM tbl_program WHERE p_id = ?", pid) != 0) {
                validID = true; 
            } else {
                System.out.println("Selected ID doesn't exist! Please enter a valid ID.");
            }
        } else {
            System.out.println("Invalid input! ID must be a number.");
            sc.nextLine(); 
        }
    }

   
    String qry = "DELETE FROM tbl_program WHERE p_id = ?";
    conf.deleteRecord(qry, pid); // Call deleteRecord to delete the program
    System.out.println("Program with ID " + pid + " has been deleted successfully.");
}
}