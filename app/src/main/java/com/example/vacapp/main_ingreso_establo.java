package com.example.vacapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class main_ingreso_establo extends AppCompatActivity implements View.OnClickListener{
    private EditText et1, et2, et3;
    private AdminSQLiteOpenHelper admin;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_ingreso_establo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et1 = findViewById(R.id.editTextNumber);
        et2 = findViewById(R.id.editText9);
        et3 = findViewById(R.id.editText10);
        admin = new AdminSQLiteOpenHelper(this);
    }
    public void ingresarEstablo(View view){
        // Obtener valores desde los EditText
        String nombre = et1.getText().toString().trim();
        String ancho = et2.getText().toString().trim();
        String largo = et3.getText().toString().trim();
        // Validar campos vacíos
        if (nombre.isEmpty() || ancho.isEmpty() || largo.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        // Convertir el ancho a tipo double
        double anchoD = 0;
        try {
            anchoD = Double.parseDouble(ancho);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El ancho debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }
        double largoD = 0;
        try {
            largoD = Double.parseDouble(largo);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El largo debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }
        // Llamar al método para agregar el establo
        long resultado = admin.AgregarEstablo(nombre, anchoD, largoD);

        // Verificar el resultado de la operación
        if (resultado != -1) {
            limpiarCampos();
            Toast.makeText(this, "Se agrego el establo exitósamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al agregar el establo", Toast.LENGTH_SHORT).show();
        }

    }
    private void limpiarCampos() {
        et1.setText("");
        et2.setText("");
        et3.setText("");
    }
    public void onClick(View view) {
        finish();
    }
}