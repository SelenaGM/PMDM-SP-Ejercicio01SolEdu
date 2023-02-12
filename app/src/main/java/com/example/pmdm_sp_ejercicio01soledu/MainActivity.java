package com.example.pmdm_sp_ejercicio01soledu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;

    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializaVistas();

        sp= getSharedPreferences(Constantes.USUARIOS, MODE_PRIVATE); //creamos la shared preference de USUARIOS

        //si el usuario ya ha hecho login anteriormente, se salte esta página
        if(sp.contains(Constantes.EMAIL) && sp.contains(Constantes.PASSWORD)){
            startActivity(new Intent(MainActivity.this,UserActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtEmail.getText().toString().equalsIgnoreCase("correo@server.com")
                    && txtPassword.getText().toString().equals("123456")){
                    //PARA EDITAR LA SHARED PREFERENCE
                    SharedPreferences.Editor editor= sp.edit();
                    editor.putString(Constantes.EMAIL, txtEmail.getText().toString());//Lo primero sería como un tag y lo segundo lo que queremos almacenar
                    editor.putString(Constantes.PASSWORD, Constantes.codifica(txtPassword.getText().toString()));  //aquí le mandamos la password pero ya codificada
                    editor.apply();
                    //AQUI LA ENVIAMOS LA INFO
                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                    finish(); //<-- Para que cierre la aplicación y no puedas volver atrás una vez estas en la siguiente ventana, sólo puedas dandole a logout

                }else{
                    Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializaVistas() {
        txtEmail= findViewById(R.id.txtEmailMain);
        txtPassword = findViewById(R.id.txtPasswordMain);
        btnLogin = findViewById(R.id.btnLogin);
    }
}