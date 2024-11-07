package ivon11;

import java.util.Scanner;
import java.util.regex.Pattern;

public class beneficiary {
    
     Scanner sc = new Scanner(System.in);

    public void btransaction() {
        String response;
        do {
            System.out.println("\n---------------------------");
            System.out.println("BENEFICIARY PANEL:");
            System.out.println("1. Add beneficiary:");
            System.out.println("2. View beneficiary:");
            System.out.println("3. Update beneficiary:");
            System.out.println("4. Delete beneficiary:");
            System.out.println("5. Exit:");

            System.out.print("Enter Selection: ");
            int act = sc.nextInt();
            sc.nextLine(); 

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
                    System.out.println("Invalid selection. Please try again.");
            }

            System.out.println("Do you want to continue? (yes/no):");
            response = sc.next();
            
        } while (response.equalsIgnoreCase("yes"));     
    }
   
  

public void addbeneficiary() {
    String bname;
    while (true) {
        System.out.print("Enter Beneficiary Name: ");
        bname = sc.nextLine().trim();
        if (!bname.isEmpty() && Pattern.matches("^[a-zA-Z\\s]+$", bname)) {
            break; // Valid name
        } else {
            System.out.println("Invalid name. Please enter a valid name (letters and spaces only).");
        }
    }

    String bage;
    while (true) {
        System.out.print("Enter Beneficiary Age: ");
        bage = sc.nextLine().trim();
        if (isPositiveInteger(bage)) {
            break; // Valid age
        } else {
            System.out.println("Invalid age. Please enter a positive integer.");
        }
    }

    String badd;
    while (true) {
        System.out.print("Enter Beneficiary Address: ");
        badd = sc.nextLine().trim();
        if (!badd.isEmpty()) {
            break; // Valid address
        } else {
            System.out.println("Address cannot be empty.");
        }
    }

    String bfam;
    while (true) {
        System.out.print("Enter Beneficiary Family Members: ");
        bfam = sc.nextLine().trim();
        if (!bfam.isEmpty() && isPositiveInteger(bfam)) {
            break; // Valid family members count
        } else {
            System.out.println("Invalid input. Please enter a positive integer for family members.");
        }
    }

    String qry = "INSERT INTO tbl_beneficiary (b_name, b_age, b_address, b_fam) VALUES (?, ?, ?, ?)";
    config conf = new config();
    
    // Call to addRecord with validated inputs
    conf.addRecord(qry, bname, bage, badd, bfam); 
}

// Helper method to check if a string represents a positive integer
private boolean isPositiveInteger(String str) {
    return str.matches("\\d+"); // Matches one or more digits
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

        System.out.print("Enter ID to update: ");
        int bid = sc.nextInt();
       

      
        while (conf.getSingleValue("SELECT b_id FROM tbl_beneficiary WHERE b_id = ?", bid) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Beneficiary ID Again:");
            bid = sc.nextInt();
           
        }

        System.out.print("Enter New Beneficiary Name: ");
        String bname = sc.next();
        System.out.print("Enter New Beneficiary Age: ");
        String bage = sc.next();
        System.out.print("Enter New Beneficiary Address: ");
        String badd = sc.next();
        System.out.print("Enter New Beneficiary Family Members: ");
        String bfam = sc.next();

        while (conf.getSingleValue("SELECT b_id FROM tbl_beneficiary WHERE b_id = ?", bid) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Beneficiary ID Again:");
            bid = sc.nextInt();
           
        }
        String qry = "UPDATE tbl_beneficiary SET b_name = ?, b_age = ?, b_address = ?, b_fam = ? WHERE b_id = ?";
        conf.updateRecord(qry, bname, bage, badd, bfam, bid);
    }
    
    public void deletebeneficiary(){
        Scanner sc = new Scanner (System.in);
        config conf = new config();
        System.out.println("Enter Id to Delete:");
        int bid = sc.nextInt();
         
        
          while (conf.getSingleValue("SELECT b_id FROM tbl_beneficiary WHERE b_id = ?", bid) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Beneficiary ID Again:");
            bid = sc.nextInt();
           
          }
        
        String qry ="DELETE FROM tbl_beneficiary WHERE b_id =?";
   
        conf.deleteRecord(qry, bid);
    }
    }
