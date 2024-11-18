package ivon11;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class beneficiary {

    Scanner sc = new Scanner(System.in);

    public void btransaction() {
         Scanner sc = new Scanner(System.in);
          
        String response = null;
        do {
            System.out.println("\n---------------------------");
            System.out.println("BENEFICIARY PANEL:");
            System.out.println("1. Add beneficiary:");
            System.out.println("2. View beneficiary:");
            System.out.println("3. Update beneficiary:");
            System.out.println("4. Delete beneficiary:");
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
                    sc.nextLine();  // Clear the buffer after invalid input
                }
            }

            switch (act) {
                case 1:
                    addbeneficiary();
                    break;
                case 2:
                    viewbeneficiary();
                    break;
                case 3:
                    viewbeneficiary();
                    updatebeneficiary();
                    viewbeneficiary();
                    break;
                case 4:
                    viewbeneficiary();
                    deletebeneficiary();
                    viewbeneficiary();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Unexpected error.");
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

    public void addbeneficiary() {
        String bname;
        

        while (true) {
            System.out.print("Enter Beneficiary Name: ");
            bname = sc.nextLine().trim();

            
            if (!bname.isEmpty() && bname.length() > 1 && Pattern.matches("^[a-zA-Z\\s]+$", bname)) {
                break;  
            } else {
                System.out.println("Invalid name. Please enter a valid name (letters and spaces only, and must be more than one character).");
            }
        }
    String bage;
    while (true) {
        System.out.print("Enter Beneficiary Age: ");
        bage = sc.nextLine().trim();
        if (isPositiveInteger(bage)) {
            break; 
        } else {
            System.out.println("Invalid age. Please input a number.");
        }
    }

    String badd;
while (true) {
    System.out.print("Enter Beneficiary Address: ");
    badd = sc.nextLine().trim();

    
    if (badd.isEmpty()) {
        
    } 
   
    if (badd.length() == 1 && Character.isLetter(badd.charAt(0))) {
        System.out.println("Address cannot be a single letter. Please enter a valid address.");
    }

    else if (badd.matches("\\d+")) {
        System.out.println("Address cannot be a number. Please enter a valid address.");
    }
   
    else {
        break; 
    }
}

    String bfam;
    while (true) {
        System.out.print("Enter Beneficiary Family Members: ");
        bfam = sc.nextLine().trim();
        if (!bfam.isEmpty() && isPositiveInteger(bfam)) {
            break; 
        } else {
            System.out.println("Invalid input. Please enter a number for family members.");
        }
    }

    String qry = "INSERT INTO tbl_beneficiary (b_name, b_age, b_address, b_fam) VALUES (?, ?, ?, ?)";
    config conf = new config();
    
  
    conf.addRecord(qry, bname, bage, badd, bfam); 
}


private boolean isPositiveInteger(String str) {
    return str.matches("\\d+"); 
}
    
    public void viewbeneficiary() {
        String qry = "SELECT * FROM tbl_beneficiary";
        String[] hrds = {"b_id", "b_name", "b_age", "b_address", "b_fam"};
        String[] clms = {"b_id", "b_name", "b_age", "b_address", "b_fam"};
        config conf = new config(); 
        conf.viewRecords(qry, hrds, clms);
    }    

public void updatebeneficiary() {
    config conf = new config();
    int bid = 0;

   
    boolean validID = false;
    while (!validID) {
        System.out.print("Enter ID to update: ");
        if (sc.hasNextInt()) { 
            bid = sc.nextInt();
            sc.nextLine(); 
           
            if (conf.getSingleValue("SELECT b_id FROM tbl_beneficiary WHERE b_id = ?", bid) != 0) {
                validID = true; 
            } else {
                System.out.println("Selected ID doesn't exist! Please enter a valid ID.");
            }
        } else {
          
            System.out.println("Invalid input! ID  to update must be a number.");
            sc.nextLine(); 
        }
    }

     String bname;

        while (true) {
            System.out.print("Enter New Beneficiary Name: ");
            bname = sc.nextLine().trim();
        String existingName = null;
  
        
            if (bname.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            } else if (bname.length() <= 1) {
                System.out.println("Name must be more than one character.");
            } else if (!Pattern.matches("^[a-zA-Z\\s]+$", bname)) {
                System.out.println("Invalid name. Please enter a valid name (letters and spaces only).");
            } else if (bname.equalsIgnoreCase(existingName)) {
                // Validate that the new name is not the same as the existing name
                System.out.println("The new name cannot be the same as the existing name.");
            } else {
                // If all validations are passed, update the beneficiary name
                System.out.println("Beneficiary name updated to: " + bname);
                break; // Exit the loop if the name is valid
            }
        }
    
   
    
        
        System.out.print("Enter New Beneficiary Age: ");
String bage = sc.nextLine().trim(); // Use nextLine() to capture the entire input line

while (true) {
    // Validate if the input is a positive integer
    if (isPositiveInteger(bage)) {
        break; // Exit the loop if the input is a valid positive integer
    } else {
        System.out.println("Invalid age. Please input a number.");
        System.out.print("Enter New Beneficiary Age: ");
        bage = sc.nextLine().trim(); // Re-prompt for valid input
    }
}
      System.out.print("Enter New Beneficiary Address: ");
String badd = sc.nextLine().trim(); // Use nextLine to read the entire line

while (true) {
    // Validate address input
    if (badd.isEmpty()) {
        System.out.println("Address cannot be empty.");
    } 
    else if (badd.length() == 1 && Character.isLetter(badd.charAt(0))) {
        System.out.println("Address cannot be a single letter. Please enter a valid address.");
    }
    else if (badd.matches("\\d+")) {
        System.out.println("Address cannot be a number. Please enter a valid address.");
    } 
    else {
        break; // Exit the loop when a valid address is entered
    }

    // Re-prompt for a valid address if the previous one was invalid
    System.out.print("Enter New Beneficiary Address: ");
    badd = sc.nextLine().trim(); // Ensure the input is read properly
}
        
        
        System.out.print("Enter New Beneficiary Family Members: ");
        String bfam = sc.next();
        
        while (true) {
        bfam = sc.nextLine().trim();
        if (!bfam.isEmpty() && isPositiveInteger(bfam)) {
            break; 
        } else {
            System.out.println("Invalid input. Please enter a number for family members.");
        }
    }
        

        while (conf.getSingleValue("SELECT b_id FROM tbl_beneficiary WHERE b_id = ?", bid) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Beneficiary ID Again:");
            bid = sc.nextInt();
           
        }
        String qry = "UPDATE tbl_beneficiary SET b_name = ?, b_age = ?, b_address = ?, b_fam = ? WHERE b_id = ?";
        conf.updateRecord(qry, bname, bage, badd, bfam, bid);
    }
    

    public void deletebeneficiary() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int bid = -1;
        
       
        while (true) {
            System.out.println("Enter Id to Delete:");

           
            if (sc.hasNextInt()) {
                bid = sc.nextInt();
                
             
                if (conf.getSingleValue("SELECT b_id FROM tbl_beneficiary WHERE b_id = ?", bid) == 0) {
                    System.out.println("Selected ID doesn't exist!");
                } else {
                  
                    break;
                }
            } else {
                System.out.println("Invalid input! Please enter a valid integer.");
                sc.next(); 
            }
        }

        String qry = "DELETE FROM tbl_beneficiary WHERE b_id = ?";
        conf.deleteRecord(qry, bid);
        System.out.println("Beneficiary with ID " + bid + " has been deleted.");
    }
}
