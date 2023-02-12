package com.example.pmdm_sp_ejercicio01soledu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.pmdm_sp_ejercicio01soledu.modelos.ContactosMatricula;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private Button btnLogOut;
    private TextView lblEmail, lblPassword;
    private Button btnCargar;
    private Button btnGuardar;
    private TextView lblCantidad;
    private SharedPreferences sp;
    private SharedPreferences spContactos;


    private List<ContactosMatricula> contactos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        sp= getSharedPreferences(Constantes.USUARIOS, MODE_PRIVATE); //La inicializamos
        spContactos = getSharedPreferences(Constantes.CONTACTOS,MODE_PRIVATE);
        contactos = new ArrayList<>();

        if(!spContactos.contains(Constantes.DATOS)) { //si no existe en la sp de contactos el constantes.datos, llamamos a la función
            creaContactosMatricula();
        }

        inicializaVariables();

        //vamos a enseñar por los label los datos que nos hemos traido de la shared preferences
        lblEmail.setText(sp.getString(Constantes.EMAIL,""));
        lblPassword.setText(Constantes.decodifica(sp.getString(Constantes.PASSWORD,"")));

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor= sp.edit();
                editor.clear(); //Cuando le demos a deslogear, esto lo borrará
                editor.apply();
                startActivity(new Intent(UserActivity.this, MainActivity.class));//para que nos vuelva a llevar a la anterior
                finish();//para que cierre la aplicación
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Conseguir un editor de la SP que corresponda y meter el JSON del ArrayList
                //Convertir los datos JSON al arraylist
                String datosJson = new Gson().toJson(contactos);
                SharedPreferences.Editor editor = spContactos.edit();
                editor.putString(Constantes.DATOS, datosJson);
                editor.apply();
            }
        });

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LO CONTRARIO
                Type tipo = new TypeToken<ArrayList<ContactosMatricula>>(){}.getType(); //Tenemos que meterle el tipo, un arraylist de contactos matricula
                ArrayList<ContactosMatricula> temp = new Gson().fromJson(sp.getString(Constantes.CONTACTOS, ""),tipo); //Dónde sacarlo y cómo lo tiene que construir
                contactos.clear();
                contactos.addAll(temp);
                lblCantidad.setText("Tenemos "+contactos.size()+" contactos");
            }
        });

    }

    private void creaContactosMatricula() {
        for (int i = 0; i < 10; i++) {
            contactos.add(new ContactosMatricula("Nombre "+1,"Ciclo "+1,"Email "+1,"Telefono "+1));
        }
    }

    private void inicializaVariables() {
        btnLogOut = findViewById(R.id.btnLogout);
        lblEmail = findViewById(R.id.lblEmailUser);
        lblPassword = findViewById(R.id.lblPasswordUser);
        btnCargar = findViewById(R.id.btnCargarDatos);
        btnGuardar= findViewById(R.id.btnGuardarDatos);
        lblCantidad= findViewById(R.id.lblCantidad);
        lblCantidad.setText("Tenemos "+contactos.size()+" contactos");
    }
}