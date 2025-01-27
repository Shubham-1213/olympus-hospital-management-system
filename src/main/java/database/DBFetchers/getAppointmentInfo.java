package database.DBFetchers;

import database.DBConnectors.SqlSearchConnection;
import hospital.Appointments.Appointment;
import hospital.Appointments.AppointmentView;
import hospital.Staff.Doctor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import static database.DBFetchers.ResultsetFunctions.size;

public class getAppointmentInfo {
    public static AppointmentView[] appointmentOnDate(Date date) throws SQLException {
        String query="SELECT appointment_id,CONCAT(patient.fname,' ',patient.lname) as 'Patient Name' , " +
                "CONCAT(staff.fname,' ',staff.lname) as 'Doctor Name', " +
                "report_id , appointment_date,appointment_time " +
                "FROM appointments " +
                "JOIN staff on appointments.staff_id=staff.staff_id " +
                "JOIN patient on appointments.patient_id=patient.patient_id " +
                "where appointment_date='"+date+"'";
        ResultSet data = SqlSearchConnection.connect(query);
        int size=size(data);
        AppointmentView[] appointmentList=new AppointmentView[size];
        int counter=0;
        while(data.next()){
            String patient_name=data.getString("Patient Name");
            long appointment_id=data.getLong("appointment_id");
            String doctor_name=data.getString("Doctor name");
            long report_id=data.getLong("report_id");
            Date appointment_date=data.getDate("appointment_date");
            Time appointment_time=data.getTime("appointment_time");
            appointmentList[counter++]=new AppointmentView(appointment_id,patient_name,doctor_name,report_id,appointment_date,appointment_time);
        }
        data.close();
        return appointmentList;
    }
}
