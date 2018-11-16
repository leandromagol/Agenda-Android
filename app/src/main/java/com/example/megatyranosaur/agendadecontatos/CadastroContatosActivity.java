package com.example.megatyranosaur.agendadecontatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.megatyranosaur.agendadecontatos.DAO.ConfiguracaoFirebase;
import com.example.megatyranosaur.agendadecontatos.Entidades.Contatos;
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

public class CadastroContatosActivity extends AppCompatActivity {

    private Button btnSalvar;
    private EditText textNome;
    private EditText textEmail;
    private EditText textEndereco;
    private EditText textTelefone;
    private Contatos contatos;
    private DatabaseReference firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contatos);

        firebase= ConfiguracaoFirebase.getFirebase();
        textNome =(EditText) findViewById(R.id.editNome);
        textEmail=(EditText) findViewById(R.id.editEmail);
        textEndereco =(EditText) findViewById(R.id.editEndereco);
        textTelefone =(EditText) findViewById(R.id.editTelefone);
        btnSalvar = (Button) findViewById(R.id.btnEditar);



        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarContato();
            }
        });
    }

    private void SalvarContato() {
        Contatos contatos = new Contatos();
        contatos.setId(UUID.randomUUID().toString());
        contatos.setNome( textNome.getText().toString().trim());
        contatos.setEmail( textNome.getText().toString().trim());
        contatos.setEndereco(textEndereco.getText().toString()  );
        contatos.setTelefone(textTelefone.getText().toString()  );

        firebase=ConfiguracaoFirebase.getFirebase().child("addcontatos");
        firebase.child(contatos.getId()).setValue(contatos);
        Intent intent = new Intent(this, PrincipalActivity.class);
        this.startActivity(intent);

    }
}
