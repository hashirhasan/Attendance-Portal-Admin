package com.example.adminauotmaticattendance.Student_section;

public class viewstudentobject {

    private String profilepic;
    private String name;
    private String rollno;
    private String email;
    private  String phone;
    private  String key;

    public viewstudentobject(String profilepic, String name, String rollno,String email, String phone,String key){

       this.rollno=rollno;
       this.email=email;
        this.name=name;
        this.profilepic=profilepic;
        this.phone=phone;
        this.key=key;

    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


}

