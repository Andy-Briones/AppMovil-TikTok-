package com.example.app_prueba_tkt;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CrearCuentaActivity extends AppCompatActivity {
List<Usuario> usuarios = new ArrayList<>();
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
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("usuarios");
        String id = dbref.push().getKey();
        Button btnAgregarUser = findViewById(R.id.btnCrearCuenta);
        btnAgregarUser.setOnClickListener(v -> {

            EditText nombreUsuario = findViewById(R.id.editTextName);
            String nombre = nombreUsuario.getText().toString();

            EditText emailUsuario = findViewById(R.id.editTextEmail);
            String email = emailUsuario.getText().toString();

            EditText passwordUsuario = findViewById(R.id.editTextPassword);
            String password = passwordUsuario.getText().toString();

            EditText paisUsuario = findViewById(R.id.editTextCountry);
            String pais = paisUsuario.getText().toString();

            String bio = "";
            EditText bioUsuario = findViewById(R.id.editTextBio);
            bio = bioUsuario.getText().toString();

            List<Usuario>amigos = new ArrayList<>();

            Usuario nuevoUsuario = new Usuario(id, nombre, email, password, bio, pais, true, amigos);
            dbref.child(id).setValue(nuevoUsuario)
                    .addOnSuccessListener(vo ->{
                        Toast.makeText(this, "Cuenta creada con exito", Toast.LENGTH_SHORT).show();
                        finish();
            });
        });
    }
}