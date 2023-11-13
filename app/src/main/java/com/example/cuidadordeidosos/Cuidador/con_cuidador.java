package com.example.cuidadordeidosos.Cuidador;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuidadordeidosos.R;
import com.example.cuidadordeidosos.model.Cuidador;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class con_cuidador extends Fragment
        implements Response.ErrorListener, Response.Listener {
    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private ArrayList<Cuidador> cuidadores;
    //volley
    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayReq;
    private View view;


    public con_cuidador() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_con_cuidador, container, false);

        //
        //instanciando a fila de requests - caso o objeto seja o view
        this.requestQueue = Volley.newRequestQueue(view.getContext());
        //inicializando a fila de requests do SO
        this.requestQueue.start();

        //array parametro de envio para o serviço
        JSONArray jsonArray = new JSONArray();
        //objeto com informações de filtro da consulta

        Cuidador cuidador = new Cuidador();
        cuidador.setEscolaridade(1);
        //incluindo objeto no array de envio
        jsonArray.put(cuidador.toJsonObject());
        //requisição para o Rest Server
        jsonArrayReq = new JsonArrayRequest(Request.Method.POST,
                "http://10.0.2.2/cuidador/conCuidador.php",
                jsonArray, this, this);
        //mando executar a requisção na fila do sistema
        requestQueue.add(jsonArrayReq);

        return this.view;

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //mostrar mensagem que veio do servidor
        Snackbar mensagem = Snackbar.make(view,
                "Ops! Houve um problema ao realizar a consulta: " +
                        error.toString(), Snackbar.LENGTH_LONG);
        mensagem.show();
    }

    @Override
    public void onResponse(Object response) {
        try {
            //array Json para receber a resposta do webservice
            JSONArray jsonArray = (JSONArray) response;
            //se a consulta não veio vazia passar para array list
            if (jsonArray != null) {
                //objeto java
                Cuidador cuidador = null;
                //array list para receber a resposta
                this.cuidadores = new ArrayList<Cuidador>();
                //preenchendo ArrayList com JSONArray recebido
                for (int i = 0, size = jsonArray.length(); i < size; i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    cuidador = new Cuidador(jo);
                    this.cuidadores.add(cuidador);
                }
                // Set the adapter
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }
                    recyclerView.setAdapter(new ConCuidadorRecyclerViewAdapter(cuidadores));
                }

            }else {
                Snackbar mensagem = Snackbar.make(view,
                        "A consulta não retornou nenhum registro!",
                        Snackbar.LENGTH_LONG);
                mensagem.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}