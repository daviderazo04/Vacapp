package com.example.vacapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import Clases.Vaca;

public class main_actualizar_vaca extends AppCompatActivity {
    private EditText et1, et2;
    private Spinner spinner1, spinner2, spinner3;
    private AdminSQLiteOpenHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_actualizar_vaca);
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
    public void ActualizarVaca(View view)
    {
        String codigo = et1.getText().toString().trim();
        String pesoStr = et2.getText().toString().trim();

        // Validar campos vacíos
        if (codigo.isEmpty() || pesoStr.isEmpty()) {
            Toast.makeText(this, "El código de la vaca y el peso son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir el peso a tipo double
        double peso = 0;
        try {
            peso = Double.parseDouble(pesoStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El peso debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener valores desde los Spinners
        String raza = spinner1.getSelectedItem().toString();  // Suponiendo que spinner1 es para la raza
        String edadStr = spinner2.getSelectedItem().toString();  // Suponiendo que spinner2 es para la edad
        String estCodigoStr = spinner3.getSelectedItem().toString();

        // Convertir la edad y el código de establo a tipo int
        int edad = 0;
        int estCodigo = 0;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "La edad y el código del establo deben ser números válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llamar al método para agregar la vaca
        long resultado = admin.ModificarVaca(codigo, raza, peso, edad, estCodigoStr);

        // Verificar el resultado de la operación
        if (resultado != -1) {
            limpiarCampos();
            Toast.makeText(this, "Se actualizaron los datos de la vaca", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar los datos de la vaca", Toast.LENGTH_SHORT).show();
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