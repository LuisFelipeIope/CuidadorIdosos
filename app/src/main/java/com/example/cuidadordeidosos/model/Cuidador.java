package com.example.cuidadordeidosos.model;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cuidador {
        //atributos
        private int id;
        private String Nome;
        private String Email;
        private String Celular;
        private int Escolaridade;
        private boolean CursoDeCuidador;
        private String OutrosCursos;
        private String dataNascimento;

        public Cuidador() {
                this.setId(0);
                this.setNome("");
                this.setEmail("");
                this.setCelular("");
                this.setEscolaridade(0);
                this.setCursoDeCuidador(false);
                this.setOutrosCursos("");
                this.setDataNascimento("1970-01-01");
        }

        public Cuidador(JSONObject jp) {
                try {
                        Integer numero = (int) jp.get("id");
                        this.setId(numero);
                        this.setNome((String) jp.get("nome"));
                        this.setEmail((String) jp.get("email"));
                        this.setCelular((String) jp.get("celular"));
                        this.setEscolaridade((Integer) jp.get("escolaridade"));
                        boolean bool =
                                Boolean.getBoolean(jp.get("Curso").toString());
                        this.setCursoDeCuidador(bool);
                        this.setOutrosCursos((String) jp.get("OutrosCursos"));
                        this.setDataNascimento((String) jp.get("nascimento"));
                } catch (JSONException e) {
                        e.printStackTrace();
                }
        }

        public JSONObject toJsonObject() {
                JSONObject json = new JSONObject();
                try {
                        json.put("id", this.id);
                        json.put("nome", this.Nome);
                        json.put("email", this.Email);
                        json.put("celular", this.Celular);
                        json.put("Curso", this.CursoDeCuidador);
                        json.put("OutrosCursos", this.OutrosCursos);
                        json.put("nascimento", this.dataNascimento);
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                return json;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getNome() {
                return Nome;
        }

        public void setNome(String nome) {
                Nome = nome;
        }

        public String getEmail() {
                return Email;
        }

        public void setEmail(String email) {
                Email = email;
        }

        public String getCelular() {
                return Celular;
        }

        public void setCelular(String celular) {
                Celular = celular;
        }

        public int getEscolaridade() {
                return Escolaridade;
        }

        public void setEscolaridade(int escolaridade) {
                Escolaridade = escolaridade;
        }

        public boolean isCursoDeCuidador() {
                return CursoDeCuidador;
        }

        public void setCursoDeCuidador(boolean cursoDeCuidador) {
                CursoDeCuidador = cursoDeCuidador;
        }

        public String getOutrosCursos() {
                return OutrosCursos;
        }

        public void setOutrosCursos(String outrosCursos) {
                OutrosCursos = outrosCursos;
        }

        public String getDataNascimento() {
                return dataNascimento;
        }

        public void setDataNascimento(String dataNascimento) {
                SimpleDateFormat formato =
                        new SimpleDateFormat("yyyy-MM-dd");
                try {
                        Date data = (Date) formato.parse(dataNascimento);
                        this.dataNascimento = dataNascimento;
                } catch (ParseException e) {
                        this.dataNascimento ="1900-01-01";
                }
        }
}