package com.example.app_prueba_tkt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_prueba_tkt.SecondActivity;
import com.example.app_prueba_tkt.entities.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        //Revisar sesiones si están activas
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            startActivity(new Intent(this, VideoActivity.class));
            finish();
            return;
        }

        //Si hay login
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        //Offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        CrearCuenta();
        Button btnIngresar = findViewById(R.id.btniniciar);
        btnIngresar.setOnClickListener(v -> {
            Validar();
        });
    }
    public void CrearCuenta()
    {
        TextView tvCrearCuenta = findViewById(R.id.crearCuenta);
        tvCrearCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(this, CrearCuentaActivity.class);
            startActivity(intent);
        });
    }
    public void Validar()
    {
        EditText email = findViewById(R.id.entradaCorreo);
        EditText contrasenia = findViewById(R.id.editTextNumberPassword);

        String inputemail = email.getText().toString().trim();
        String inputpassword = contrasenia.getText().toString().trim();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(inputemail, inputpassword)
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful())
                   {
                       startActivity(new Intent(this, VideoActivity.class));
                   }
                   else
                   {
                       Toast.makeText(this, "Error al iniciar Sesion"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
                });

    }
}