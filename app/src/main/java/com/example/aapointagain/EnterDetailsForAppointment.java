package com.example.aapointagain;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EnterDetailsForAppointment extends AppCompatActivity {

    private ProgressBar progressBar, progressBar2;
    private FirebaseFirestore fstore, f1store;
    private TextView name_head, age_head, sex_head, phoneno_head, othdet_head, hospno_head;
    private EditText name_value, age_value, phoneno_value, othdet_value, hospno_value;

    public String checkerSub, checkerType, checkerDate, checkerdatesss;
    public int flag=0;

    public TextView text1;
    public TextView DocName, hiddenColon;
    public TextView DiagnosisName, hiddenA, hiddenCase;

    public TextView NamePatientTitle, NamePatientRetr;
    public TextView AgePatientTitle, AgePatientRetr;
    public TextView SexPatientTitle, SexPatientRetr;
    public TextView PhonePatientTitle, PhonePatientRetr;
    public TextView OthDeetsPatientTitle, OthDeetsPatientRetr;
    public TextView HospNoPatientTitle, HospNoPatientRetr;
    public TextView DatePatientTitle, DatePatientRetr;

    public ImageView hiddenCally, home1, back1;

    public TextView AreYouSure;


    public ScrollView sc1;
    public RelativeLayout R1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_enter_details_for_appointment);

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
                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });

        progressBar=(ProgressBar)findViewById(R.id.progressbar1);
        progressBar2=(ProgressBar)findViewById(R.id.progressbar2);

        progressBar2.setVisibility(View.VISIBLE);

        FirebaseApp.initializeApp(this);
        fstore=FirebaseFirestore.getInstance();
        f1store=FirebaseFirestore.getInstance();

        sc1=(ScrollView)findViewById(R.id.sc1);
        R1=(RelativeLayout)findViewById(R.id.relativelayout);

        name_head=(TextView)findViewById(R.id.PatientName);
        name_value=(EditText)findViewById(R.id.NameOfPatient);

        age_head=(TextView)findViewById(R.id.PatientAge);
        age_value=(EditText)findViewById(R.id.AgeOfPatient);

        sex_head=(TextView)findViewById(R.id.PatientSex);


        phoneno_head=(TextView)findViewById(R.id.PatientPhoneNo);
        phoneno_value=(EditText)findViewById(R.id.PhoneNoOfPatient);

        othdet_head=(TextView)findViewById(R.id.PatientOtherDet);
        othdet_value=(EditText)findViewById(R.id.OtherDetOfPatient);

        hospno_head=(TextView)findViewById(R.id.HospitalNmber);
        hospno_value=(EditText)findViewById(R.id.HospitalNo);


        text1 = (TextView)findViewById(R.id.text1);
        DocName=(TextView)findViewById(R.id.DocNameRetr);
        hiddenColon=(TextView)findViewById(R.id.hiddenColon);
        DiagnosisName=(TextView)findViewById(R.id.DiagnosisNameRetr);
        hiddenA=(TextView)findViewById(R.id.hiddenA);
        hiddenCase=(TextView)findViewById(R.id.hiddenCase);


        NamePatientTitle=(TextView)findViewById(R.id.NamePatientTitle);
        NamePatientRetr=(TextView)findViewById(R.id.NamePatientRetr);
        AgePatientTitle=(TextView)findViewById(R.id.AgePatientTitle);
        AgePatientRetr=(TextView)findViewById(R.id.AgePatientRetr);
        SexPatientTitle=(TextView)findViewById(R.id.SexPatientTitle);
        SexPatientRetr=(TextView)findViewById(R.id.SexPatientRetr);
        PhonePatientTitle=(TextView)findViewById(R.id.PhonePatientTitle);
        PhonePatientRetr=(TextView)findViewById(R.id.PhonePatientRetr);
        OthDeetsPatientTitle=(TextView)findViewById(R.id.OthDeetsPatientTitle);
        OthDeetsPatientRetr=(TextView)findViewById(R.id.OthDeetsPatientRetr);
        HospNoPatientTitle=(TextView)findViewById(R.id.HospNoPatientTitle);
        HospNoPatientRetr=(TextView)findViewById(R.id.HospNoPatientRetr);
        DatePatientTitle=(TextView)findViewById(R.id.DatePatientTitle);
        DatePatientRetr=(TextView)findViewById(R.id.DatePatientRetr);

        AreYouSure = (TextView)findViewById(R.id.AreYouSure);




        f1store.collection("Extras").document("Stuff").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();

                    checkerType=documentSnapshot.getString("AppointmentType");
                    checkerSub=documentSnapshot.getString("SubSelected");
                    checkerDate=documentSnapshot.getString("DatePicked");
                    checkerdatesss=documentSnapshot.getString("datesss0");

                    if (checkerType.equals("Doctors")){
                        DocName.setText(checkerSub);
                        text1.setVisibility(View.VISIBLE);
                        DocName.setVisibility(View.VISIBLE);
                        hiddenColon.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.INVISIBLE);
                    }
                    if (checkerType.equals("Diagnosis")) {
                        DiagnosisName.setText(checkerSub);
                        text1.setVisibility(View.VISIBLE);
                        hiddenA.setVisibility(View.VISIBLE);
                        DiagnosisName.setVisibility(View.VISIBLE);
                        hiddenCase.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.INVISIBLE);
                    }
                    //Toast.makeText(getApplicationContext(), "day2- "+day2,  Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_SHORT).show();
                }

            }
        });

        final Button noDont = (Button)findViewById(R.id.noDONTMAKE);
        final Button yesMake = (Button)findViewById(R.id.yesMAKE);
        final Button retain = (Button)findViewById(R.id.retainData);
        //when Submit button is clicked, the ??? class opens
        final Button submit=(Button)findViewById(R.id.PatientAppSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                final String oncl_name_head=name_head.getText().toString().trim();
                final String oncl_name_value=name_value.getText().toString().trim();

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

                final String oncl_hospno_head=hospno_head.getText().toString().trim();
                final String oncl_hospno_value=hospno_value.getText().toString().trim();


                //ensuring all fields are filled
                if (TextUtils.isEmpty(oncl_name_value))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Name.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    name_value.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(oncl_age_value))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Age.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    age_value.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(oncl_phoneno_value))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Phone Number.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    phoneno_value.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(oncl_othdet_value))
                {
                    Toast.makeText(getApplicationContext(), "Please type 'none' if you wish to enter no other details.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    othdet_value.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(oncl_hospno_value))
                {
                    Toast.makeText(getApplicationContext(), "Please type '-' if you do not wish to enter hospital number.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    hospno_value.requestFocus();
                    return;
                }













                progressBar.setVisibility(View.VISIBLE);



                final List<String> list = new ArrayList<>();
                fstore.collection("Appointments").document("Via")
                        .collection("Patient Name").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getApplicationContext(), "doc-" + document.getId(), Toast.LENGTH_SHORT).show();
                                list.add(document.getId());
                            }
                            if (list.contains(oncl_name_value)) {
                                //Toast.makeText(getApplicationContext(), "list - " + list, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "The name already exists in our database\nPlease add initials to the name.", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                name_value.requestFocus();
                                return;

                            }
                            else {
                                //Toast.makeText(getApplicationContext(), "else -" + list, Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.INVISIBLE);

                                R1.setVisibility(View.INVISIBLE);
                                submit.setVisibility(View.INVISIBLE);


                                AreYouSure.setVisibility(View.VISIBLE);
                                noDont.setVisibility(View.VISIBLE);
                                yesMake.setVisibility(View.VISIBLE);

                                NamePatientRetr.setText(oncl_name_value);
                                AgePatientRetr.setText(oncl_age_value);
                                SexPatientRetr.setText(oncl_sex_value);
                                PhonePatientRetr.setText(oncl_phoneno_value);
                                OthDeetsPatientRetr.setText(oncl_othdet_value);
                                HospNoPatientRetr.setText(oncl_hospno_value);
                                DatePatientRetr.setText(checkerDate);


                                NamePatientTitle.setVisibility(View.VISIBLE);
                                NamePatientRetr.setVisibility(View.VISIBLE);
                                AgePatientTitle.setVisibility(View.VISIBLE);
                                AgePatientRetr.setVisibility(View.VISIBLE);
                                SexPatientTitle.setVisibility(View.VISIBLE);
                                SexPatientRetr.setVisibility(View.VISIBLE);
                                PhonePatientTitle.setVisibility(View.VISIBLE);
                                PhonePatientRetr.setVisibility(View.VISIBLE);
                                OthDeetsPatientTitle.setVisibility(View.VISIBLE);
                                OthDeetsPatientRetr.setVisibility(View.VISIBLE);
                                HospNoPatientTitle.setVisibility(View.VISIBLE);
                                HospNoPatientRetr.setVisibility(View.VISIBLE);
                                DatePatientTitle.setVisibility(View.VISIBLE);
                                DatePatientRetr.setVisibility(View.VISIBLE);





                                noDont.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        //Toast.makeText(getApplicationContext(), "NO was cliked!", Toast.LENGTH_SHORT).show();

                                        NamePatientTitle.setVisibility(View.INVISIBLE);
                                        NamePatientRetr.setVisibility(View. INVISIBLE);
                                        AgePatientTitle.setVisibility(View.INVISIBLE);
                                        AgePatientRetr.setVisibility(View.INVISIBLE);
                                        SexPatientTitle.setVisibility(View.INVISIBLE);
                                        SexPatientRetr.setVisibility(View.INVISIBLE);
                                        PhonePatientTitle.setVisibility(View.INVISIBLE);
                                        PhonePatientRetr.setVisibility(View.INVISIBLE);
                                        OthDeetsPatientTitle.setVisibility(View.INVISIBLE);
                                        OthDeetsPatientRetr.setVisibility(View.INVISIBLE);
                                        HospNoPatientTitle.setVisibility(View.INVISIBLE);
                                        HospNoPatientRetr.setVisibility(View.INVISIBLE);
                                        DatePatientTitle.setVisibility(View.INVISIBLE);
                                        DatePatientRetr.setVisibility(View.INVISIBLE);

                                        AreYouSure.setVisibility(View.INVISIBLE);
                                        noDont.setVisibility(View.INVISIBLE);
                                        yesMake.setVisibility(View.INVISIBLE);

                                        submit.setVisibility(View.VISIBLE);
                                        R1.setVisibility(View.VISIBLE);

                                    }
                                });


                                //making appointment
                                yesMake.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        progressBar.setVisibility(View.VISIBLE);

                                        if (checkerType.equals("Doctors"))
                                        {

                                            DocumentReference documentReference33=fstore.collection("Dates")
                                                    .document(checkerDate).collection(checkerSub)
                                                    .document(oncl_name_value);

                                            Map<String, Object> user33 = new HashMap<>();
                                            user33.put(oncl_name_head, oncl_name_value);
                                            user33.put(oncl_age_head, oncl_age_value);
                                            user33.put(oncl_sex_head, oncl_sex_value);
                                            user33.put("PhoneNumber", oncl_phoneno_value);
                                            user33.put("OtherDetails", oncl_othdet_value);
                                            user33.put("AppointmentType", checkerType);
                                            user33.put("Doctor", checkerSub);
                                            user33.put("Diagnosis", "-");
                                            user33.put("DatePicked", checkerDate);
                                            user33.put("HospitalNumber", oncl_hospno_value);

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
                                            user.put("Doctor", checkerSub);
                                            user.put("Diagnosis", "-");
                                            user.put("DatePicked", checkerDate);
                                            user.put("HospitalNumber", oncl_hospno_value);
                                            user.put("datesss", checkerdatesss);


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
                                                        .document(checkerSub).collection("Patients")
                                                        .document(oncl_name_value);

                                                documentReference2.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                //via Doctor and Date,
                                                DocumentReference documentReference2i=fstore.collection("AppByDocDate")
                                                        .document(checkerSub).collection(checkerDate)
                                                        .document(oncl_name_value);

                                                documentReference2i.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                                                    }
                                                });





                                            //via hospital number
                                            DocumentReference documentReference3=fstore.collection("Appointments")
                                                    .document("Via").collection("Hospital Number")
                                                    .document(oncl_hospno_value).collection("Patients")
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
                                            user.put("Diagnosis", checkerSub);
                                            user.put("DatePicked", checkerDate);
                                            user.put("HospitalNumber", oncl_hospno_value);
                                            user.put("datesss", checkerdatesss);

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
                                                    .document(checkerSub).collection("Patients")
                                                    .document(oncl_name_value);

                                            documentReference2.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            //via diagnosis and date
                                            DocumentReference documentReference2i=fstore.collection("AppByDiagDate")
                                                    .document(checkerSub).collection(checkerDate)
                                                    .document(oncl_name_value);

                                            documentReference2i.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                            //via hospital number
                                            DocumentReference documentReference3=fstore.collection("Appointments")
                                                    .document("Via").collection("Hospital Number")
                                                    .document(oncl_hospno_value).collection("Patients")
                                                    .document(oncl_name_value);

                                            documentReference3.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    //Toast.makeText(getApplicationContext(), "Success in firestore", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }


                                        Toast.makeText(getApplicationContext(), oncl_name_value+"'s appointment has been made successfully!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), OptionsMain.class));

                                        finish();
                                    }
                                });



                            }

                        }


//
                        else {

                        }
                    }
                });










            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DateSelect.class));
    }

}







