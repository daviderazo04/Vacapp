package com.example.vacapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Clases.Usuario;

public class Inicio_sesion extends AppCompatActivity {
    Button ingresar;
    EditText editTextNombreU, editTextContrasenia;
    AdminSQLiteOpenHelper admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ingresar = findViewById(R.id.ingresar);
        editTextNombreU = findViewById(R.id.NombreU);
        editTextContrasenia = findViewById(R.id.contrasenia);
        admin=new AdminSQLiteOpenHelper(this);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIngresar(v); // Llamar al método de validación al hacer clic en el botón
            }
        });

    }
    public void onClickIngresar(View view) {
        String nombreUsuario = editTextNombreU.getText().toString().trim();
        String contrasenia = editTextContrasenia.getText().toString().trim();

        if (nombreUsuario.isEmpty() || contrasenia.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = admin.BuscarUsuarioPorNombre(nombreUsuario);

        if (usuario != null) {
            if (usuario.getContraseña().equals(contrasenia)) {
                // Contraseña correcta, iniciar sesión
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                // Abrir la siguiente actividad
                Intent intent = new Intent(Inicio_sesion.this, Ventana_principal.class);
                startActivity(intent);
            } else {
                // Contraseña incorrecta
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        } else {
            // El usuario no existe
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}