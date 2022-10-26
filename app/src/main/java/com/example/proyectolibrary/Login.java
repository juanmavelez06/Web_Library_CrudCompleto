package com.example.proyectolibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.Normalizer;

public class Login extends AppCompatActivity {
    TextView linkFormularioRegistrar, linkOlvideContrasena;

    private TextView textViewRegistrarse;
    EditText email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();

        linkFormularioRegistrar = (TextView) findViewById(R.id.textViewRegistrarse);
        linkOlvideContrasena = (TextView) findViewById(R.id.textViewOlvideContrasena);
        email = findViewById(R.id.txtemail);
        password = findViewById(R.id.txtPasswordL);

        linkFormularioRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Login.this,FormularioRegistrar.class));
                Intent i = new Intent(Login.this, FormularioRegistrar.class);
                startActivity(i);
            }
        });

        linkOlvideContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                Intent i = new Intent(Login.this, OlvideContrasena.class);
                startActivity(i);
            }
        });


    }

    public void Ingresar (View view){
        String emailUser = email.getText().toString();
        String PasswordUser = password.getText().toString();

        if(emailUser.isEmpty()||PasswordUser.isEmpty()){
            validacion();
        }else{
            LoginUser(emailUser,PasswordUser);

            Toast.makeText(getApplicationContext(), "Ingreso exitoso",Toast.LENGTH_SHORT).show();
            limpiarElementos();
        };
    }

    private void LoginUser(String emailUser, String passwordUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(Login.this,MainActivity.class));
                    Toast.makeText(getApplicationContext(), "¡Bienvenido, Feliz día!",Toast.LENGTH_SHORT).show();
                    limpiarElementos();
                }else{
                    Toast.makeText(getApplicationContext(), "No fue posible ingresar, intentelo de nuevo",Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error de inicio",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void limpiarElementos() {
        email.setText("");
        password.setText("");
    }

    private void validacion() {
        String emailUser = email.getText().toString();
        String PasswordUser = password.getText().toString();

        if(emailUser.isEmpty()){
            email.setError("Campo Requerido");
            Toast.makeText(getApplicationContext(), "Por favor ingrese su email",Toast.LENGTH_SHORT).show();
        }else if(PasswordUser.isEmpty()){
            password.setError("Campo Requerido");
            Toast.makeText(getApplicationContext(), "Por favor ingrese su contraseña",Toast.LENGTH_SHORT).show();
        }
    }
}