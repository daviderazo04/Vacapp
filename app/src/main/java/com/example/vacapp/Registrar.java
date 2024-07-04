package com.example.vacapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Registrar extends AppCompatActivity {
    EditText editTextNombreU, editTextContrasenia, editTextConfirmarContrasenia;
    AdminSQLiteOpenHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas y administrador de base de datos
        editTextNombreU = findViewById(R.id.NombreU);
        editTextContrasenia = findViewById(R.id.contrasenia);
        editTextConfirmarContrasenia = findViewById(R.id.confirmarContrasenia);
        admin = new AdminSQLiteOpenHelper(this);
    }

    public void onClickIngresar(View view) {
        // Obtener los valores ingresados por el usuario
        String nUsuario = editTextNombreU.getText().toString().trim();
        String contraseña = editTextContrasenia.getText().toString();
        String confirmarContraseniaS = editTextConfirmarContrasenia.getText().toString();

        // Validar que las contraseñas coincidan
        if (!contraseña.equals(confirmarContraseniaS)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que los campos no estén vacíos
        if (nUsuario.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insertar datos del usuario en la base de datos
        long resultado = admin.AgregarUsuario(nUsuario, contraseña);

        if (resultado != -1) {
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Ventana_principal.class));
            finish(); // Finalizar la actividad de registro
        } else {
            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
        }
    }

}
