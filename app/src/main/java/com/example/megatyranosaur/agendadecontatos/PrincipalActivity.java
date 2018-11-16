package com.example.megatyranosaur.agendadecontatos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.megatyranosaur.agendadecontatos.Adapter.ContatosAdapter;
import com.example.megatyranosaur.agendadecontatos.DAO.ConfiguracaoFirebase;
import com.example.megatyranosaur.agendadecontatos.Entidades.Contatos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {
    private ListView listView;
    DatabaseReference ref;
    private ArrayAdapter<Contatos> adapter;
    private ValueEventListener valueEventListenerContatos;
    FirebaseDatabase database;
    private Button btnContato;
    ArrayList<String> list;
    private ArrayList<Contatos> contatos;
    private AlertDialog alerta;
    Contatos contatosExcluir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        contatos=new ArrayList<>();

        listView = (ListView) findViewById(R.id.ListaContatos);

        adapter = new ContatosAdapter(this,contatos);

        listView.setAdapter(adapter);

        ref= ConfiguracaoFirebase.getFirebase().child("addcontatos");
        valueEventListenerContatos =new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contatos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Contatos contatosNovos = dados.getValue(Contatos.class);

                    contatos.add(contatosNovos);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contatosExcluir = adapter.getItem(position);

                AlertDialog.Builder  builder = new AlertDialog.Builder(PrincipalActivity.this);

                builder.setTitle("Excluir "+contatosExcluir.getNome().toString());

                builder.setMessage("Tem certeza?");

                builder.setPositiveButton("SIm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         ref=ConfiguracaoFirebase.getFirebase().child("addcontatos");

                         ref.child(contatosExcluir.getId().toString()).removeValue();

                        Toast.makeText(PrincipalActivity.this,"Excluir feita",Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PrincipalActivity.this,"Exclisao Cancelada",Toast.LENGTH_LONG).show();
                    }
                });

                alerta=builder.create();

                alerta.show();


            }

        });

        listView.setLongClickable (true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               contatosExcluir = adapter.getItem(position);
               Intent intent = new Intent(PrincipalActivity.this, EditarContato.class);
                Bundle params = new Bundle();
                params.putString("id",contatosExcluir.getId().toString().trim());
                params.putString("Nome",contatosExcluir.getNome().toString().trim());
               params.putString("Email",contatosExcluir.getEmail().toString().trim());
               params.putString("Endereco",contatosExcluir.getEndereco().toString().trim());
               params.putString("Telefone",contatosExcluir.getTelefone().toString().trim());

               intent.putExtras(params);

               startActivity(intent);
               return false;
           }
       });





        };




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_novo:
                Intent intent = new Intent(this, CadastroContatosActivity.class);
                this.startActivity(intent);
                break;
            case R.id.sair:
                intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ref.addValueEventListener(valueEventListenerContatos);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ref.removeEventListener(valueEventListenerContatos);
    }
}