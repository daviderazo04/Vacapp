package com.example.vacapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Ventana_principal extends AppCompatActivity {
Button cerrar_sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cerrar_sesion = findViewById(R.id.cerrar_sesion);
        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpiar las preferencias de usuario o sesión
                getSharedPreferences("PREFS_NAME", 0).edit().clear().apply();

                // Redirigir al usuario a la pantalla de inicio de sesión
                Intent intent = new Intent(Ventana_principal.this, MainActivity.class);
                startActivity(intent);
                finish();  // Cerrar la actividad actual
            }
        });
    }
    public void onClick(View view) {
        Intent intent = null;
        if (view.getId() == R.id.button_ganado) {
            intent = new Intent(this, main_vaca.class);
        } else if (view.getId() == R.id.Registro_produccion) {
            intent = new Intent(this, main_produc.class);
        } else if (view.getId() == R.id.Registro_establo) {
            intent = new Intent(this, main_establo.class);
        } else if (view.getId() == R.id.Grafica) {
            intent = new Intent(this, main_grafica.class);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

}