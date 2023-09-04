package com.example.cuidadordeidosos;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cuidadordeidosos.model.Cuidador;

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
    private CalendarView cvDataNasc;
    private Button btSalvar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        this.cvDataNasc = (CalendarView)
                view.findViewById(R.id.cvDataNasc);
        this.btSalvar = (Button) view.findViewById(R.id.btSalvar);
        this.btSalvar.setOnClickListener(this);
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
//Pegando a Data do CalendarView
                SimpleDateFormat sdf = new
                        SimpleDateFormat("dd/MM/yyyy");
                String dataSelecionada = sdf.format(new
                        Date(cvDataNasc.getDate()));
                u.setDataNascimento(dataSelecionada);
//mensagem de sucessot
                Context context = view.getContext();
                CharSequence text = "salvo com sucesso!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText
                        (context, text, duration);
                toast.show();

        }
    }

}