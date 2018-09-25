/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Emm
 */
public class Contrato {
    
    private Integer idContrato;
    private String objetoContrato;
    private Double orcamentoComprometido;
    private boolean ativo;
    private String empresaContratada;
    private String departamentoResponsavel;
    private Usuario funcionarioGestor;


    public Contrato() {
    }
    
    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer codigo) {
        this.idContrato = codigo;
    }

    public String getObjetoContrato() {
        return objetoContrato;
    }

    public void setObjetoContrato(String objeto) {
        this.objetoContrato = objeto;
    }

    public Double getOrcamentoComprometido() {
        return orcamentoComprometido;
    }

    public void setOrcamentoComprometido(Double orcamentoComprometido) {
        this.orcamentoComprometido = orcamentoComprometido;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmpresaContratada() {
        return empresaContratada;
    }

    public void setEmpresaContratada(String contratado) {
        this.empresaContratada = contratado;
    }

    public String getDepartamentoResponsavel() {
        return departamentoResponsavel;
    }

    public void setDepartamentoResponsavel(String departamentoResponsavel) {
        this.departamentoResponsavel = departamentoResponsavel;
    }

    public Usuario getFuncionarioGestor() {
        return funcionarioGestor;
    }

    public void setFuncionarioGestor(Usuario funcionarioGestor) {
        this.funcionarioGestor = funcionarioGestor;
    }


    
}
