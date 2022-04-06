package com.sebastopol.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sebastopol.sqllite.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding x;

    EditText txtCodigo, txtDescripcion, txtPrecio;
    Button btnBuscar, btnGuardar, btnEditar, btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());


        btnBuscar      = findViewById(R.id.btnBuscar);
        btnGuardar     = findViewById(R.id.btnGuardar);
        btnEditar      = findViewById(R.id.btnEditar);
        btnBorrar      = findViewById(R.id.btnBorrar);
    }

    public void guardar(View view){
        ConexionBD conexion = new ConexionBD(this, "administracion",null,1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = x.txtCodigo.getText().toString();
        String descripcion = x.txtDescripcion.getText().toString();
        String precio = x.txtPrecio.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion",descripcion);
        registro.put("precio", precio);

        bd.insert("articulos",null,registro);
        bd.close();

        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");

        Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
    }

    public void buscar(View view){
        ConexionBD conexion = new ConexionBD(this,"administracion",null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = txtCodigo.getText().toString();

        Cursor fila = bd.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo='"+codigo+"'",null);

        if(fila.moveToFirst()){
            txtDescripcion.setText(fila.getString(0));
            txtPrecio.setText(fila.getString(1));
            bd.close();
            Toast.makeText(this,"Producto encontrado",Toast.LENGTH_SHORT).show();
        }
        else{
            bd.close();
            Toast.makeText(this, "No se ha encontrado el Producto", Toast.LENGTH_SHORT).show();
            txtCodigo.setText("");
            txtDescripcion.setText("");
            txtPrecio.setText("");
        }

    }

    public void editar(View view){
        ConexionBD conexion = new ConexionBD(this,"administracion",null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = x.txtCodigo.getText().toString();
        String descripcion = x.txtCodigo.getText().toString();
        String precio = x.txtCodigo.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion",descripcion);
        registro.put("precio", precio);

        int a = bd.update("articulos", registro,"codigo='"+codigo+"'",null);

        if(a>0){
            Toast.makeText(this, "Modificado Correcatamente", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "No se ha podido Modificar", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void borrar(View view){
        ConexionBD conexion = new ConexionBD(this,"administracion",null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String codigo = x.txtCodigo.getText().toString();


        int a = bd.delete("articulos", "codigo='"+codigo+"'",null);

        if(a > 0){
            Toast.makeText(this, "Borrado Correcatamente", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "No se ha podido Borrar", Toast.LENGTH_SHORT).show();
        }
        bd.close();

        x.txtCodigo.setText("");
        x.txtDescripcion.setText("");
        x.txtPrecio.setText("");

    }



}