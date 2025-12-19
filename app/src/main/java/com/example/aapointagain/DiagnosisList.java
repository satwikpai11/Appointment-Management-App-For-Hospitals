package com.example.aapointagain;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DiagnosisList extends AppCompatActivity {

    private FirebaseFirestore fstore;

    public ImageView hiddenCally, home1, back1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_diagnosis_list);

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
                startActivity(new Intent(getApplicationContext(), MakeAppointmentOptions.class));
            }
        });


        FirebaseApp.initializeApp(this);

        fstore=FirebaseFirestore.getInstance();


        Button diagnosisA = (Button)findViewById(R.id.cerebrovascular);
        diagnosisA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Cerebrovascular");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });


        Button diagnosisB = (Button)findViewById(R.id.traumaticbrainInjury);
        diagnosisB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Traumatic Brain Injury");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });


        Button diagnosisC = (Button)findViewById(R.id.neuroOncology);
        diagnosisC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Neuro-Oncology");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });


        Button diagnosisD = (Button)findViewById(R.id.spine);
        diagnosisD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Spine");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });


        Button diagnosisE = (Button)findViewById(R.id.paediatrics);
        diagnosisE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Paediatrics");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });


        Button diagnosisF = (Button)findViewById(R.id.functionalNeurosurgery);
        diagnosisF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Functional Neurosurgery");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MakeAppointmentOptions.class));
    }

}
