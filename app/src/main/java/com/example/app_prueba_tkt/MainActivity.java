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

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //VERIFICAR LOGIN
//        preferences = getSharedPreferences("com.example.app_prueba_tkt.preferences", MODE_PRIVATE);
//        boolean autenticar = preferences.getBoolean("Esta_auntenticado", false);
//        if (autenticar)
//        {
//            Open();
//            return;
//        }

        Button btnGoogle = findViewById(R.id.btnInicionGoogle);

        btnGoogle.setOnClickListener(v ->
        {
            Log.i("Main", "Ah");
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
//            if (true)
//            {
//                SharedPreferences.Editor editor= preferences.edit();
//                editor.putBoolean("Esta_auntenticado",true);
//                editor.putString("","");
//                editor.apply();
//                Open();
//            }
        });

    }
//    public void Open()
//    {
//        Intent intent = new Intent(this, SecondActivity.class);
//        startActivity(intent);
//        finish();
//    }
}