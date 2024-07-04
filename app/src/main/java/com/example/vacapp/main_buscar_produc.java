package com.example.vacapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Clases.Produccion;
import Clases.ProduccionAdapter;

public class main_buscar_produc extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProduccionAdapter produccionAdapter;
    private AdminSQLiteOpenHelper admin;
    private EditText editTextFecha;
    private EditText editTextLitros;
    private Spinner spinnerCodigoVaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_buscar_produ);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        admin = new AdminSQLiteOpenHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editTextFecha = findViewById(R.id.editTextFecha);
        editTextLitros = findViewById(R.id.editTextLitros);
        spinnerCodigoVaca = findViewById(R.id.CodigoVaca_txt);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.Buscarbp_btn) {
            String litrosStr = editTextLitros.getText().toString().trim();
            int litrosMinimos = litrosStr.isEmpty() ? 0 : Integer.parseInt(litrosStr);

            // Llamar al método VerProduccionPorLitros para obtener el Cursor con los datos de producción por litros
            Cursor cursor = admin.VerProduccionPorLitros(litrosMinimos);

            // Procesar el Cursor y actualizar el RecyclerView
            List<Produccion> produccionList = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int codigoProduccion = cursor.getInt(cursor.getColumnIndex("prod_codigo"));
                    @SuppressLint("Range") String codigoVaca = cursor.getString(cursor.getColumnIndex("vaca_codigo"));
                    @SuppressLint("Range") String fechaProduccion = cursor.getString(cursor.getColumnIndex("prod_fecha"));
                    @SuppressLint("Range") int litrosProduccion = cursor.getInt(cursor.getColumnIndex("prod_litros"));


                    Produccion produccion = new Produccion(codigoProduccion, codigoVaca, fechaProduccion, litrosProduccion);
                    produccionList.add(produccion);
                } while (cursor.moveToNext());
            }


            if (cursor != null) {
                cursor.close();
            }


            produccionAdapter = new ProduccionAdapter(this, produccionList);
            recyclerView.setAdapter(produccionAdapter);

           
            if (produccionList.isEmpty()) {
                Toast.makeText(this, "No se encontraron producciones con al menos " + litrosMinimos + " litros.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Producciones encontradas: " + produccionList.size(), Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.Regresar_btn) {
            finish();
        }
        if (view.getId() == R.id.Buscarbp_btn) {
            String fecha = editTextFecha.getText().toString().trim();


            Cursor cursor = admin.VerProduccionPorFecha(fecha);


            List<Produccion> produccionList = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int codigoProduccion = cursor.getInt(cursor.getColumnIndex("prod_codigo"));
                    @SuppressLint("Range") String codigoVaca = cursor.getString(cursor.getColumnIndex("vaca_codigo"));
                    @SuppressLint("Range") String fechaProduccion = cursor.getString(cursor.getColumnIndex("prod_fecha"));
                    @SuppressLint("Range") int litrosProduccion = cursor.getInt(cursor.getColumnIndex("prod_litros"));


                    Produccion produccion = new Produccion(codigoProduccion, codigoVaca, fechaProduccion, litrosProduccion);
                    produccionList.add(produccion);
                } while (cursor.moveToNext());
            }


            if (cursor != null) {
                cursor.close();
            }


            produccionAdapter = new ProduccionAdapter(this, produccionList);
            recyclerView.setAdapter(produccionAdapter);


            if (produccionList.isEmpty()) {
                Toast.makeText(this, "No se encontraron producciones para la fecha especificada.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Producciones encontradas: " + produccionList.size(), Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.Regresar_btn) {
            finish();
        }
        if (view.getId() == R.id.Buscarbp_btn) {
            String fecha = editTextFecha.getText().toString().trim();
            String litrosStr = editTextLitros.getText().toString().trim();
            String codigoVaca = spinnerCodigoVaca.getSelectedItem().toString();

            int litros = litrosStr.isEmpty() ? 0 : Integer.parseInt(litrosStr);

            Cursor cursor = admin.VerProduccion(codigoVaca);

            List<Produccion> produccionList = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    cursor.getColumnIndex("prod_codigo");
                    @SuppressLint("Range") int prodCodigo = cursor.getInt(cursor.getColumnIndex("prod_codigo"));
                    @SuppressLint("Range") String fechaProduccion = cursor.getString(cursor.getColumnIndex("prod_fecha"));
                    @SuppressLint("Range") int litrosProduccion = cursor.getInt(cursor.getColumnIndex("prod_litros"));

                    Produccion produccion = new Produccion(prodCodigo, codigoVaca, fechaProduccion, litrosProduccion);
                    produccionList.add(produccion);
                } while (cursor.moveToNext());
            }

            if (cursor != null) {
                cursor.close();
            }

            produccionAdapter = new ProduccionAdapter(this, produccionList);
            recyclerView.setAdapter(produccionAdapter);

            if (produccionList.isEmpty()) {
                Toast.makeText(this, "No se encontraron producciones.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Producciones encontradas: " + produccionList.size(), Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.Regresar_btn) {
            finish();
        }

    }
}
