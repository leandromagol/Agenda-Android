package com.example.megatyranosaur.agendadecontatos.Entidades;

import com.example.megatyranosaur.agendadecontatos.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class Usuarios {
 private String id;
 private String email;
 private String senha;

    public Usuarios(){

    }
    public void Salvar(){
        DatabaseReference refetenciaFirebase = ConfiguracaoFirebase.getFirebase();
        refetenciaFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }
    @Exclude

    public void toMap(){
        HashMap<String,Object>hashMapUsuario=new HashMap<>();

        hashMapUsuario.put("id",getId());
        hashMapUsuario.put("email",getEmail());
        hashMapUsuario.put("senha",getSenha());

    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
