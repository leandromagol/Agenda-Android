package com.example.megatyranosaur.agendadecontatos;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.megatyranosaur.agendadecontatos.DAO.ConfiguracaoFirebase;
import com.example.megatyranosaur.agendadecontatos.Entidades.Usuarios;
import com.example.megatyranosaur.agendadecontatos.Helpers.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnCadastro;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtEmail = findViewById(R.id.EmailInput);
        edtSenha = findViewById(R.id.SenhaInput);
        btnCadastro = findViewById(R.id.CadastrarBtn);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")){
                usuarios = new Usuarios();
                usuarios.setEmail(edtEmail.getText().toString());
                usuarios.setSenha(edtSenha.getText().toString());
                CadastrarUsuario();
                }else {
                    Toast.makeText(CadastroActivity.this,"Preencha email e senha",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void CadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.gerFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,"Usuario cadastrado",Toast.LENGTH_SHORT).show();

                    String IdentificadorUsuario=Base64Custom.codificarBase64(usuarios.getEmail());

                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(IdentificadorUsuario);
                    usuarios.Salvar();
                    abrirLoginUsuario();


                }else {
                    String erroExcecao="";
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao=("Digite uma senha mais forte com 8 caracteres letras e numeros");
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao=("Email invalido");


                    }catch (Exception e){
                        erroExcecao =("Ocoreu um erro");
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this,erroExcecao,Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void abrirLoginUsuario() {
    }


}
