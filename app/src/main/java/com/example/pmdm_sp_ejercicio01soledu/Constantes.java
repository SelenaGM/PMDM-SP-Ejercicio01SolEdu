package com.example.pmdm_sp_ejercicio01soledu;

public class Constantes {

    public static final String USUARIOS = "usuarios";
    public static final String CONTACTOS = "contactos";

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String DATOS = "datos";
    //Usamos la libreria para convertir esto en un GSON
    // --> Project Structure --> Dependency --> app --> + Library (en Declared Dependencies) --> com.google.code.gson

    public static String codifica(String password){
        StringBuilder stringBuilder= new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            stringBuilder.append((char) (password.charAt(i)+1)); //esto es para cambiar el character por el siguiente
        }
        return stringBuilder.toString();
    }

    public static String decodifica(String passwordCifrado){
        StringBuilder stringBuilder= new StringBuilder();
        for (int i = 0; i < passwordCifrado.length(); i++) {
            stringBuilder.append((char) (passwordCifrado.charAt(i)-1)); //esto es para cambiar el character por el anterior
        }
        return stringBuilder.toString();
    }
}
