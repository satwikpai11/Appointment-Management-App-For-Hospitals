package com.example.aapointagain;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EditAppointmentEnterDetails extends AppCompatActivity {

    private FirebaseFirestore fstore, f1store;

    private ProgressBar progressBar, progressBar2;

    public TextView NameofPatient;
    public TextView patientNameRetrieval, hiddenEditin, hiddenAppo;

    private TextView name_head, age_head, sex_head, phoneno_head, othdet_head, hospno_head, date_head, date_value;
    private EditText name_value, age_value, phoneno_value, othdet_value, hospno_value;

    public TextView dateselected, mdisplaydate, daypicked;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public String checkerType, checkerSub1, checkerSub2, checkerDate, checkerDatesss, checkerHospno;
    //checkerSub1 is Doctor
    //checkerSub2 is Diagnosis

    public ImageView hiddenQuest;
    Dialog mydialog;

    public TextView hidDocNametitle, hidDocNameRetr, hidDateTitle, hidDateRetr, hidDatesssRetr, hidHospNoTitle, hidHospNoRetr, hidDash;

    public static final String EXTRA_TEXT1 = "com.example.aapointagain.EXTRA_TEXT1";

    public ImageView hiddenCally, home1, back1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_edit_appointment_enter_details);

        progressBar2=(ProgressBar)findViewById(R.id.progressbar2);
        progressBar=(ProgressBar)findViewById(R.id.progressbar1);

        mydialog = new Dialog(this);
        hiddenQuest=(ImageView)findViewById(R.id.hiddenQuest);

        progressBar2.setVisibility(View.VISIBLE);

        FirebaseApp.initializeApp(this);

        fstore= FirebaseFirestore.getInstance();
        f1store=FirebaseFirestore.getInstance();

        name_head=(TextView)findViewById(R.id.PatientName);


        age_head=(TextView)findViewById(R.id.PatientAge);
        age_value=(EditText)findViewById(R.id.AgeOfPatient);

        sex_head=(TextView)findViewById(R.id.PatientSex);


        phoneno_head=(TextView)findViewById(R.id.PatientPhoneNo);
        phoneno_value=(EditText)findViewById(R.id.PhoneNoOfPatient);

        othdet_head=(TextView)findViewById(R.id.PatientOtherDet);
        othdet_value=(EditText)findViewById(R.id.OtherDetOfPatient);



        NameofPatient = (TextView)findViewById(R.id.NameOfPatient);
        patientNameRetrieval = (TextView)findViewById(R.id.patient_Name_retrieve);
        hiddenEditin = (TextView)findViewById(R.id.hiddenEditing);
        hiddenAppo= (TextView)findViewById(R.id.hiddenAppo);


        hidDocNametitle=(TextView)findViewById(R.id.hiddenDocNametitle);
        hidDash=(TextView)findViewById(R.id.hiddendash);
        hidDocNameRetr=(TextView)findViewById(R.id.hiddenDocNameRetr);
        hidDateTitle=(TextView)findViewById(R.id.hiddenDatetitle1);
        hidDateRetr=(TextView)findViewById(R.id.hiddenDateRetr1);
        hidDatesssRetr=(TextView)findViewById(R.id.hiddenDatessssRetr1);
        hidHospNoTitle=(TextView)findViewById(R.id.hiddenHospnoTitle1);
        hidHospNoRetr=(TextView)findViewById(R.id.hiddenHospnoRetr1);


        home1=(ImageView)findViewById(R.id.home1);
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OptionsMain.class));
            }
        });

        back1=(ImageView)findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OptionsMain.class));
            }
        });


        f1store.collection("Extras").document("Stuff").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    final DocumentSnapshot documentSnapshot = task.getResult();
                    NameofPatient.setText(documentSnapshot.getString("EditItem"));
                    patientNameRetrieval.setText(documentSnapshot.getString("EditItem"));
                    hiddenEditin.setVisibility(View.VISIBLE);
                    patientNameRetrieval.setVisibility(View.VISIBLE);
                    hiddenAppo.setVisibility(View.VISIBLE);

                    final String retr_patientName = patientNameRetrieval.getText().toString().trim();

                    fstore.collection("Appointments").document("Via")
                            .collection("Patient Name").document(retr_patientName)
                            .collection("Patients").document(retr_patientName)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                final DocumentSnapshot documentSnapshot = task.getResult();

                                //Toast.makeText(getApplicationContext(), "name - " + documentSnapshot.getString("Age"),  Toast.LENGTH_SHORT).show();

                                checkerType=documentSnapshot.getString("AppointmentType");
                                checkerSub1=documentSnapshot.getString("Doctor");
                                checkerSub2=documentSnapshot.getString("Diagnosis");
                                checkerDate=documentSnapshot.getString("DatePicked");
                                checkerDatesss=documentSnapshot.getString("datesss");
                                checkerHospno=documentSnapshot.getString("HospitalNumber");

                                if (documentSnapshot.getString("AppointmentType").equals("Doctors")) {

                                    hidDocNametitle.setText("Doctor ");
                                    hidDocNameRetr.setText(checkerSub1);
                                    hidDateRetr.setText(checkerDate);
                                    hidDatesssRetr.setText(checkerDatesss);
                                    hidHospNoRetr.setText(checkerHospno);

                                    hidDocNametitle.setVisibility(View.VISIBLE);
                                    hidDash.setVisibility(View.VISIBLE);
                                    hidDocNameRetr.setVisibility(View.VISIBLE);
                                    hidDateTitle.setVisibility(View.VISIBLE);
                                    hidDateRetr.setVisibility(View.VISIBLE);
                                    hidHospNoTitle.setVisibility(View.VISIBLE);
                                    hidHospNoRetr.setVisibility(View.VISIBLE);
                                    hiddenQuest.setVisibility(View.VISIBLE);


                                }
                                if (documentSnapshot.getString("AppointmentType").equals("Diagnosis")) {

                                    hidDocNametitle.setText("Diagnosis ");
                                    hidDocNameRetr.setText(checkerSub2);
                                    hidDateRetr.setText(checkerDate);
                                    hidDatesssRetr.setText(checkerDatesss);
                                    hidHospNoRetr.setText(checkerHospno);

                                    hidDocNametitle.setVisibility(View.VISIBLE);
                                    hidDash.setVisibility(View.VISIBLE);
                                    hidDocNameRetr.setVisibility(View.VISIBLE);
                                    hidDateTitle.setVisibility(View.VISIBLE);
                                    hidDateRetr.setVisibility(View.VISIBLE);
                                    hidHospNoTitle.setVisibility(View.VISIBLE);
                                    hidHospNoRetr.setVisibility(View.VISIBLE);
                                    hiddenQuest.setVisibility(View.VISIBLE);


                                }


                                age_value.setText(documentSnapshot.getString("Age"));
                                phoneno_value.setText(documentSnapshot.getString("PhoneNumber"));
                                othdet_value.setText(documentSnapshot.getString("OtherDetails"));

                                progressBar2.setVisibility(View.INVISIBLE);


                            }
                            else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                            }
                        }
                    });





                }
                else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                }
            }
        });


        hiddenQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mydialog.setContentView(R.layout.days_popup);

                mydialog.show();

                Button delFromPopup=(Button)mydialog.findViewById(R.id.deleteFromPopup);
                delFromPopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(), EditAppointmentSearch.class));

                        openViewActivity();

                    }
                });

                Button okayButton=(Button)mydialog.findViewById(R.id.okayButton);
                okayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mydialog.dismiss();
                    }
                });


                /*FirebaseAuth.getInstance().signOut();
                finish();
                Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));*/
            }
        });




        //when Submit button is clicked, the ??? class opens
        Button submit=(Button)findViewById(R.id.PatientAppSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressBar.setVisibility(View.VISIBLE);

                final String oncl_name_head=name_head.getText().toString().trim();
                final String oncl_name_value=NameofPatient.getText().toString().trim();

                final String oncl_age_head=age_head.getText().toString().trim();
                final String oncl_age_value=age_value.getText().toString().trim();

                final String oncl_sex_head=sex_head.getText().toString().trim();
                //following 2 lines helps in getting value of radio button
                RadioGroup rg=(RadioGroup)findViewById(R.id.PatientSexRadGroup);
                final String oncl_sex_value=((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                //Toast.makeText(getApplicationContext(), "sex chosen - " + oncl_sex_value, Toast.LENGTH_LONG).show();

                final String oncl_phoneno_head=phoneno_head.getText().toString().trim();
                final String oncl_phoneno_value=phoneno_value.getText().toString().trim();

                final String oncl_othdet_head=othdet_head.getText().toString().trim();
                final String oncl_othdet_value=othdet_value.getText().toString().trim();


                //ensuring all fields are filled


                if (TextUtils.isEmpty(oncl_name_value))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Name.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if (TextUtils.isEmpty(oncl_age_value))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Age.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if (TextUtils.isEmpty(oncl_phoneno_value))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Phone Number.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                if (TextUtils.isEmpty(oncl_othdet_value))
                {
                    Toast.makeText(getApplicationContext(), "Please type 'none' if you wish to enter no other details.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }




                if (checkerType.equals("Doctors"))
                {

                    DocumentReference documentReference33=fstore.collection("Dates")
                            .document(checkerDate).collection(checkerSub1)
                            .document(oncl_name_value);

                    Map<String, Object> user33 = new HashMap<>();
                    user33.put(oncl_name_head, oncl_name_value);
                    user33.put(oncl_age_head, oncl_age_value);
                    user33.put(oncl_sex_head, oncl_sex_value);
                    user33.put("PhoneNumber", oncl_phoneno_value);
                    user33.put("OtherDetails", oncl_othdet_value);
                    user33.put("AppointmentType", checkerType);
                    user33.put("Doctor", checkerSub1);
                    user33.put("Diagnosis", "-");
                    user33.put("DatePicked", checkerDate);
                    user33.put("HospitalNumber", checkerHospno);

                    documentReference33.set(user33).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //via patient name
                    DocumentReference documentReference=fstore.collection("Appointments")
                            .document("Via").collection("Patient Name")
                            .document(oncl_name_value).collection("Patients")
                            .document(oncl_name_value);

                    Map<String, Object> user = new HashMap<>();
                    user.put(oncl_name_head, oncl_name_value);
                    user.put(oncl_age_head, oncl_age_value);
                    user.put(oncl_sex_head, oncl_sex_value);
                    user.put("PhoneNumber", oncl_phoneno_value);
                    user.put("OtherDetails", oncl_othdet_value);
                    user.put("AppointmentType", checkerType);
                    user.put("Doctor", checkerSub1);
                    user.put("Diagnosis", "-");
                    user.put("DatePicked", checkerDate);
                    user.put("HospitalNumber", checkerHospno);
                    user.put("datesss", checkerDatesss);


                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });

                    DocumentReference documentReference10=fstore.collection("Appointments")
                            .document("Via").collection("Patient Name")
                            .document(oncl_name_value);


                    documentReference10.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });


                    //via Date
                    DocumentReference documentReference1=fstore.collection("Appointments")
                            .document("Via").collection("Date")
                            .document(checkerDate).collection("Patients")
                            .document(oncl_name_value);

                    documentReference1.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });


                    //via appointment type
                    DocumentReference documentReference2=fstore.collection("Appointments")
                            .document("Via").collection(checkerType)
                            .document(checkerSub1).collection("Patients")
                            .document(oncl_name_value);

                    documentReference2.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });


                    //via hospital number
                    DocumentReference documentReference3=fstore.collection("Appointments")
                            .document("Via").collection("Hospital Number")
                            .document(checkerHospno).collection("Patients")
                            .document(oncl_name_value);

                    documentReference3.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });

                }



                if (checkerType.equals("Diagnosis"))
                {



                    //via patient name
                    DocumentReference documentReference=fstore.collection("Appointments")
                            .document("Via").collection("Patient Name")
                            .document(oncl_name_value).collection("Patients")
                            .document(oncl_name_value);


                    Map<String, Object> user = new HashMap<>();
                    user.put(oncl_name_head, oncl_name_value);
                    user.put(oncl_age_head, oncl_age_value);
                    user.put(oncl_sex_head, oncl_sex_value);
                    user.put("PhoneNumber", oncl_phoneno_value);
                    user.put("OtherDetails", oncl_othdet_value);
                    user.put("AppointmentType", checkerType);
                    user.put("Doctor", "-");
                    user.put("Diagnosis", checkerSub2);
                    user.put("DatePicked", checkerDate);
                    user.put("HospitalNumber", checkerHospno);
                    user.put("datesss", checkerDatesss);


                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });

                    DocumentReference documentReference111=fstore.collection("Appointments")
                            .document("Via").collection("Patient Name")
                            .document(oncl_name_value);


                    documentReference111.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });



                    //updating the AppByDocDate and AppByDocDate paths
                    if (checkerType.equals("Doctors"))
                    {

                        //updating the AppByDocDate/*docname*/*date*/.. path
                        DocumentReference documentReference2i=fstore.collection("AppByDocDate")
                                .document(checkerSub1).collection(checkerDate)
                                .document(oncl_name_value);

                        documentReference2i.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                progressBar.setVisibility(View.INVISIBLE);
                                //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    if (checkerType.equals("Diagnosis"))
                    {

                        //updating the AppByDocDate/*docname*/*date*/.. path
                        DocumentReference documentReference2i=fstore.collection("AppByDiagDate")
                                .document(checkerSub2).collection(checkerDate)
                                .document(oncl_name_value);

                        documentReference2i.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                progressBar.setVisibility(View.INVISIBLE);
                                //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }







                    //via Date
                    DocumentReference documentReference1=fstore.collection("Appointments")
                            .document("Via").collection("Date")
                            .document(checkerDate).collection("Patients")
                            .document(oncl_name_value);

                    documentReference1.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });


                    //via appointment type
                    DocumentReference documentReference2=fstore.collection("Appointments")
                            .document("Via").collection(checkerType)
                            .document(checkerSub2).collection("Patients")
                            .document(oncl_name_value);

                    documentReference2.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });


                    //via hospital number
                    DocumentReference documentReference3=fstore.collection("Appointments")
                            .document("Via").collection("Hospital Number")
                            .document(checkerHospno).collection("Patients")
                            .document(oncl_name_value);

                    documentReference3.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                        }
                    });

                }


                Toast.makeText(getApplicationContext(), oncl_name_value+"'s appointment has been updated successfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), OptionsMain.class));
                finish();





            }
        });



    }
    public void openViewActivity() {

        TextView editText1 = (TextView)findViewById(R.id.NameOfPatient);
        String text1 = editText1.getText().toString();

        Intent intent = new Intent(getApplicationContext(), EditAppointmentSearch.class);
        intent.putExtra(EXTRA_TEXT1, text1);

        startActivity(intent);
        finish();
    }
}
