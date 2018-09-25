/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author Emm
 */
public class Usuario {
   
    private Integer idUsuairo;
    private String nome;
    private String departamento;
    private Date dataAdmissao;
    private boolean ativo;
    private String login;
    private String password;
    private String papelUsuario;


    public Integer getIdUsuario() {
        return idUsuairo;
    }

    public void setIdUsuario(Integer matricula) {
        this.idUsuairo = matricula;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }


    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ATIVO) {
        this.ativo = ATIVO;
    }

    public String getPapelUsuario() {
        return papelUsuario;
    }

    public void setPapelUsuario(String papelUsuario) {
        this.papelUsuario = papelUsuario;
    }

        
    
    
}
