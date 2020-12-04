package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    DatabaseReference reference;

    EditText Nome;
    EditText Data;
    EditText RG;
    EditText Endereco;
    ListView Ver;

    public List<String> itens = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nome = findViewById(R.id.txtName);
        Data = findViewById(R.id.txtDate);
        RG = findViewById(R.id.txtRG);
        Endereco = findViewById(R.id.txtEnd);
        Ver = findViewById(R.id.Ver);

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

    public void CarregarListView()
    {
        reference = FirebaseDatabase.getInstance().getReference().child("Pessoa");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Pessoa a = dataSnapshot1.getValue(Pessoa.class);
                    itens.add("Nome:" + a.getNome() +", Data: " + a.getData()+", RG: " + a.getRG()+", Endereco: " + a.getEndereco());
                }

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        itens
                );
                Ver.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}