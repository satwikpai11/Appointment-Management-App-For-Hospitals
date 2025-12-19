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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DoctorsList extends AppCompatActivity {

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
        setContentView(R.layout.activity_doctors_list);

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

        Button drA = (Button)findViewById(R.id.DrA);
        drA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Dr. Girish");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });



        Button drB = (Button)findViewById(R.id.DrB);
        drB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Dr. Raghavendra");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });



        Button drC = (Button)findViewById(R.id.DrC);
        drC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Dr. Vinod");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });



        Button drD = (Button)findViewById(R.id.DrD);
        drD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Dr. Lakshmiprasad");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });



        Button drE = (Button)findViewById(R.id.DrE);
        drE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Dr. Rajesh");

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getApplicationContext(), "Doctor added", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), DateSelect.class));
            }
        });



        Button drF = (Button)findViewById(R.id.DrF);
        drF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=fstore.collection("Extras")
                        .document("Stuff");

                Map<String, Object> user = new HashMap<>();
                user.put("SubSelected", "Dr. Aswin");

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
