package com.example.megatyranosaur.agendadecontatos.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.megatyranosaur.agendadecontatos.Entidades.Contatos;
import com.example.megatyranosaur.agendadecontatos.PrincipalActivity;
import com.example.megatyranosaur.agendadecontatos.R;

import java.util.List;

public class ContatosAdapter extends ArrayAdapter<Contatos> {
    private final List<Contatos> contatos;
    private Context context;

    public ContatosAdapter(Context context, List<Contatos> objects) {
        super(context,0,objects);
        this.context=context;
        this.contatos =objects;
    }



    @Override
    public View getView(int position,  View convertView,ViewGroup parent) {

        View view =null;

        if (contatos != null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_contatos,parent,false);
            TextView Nome =(TextView) view.findViewById(R.id.Nome);
            TextView Email =(TextView) view.findViewById(R.id.Email);
            TextView Endereco =(TextView) view.findViewById(R.id.Endereco);
            TextView Telefone =(TextView) view.findViewById(R.id.Telefone);

            Contatos contatos2 = contatos.get(position);

            Nome.setText(contatos2.getNome());
            Email.setText(contatos2.getEmail());
            Endereco.setText(contatos2.getEndereco());
            Telefone.setText(contatos2.getTelefone());
        }
        return view;
    }
}



