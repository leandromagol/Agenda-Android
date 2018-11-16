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

public class EditarContato extends AppCompatActivity {
    private Button btnEditar;
    private EditText textNome;
    private EditText textEmail;
    private EditText textEndereco;
    private EditText textTelefone;
    private Contatos contatos;
    private DatabaseReference firebase;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contato);

        textNome = (EditText) findViewById(R.id.editNome);
         textEmail = (EditText) findViewById(R.id.editEmail);
         textEndereco = (EditText) findViewById(R.id.editEndereco);
         textTelefone = (EditText) findViewById(R.id.editTelefone);
         btnEditar = (Button) findViewById(R.id.btnEditar);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params!=null){
                 id = params.getString("id");
                String nome = params.getString("Nome");
                String email = params.getString("Email");
                String endereco = params.getString("Endereco");
                String telefone = params.getString("Telefone");

                textNome.setText(nome);
                textEmail.setText(email);
                textEndereco.setText(endereco);
                textTelefone.setText(telefone);


            }
        }

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditarContato();
            }
        });
    }

    private void EditarContato() {
        Contatos contatos = new Contatos();
        contatos.setId(id);
        contatos.setNome( textNome.getText().toString().trim());
        contatos.setEmail( textEmail.getText().toString().trim());
        contatos.setEndereco(textEndereco.getText().toString()  );
        contatos.setTelefone(textTelefone.getText().toString()  );

        firebase=ConfiguracaoFirebase.getFirebase().child("addcontatos");
        firebase.child(contatos.getId()).setValue(contatos);

        Intent intent = new Intent(this, PrincipalActivity.class);
        this.startActivity(intent);



    }

}
