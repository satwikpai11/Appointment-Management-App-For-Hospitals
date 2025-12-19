INDUSTRY PROJECT - KMC Hospital, Manipal - Appointment Management App

Overview

The Appointment Management App is an Android mobile application developed to handle doctor appointment scheduling and management for KMC Manipal Hospital.
The application allows users to create, view, search, and edit appointments, and provides structured workflows for selecting doctors, dates, and patient details.

This project demonstrates Android application development, Firebase integration, and RecyclerView-based UI navigation for hospital appointment handling.

⸻

Platform and Technology Stack
	•	Platform: Android
	•	Language: Java
	•	Build System: Gradle
	•	UI Components: Activities, RecyclerView
	•	Backend / Database: Firebase (Google Services configured)
	•	IDE: Android Studio

⸻

Core Functionalities (Verified from Code)

1. Appointment Creation
	•	Enter patient details for an appointment
	•	Select doctor from a predefined doctors list
	•	Choose appointment date
	•	Store appointment details in the backend

Relevant classes:
	•	EnterDetailsForAppointment.java
	•	MakeAppointmentOptions.java
	•	DoctorsList.java
	•	DateSelect.java

⸻

2. View Appointments
	•	View existing appointments
	•	Search appointments using defined criteria
	•	Display appointments using RecyclerView

Relevant classes:
	•	ViewAppointmentOptions.java
	•	ViewAppointmentSearch.java
	•	recylceViewAt1.java

⸻

3. Edit Appointments
	•	Search for an existing appointment
	•	Modify appointment details
	•	Update stored appointment information

Relevant classes:
	•	EditAppointmentSearch.java
	•	EditAppointmentEnterDetails.java

⸻

4. Supporting Modules
	•	Diagnosis listing support
	•	Data models for appointments and related entities

Relevant classes:
	•	DiagnosisList.java
	•	ProductsModel.java

⸻

Application Flow
	1.	User launches the app
	2.	Main menu provides appointment-related options
	3.	User can:
	•	Make a new appointment
	•	View existing appointments
	•	Edit an appointment
	4.	Doctor selection and date selection handled via dedicated activities
	5.	Appointment data is stored and retrieved using Firebase services


⸻

How to Run the Application

Prerequisites
	•	Android Studio installed
	•	Android SDK configured
	•	Internet connection (Firebase services)

Steps
	1.	Extract the project zip
	2.	Open the project in Android Studio
	3.	Allow Gradle to sync dependencies
	4.	Connect an Android device or start an emulator
	5.	Run the application

⸻

Institution

Kasturba Medical College (KMC), Manipal
