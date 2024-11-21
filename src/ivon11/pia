
package ivon11;

import java.sql.*;
import java.util.Scanner;

public class report {
    private Scanner input = new Scanner(System.in);
    private config conf = new config();

    // Main menu for generating reports
    public void rtTransaction() {
        boolean exit = true;
        do {
            System.out.println("------------------------------------------");
            System.out.println("Releasing Report");
            System.out.println("");
            System.out.println("1. General Report");
            System.out.println("2. Individual Releasing Report");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");

            int choice;
            while (true) {
                try {
                    choice = input.nextInt();
                    if (choice > 0 && choice < 4) {
                        break;
                    } else {
                        System.out.print("Invalid choice. Enter again: ");
                    }
                } catch (Exception e) {
                    input.next();  // Clear the invalid input
                    System.out.print("Invalid input. Enter again: ");
                }
            }

            switch (choice) {
                case 1:
                    generalReport();
                    break;
               
                case 2:
                    individualReport();
                    break;
                    
                default:
                    exit = false;
                    System.out.println("Exiting the report system.");
                    break;
            }
        } while (exit);
    }

    // General Report method
    private void generalReport() {
        System.out.println("-------------------------------------------------");
        System.out.println("           General Releasing Report         ");
        System.out.println("------------------------------------------------");

        String query = "SELECT r_id, b_name, p_name, r_cash, r_date, r_status "
                     + "FROM tbl_releasing "
                     + "JOIN tbl_beneficiary ON tbl_releasing.b_id = tbl_beneficiary.b_id "
                     + "JOIN tbl_program ON tbl_releasing.p_id = tbl_program.p_id";
        
        try (Connection conn = conf.connectDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%-10s %-20s %-20s %-15s %-20s %-10s\n", "Releasing ID", "Beneficiary Name", "Program Name", "Released Cash", "Release Date", "Status");

            while (rs.next()) {
                System.out.printf("%-10d %-20s %-20s %-15.2f %-20s %-10s\n",
                        rs.getInt("r_id"),
                        rs.getString("b_name"),
                        rs.getString("p_name"),
                        rs.getDouble("r_cash"),
                        rs.getString("r_date"),
                        rs.getString("r_status"));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving general report data: " + e.getMessage());
        }
    }

   // Individual Report method
private void individualReport() {
    boolean exit = true;
    System.out.println("-----------------------------------------------");
    System.out.println("Individual Releasing Report");

    // Display all available releasing IDs first
    displayAvailableReleasingIDs();

    System.out.println("Enter Releasing ID to View ");
    
    int rid = -1;  // Default value to indicate an invalid ID initially
    while (exit) {
        System.out.print("Enter Releasing ID: ");
        try {
            rid = input.nextInt();
            
            // Check if user wants to exit
            if (rid == 0) {
                exit = false;
                System.out.println("Exiting Individual Report.");
                break;  // Exit the loop
            }

            // Validate the Releasing ID
            if (doesReleasingIDExist(rid)) {
                break;  // Exit the loop if valid Releasing ID is found
            } else {
                System.out.println("Releasing ID does not exist. Please enter a valid Releasing ID.");
            }

        } catch (Exception e) {
            input.next();  // Clear the invalid input from the scanner buffer
            System.out.println("Invalid input. Please enter a valid Releasing ID (numeric value).");
        }
    }

    // Fetch and display data for valid ID
    if (rid != 0) {
        displayIndividualReport(rid);
    }
}

// Method to display all available Releasing IDs
private void displayAvailableReleasingIDs() {
    String sql = "SELECT r_id FROM tbl_releasing";
    try (Connection conn = conf.connectDB();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        System.out.println("Available Releasing IDs:");
        boolean found = false;
        while (rs.next()) {
            System.out.println("Releasing ID: " + rs.getInt("r_id"));
            found = true;
        }

        if (!found) {
            System.out.println("No available Releasing IDs.");
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving Releasing IDs: " + e.getMessage());
    }
}

// Method to display individual report details for the given Releasing ID
private void displayIndividualReport(int rid) {
    try {
        String releasingSQL = "SELECT b_name, p_name, r_cash, r_date, r_status "
                            + "FROM tbl_releasing "
                            + "JOIN tbl_beneficiary ON tbl_releasing.b_id = tbl_beneficiary.b_id "
                            + "JOIN tbl_program ON tbl_releasing.p_id = tbl_program.p_id "
                            + "WHERE r_id = ?";

        try (Connection conn = conf.connectDB();
             PreparedStatement releasingStmt = conn.prepareStatement(releasingSQL)) {

            releasingStmt.setInt(1, rid);
            try (ResultSet releasingRs = releasingStmt.executeQuery()) {
                if (releasingRs.next()) {
                    System.out.println("-------------------------------------------------------------");
                    System.out.println("Individual Releasing Information");
                    System.out.printf("Beneficiary Name: %s\n", releasingRs.getString("b_name"));
                    System.out.printf("Program Name: %s\n", releasingRs.getString("p_name"));
                    System.out.printf("Released Cash: %.2f\n", releasingRs.getDouble("r_cash"));
                    System.out.printf("Released Date: %s\n", releasingRs.getString("r_date"));
                    System.out.printf("Status: %s\n", releasingRs.getString("r_status"));
                    System.out.println("------------------------------------------------------------------");
                } else {
                    System.out.println("No record found for Releasing ID: " + rid);
                }
            }
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving data for Releasing ID: " + e.getMessage());
    }
}

// Helper method to check if a releasing ID exists in the database
private boolean doesReleasingIDExist(int rid) {
    String query = "SELECT COUNT(*) FROM tbl_releasing WHERE r_id = ?";
    try (Connection conn = conf.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setInt(1, rid);
        try (ResultSet rs = pstmt.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }

    } catch (SQLException e) {
        System.out.println("Error checking Releasing ID existence: " + e.getMessage());
    }
    return false;
}
}