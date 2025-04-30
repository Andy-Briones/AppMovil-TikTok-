package com.example.app_prueba_tkt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.example.app_prueba_tkt.SecondActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        Button btnIngresar = findViewById(R.id.btniniciar);
        btnIngresar.setOnClickListener(v -> {
            Log.i("Main", "Ah");

            Intent intent = new Intent(this, VideoActivity.class);
            startActivity(intent);
        });
    }
}