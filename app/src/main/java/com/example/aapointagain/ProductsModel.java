package com.example.aapointagain;

public class ProductsModel {

     private String Name;
     private String Age;
     private String Sex;
     private String PhoneNumber;
     private String Doctor;
     private String Diagnosis;
     private String DatePicked;
     private String HospitalNumber;
     private String OtherDetails;

     private ProductsModel() {}

     private ProductsModel(String Name, String Age, String Sex, String PhoneNumber, String Doctor, String Diagnosis, String DatePicked, String HospitalNumber,String OtherDetails) {
            this.Name=Name;
            this.Age=Age;
            this.Sex=Sex;
            this.PhoneNumber=PhoneNumber;
            this.Doctor=Doctor;
            this.Diagnosis=Diagnosis;
            this.DatePicked=DatePicked;
            this.HospitalNumber=HospitalNumber;
            this.OtherDetails=OtherDetails;

     }

     public void setName(String name) {
          Name = name;
     }

     public void setAge(String age) {
          Age = age;
     }

     public void setSex(String sex) {
          Sex = sex;
     }

     public void setPhoneNumber(String phoneNumber) {
          PhoneNumber = phoneNumber;
     }

     public void setDoctor(String doctor) {
          Doctor = doctor;
     }

     public void setDiagnosis(String diagnosis) {
          Diagnosis = diagnosis;
     }

     public void setDatePicked(String datePicked) {
          DatePicked = datePicked;
     }

     public void setOtherDetails(String otherDetails) {
          OtherDetails = otherDetails;
     }

     public void setHospitalNumber(String hospitalNumber) {
          HospitalNumber = hospitalNumber;
     }



     public String getName() {
          return Name;
     }

     public String getAge() {
          return Age;
     }

     public String getSex() {
          return Sex;
     }

     public String getPhoneNumber() {
          return PhoneNumber;
     }

     public String getDoctor() {
          return Doctor;
     }

     public String getDiagnosis() {
          return Diagnosis;
     }

     public String getDatePicked() {
          return DatePicked;
     }

     public String getOtherDetails() {
          return OtherDetails;
     }

     public String getHospitalNumber() {
          return HospitalNumber;
     }




}
