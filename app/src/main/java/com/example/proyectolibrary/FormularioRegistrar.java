package com.example.proyectolibrary;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FormularioRegistrar extends AppCompatActivity {

    EditText NombreCompleto, Identificacion, TextDate, DepartamentoNacimiento,
            MunicipioNacimiento, TextPhone, Correo, password;

    //Declaro las variables que usare para conectar con los id del xml ⬆️

    CheckBox Terminos_Condiciones;

    FirebaseFirestore mFirestore;  //Conexiones necesarias para trabajar Firebase con mAuth ️
    FirebaseAuth mAuth;



     /*FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_registrar);

        NombreCompleto = findViewById(R.id.txtNombreCompleto);
        Identificacion = findViewById(R.id.txtId);
        TextDate = findViewById(R.id.editTextDate);
        DepartamentoNacimiento = findViewById(R.id.txtDepartamentoNacimiento);
        MunicipioNacimiento = findViewById(R.id.txtMunicipioNacimiento);
        TextPhone = findViewById(R.id.editTextPhone);
        Correo = findViewById(R.id.txtCorreo);
        password = findViewById(R.id.txtPassword);
        Terminos_Condiciones = (CheckBox) findViewById(R.id.checkBox);

        //Conecto las variables con los id

        mFirestore = FirebaseFirestore.getInstance();  //Instancio los metodoso a usar de Firebase
        mAuth = FirebaseAuth.getInstance();



        //inicializarFirebase();
    }

   /* private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =  firebaseDatabase.getReference();
    }*/

    String nombre;
     String date;
     String departamento;
     String municipio;
     String telefono;
     String email;
     String password_;
     String identificacion;



     public void AceptarTerminos(View view){ //Metodo Onclick para Validar el CheckBox
         if (view.getId() == R.id.checkBox){
         }
     }



    public void GuardarMessage (View view) { //Metodo para guardar datos
        nombre = NombreCompleto.getText().toString();
        date = TextDate.getText().toString();
        departamento = DepartamentoNacimiento.getText().toString();
        municipio = MunicipioNacimiento.getText().toString();
        telefono = TextPhone.getText().toString();
        email = Correo.getText().toString();
        password_ = password.getText().toString();
        identificacion = Identificacion.getText().toString();

        //Varuables auxiliares con las variables ya conectadas a los Id

        if (nombre.equals("")||identificacion.isEmpty()||date.equals("")||departamento.equals("")||municipio.equals("")||telefono.equals("")||email.isEmpty()||password_.isEmpty()||Terminos_Condiciones.isChecked() == false){
            validacion(); //Condicion que me valida los campos vacios y su metodo de validacion
        }else if (Terminos_Condiciones.isChecked()){ //Declaro como necesario el CheckBox para ejecutar la condicion
            registerUser(nombre,identificacion,date,departamento,municipio,telefono,email,password_);
            //Metodo que me tiene las propiedades de la base de datos

            /*Persona persona = new Persona();
            persona.setUid(UUID.randomUUID().toString());

            persona.setNombre(nombre);
            persona.setIdentificacion(Integer.parseInt(identificacion));
            persona.setFechaNacimiento(date);
            persona.setDepartamento_N(departamento);
            persona.setMunicipio_N(municipio);
            persona.setTelefono(Integer.parseInt(telefono));
            persona.setEmail(email);
            persona.setPassword(password_);

            databaseReference.child("Persona").child(persona.getUid()).setValue(persona);*/

            Toast.makeText(getApplicationContext(), "Agregado con exito",Toast.LENGTH_SHORT).show();
            limpiarElementos(); //Mensaje cuando se cumple la condicion y limpieza de los campos por medio de la funcion
        }

    }
    public void Regresar(View view){//Funcion para regresar entre pantallas
        finish();
        startActivity(new Intent(FormularioRegistrar.this , Login.class));
    }

    private void registerUser(String nombre, String identificacion, String date, String departamento, String municipio, String telefono, String email, String password_)//Metodo para registrar datos
    { //Me crea una ⬇️identificacion unica con email y password
        mAuth.createUserWithEmailAndPassword(email,password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) { //Mapeo para Firebase de los datos

                String id = mAuth.getCurrentUser().getUid(); //Creacion de Id unico
                Map<String, Object> map = new HashMap<>(); //Mapeo de los Datos
                map.put("id",id);
                map.put("nombre",nombre); //Nombre de los datos con los atributos anteriormente declarados
                map.put("identificacion", identificacion);
                map.put("date", date);
                map.put("departamento",departamento);
                map.put("municipio", municipio);
                map.put("telefono", telefono);
                map.put("email", email);
                map.put("password", password_);

                mFirestore.collection("usuarios").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) { //Si se cumple la funcion me ejecuta la funcion de mensaje y redireccion
                        finish();
                        startActivity(new Intent(FormularioRegistrar.this , MainActivity.class));
                        Toast.makeText(getApplicationContext(), "Usuario registrado con éxito",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) { //Si falla se ejecuta la funcion de mensaje y redireccion en caso de error
                        Toast.makeText(getApplicationContext(), "Error al Guardar",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al Registrar",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarElementos() { //Metodo para limpiar elementos
        NombreCompleto.setText("");
        TextDate.setText("");
        Identificacion.setText("");
        DepartamentoNacimiento.setText("");
        MunicipioNacimiento.setText("");
        TextPhone.setText("");
        Correo.setText("");
        password.setText("");
    }

    private void validacion() { //Metodo para Validar

        String nombre = NombreCompleto.getText().toString();
        String date = TextDate.getText().toString();
        String departamento = DepartamentoNacimiento.getText().toString();
        String municipio = MunicipioNacimiento.getText().toString();
        String telefono = TextPhone.getText().toString();
        String email = Correo.getText().toString();
        String password_ = password.getText().toString();
        String identificacion = Identificacion.getText().toString();
        Terminos_Condiciones = (CheckBox) findViewById(R.id.checkBox);


        if(nombre.equals("")){ //Validaciones y mensajes de error
            NombreCompleto.setError("Campo Requerido");
            Toast.makeText(getApplicationContext(), "Por favor ingrese un Nombre",Toast.LENGTH_SHORT).show();
        }else if(Terminos_Condiciones.isChecked()==false){
            Terminos_Condiciones.setError("Debe aceptar los terminos y condiciones");
            Toast.makeText(getApplicationContext(), "Por favor acepte los terminos y condiciones",Toast.LENGTH_SHORT).show();
        }else if(date.equals("")){
            TextDate.setError("Campo Requerido");
            Toast.makeText(getApplicationContext(), "Por favor ingrese su fecha de Nacimiento",Toast.LENGTH_SHORT).show();
        }else if(departamento.equals("")){
            DepartamentoNacimiento.setError("Campo Requerido");
            Toast.makeText(getApplicationContext(), "Por favor ingrese su departamento",Toast.LENGTH_SHORT).show();
        }else if(municipio.equals("")){
            MunicipioNacimiento.setError("Campo Requerido");
            Toast.makeText(getApplicationContext(), "Por favor ingrese su Municipio",Toast.LENGTH_SHORT).show();
        }else if (telefono.equals("")){
            TextPhone.setError("Campo Requerido");
            Toast.makeText(getApplicationContext(), "Por favor ingrese su Telefono",Toast.LENGTH_SHORT).show();
        }else if (email.equals("")){
            Correo.setError("Campo Requerido");
            Toast.makeText(getApplicationContext(), "Por favor ingrese su Correo Electronico",Toast.LENGTH_SHORT).show();
        }else if (password_.equals("")){
            password.setError("Campo Requerido");
            Toast.makeText(getApplicationContext(), "Por favor ingrese una Contraseña",Toast.LENGTH_SHORT).show();
        }else if (identificacion.equals("")) {
            Identificacion.setError("Campo Requerido");
            Toast.makeText(getApplicationContext(), "Por favor ingrese su numero de Identificacion ",Toast.LENGTH_SHORT).show();
        }
    }
}
