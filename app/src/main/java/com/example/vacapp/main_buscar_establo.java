package com.example.vacapp;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import Clases.Establo;

public class main_buscar_establo extends AppCompatActivity {


    private AdminSQLiteOpenHelper admin;
    private TableLayout tableLayout;
    private EditText et1, et2, et3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_buscar_establo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et1=findViewById(R.id.editText24);
        et2=findViewById(R.id.editText6);
        et3=findViewById(R.id.editText8);

        tableLayout = findViewById(R.id.tableLayout);

        admin = new AdminSQLiteOpenHelper(this);
    }
    public void buscarEstablos(View view) {
        limpiarFilasDinamicas();
        try {
            String p1 = null;
            Double p2 = null, p3 = null;
            if(!et1.getText().toString().isEmpty())
                p1 = et1.getText().toString();
            if(!et2.getText().toString().isEmpty())
                p2 = Double.parseDouble(et2.getText().toString());
            if(!et3.getText().toString().isEmpty())
                p3 = Double.parseDouble(et3.getText().toString());
            List<Establo> establoList = admin.obtenerEstabloParametro(p1,p2,p3);
            String[][] datos = new String[establoList.size()][3];
            for (int i = 0; i < establoList.size(); i++) {
                datos[i][0] = establoList.get(i).getCodigo();
                datos[i][1] = String.valueOf(establoList.get(i).getAncho());
                datos[i][2] = String.valueOf(establoList.get(i).getLargo());
            }
            for (String[] row : datos) {
                TableRow tableRow = new TableRow(this);
                for (String cell : row) {
                    TextView textView = new TextView(this);
                    textView.setText(cell);
                    textView.setPadding(8, 8, 8, 8);
                    tableRow.addView(textView);
                }
                tableLayout.addView(tableRow);
            }
            if(!establoList.isEmpty())
                Toast.makeText(this, "Datos encontrados", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "No se encontraron datos del establo", Toast.LENGTH_SHORT).show();
        }catch (NullPointerException e) {
            Toast.makeText(this, "No se encontraron datos del establo", Toast.LENGTH_SHORT).show();
        }

    }
    private void limpiarFilasDinamicas() {
        // Obtener el número de filas dinámicas (después de la primera fila de títulos)
        int count = tableLayout.getChildCount();

        // Eliminar todas las filas dinámicas, empezando desde la segunda fila (índice 1)
        for (int i = count - 1; i > 0; i--) {
            View child = tableLayout.getChildAt(i);
            if (child instanceof TableRow) {
                tableLayout.removeViewAt(i);
            }
        }
    }

    public void onClick(View view) {
        finish();
    }
}