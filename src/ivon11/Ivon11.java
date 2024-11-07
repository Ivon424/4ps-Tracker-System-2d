package ivon11;

import java.util.Scanner;


public class Ivon11 {

  
    public static void main(String[] args) {
     Scanner sc = new  Scanner(System.in);
     
     boolean  exit = true;
     do{
         System.out.println("\n---------------------------");
        System.out.println("Welcome to the 4p's Tracker:");
        System.out.println("");
        System.out.println("1.Beneficiary:");
        System.out.println("2.Program:");
         System.out.println("3.Releasing:");
         System.out.println("4.Report:");
        System.out.println("5.Exit:");
        
        
        System.out.println("Enter Choice:");
        int act = sc.nextInt();
        
        switch(act){
            case 1:
                beneficiary bs = new beneficiary();
                bs.btransaction();
                break;  
            case 2:
                program pm =new program();
                pm.ptransaction();
               
                break;
                
            case 3:
                releasing rg =new releasing();
                rg.rtransaction();
                
            case 5:
                System.out.print("Exit Selected....type'yes' to continue:");
                String resp = sc.next();
                if(resp.equalsIgnoreCase("yes")){
                exit =false;
                }
                break;
        }  
     }while(exit);
     }
     

     }
