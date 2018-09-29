package com.br.imobiliaria.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.br.imobiliaria.DetalhesImovelActivity;
import com.br.imobiliaria.R;
import com.br.imobiliaria.models.Foto;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.utils.TratamentoImagem;

import java.util.List;

public class ListaImovelAdapter extends ArrayAdapter<Imovel> {

    private Context context;
    private List<Imovel> imoveis;
    private TextView nome, bairro, valor, detalhes;
    private ImageView capa;
    LinearLayout linearLayout;
    private Imovel imovel;


    public ListaImovelAdapter(@NonNull Context context, List<Imovel> imoveis) {
        super(context, 0, imoveis);
        this.imoveis = imoveis;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int  position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.imovel = this.imoveis.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_imoveis_adapter, null);
        }
        this.binding(convertView);
        this.carregarLabels(this.imovel);

        this.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imovel = imoveis.get(position);
                Intent intent = new Intent(context, DetalhesImovelActivity.class);
                intent.putExtra("imovel", imovel);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private void carregarLabels(Imovel imovel) {
        this.nome.setText(imovel.getNome());
        this.bairro.setText("Localização: " + imovel.getBairro());
        this.valor.setText("R$ " + String.valueOf(imovel.getPreco()));
        this.capa.setImageBitmap(TratamentoImagem.convertArrayBytesToBitMapImage(imovel.obterFotoPrincipal().getArquivo()));
    }


    private void binding(View view) {
        this.nome = view.findViewById(R.id.listAdNome);
        this.bairro = view.findViewById(R.id.listAdBairro);
        this.valor = view.findViewById(R.id.listAdValor);
        this.capa = view.findViewById(R.id.listAdImage);
        this.detalhes = view.findViewById(R.id.listAdDetalhes);
        this.linearLayout = view.findViewById(R.id.linearLayout_adapter);
    }


}
