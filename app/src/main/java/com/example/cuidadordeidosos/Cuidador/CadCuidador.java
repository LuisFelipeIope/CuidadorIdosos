package com.example.cuidadordeidosos.Cuidador;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuidadordeidosos.R;
import com.google.android.material.snackbar.Snackbar;

import com.example.cuidadordeidosos.model.Cuidador;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CadCuidador extends Fragment implements View.OnClickListener {
    private View view;
    private EditText etNome;
    private EditText etMail;
    private EditText etCelular;
    private CheckBox cbCurso;
    private Spinner spEscolaridade;
    private EditText etOutrosCursos;
    private Button btSalvar;

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectReq;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_cad_cuidador, container, false);

        this.etNome = (EditText) view.findViewById(R.id.editNome);
        this.etMail = (EditText) view.findViewById(R.id.editEmail);
        this.etCelular = (EditText) view.findViewById(R.id.editCelular);
//spinner
        this.spEscolaridade = (Spinner) view.findViewById(R.id.Escolaridade);
//checkBox
        this.cbCurso = (CheckBox) view.findViewById(R.id.checkBoxCursoDeCuidador);
//calendarView
        this.etOutrosCursos = (EditText) view.findViewById(R.id.editCursos);
        this.btSalvar = (Button) view.findViewById(R.id.btSalvar);
        this.btSalvar.setOnClickListener(this);
        //instanciando a fila de requests - caso o objeto seja o view
        this.requestQueue = Volley.newRequestQueue(view.getContext());
//inicializando a fila de requests do SO
        this.requestQueue.start();

        return view;
    }

    @Override
    public void onClick(View view){
       if (view.getId() == R.id.btSalvar) {
//verificando se é o botão salvar

//instanciando objeto de negócio
                Cuidador u = new Cuidador();
//populando objeto com dados da tela
                u.setNome(this.etNome.getText().toString());
                u.setEmail(this.etMail.getText().toString());
                u.setCelular(this.etCelular.getText().toString());
//status do checkBox
                u.setCursoDeCuidador(this.cbCurso.isChecked());
//indice do item selecionado do Spinner
                u.setEscolaridade(this.spEscolaridade.getSelectedItemPosition());

                u.setCelular(this.etOutrosCursos.getText().toString());

//mensagem de sucessot

           jsonObjectReq  = new JsonObjectRequest(
                       Request.Method.POST,
                       "http://10.0.2.2/cuidador/cadCuidador.php",
                       u.toJsonObject(), this::onResponse, this::onErrorResponse);

                requestQueue.add(jsonObjectReq);

                Log.d("Dados: ",u.toJsonObject().toString());

        }
    }


        public void onErrorResponse(VolleyError error) {
            Log.d("Erro:", error.toString());

            Snackbar mensagem = Snackbar.make(view,
                    "Ops! Houve um problema ao realizar o cadastro: " +
                            error.toString(),Snackbar.LENGTH_LONG);
            mensagem.show();


        }

    public void onResponse(Object response) {
        try {
            //instanciando objeto para manejar o JSON que recebemos
            JSONObject jason = new JSONObject(response.toString());
            //context e text são para a mensagem na tela o Toast
            Context context = view.getContext();
            //pegando mensagem que veio do json
            CharSequence mensagem = jason.getString("message");
            //duração da mensagem na tela
            int duration = Toast.LENGTH_SHORT;
            //verificando se salvou sem erro para limpar campos da tela
            if (jason.getBoolean("success")){
                //campos texto
                this.etNome.setText("");
                this.etMail.setText("");
                this.etCelular.setText("");
                this.etOutrosCursos.setText("");
                //selecionando primiro item dos spinners
                this.spEscolaridade.setSelection(0);

            }
            //mostrando a mensagem que veio do JSON
            Toast toast = Toast.makeText (context, mensagem, duration);
            toast.show();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //mensagem de sucesso


    }

}