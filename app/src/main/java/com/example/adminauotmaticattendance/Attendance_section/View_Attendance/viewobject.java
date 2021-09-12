package com.example.adminauotmaticattendance.Attendance_section.View_Attendance;

public class viewobject {


    private String name;
    private String Attendance;
    private String ac;
    private String attended;
    private String total_classes;
    public  viewobject(String name,String ac,String attended,String total_classes,String Attendance){

        this.ac=ac;
        this.name=name;
        this.attended=attended;
        this.total_classes=total_classes;
        this.Attendance=Attendance;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public String getAttended() {
        return attended;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getTotal_classes() {
        return total_classes;
    }

    public void setTotal_classes(String total_classes) {
        this.total_classes = total_classes;
    }

    public String getAttendance() {
        return Attendance;
    }

    public void setAttendance(String attendance) {
        this.Attendance = attendance;
    }
}
