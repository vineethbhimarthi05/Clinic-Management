package ClinicManagement;

import Exceptions.MedicalRecordNotFoundException;
import Exceptions.PatientRecordNotFoundException;
import databaseConnection.Conn;

import java.sql.ResultSet;
import java.util.Scanner;

public class Medical {
    static Scanner in=new Scanner(System.in);
    public Medical()
    {
        System.out.println("Select the Operation you want to perform");
        System.out.println("1. Add a new medical record");
        System.out.println("2. View medical record details");
        System.out.println("3. Update medical record information");
        System.out.println("4. Delete a medical record");

        chooseMopt();

    }

    private void chooseMopt() {
        int mn = in.nextInt();
        switch (mn) {
            case 1:
                addMrecord();
                break;
            case 2:try {
                viewMrecord();
            }catch (MedicalRecordNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
                break;
            case 3:
                updateMrecord();
                break;
            case 4:
                deleteMrecord();
                break;
            default:
                System.out.println("Choose among the given options !!!");
                chooseMopt();
                break;


        }
    }


        private void deleteMrecord () {
            System.out.println("Enter the ID of the Medical record to be deleted :");
            int id=in.nextInt();

            try{
                Conn c=new Conn();
                String query="delete from medical where rec_id='"+id+"'";
                c.s.executeUpdate(query);

                System.out.println(" Medical record with id:'"+id+"' is deleted!!");

            }catch (Exception e)
            {
                System.out.println(e);
            }
        }

        private void updateMrecord () {
            System.out.println("Enter the ID of the Medical record to be Updated :");
            int id=in.nextInt();
            System.out.println("Choose the field to be Updated:");
            System.out.println("1.Medical Record ID");
            System.out.println("2.Patient ID");
            System.out.println("3.Diagnosis");
            System.out.println("4.Treatment");
            System.out.println("5.Date");
            int opt=in.nextInt();
            String change="";
            String column="";
            int mnid=0;
            int nid=0;

            switch(opt)
            {
                case 1:
                    System.out.println("Enter New Medical record ID:");
                    mnid=in.nextInt();
                    break;
                case 2:
                    System.out.println("Enter New patient ID:");
                    nid=in.nextInt();
                    break;
                case 3:
                    System.out.println("Enter Updated Diagnosis:");
                    column="dgs";
                    change=in.next();
                    break;
                case 4:
                    System.out.println("Enter Updated Treatment:");
                    column="treat";
                    change=in.next();
                    break;
                case 5:
                    System.out.println("Enter Updated Date:");
                    column="date";
                    change=in.next();
                    break;
            }

            try{
                Conn c=new Conn();
                String query="";
                if(mnid>0)
                    query="update medical set rec_id="+mnid+" where rec_id="+id+" ";
                else if(nid>0)
                    query="update medical set pid="+nid+" where rec="+id+" ";
                else
                    query="update medical set "+column+"='"+change+"' where rec_id="+id+" ";

                c.s.executeUpdate(query);

                System.out.println(" Medical record Updated ");

            }catch (Exception e)
            {
                System.out.println(e);
            }
        }

        private void viewMrecord() throws MedicalRecordNotFoundException {
            System.out.println("Enter the ID of the Medical record to be shown:");
            int id=in.nextInt();

            try{
                Conn c=new Conn();
                String query="select * from medical where rec_id='"+id+"'";
                // c.s.executeUpdate(query);
                ResultSet rs=c.s.executeQuery(query);
                if(!rs.isBeforeFirst())
                    throw new MedicalRecordNotFoundException("Medical Record not found for id:"+id);
                while(rs.next())
                {
                    int mid=rs.getInt("rec_id");
                    int pid=rs.getInt("pid");
                    String dgs=rs.getString("dgs");
                    String treatment= rs.getString("treat");
                    String date= rs.getString("date");
                    System.out.println("Details of Patient:");
                    System.out.println();
                    System.out.println("Medical Record ID: '"+mid+"'");
                    System.out.println("Patient ID:'"+pid+"'");
                    System.out.println("Diagnosis:'"+dgs+"'");
                    System.out.println("Treatment:'"+treatment+"'");
                    System.out.println("date:'"+date+"'");

                }
                // System.out.println(" Patient record with id:'"+id+"' is deleted!!");

            }catch (MedicalRecordNotFoundException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }

        private void addMrecord () {
            System.out.println("Enter the details");
            System.out.println("Enter Record ID:");
            int rid=in.nextInt();
            System.out.println("Enter Patient ID:");
            int pid=in.nextInt();
            System.out.println("Enter Diagnosis:");
            String dgs=in.next();
            System.out.println("Enter Treatment:");
            in.nextLine();
            String t=in.nextLine();
            System.out.println("Enter Date (YYYY-MM-DD):");
            String date=in.next();



            try{
                Conn c=new Conn();
                String query="insert into medical values('"+rid+"','"+pid+"','"+dgs+"','"+t+"','"+date+"')";
                c.s.executeUpdate(query);
            }catch (Exception e)
            {
                System.out.println(e);
            }

            System.out.println( "Details added successfully");

        }
    }
