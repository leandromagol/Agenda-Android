package com.example.megatyranosaur.agendadecontatos;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megatyranosaur.agendadecontatos.DAO.ConfiguracaoFirebase;
import com.example.megatyranosaur.agendadecontatos.Entidades.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    private Button btnCadastrar;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edtEmail = findViewById(R.id.EmailInput);
        edtSenha = findViewById(R.id.SenhaInput);
        btnLogar=findViewById(R.id.LogarBtn);
        btnCadastrar=findViewById(R.id.CadastrarBtn);

   btnLogar.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if (!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("") ){
                usuarios =new Usuarios();
                usuarios.setEmail(edtEmail.getText().toString());
                usuarios.setSenha(edtSenha.getText().toString());

                ValidarLogin();
           }else {
               Toast.makeText(MainActivity.this,"Preencha email e senha",Toast.LENGTH_SHORT).show();
           }
       }
   });
    }

    private void ValidarLogin(){
        autenticacao=ConfiguracaoFirebase.gerFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(),usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()){
                     AbrirTelaPrincipal();
                     Toast.makeText(MainActivity.this,"Logado",Toast.LENGTH_SHORT).show();

                 }
                 else{
                     Toast.makeText(MainActivity.this,"Dados Invalidos" ,Toast.LENGTH_SHORT).show();

                 }
            }
        });
    }
public void AbrirTelaPrincipal(){
    Intent IntentAbrirTelaPrincipal = new Intent(MainActivity.this,PrincipalActivity.class);
    startActivity(IntentAbrirTelaPrincipal);
}
public void CadastroBtn(View view){
        Intent intent = new Intent(this,CadastroActivity.class);
        startActivity(intent);

}

}
