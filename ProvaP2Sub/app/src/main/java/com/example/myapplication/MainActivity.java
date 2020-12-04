package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    DatabaseReference reference;

    EditText Nome;
    EditText Data;
    EditText RG;
    EditText Endereco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nome = findViewById(R.id.txtName);
        Data = findViewById(R.id.txtDate);
        RG = findViewById(R.id.txtRG);
        Endereco = findViewById(R.id.txtEnd);

        IniciarFirebase();
    }

    private void IniciarFirebase()
    {
        db = FirebaseDatabase.getInstance();
        db.setPersistenceEnabled(true);
        FirebaseApp.initializeApp(this);
        reference = db.getReference();
    }

    public void Cadast_Pessoa(View view)
    {
        Pessoa Humano = new Pessoa();
        Humano.setNome(Nome.getText().toString());
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Humano.setData(formato.parse(Data.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Humano.setRG(Integer.parseInt(RG.getText().toString()));
        Humano.setEndereco(Endereco.getText().toString());
        reference.child("Pessoa").child(Humano.getNome()).setValue(Humano);
        Toast.makeText(getApplicationContext(),"Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
    }






}