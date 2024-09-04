package ClinicManagement;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//import java.sql.PreparedStatement;
import java.util.*;
public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Clinic Management System");
        System.out.println("Choose the Sector you want to manage");
        System.out.println(" 1. Patient Management");
        System.out.println(" 2. Appointment Management");
        System.out.println(" 3. Medical Record Management");
        Scanner in=new Scanner(System.in);
        int n=in.nextInt();

        choose(n);
        System.out.println("Thank You !!!");

    }

    private static void choose(int n) {
        Scanner in=new Scanner(System.in);
        switch(n)
        {
            case 1:Patient p=new Patient();
                break;
            case 2:Appointment a=new Appointment();
                break;
            case 3:Medical m=new Medical();
                break;
            default:
                System.out.println("Choose Among the Given Options !!!");
                n=in.nextInt();
                choose(n);
                break;
        }
    }


}