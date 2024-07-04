package com.example.vacapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;


public class main_ingreso_produc extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextFecha;
    private EditText editTextLitros;
    private EditText editTextVacaCodigo;

    private AdminSQLiteOpenHelper admin;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_ingreso_produ);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        admin = new AdminSQLiteOpenHelper(this);
        editTextFecha = findViewById(R.id.editTextFecha);
        editTextLitros = findViewById(R.id.editTextLitros);
        editTextVacaCodigo = findViewById(R.id.editTextVacaCodigo);


        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(main_ingreso_produc.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        selectedMonth += 1; // Los meses son 0-based
                        String fechaSeleccionada = selectedYear + "-" + (selectedMonth < 10 ? "0" + selectedMonth : selectedMonth) + "-" + (selectedDay < 10 ? "0" + selectedDay : selectedDay);
                        editTextFecha.setText(fechaSeleccionada);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }


    public void onClick(View view) {
        if (view.getId() == R.id.Guardarp_btn) {
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.Regresarp_btn) {
            finish();
        }
    }
    public void guardarProduccion(View view) {

        String vacaCodigo = editTextVacaCodigo.getText().toString().trim();
        String fecha = editTextFecha.getText().toString().trim();
        String litrosStr = editTextLitros.getText().toString().trim();


        if (vacaCodigo.isEmpty() || fecha.isEmpty() || litrosStr.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }


        int litros = Integer.parseInt(litrosStr);
        try {

            long resultado = admin.AgregarProduccion(vacaCodigo, fecha, litros);

            if (resultado != -1) {
                limpiarCampos();
                Toast.makeText(this, "Producción guardada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al guardar la producción", Toast.LENGTH_SHORT).show();
            }
        }catch (NullPointerException e) {
            Toast.makeText(this, "No se encontro el codigo de la vaca", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        editTextFecha.setText("");
        editTextLitros.setText("");
        editTextVacaCodigo.setText("");
    }
}