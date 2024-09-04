package ClinicManagement;

import Exceptions.AppointmentNotFoundException;
import Exceptions.PatientRecordNotFoundException;
import databaseConnection.Conn;

import java.sql.ResultSet;
import java.util.Scanner;


public class Appointment {
   static Scanner in=new Scanner(System.in);
    public Appointment()
    {
        System.out.println("Select the Operation you want to perform");
        System.out.println("1. Schedule A New Appointment");
        System.out.println("2. View Appointment Details");
        System.out.println("3. Update Appointment Info");
        System.out.println("4. Cancel an Appointment");

        chooseAopt();
    }

    private void chooseAopt() {
        int opt=in.nextInt();
        switch (opt)
        {
            case 1:addApp();
                break;
            case 2:
                try {
                    viewApp();
                }catch (AppointmentNotFoundException e)
                {
                    System.out.println(e.getMessage());
                }
                break;
            case 3:updateApp();
                break;
            case 4:cancelApp();
                break;
            default:System.out.println("Choose among the given options !!!");
                chooseAopt();
                break;

        }
    }

    private void cancelApp() {
        System.out.println("Enter the ID of the Appointment to be deleted :");
        int id=in.nextInt();
        String status="Cancelled";

        try{
            Conn c=new Conn();
            String query="update appointment set satus='"+status+"' where app_id='"+id+"'";
            c.s.executeUpdate(query);

            System.out.println(" Appointment record with id:'"+id+"' is cancelled!!");

        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private void updateApp() {
        System.out.println("Enter the ID of the Appointment to be Updated :");
        int id=in.nextInt();
        System.out.println("Choose the field to be Updated:");
        System.out.println("1.Appoint ID");
        System.out.println("2.Patient ID");
        System.out.println("3.Date of Appointment");
        System.out.println("4.Doctor");
        System.out.println("5.Status");
        int opt=in.nextInt();
        String change="";
        String column="";
        int naid=0;
        int nid=0;
        switch(opt)
        {
            case 1:
                System.out.println("Enter New Appointment ID:");
                column="app_id";
                naid=in.nextInt();
                break;
            case 2:
                System.out.println("Enter Patient ID:");
                column="pid";
                nid=in.nextInt();
                break;
            case 3:
                System.out.println("Enter New appointment Date:");
                column="app_date";
                change=in.next();
                break;
            case 4:
                System.out.println("Enter updated doctor name:");
                column="doctor";
                change=in.next();
                break;
            case 5:
                System.out.println("choose updated status:");
                System.out.println("1.Scheduled");
                System.out.println("2.Completed");
                System.out.println("3.Cancelled");
                int i=in.nextInt();
                switch (i)
                {
                    case 1:
                        change="Scheduled";
                        break;
                    case 2:
                        change="Completed";
                        break;
                    case 3:
                        change="Cancelled";
                        break;
                }
                column="status";
                break;
        }

        try{
            Conn c=new Conn();
            String query="";
            if(naid>0)
                query="update appointment set app_id="+naid+" where app_id="+id+" ";
            else if(nid>0)
                query="update appointment set pid="+nid+" where app_id="+id+" ";
            else
                query="update appointment set "+column+"='"+change+"' where app_id="+id+" ";

            c.s.executeUpdate(query);

            System.out.println(" Appointment record Updated ");

        }catch (Exception e)
        {
            System.out.println(e);
        }

    }

    private void viewApp() throws AppointmentNotFoundException{

        System.out.println("Enter the ID of the Appointment to be shown:");
        int id=in.nextInt();

        try{
            Conn c=new Conn();
            String query="select * from appointment where app_id='"+id+"'";
            // c.s.executeUpdate(query);
            ResultSet rs=c.s.executeQuery(query);

            if(!rs.isBeforeFirst())
                throw new AppointmentNotFoundException("Appointment not found for id:"+id);
            while(rs.next())
            {
                int aid=rs.getInt("app_id");
                int pid=rs.getInt("pid");
                String dob=rs.getString("app_date");
                String gender= rs.getString("doctor");
                String phone= rs.getString("status");
                System.out.println("Details of Patient:");
                System.out.println();
                System.out.println("Appointment ID: '"+aid+"'");
                System.out.println("Patient ID:'"+pid+"'");
                System.out.println("Date of Appointment:'"+dob+"'");
                System.out.println("Doctor:'"+gender+"'");
                System.out.println("status:'"+phone+"'");

            }
            // System.out.println(" Patient record with id:'"+id+"' is deleted!!");

        }catch (AppointmentNotFoundException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private void addApp() {

        System.out.println("Enter the details");
        System.out.println("Enter Appointment ID:");
        int aid=in.nextInt();
        System.out.println("Enter Patient ID:");
        int pid=in.nextInt();
        System.out.println("Enter Date of Appointment(YYYY-MM-DD):");
        String doa=in.next();
        System.out.println("Enter Doctor name:");
        String doctor=in.next();
        String status="Scheduled";


        try{
            Conn c=new Conn();
            String query="insert into appointment values('"+aid+"','"+pid+"','"+doa+"','"+doctor+"','"+status+"')";
            c.s.executeUpdate(query);
        }catch (Exception e)
        {
            System.out.println(e);
        }

        System.out.println( "Details added successfully");

    }


}
