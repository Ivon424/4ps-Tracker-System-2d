
package ivon11;

import java.util.InputMismatchException;
import java.util.Scanner;

public class releasing {
   
    private Scanner sc = new Scanner(System.in);

    public void rtransaction() {
        String response = null;
        do {
            System.out.println("\n---------------------------");
            System.out.println("RELEASING PANEL:");
            System.out.println("1. Add released");
            System.out.println("2. View released");
            System.out.println("3. Update released");
            System.out.println("4. Delete released");
            System.out.println("5. Exit");
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
                    addreleasing();
                    viewreleasing();
                    break;
                case 2:
                    viewreleasing();
                    break;
                case 3:
                    viewreleasing();
                    updatereleasing();
                    viewreleasing();
                    break;
                case 4:
                    viewreleasing();
                    deletereleasing();
                    viewreleasing();
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
   public void addreleasing() {
        config conf = new config();

      
        beneficiary bs = new beneficiary();
        bs.viewbeneficiary();

        int bid = getValidBeneficiaryId(conf); 
       
        program pm = new program();
        pm.viewprogram();

        int pid = getValidProgramId(conf);  

       
        double rcash = getValidReleasedCash();

       
        String rdate = getValidReleasedDate();

                String rstatus = getValidReleasedStatus();

       
        String sql = "INSERT INTO tbl_releasing (b_id, p_id, r_cash, r_date, r_status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, bid, pid, rcash, rdate, rstatus);

        System.out.println("Releasing successfully added.");
    }

  
    private int getValidBeneficiaryId(config conf) {
        int bid;
        while (true) {
            System.out.print("Enter the ID of the Beneficiary: ");
            if (sc.hasNextInt()) {
                bid = sc.nextInt();
                String bsql = "SELECT b_id FROM tbl_beneficiary WHERE b_id = ?";
                if (conf.getSingleValue(bsql, bid) != 0) {
                    sc.nextLine(); 
                    break;  
                }
                System.out.println("Beneficiary does not exist. Please select a valid Beneficiary ID.");
            } else {
                System.out.println("Invalid input! Beneficiary ID must be a number.");
                sc.next(); 
            }
        }
        return bid;
    }

    
    private int getValidProgramId(config conf) {
        int pid;
        while (true) {
            System.out.print("Enter the ID of the Program: ");
            if (sc.hasNextInt()) {
                pid = sc.nextInt();
                String psql = "SELECT p_id FROM tbl_program WHERE p_id = ?";
                if (conf.getSingleValue(psql, pid) != 0) {
                    sc.nextLine();  
                    break;  
                }
                System.out.println("Program does not exist. Please select a valid Program ID.");
            } else {
                System.out.println("Invalid input! Program ID must be a number.");
                sc.next(); 
            }
        }
        return pid;
    }

   
    private double getValidReleasedCash() {
        double rcash;
        while (true) {
            System.out.print("Enter Released Cash: ");
            if (sc.hasNextDouble()) {
                rcash = sc.nextDouble();
                if (rcash > 0) {
                    sc.nextLine();  
                    break;  
                } else {
                    System.out.println("Invalid amount. Cash must be a positive number.");
                }
            } else {
                System.out.println("Invalid input! Released Cash must be a number.");
                sc.next();  
            }
        }
        return rcash;
    }

   
    private String getValidReleasedDate() {
        String rdate;
        while (true) {
            System.out.print("Enter Released Date (YYYY-MM-DD): ");
            rdate = sc.nextLine().trim();
            if (rdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                break;  
            } else {
                System.out.println("Invalid date format. Please enter in the format YYYY-MM-DD.");
            }
        }
        return rdate;
    }

   
    private String getValidReleasedStatus() {
        String rstatus;
        while (true) {
            System.out.print("Enter Released Status (e.g., Pending, Released, Canceled): ");
            rstatus = sc.nextLine().trim();
            if (rstatus.equalsIgnoreCase("Pending") || rstatus.equalsIgnoreCase("Released") || rstatus.equalsIgnoreCase("Canceled")) {
                break;  // Valid status
            } else {
                System.out.println("Invalid status. Please enter one of the following: Pending, Released, Canceled.");
            }
        }
        return rstatus;
    }

   
    public void viewreleasing() {
        config conf = new config();
        String projectQuery = "SELECT * FROM tbl_releasing "
                + "JOIN tbl_beneficiary ON tbl_releasing.b_id = tbl_beneficiary.b_id "
                + "JOIN tbl_program ON tbl_releasing.p_id = tbl_program.p_id";
        String[] projectHeaders = {"Releasing ID", "Beneficiary Name", "Program Name", "Released Cash", "Released Date", "Status"};
        String[] projectColumns = {"r_id", "b_name", "p_name", "r_cash", "r_date", "r_status"};

        conf.viewRecords(projectQuery, projectHeaders, projectColumns);
    }

    
    public void updatereleasing() {
    
config conf = new config();
        int rid = getValidReleasingId(conf); 
        String pname = getValidProgramName();  
        int pid = getProgramIdFromName(conf, pname);  

        double rcash = getValidReleasedCash();  
        String rdate = getValidReleasedDate(); 
        String rstatus = getValidReleasedStatus(); 
        // Update releasing record query
        String sql = "UPDATE tbl_releasing SET p_id = ?, r_cash = ?, r_date = ?, r_status = ? WHERE r_id = ?";
        conf.updateRecord(sql, pid, rcash, rdate,rstatus, rid);  // Execute the update

        System.out.println("Releasing record updated successfully.");
    }

    
    private int getValidReleasingId(config conf) {
        int rid = 0;
        boolean validID = false;
        while (!validID) {
            System.out.print("Enter ID of releasing to update: ");
            if (sc.hasNextInt()) {
                rid = sc.nextInt();
                sc.nextLine(); 
                if (conf.getSingleValue("SELECT r_id FROM tbl_releasing WHERE r_id = ?", rid) != 0) {
                    validID = true; 
                } else {
                    System.out.println("Selected ID doesn't exist! Please enter a valid ID.");
                }
            } else {
                System.out.println("Invalid input! ID must be a number.");
                sc.nextLine();
            }
        }
        return rid;
    }

   
    private String getValidProgramName() {
        String pname = "";
        boolean isValidProgram = false;
        String[] validPrograms = {"UNIFAST", "4PS", "MALASAKIT", "TULONG DUNGOG"};
        while (!isValidProgram) {
            System.out.println("(UNIFAST, 4PS, MALASAKIT, TULONG DUNGOG)");
            System.out.print("Enter New Program Name: ");
            pname = sc.nextLine().trim();

          
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
            }
        }
        return pname;
    }

    
    private int getProgramIdFromName(config conf, String pname) {
        String sql = "SELECT p_id FROM tbl_program WHERE p_name = ?";
        return (int) conf.getSingleValue(sql, pname);  
    }

    public void deletereleasing() {
     Scanner sc = new Scanner(System.in);
    config conf = new config();

   
    int rid = 0;
    boolean validID = false;
    while (!validID) {
        System.out.print("Enter Id to Delete: ");
        
       
        if (sc.hasNextInt()) {
            rid = sc.nextInt();
           
            if (conf.getSingleValue("SELECT r_id FROM tbl_releasing WHERE r_id = ?", rid) != 0) {
                validID = true; 
            } else {
                System.out.println("Selected ID doesn't exist! Please enter a valid ID.");
            }
        } else {
            System.out.println("Invalid input! ID must be a number.");
            sc.nextLine(); 
        }
    }

   
    String qry = "DELETE FROM tbl_releasing WHERE r_id = ?";
    conf.deleteRecord(qry, rid); 
    System.out.println("Program with ID " + rid + " has been deleted successfully.");
}
}