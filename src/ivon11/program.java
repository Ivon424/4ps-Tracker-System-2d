

package ivon11;

import java.util.Scanner;


public class program {
    
     
     Scanner sc = new Scanner(System.in);

    public void ptransaction() {
        String response;
        do {
            System.out.println("\n---------------------------");
            System.out.println("program PANEL:");
            System.out.println("1. Add program:");
            System.out.println("2. View program:");
            System.out.println("3. Update program:");
            System.out.println("4. Delete program:");
            System.out.println("5. Exit:");

            System.out.print("Enter Selection: ");
            int act = sc.nextInt();
            sc.nextLine(); 
            
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

            System.out.println("Do you want to continue? (yes/no):");
            response = sc.next();
            
        } while (response.equalsIgnoreCase("yes"));     
    }
   
  public void addprogram(){
  
        System.out.println("(UNIFAST, 4PS, MALASAKIT, TULONG DUNGOG)");
        System.out.print("Enter Program Name: ");
        
        
        String pname = sc.nextLine().trim();  
        if (pname.isEmpty()) {
            System.out.println("Error: Program name cannot be empty.");
            return;
        }

      
        String[] validPrograms = {"UNIFAST", "4PS", "MALASAKIT", "TULONG DUNGOG"};
        
        boolean isValid = false;
        for (String validProgram : validPrograms) {
            if (validProgram.equalsIgnoreCase(pname)) {
                isValid = true;
                break;
            }
        }

        
        if (!isValid) {
            System.out.println("Error: Invalid program name. Please choose from the following:");
            for (String validProgram : validPrograms) {
                System.out.println(validProgram);
            }
            return;
        }

        
        String qry = "INSERT INTO tbl_program (p_name) VALUES (?)";
        config conf = new config();
        conf.addRecord(qry, pname);

        System.out.println("Program added successfully.");
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
        System.out.print("Enter ID to update: ");
        int pid = sc.nextInt();
        
        
        if (pid <= 0) {
            System.out.println("Error: ID must be a positive integer.");
            return;
        }

      
        System.out.println("(UNIFAST, 4PS, MALASAKIT, TULONG DUNGOG)");
        System.out.print("Enter new Program Name: ");
        sc.nextLine();  
        String pname = sc.nextLine().trim();
        if (pname.isEmpty()) {
            System.out.println("Error: Program name cannot be empty.");
            return; 
        }

      
        String[] validPrograms = {"UNIFAST", "4PS", "MALASAKIT", "TULONG DUNGOG"};
        boolean isValid = false;
        for (String validProgram : validPrograms) {
            if (validProgram.equalsIgnoreCase(pname)) {
                isValid = true;
                break;
            }
        }

       
        if (!isValid) {
            System.out.println("Error: Invalid program name. Please choose from the following:");
            for (String validProgram : validPrograms) {
                System.out.println(validProgram);
            }
            return;
        }

     
        String qry = "UPDATE tbl_program SET p_name = ? WHERE p_id = ?";
        
       
        conf.updateRecord(qry, pname, pid);

        System.out.println("Program updated successfully.");
    }
  
  
    
    public void deleteprogram(){
        Scanner sc = new Scanner (System.in);
        config conf = new config();
        System.out.println("Enter Id to Delete:");
        int pid = sc.nextInt();
         
        
          while (conf.getSingleValue("SELECT p_id FROM tbl_program WHERE p_id = ?", pid) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Program ID Again:");
            pid = sc.nextInt();
           
          }
        
        String qry ="DELETE FROM tbl_program WHERE p_id =?";
   
        conf.deleteRecord(qry, pid);
    }
    }