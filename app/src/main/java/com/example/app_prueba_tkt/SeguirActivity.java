package com.example.app_prueba_tkt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeguirActivity extends AppCompatActivity {

    TextView nombre;
    Button btnSeguir;
    String userActual;
    String userDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seguir);

        nombre = findViewById(R.id.tvNombreUsuario);
        btnSeguir = findViewById(R.id.btnseguimiento);

        userActual = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userDestino = getIntent().getStringExtra("idUsuario");

        if (userDestino == null)
        {
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("usuarios").child(userDestino);
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("nombreUsuario").getValue(String.class);
                if(name != null)
                {
                    nombre.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference seguir = FirebaseDatabase.getInstance().getReference("Follows");
        btnSeguir.setOnClickListener(v ->
        {
            seguir.child(userActual).child(userDestino).setValue(true)
                    .addOnSuccessListener(vv ->
                    {
                       Toast.makeText(this,"Siguiendo al usuario",Toast.LENGTH_SHORT).show();
                        btnSeguir.setText("Dejar de seguir");
                    }).addOnFailureListener(f ->
                    {
                        Toast.makeText(this,"Error al seguir", Toast.LENGTH_SHORT).show();
                        btnSeguir.setText("Seguir");
                    });
        });
    }
}