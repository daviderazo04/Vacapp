package com.example.vacapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class main_produc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_produ);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClick(View view) {
        Intent intent = null;
        if (view.getId() == R.id.IProduc_btn) {
            intent = new Intent(this, main_ingreso_produc.class);
        } else if (view.getId() == R.id.UProduc_btn) {
            intent = new Intent(this, main_actualizar_produc.class);
        } else if (view.getId() == R.id.Regresar_btn) {
            finish();
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}