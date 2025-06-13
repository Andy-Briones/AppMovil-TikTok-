package com.example.app_prueba_tkt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_prueba_tkt.adapters.UsuarioAdapter;
import com.example.app_prueba_tkt.entities.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CrearCuentaActivity extends AppCompatActivity {
List<Usuario> usuarios = new ArrayList<>();
public String bio;
UsuarioAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_cuenta);
        ConexionFireBase();
    }
    public void ConexionFireBase()
    {
        Button btnAgregarUser = findViewById(R.id.btnCrearCuenta);
        btnAgregarUser.setOnClickListener(v ->{
            EditText nombreUsuario = findViewById(R.id.editTextName);
            String nombre = nombreUsuario.getText().toString();

            EditText emailUsuario = findViewById(R.id.editTextEmail);
            String email = emailUsuario.getText().toString().trim();

            EditText passwordUsuario = findViewById(R.id.editTextPassword);
            String password = passwordUsuario.getText().toString().trim();

            EditText paisUsuario = findViewById(R.id.editTextCountry);
            String pais = paisUsuario.getText().toString();

            bio = "";
            EditText bioUsuario = findViewById(R.id.editTextBio);
            bio = bioUsuario.getText().toString();
            List<Usuario>amigos = new ArrayList<>();

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
               if (task.isSuccessful())
               {
                   String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                   Usuario nuevoUsuario = new Usuario(id, nombre, email, password, bio, pais, true, amigos);

                   DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("usuarios");
                   dbref.child(id).setValue(nuevoUsuario)
                           .addOnSuccessListener(vo ->{
                               Toast.makeText(this, "Se guardaron los datos correctamente", Toast.LENGTH_SHORT).show();
                               finish();
                           });
                   startActivity(new Intent(this, SecondActivity.class));
                   finish();
               }
               else
               {
                   Toast.makeText(this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
               }
            });
        });
    }
}