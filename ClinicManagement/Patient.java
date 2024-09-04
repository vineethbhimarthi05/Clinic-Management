package ClinicManagement;

import databaseConnection.Conn;

import java.sql.ResultSet;
import java.util.Scanner;
import Exceptions.PatientRecordNotFoundException;


public class Patient {
     static Scanner in=new Scanner(System.in);

    public Patient(){
        System.out.println("Select the Operation you want to perform");
        System.out.println("1. Add a new patient");
        System.out.println("2. View patient details");
        System.out.println("3. Update patient details");
        System.out.println("4. Delete a patient");

        choosePopt();
    }

    private void choosePopt() {
        //Scanner in=new Scanner(System.in);

        int pn=in.nextInt();
        switch (pn)
        {
            case 1:addPatient();
                break;
            case 2:
                try {
                    viewPatient();
                } catch (PatientRecordNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 3:updatePatient();
                break;
            case 4:deletePatient();
                break;
            default:System.out.println("Choose among the given options !!!");
                choosePopt();
                break;

        }
    }

    private static void addPatient() {
        System.out.println("Enter the details");
        System.out.println("Enter Patient ID:");
        int id=in.nextInt();
        System.out.println("Enter Patient Name:");
        String name=in.next();
        System.out.println("Enter Date of Birth(YYYY-MM-DD):");
        String dob=in.next();
        System.out.println("Enter Gender (M/F):");
        String g=in.next();
        System.out.println("Enter Contact info:");
        String phone=in.next();


        try{
            Conn c=new Conn();
            String query="insert into patient values('"+id+"','"+name+"','"+dob+"','"+g+"','"+phone+"')";
            c.s.executeUpdate(query);
        }catch (Exception e)
        {
            System.out.println(e);
        }

        System.out.println( "Details added successfully");

    }
    private static void deletePatient() {
        System.out.println("Enter the ID of the Patient to be deleted :");
        int id=in.nextInt();

        try{
            Conn c=new Conn();
            String query="delete from patient where pid='"+id+"'";
            c.s.executeUpdate(query);

            System.out.println(" Patient record with id:'"+id+"' is deleted!!");

        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private static void viewPatient() throws PatientRecordNotFoundException {

        System.out.println("Enter the ID of the Patient to be shown:");
        int id=in.nextInt();

        try{
            Conn c=new Conn();
            String query="select * from patient where pid='"+id+"'";
           // c.s.executeUpdate(query);
            ResultSet rs=c.s.executeQuery(query);

            if(!rs.isBeforeFirst())
                throw new PatientRecordNotFoundException("Patient Record not found for id:"+id);
            while(rs.next())
            {
                int pid=rs.getInt("pid");
                String name=rs.getString("name");
                String dob=rs.getString("dob");
                String gender= rs.getString("gender");
                String phone= rs.getString("contact_info");
                System.out.println("Details of Patient:");
                System.out.println();
                System.out.println("Patient ID: '"+pid+"'");
                System.out.println("Patient Name:'"+name+"'");
                System.out.println("Date of Birth:'"+dob+"'");
                System.out.println("Genger:'"+gender+"'");
                System.out.println("Contact:'"+phone+"'");

            }
           // System.out.println(" Patient record with id:'"+id+"' is deleted!!");

        }catch (PatientRecordNotFoundException e)
        {
            throw e;
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void updatePatient() {

        System.out.println("Enter the ID of the Patient to be Updated :");
        int id=in.nextInt();
        System.out.println("Choose the field to be Updated:");
        System.out.println("1.Patient ID");
        System.out.println("2.Patient Name");
        System.out.println("3.Date of Birth");
        System.out.println("4.Gender");
        System.out.println("5.Contact");
        int opt=in.nextInt();
        String change="";
        String column="";
        int nid=0;

        switch(opt)
        {
            case 1:
                System.out.println("Enter New ID:");
                nid=in.nextInt();
                break;
            case 2:
                System.out.println("Enter New Name:");
                column="name";
                change=in.next();
                break;
            case 3:
                System.out.println("Enter New DOB:");
                column="dob";
                change=in.next();
                break;
            case 4:
                System.out.println("Enter New Gender:");
                column="gender";
                change=in.next();
                break;
            case 5:
                System.out.println("Enter New Number:");
                column="contact_info";
                change=in.next();
                break;
        }

        try{
            Conn c=new Conn();
            String query="";
            if(nid>0)
                query="update patient set pid="+nid+" where pid="+id+" ";
            else
                query="update patient set "+column+"='"+change+"' where pid="+id+" ";

            c.s.executeUpdate(query);

            System.out.println(" Patient record Updated ");

        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

}
