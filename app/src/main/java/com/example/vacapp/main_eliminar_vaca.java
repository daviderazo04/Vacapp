package com.example.vacapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import Clases.Vaca;

public class main_eliminar_vaca extends AppCompatActivity {
    private EditText et1, et2;
    private Spinner spinner1, spinner2, spinner3;
    private AdminSQLiteOpenHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this); // Comentado para prueba
        setContentView(R.layout.ventana_eliminar_vaca);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et1 = findViewById(R.id.editText3);
        spinner1 = findViewById(R.id.raza_txt);
        et2 = findViewById(R.id.editText4);
        spinner2 = findViewById(R.id.edad_txt);
        spinner3 = findViewById(R.id.establo_txt);
        admin = new AdminSQLiteOpenHelper(this);
        try {

            List<String> nombresEstablos = admin.obtenerCodigosEstablo();
            if(nombresEstablos.isEmpty()){
                Toast.makeText(this, "No se encontraron establos. Ingrese uno para continuar", Toast.LENGTH_SHORT).show();
                finish();
            }
            // Crear el adaptador para el spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresEstablos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Establecer el adaptador al spinner
            spinner3.setAdapter(adapter);
        } catch (NullPointerException e) {
            Toast.makeText(this, "No se han encontrado establos", Toast.LENGTH_SHORT).show();
        }
    }

    public void EliminarVaca(View view) {
        String codigo = et1.getText().toString().trim();
        if (codigo.isEmpty()) {
            Toast.makeText(this, "Ingrese el código de la vaca", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int verificador = admin.EliminarVaca(codigo);
            if (verificador != -1) {
                Toast.makeText(this, "Vaca eliminada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se pudo eliminar la Vaca", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarVacaPorCodigo(View view) {
        String codigo = et1.getText().toString().trim();

        if (codigo.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un código de vaca.", Toast.LENGTH_SHORT).show();
            return;
        }

        Vaca vaca = admin.buscarVacaPorCodigo(codigo);

        if (vaca != null) {
            // Actualizar campos con los datos de la vaca encontrada
            setSpinnerToValue(spinner1, vaca.getRaza());
            setSpinnerToValue(spinner2, String.valueOf(vaca.getEdad()));
            setSpinnerToValue(spinner3, String.valueOf(vaca.getEstablo()));
            et2.setText(String.valueOf(vaca.getPeso()));
            Toast.makeText(this, "Vaca encontrada: " + vaca.getCodigo(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se encontró ninguna vaca con el código especificado.", Toast.LENGTH_SHORT).show();
        }
    }
    private void setSpinnerToValue(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        int position = adapter.getPosition(value);
        if (position >= 0) {
            spinner.setSelection(position);
        }
    }
    private void limpiarCampos() {
        et1.setText("");
        et2.setText("");
    }
    public void onClick(View view) {
        finish();
    }
}
