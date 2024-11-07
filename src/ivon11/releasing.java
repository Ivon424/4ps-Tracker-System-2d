
package ivon11;

import java.util.Scanner;

public class releasing {
    // Use a single Scanner object for all input
    private Scanner sc = new Scanner(System.in);

    public void rtransaction() {
        String response;
        do {
            System.out.println("\n---------------------------");
            System.out.println("RELEASING PANEL:");
            System.out.println("1. Add released");
            System.out.println("2. View released");
            System.out.println("3. Update released");
            System.out.println("4. Delete released");
            System.out.println("5. Exit");

            System.out.print("Enter Selection: ");
            int act = sc.nextInt();
            sc.nextLine(); // Clear buffer

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

            System.out.println("Do you want to continue? (yes/no):");
            response = sc.nextLine();

        } while (response.equalsIgnoreCase("yes"));
    }

    // Add a new release (with input validation)
    public void addreleasing() {
        config conf = new config();

        // Select a beneficiary
        beneficiary bs = new beneficiary();
        bs.viewbeneficiary();

        int bid = getValidBeneficiaryId(conf);  // Get valid beneficiary ID

        // Select a program
        program pm = new program();
        pm.viewprogram();

        int pid = getValidProgramId(conf);  // Get valid program ID

        // Released cash (double check input)
        double rcash = getValidReleasedCash();

        // Released date (validate date format)
        String rdate = getValidReleasedDate();

        // Released status (validate status input)
        String rstatus = getValidReleasedStatus();

        // Insert into the database
        String sql = "INSERT INTO tbl_releasing (b_id, p_id, r_cash, r_date, r_status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, bid, pid, rcash, rdate, rstatus);

        System.out.println("Releasing successfully added.");
    }

    // Method to get a valid beneficiary ID
    private int getValidBeneficiaryId(config conf) {
        int bid;
        while (true) {
            System.out.print("Enter the ID of the Beneficiary: ");
            bid = sc.nextInt();
            String bsql = "SELECT b_id FROM tbl_beneficiary WHERE b_id = ?";
            if (conf.getSingleValue(bsql, bid) != 0) {
                break;  // Beneficiary exists
            }
            System.out.println("Beneficiary does not exist. Please select a valid Beneficiary ID.");
        }
        return bid;
    }

    // Method to get a valid program ID
    private int getValidProgramId(config conf) {
        int pid;
        while (true) {
            System.out.print("Enter the ID of the Program: ");
            pid = sc.nextInt();
            String psql = "SELECT p_id FROM tbl_program WHERE p_id = ?";
            if (conf.getSingleValue(psql, pid) != 0) {
                break;  // Program exists
            }
            System.out.println("Program does not exist. Please select a valid Program ID.");
        }
        return pid;
    }

    // Method to get valid released cash (double)
    private double getValidReleasedCash() {
        double rcash;
        while (true) {
            System.out.print("Enter Released Cash: ");
            rcash = sc.nextDouble();
            if (rcash > 0) {
                break;  // Cash must be positive
            } else {
                System.out.println("Invalid amount. Cash must be a positive number.");
            }
        }
        return rcash;
    }

    // Method to get a valid released date (format YYYY-MM-DD)
    private String getValidReleasedDate() {
        String rdate;
        while (true) {
            System.out.print("Enter Released Date (YYYY-MM-DD): ");
            rdate = sc.nextLine();
            if (rdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                break;  // Date format is correct
            } else {
                System.out.println("Invalid date format. Please enter in the format YYYY-MM-DD.");
            }
        }
        return rdate;
    }

    // Method to get valid status (must be one of a predefined set)
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

    // View all released records
    public void viewreleasing() {
        config conf = new config();
        String projectQuery = "SELECT * FROM tbl_releasing "
                + "JOIN tbl_beneficiary ON tbl_releasing.b_id = tbl_beneficiary.b_id "
                + "JOIN tbl_program ON tbl_releasing.p_id = tbl_program.p_id";
        String[] projectHeaders = {"Releasing ID", "Beneficiary Name", "Program Name", "Released Cash", "Released Date", "Status"};
        String[] projectColumns = {"r_id", "b_name", "p_name", "r_cash", "r_date", "r_status"};

        conf.viewRecords(projectQuery, projectHeaders, projectColumns);
    }

    // Update releasing record
    public void updatereleasing() {
        config conf = new config();

        System.out.print("Enter Releasing ID to Update: ");
        int rid = sc.nextInt();
        sc.nextLine(); // Clear the buffer

        System.out.print("Enter new Beneficiary Name: ");
        String bname = sc.nextLine();

        System.out.print("Enter new Program Name: ");
        String pname = sc.nextLine();

        double rcash = getValidReleasedCash();

        String rdate = getValidReleasedDate();

        String rstatus = getValidReleasedStatus();

        String sql = "UPDATE tbl_releasing SET b_name = ?, p_name = ?, r_cash = ?, r_date = ?, r_status = ? WHERE r_id = ?";
        conf.updateRecord(sql, bname, pname, rcash, rdate, rstatus, rid);

        System.out.println("Releasing record updated successfully.");
    }

    // Delete releasing record
    public void deletereleasing() {
        config conf = new config();
        System.out.print("Enter Releasing ID to delete: ");
        int rid = sc.nextInt();
        sc.nextLine(); // Clear buffer

        System.out.print("Are you sure you want to delete Releasing ID " + rid + "? (Y/N): ");
        String confirmation = sc.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            String sql = "DELETE FROM tbl_releasing WHERE r_id = ?";
            conf.deleteRecord(sql, rid);
            System.out.println("Releasing deleted successfully.");
        } else {
            System.out.println("Delete action canceled.");
        }
    }
}
    

    
    
    
    
    
    
    
    

