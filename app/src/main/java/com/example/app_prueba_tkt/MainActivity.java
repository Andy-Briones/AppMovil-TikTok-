package com.example.app_prueba_tkt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    Button btnIngresarC;
    Button btnIngresarFb;
    Button btnIngresaG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null)
//        {
//            Intent intent = new Intent(this, SecondActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        }

        btnIngresaG = findViewById(R.id.btnInicionGoogle);
        btnIngresarC = findViewById(R.id.btnInicioUserName);
        btnIngresarFb = findViewById(R.id.btnInicioFacebook);
        Botones();


    }
    public void Botones()
    {
        btnIngresaG.setOnClickListener(v ->
        {
            Log.i("Main", "Ah");
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });
        btnIngresarC.setOnClickListener(v -> {
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        });
        btnIngresarFb.setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });
    }
}