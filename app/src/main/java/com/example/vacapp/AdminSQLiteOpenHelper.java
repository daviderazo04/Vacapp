package com.example.vacapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Clases.Establo;
import Clases.ProduccionPromedio;
import Clases.Usuario;
import Clases.Vaca;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper
{
    public AdminSQLiteOpenHelper(Context context) {
        super(context, "vacas.db", null, 1);
    }
    private static int usuarioSesion;
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE usuario (" +
                "usr_codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usr_usuario TEXT, " +
                "usr_contraseña TEXT)");

        db.execSQL("CREATE TABLE establo (" +
                "est_codigo TEXT PRIMARY KEY, " +
                "usr_codigo INTEGER, " +
                "est_ancho REAL, " +
                "est_largo REAL, " +
                "FOREIGN KEY(usr_codigo) REFERENCES usuario(usr_codigo) " +
                "ON DELETE CASCADE ON UPDATE CASCADE)");

        db.execSQL("CREATE TABLE vaca (" +
                "vaca_codigo TEXT PRIMARY KEY, " +
                "vaca_raza TEXT, " +
                "vaca_peso REAL, " +
                "vaca_edad INTEGER, " +
                "est_codigo INTEGER, " +
                "FOREIGN KEY(est_codigo) REFERENCES establo(est_codigo) " +
                "ON DELETE CASCADE ON UPDATE CASCADE)");

        db.execSQL("CREATE TABLE produccion (" +
                "prod_codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "vaca_codigo TEXT, " +
                "prod_fecha TEXT, " +  //darle formato de fecha
                "prod_litros INTEGER, " +
                "FOREIGN KEY(vaca_codigo) REFERENCES vaca(vaca_codigo) " +
                "ON DELETE CASCADE ON UPDATE CASCADE)");
    }

    @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Eliminar todas las tablas existentes
            db.execSQL("DROP TABLE IF EXISTS usuario");
            db.execSQL("DROP TABLE IF EXISTS vaca");
            db.execSQL("DROP TABLE IF EXISTS produccion");
            db.execSQL("DROP TABLE IF EXISTS establo");
            // Recrear las tablas
            onCreate(db);
        }
        //METODOS TABLA USUARIO

    public long AgregarUsuario(String usuario, String contraseña) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("usr_usuario", usuario);
        registro.put("usr_contraseña", contraseña);

        long resultado = db.insert("usuario", null, registro);
        db.close();
        return resultado;
    }

    public Cursor VerUsuario(int codigoUsuario)
        {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT usr_codigo, usr_nombre, usr_usuario, usr_contraseña " +
                    "FROM usuario WHERE usr_codigo=?", new String[]{String.valueOf(codigoUsuario)});
        }
    public int ModificarUsuario(int codigoUsuario, String usuario, String contraseña) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("usr_usuario", usuario);
        registro.put("usr_contraseña", contraseña);

        int cant = db.update("usuario", registro, "usr_codigo=?", new String[]{String.valueOf(codigoUsuario)});
        db.close();
        return cant;
    }

    public Usuario BuscarUsuarioPorCodigo(int codigoUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Usuario usuario = null;

        String query = "SELECT usr_codigo, usr_usuario, usr_contraseña FROM usuario WHERE usr_codigo = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(codigoUsuario)});

        if (cursor.moveToFirst()) {
            int usrCodigo = cursor.getInt(0);
            String usrUsuario = cursor.getString(1);
            String usrContraseña = cursor.getString(2);

            usuario = new Usuario(usrCodigo, usrUsuario, usrContraseña);
        }
        cursor.close();
        db.close();

        return usuario;
    }

    public Usuario BuscarUsuarioPorNombre(String nombreUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Usuario usuario = null;

        String query = "SELECT usr_codigo, usr_usuario, usr_contraseña FROM usuario WHERE usr_usuario = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombreUsuario});

        if (cursor.moveToFirst()) {
            int usrCodigo = cursor.getInt(0);
            usuarioSesion = usrCodigo;
            String usrUsuario = cursor.getString(1);
            String usrContraseña = cursor.getString(2);
            usuario = new Usuario(usrCodigo, usrUsuario, usrContraseña);
        }

        cursor.close();
        db.close();

        return usuario;
    }


