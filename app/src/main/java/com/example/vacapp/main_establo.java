package com.example.vacapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class main_establo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_establo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClick(View view) {
        Intent intent = null;
        if (view.getId() == R.id.IEstablo_btn) {
            intent = new Intent(this, main_ingreso_establo.class);
        } else if (view.getId() == R.id.UEstablo_btn) {
            intent = new Intent(this, main_actualizar_establo.class);
        } else if (view.getId() == R.id.DEstablo_btn) {
            intent = new Intent(this, main_eliminar_establo.class);
        } else if (view.getId() == R.id.BEstablo_btn) {
            intent = new Intent(this, main_buscar_establo.class);
        } else if (view.getId() == R.id.Regresar_btn) {
            finish();
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}