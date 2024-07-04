package com.example.vacapp;

import android.app.DatePickerDialog;
import android.database.Cursor;
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

public class main_actualizar_produc extends AppCompatActivity {

    private EditText editTextVacaCodigo;
    private EditText editTextFecha;
    private EditText editTextLitros;
    private AdminSQLiteOpenHelper admin;

    private int codigoProdSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_actualizar_produ);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        admin = new AdminSQLiteOpenHelper(this);
        editTextVacaCodigo = findViewById(R.id.editTextVacaCodigo);
        editTextFecha = findViewById(R.id.editTextFecha);
        editTextLitros = findViewById(R.id.editTextLitros);
        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(main_actualizar_produc.this, new DatePickerDialog.OnDateSetListener() {
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
        finish();
    }
    public void buscarProduccionXCodigo(View view) {
        String vacaCodigo = editTextVacaCodigo.getText().toString().trim();
        String fecha = editTextFecha.getText().toString().trim();
        Cursor cursor = admin.obtenerProduccion(vacaCodigo, fecha);

        if (cursor != null && cursor.moveToFirst()) {
            int ltrs = cursor.getInt(1);
            codigoProdSesion = cursor.getInt(0);
            editTextLitros.setText(String.valueOf(ltrs));
        } else {
            Toast.makeText(this, "Producción no encontrada", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public void modificarProduccion(View view) {

        String vacaCodigo = editTextVacaCodigo.getText().toString().trim();
        String fecha = editTextFecha.getText().toString().trim();
        String litrosStr = editTextLitros.getText().toString().trim();
        int litros = Integer.parseInt(litrosStr);
        try {

            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(this);
            int cantidadActualizada = adminSQLiteOpenHelper.ModificarProduccion(codigoProdSesion, vacaCodigo, fecha, litros);


            if (cantidadActualizada > 0) {
                Toast.makeText(this, "Producción modificada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al modificar la producción", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            Toast.makeText(this, "No se encontro la vaca", Toast.LENGTH_SHORT).show();
        }
    }
}