//METODOS TABLA VACA
    public long AgregarVaca(String codigo, String raza, double peso, int edad, String estCodigo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("vaca_codigo", codigo);
        registro.put("vaca_raza", raza);
        registro.put("vaca_peso", peso);
        registro.put("vaca_edad", edad);
        registro.put("est_codigo", estCodigo);

        long resultado = db.insert("vaca", null, registro);
        db.close();
        return resultado;
    }


    public int ModificarVaca(String codigo, String raza, double peso, int edad, String estCodigo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("vaca_raza", raza);
        registro.put("vaca_peso", peso);
        registro.put("vaca_edad", edad);
        registro.put("est_codigo", estCodigo);  // Añadir código del establo

        int cant = db.update("vaca", registro, "vaca_codigo=?", new String[]{codigo});
        db.close();
        return cant;
    }

    public Vaca buscarVacaPorCodigo(String codigo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Vaca vaca = null;

        String query = "SELECT vaca_codigo, vaca_raza, vaca_peso, vaca_edad, est_codigo FROM vaca WHERE vaca_codigo = ?";
        Cursor cursor = db.rawQuery(query, new String[]{codigo});

        if (cursor.moveToFirst()) {
            String vacaCodigo = cursor.getString(0);
            String vacaRaza = cursor.getString(1);
            double vacaPeso = cursor.getDouble(2);
            int vacaEdad = cursor.getInt(3);
            String vacaEstablo = cursor.getString(4);

            vaca = new Vaca(vacaCodigo, vacaRaza, vacaPeso, vacaEdad, vacaEstablo);
        }

        cursor.close();
        db.close();

        return vaca;
    }
    public boolean obtenerVaca(String codigo) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean comp = false;
        Cursor c = db.rawQuery("SELECT vaca_raza, vaca_peso, vaca_edad, est_codigo " +
                "FROM vaca WHERE vaca_codigo=?", new String[]{codigo});
        if(c.moveToNext())
            comp = true;
        return comp;
    }
    public List<Vaca> buscarVacasPorCriterios(String codigo, String raza, String establo, String edadIntervalo) {
        List<Vaca> vacaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder query = new StringBuilder("SELECT vaca_codigo, vaca_raza, vaca_peso, vaca_edad, est_codigo FROM vaca WHERE 1=1");

        if (codigo != null) {
            query.append(" AND vaca_codigo = '").append(codigo).append("'");
        }
        if (raza != null) {
            query.append(" AND vaca_raza = '").append(raza).append("'");
        }
        if (establo != null) {
            query.append(" AND est_codigo = '").append(establo).append("'");
        }
        if (edadIntervalo != null) {
            switch (edadIntervalo) {
                case "1-5":
                    query.append(" AND vaca_edad BETWEEN 1 AND 5");
                    break;
                case "5-10":
                    query.append(" AND vaca_edad BETWEEN 5 AND 10");
                    break;
                case "10-15":
                    query.append(" AND vaca_edad BETWEEN 10 AND 15");
                    break;
                case "15+":
                    query.append(" AND vaca_edad >= 15");
                    break;
                default:
                    break;
            }
        }

        Cursor cursor = db.rawQuery(query.toString(), null);

        if (cursor.moveToFirst()) {
            do {
                String vacaCodigo = cursor.getString(0);
                String vacaRaza = cursor.getString(1);
                double vacaPeso = cursor.getDouble(2);
                int vacaEdad = cursor.getInt(3);
                String vacaEstablo = cursor.getString(4);

                Vaca vaca = new Vaca(vacaCodigo, vacaRaza, vacaPeso, vacaEdad, vacaEstablo);
                vacaList.add(vaca);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return vacaList;
    }
    public List<Vaca> obtenerTodasLasVacas() {
        List<Vaca> vacaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT vaca_codigo, vaca_raza, vaca_peso, vaca_edad, est_codigo FROM vaca", null);

        if (cursor.moveToFirst()) {
            do {
                String codigo = cursor.getString(0);
                String raza = cursor.getString(1);
                double peso = cursor.getDouble(2);
                int edad = cursor.getInt(3);
                String establo = cursor.getString(4);

                Vaca vaca = new Vaca(codigo, raza, peso, edad, establo);
                vacaList.add(vaca);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return vacaList;
    }

    public int EliminarVaca(String codigo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int cant = db.delete("vaca", "vaca_codigo=?", new String[]{codigo});
        db.close();
        return cant;
    }

    //METODOS TABLA PRODUCCIÓN
    public long AgregarProduccion(String vaca_codigo, String fecha, int litros) {
        SQLiteDatabase db = null;
        long resultado = -1;
        boolean comp;

        try {
            db = this.getWritableDatabase();
            comp = obtenerVaca(vaca_codigo);

            if (comp) {
                ContentValues registro = new ContentValues();
                registro.put("vaca_codigo", vaca_codigo);
                registro.put("prod_fecha", fecha);  // Guardar fecha como TEXT
                registro.put("prod_litros", litros);

                resultado = db.insert("produccion", null, registro);
            }
        } catch (Exception e) {
            // Manejo de excepciones, como registro de errores
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return resultado;
    }

    public int ModificarProduccion(int prod_codigo, String vaca_codigo, String fecha, int litros) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("vaca_codigo", vaca_codigo);
        registro.put("prod_fecha", fecha);  // Guardar fecha como TEXT
        registro.put("prod_litros", litros);

        int cant = db.update("produccion", registro, "prod_codigo=?", new String[]{String.valueOf(prod_codigo)});
        return cant;
    }

    public int EliminarProduccion(int prod_codigo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int cant = db.delete("produccion", "prod_codigo=?", new String[]{String.valueOf(prod_codigo)});
        db.close();
        return cant;
    }

    public Cursor VerProduccion(int prod_codigo) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT vaca_codigo, prod_fecha, prod_litros FROM produccion WHERE prod_codigo=?", new String[]{String.valueOf(prod_codigo)});
    }
    public Cursor obtenerProduccion(String vaca_codigo, String fecha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = { vaca_codigo, fecha }; // Array para los argumentos de selección
        return db.rawQuery("SELECT prod_codigo, prod_litros FROM produccion WHERE vaca_codigo=? AND prod_fecha=?", selectionArgs);
    }

    public Cursor VerProduccion(String vaca_codigo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = { vaca_codigo }; // Array para los argumentos de selección
        return db.rawQuery("SELECT prod_codigo, prod_fecha, prod_litros FROM produccion WHERE vaca_codigo=?", selectionArgs);
    }
    public Cursor VerProduccionPorFecha(String fecha) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT vaca_codigo, prod_fecha, prod_litros FROM produccion WHERE prod_fecha=?", new String[]{fecha});
    }

    public Cursor VerProduccionPorLitros(int litrosMinimos) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = { String.valueOf(litrosMinimos) };
        return db.rawQuery("SELECT prod_codigo, vaca_codigo, prod_fecha, prod_litros FROM produccion WHERE prod_litros >= ?", selectionArgs);
    }

    public List<ProduccionPromedio> obtenerPromedioProduccion(String intervalo) {
        List<ProduccionPromedio> listaPromedios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "";

            if (intervalo.equalsIgnoreCase("Últimos 7 días")) {
                query = "SELECT vaca_codigo, AVG(prod_litros) as promedio FROM produccion " +
                        "WHERE prod_fecha >= date('now', '-7 days') " +
                        "GROUP BY vaca_codigo";
            } else if (intervalo.equalsIgnoreCase("Último mes")) {
                query = "SELECT vaca_codigo, AVG(prod_litros) as promedio FROM produccion " +
                        "WHERE prod_fecha >= date('now', 'start of month') " +
                        "GROUP BY vaca_codigo";
            }

            cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                String vacaCodigo = cursor.getString(cursor.getColumnIndexOrThrow("vaca_codigo"));
                double promedio = cursor.getDouble(cursor.getColumnIndexOrThrow("promedio"));
                listaPromedios.add(new ProduccionPromedio(vacaCodigo, promedio));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaPromedios;
    }
    //METODOS TABLA ESTABLO
    public long AgregarEstablo(String nombre, double ancho, double largo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("est_codigo", nombre);
        registro.put("usr_codigo", usuarioSesion);
        registro.put("est_ancho", ancho);
        registro.put("est_largo", largo);

        long resultado = db.insert("establo", null, registro);
        db.close();
        return resultado;
    }

    public long ModificarEstablo(String nombre, double ancho, double largo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("est_codigo", nombre);
        registro.put("usr_codigo", usuarioSesion);
        registro.put("est_ancho", ancho);
        registro.put("est_largo", largo);

        long resultado = db.update("establo", registro, "est_codigo=?", new String[]{String.valueOf(nombre)});
        db.close();
        return resultado;
    }

    public Cursor VerEstablo(String est_codigo) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT est_ancho, est_largo FROM establo WHERE est_codigo=?", new String[]{String.valueOf(est_codigo)});
    }

    public int EliminarEstablo(String est_codigo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int cant = db.delete("establo", "est_codigo=?", new String[]{String.valueOf(est_codigo)});
        db.close();
        return cant;
    }

    public List<String> obtenerCodigosEstablo() {
        List<String> listaCodigos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT est_codigo FROM establo WHERE usr_codigo="+usuarioSesion, null);
        if (cursor.moveToFirst()) {
            do {
                String codigo = String.valueOf(cursor.getString(0));
                listaCodigos.add(codigo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaCodigos;
    }
    public List<Establo> obtenerEstabloParametro(String nombre, Double ancho, Double largo) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Establo> establoList = new ArrayList<>();
        Cursor cursor;
        // Construir la consulta dinámicamente
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM establo WHERE usr_codigo="+usuarioSesion);

        List<String> selectionArgs = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty()) {
            queryBuilder.append(" AND est_codigo LIKE ?");
            selectionArgs.add("%" + nombre + "%");
        }
        if (ancho != null) {
            queryBuilder.append(" AND est_ancho = ?");
            selectionArgs.add(String.valueOf(ancho));
        }
        if (largo != null) {
            queryBuilder.append(" AND est_largo = ?");
            selectionArgs.add(String.valueOf(largo));
        }
        if(selectionArgs.size()>0) {
            // Convertir la lista de argumentos a un array
            String[] argsArray = new String[selectionArgs.size()];
            selectionArgs.toArray(argsArray);

            // Ejecutar la consulta
            cursor = db.rawQuery(queryBuilder.toString(), argsArray);
        }
        else{
            cursor = db.rawQuery(queryBuilder.toString(), null);
        }

        // Procesar los resultados del cursor
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String codigo = cursor.getString(0);
                int usuario = cursor.getInt(1);
                double anchoEstablo = cursor.getDouble(2);
                double largoEstablo = cursor.getDouble(3);

                Establo establo = new Establo(codigo, usuario, anchoEstablo, largoEstablo);
                establoList.add(establo);
            } while (cursor.moveToNext());
        }

        // Cerrar recursos
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return establoList;
    }
    public List<Establo> obtenerTodosEstablos() {
        List<Establo> establoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM establo WHERE usr_codigo="+usuarioSesion, null);

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(0);
                double ancho = cursor.getDouble(2);
                double largo = cursor.getDouble(3);

                Establo establo = new Establo(nombre, usuarioSesion, ancho, largo);
                establoList.add(establo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return establoList;
    }

    public int obtenerNumeroVacasEstablo(String idEstablo) {
        int numero = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM vaca WHERE est_codigo =?", new String[]{idEstablo}, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                numero += 1;
            }
        }
        cursor.close();
        db.close();
        return numero;
    }



}
