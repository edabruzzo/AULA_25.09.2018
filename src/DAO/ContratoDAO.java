/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Contrato;
import Model.Usuario;
import Util.OperacoesBancoDados;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistema_controle_contratos.Sistema_Controle_Contratos;

/**
 *
 * @author Emm
 */
public class ContratoDAO {

    OperacoesBancoDados fabrica = new OperacoesBancoDados();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Sistema_Controle_Contratos principal = new Sistema_Controle_Contratos();



    public String criarContrato(Contrato contrato) throws ClassNotFoundException, SQLException {

        /*REGRA DE NEGÓCIO: 
            FUNCIONÁRIO SÓ PODE CRIAR UM CONTRATO SE ELE TIVER PAPEL DE GESTOR NO SISTEMA
         */
        String mensagem = null;
        
        /*REGRA DE NEGÓCIO: A GESTÃO DE CONTRATOS COM VALOR SUPERIOR A R$ 50.000, 
        NÃO PODE SER FEITA POR FUNCIONÁRIOS QUE NÃO TENHAM CARGO DE GESTÃO
        */
        if(contrato.getFuncionarioGestor().getPapelUsuario().equals("servidor") 
                && contrato.getOrcamentoComprometido() > 50000 ){
            
               mensagem = "REGRA DE NEGÓCIO: \n"
                + "A GESTÃO DE CONTRATOS COM VALOR SUPERIOR A R$ 50.000,00 " 
                +" NÃO PODE SER FEITA POR FUNCIONÁRIOS QUE NÃO TENHAM CARGO DE GESTÃO" ;  
               
                
                return mensagem;

            
        }else 
            
        //REGRA DE NEGÓCIO: A GESTÃO DE CONTRATOS SÓ PODE SER FEITA POR FUNCIONÁRIOS ATIVOS NO SISTEMA
        if(contrato.getFuncionarioGestor().isAtivo() == true){
            
                 
            if (principal.getUsuarioLogado().getPapelUsuario().equals("gestor")) {

            String sql = "INSERT INTO tb_contrato("
                    + "objetoContrato, "
                    + "orcamentoComprometido, "
                    + "ATIVO, "
                    + "empresaContratada, "
                    + "departamentoResponsavel, "
                    + "id_funcionarioGestor)"
                    + "VALUES ('" + contrato.getObjetoContrato()
                    + "', " + contrato.getOrcamentoComprometido()
                    + ", " + contrato.isAtivo()
                    + ", '" + contrato.getEmpresaContratada()
                    + "', '" + contrato.getDepartamentoResponsavel()
                    + "', " + contrato.getFuncionarioGestor().getIdUsuario()
                    + ");";

            fabrica.executaQuerieUpdate(conn, sql);

            mensagem = "Contrato criado com sucesso";

        } else {
            mensagem = "REGRA DE NEGÓCIO: \n" 
                       +"FUNCIONÁRIO SÓ PODE CRIAR UM CONTRATO SE ELE TIVER PAPEL DE GESTOR NO SISTEMA\n"
                       +"Você não tem permissão para criar contratos no sistema";
        }
        }else{
             mensagem = "REGRA DE NEGÓCIO: \n"
                     + "A GESTÃO DE CONTRATOS SÓ PODE SER FEITA POR FUNCIONÁRIOS ATIVOS NO SISTEMA\n"
                     + "\n"
                     + "O funcionário ("+contrato.getFuncionarioGestor().getNome()+")"
                     + "ao qual você está tentando atribuir a gestão do contrato "
                     + "não está ativo no sistema" ;  
        }
    
    
        return mensagem;

    }

    
    public String editarContrato(Connection conn, Contrato contrato) throws ClassNotFoundException, SQLException {
        
        
        String mensagem = null;
        //    REGRA DE NEGÓCIO: FUNCIONÁRIO SÓ PODE EDITAR UM CONTRATO SE ELE FOR GESTOR DO CONTRATO ESPECÍFICO

        
        /*REGRA DE NEGÓCIO: A GESTÃO DE CONTRATOS COM VALOR SUPERIOR A R$ 50.000, 
        NÃO PODE SER FEITA POR FUNCIONÁRIOS QUE NÃO TENHAM CARGO DE GESTÃO
        */
        if(contrato.getFuncionarioGestor().getPapelUsuario().equals("servidor") 
                && contrato.getOrcamentoComprometido() > 50000 ){

            mensagem = "REGRA DE NEGÓCIO: \n"
                     + "A GESTÃO DE CONTRATOS COM VALOR SUPERIOR A R$ 50.000, " 
                     +" NÃO PODE SER FEITA POR FUNCIONÁRIOS QUE NÃO TENHAM CARGO DE GESTÃO" ; 
            
            return mensagem;
        
        //REGRA DE NEGÓCIO: A GESTÃO DE CONTRATOS SÓ PODE SER FEITA POR FUNCIONÁRIOS ATIVOS NO SISTEMA
        }else if(contrato.getFuncionarioGestor().isAtivo() == true){

        String sql1 = "UPDATE tb_contrato "
                + "SET objetoContrato = '" + contrato.getObjetoContrato()
                + "', orcamentoComprometido = " + contrato.getOrcamentoComprometido()
                + ", ATIVO = " + contrato.isAtivo()
                + ", empresaContratada = '" + contrato.getEmpresaContratada()
                + "', departamentoResponsavel = '" + contrato.getDepartamentoResponsavel()
                + "', id_funcionarioGestor = " + contrato.getFuncionarioGestor().getIdUsuario()
                + " WHERE id_contrato = " + contrato.getIdContrato() + ";";
        try{
        fabrica.executaQuerieUpdate(conn, sql1);
        mensagem = "Contrato editado com sucesso";    
        
        }catch(Exception ex){
            
        ex.printStackTrace();
        mensagem = ex.getMessage();
        
        }
        }else{
            
          mensagem = "REGRA DE NEGÓCIO: \n"
                     + "A GESTÃO DE CONTRATOS SÓ PODE SER FEITA POR FUNCIONÁRIOS ATIVOS NO SISTEMA\n"
                  +"\n"   
                  + "O funcionário "
                  + "("+contrato.getFuncionarioGestor().getNome()+") "
                  + "ao qual você está tentando atribuir a gestão"
                  + " do contrato não está ativo no sistema" ;  
        
        }
        
        return mensagem;
        
    }

    public String removerContrato(Connection conn, Contrato contrato) throws SQLException, ClassNotFoundException {

        /*
        
        REGRA DE NEGÓCIO:
        
        APENAS O GESTOR DO CONTRATO PODE EXCLUIR UM CONTRATO
        
         */
        String mensagem = null;

        if (contrato.getFuncionarioGestor().getIdUsuario() == principal.getUsuarioLogado().getIdUsuario()) {

            String sql = "DELETE FROM tb_contrato WHERE id_contrato = "
                    + contrato.getIdContrato() + ";";

            fabrica.executaQuerieUpdate(conn, sql);

            mensagem = "Contrato deletado com sucesso!";

        } else {

            mensagem = "REGRA DE NEGÓCIO:\n" +
"        \n" +
"        APENAS O GESTOR DO CONTRATO, QUE NESTE CONTRATO É O FUNCIONÁRIO"
                    + " "+contrato.getFuncionarioGestor().getNome()+" "
                    + "PODE EXCLUIR UM CONTRATO\n"
         
        + "Você não tem permissão para remover o contrato";

        }

        return mensagem;

    }

    public List<Contrato> consultaContratos(Connection conn) throws ClassNotFoundException, SQLException {

        /*
        
        REGRA DE NEGÓCIO: 
        
        SE FUNCIONÁRIO É GESTOR ELE ENXERGA TODOS OS CONTRATOS,
        INCLUSIVE DE OUTROS DEPARTAMENTOS.
        SE ELE NÃO FOR GESTOR, 
        APENAS ENXERGA CONTRATOS DO SEU DEPARTAMENTO OU CONTRATOS 
        DE OUTROS DEPARTAMENTOS EM QUE ELE É GESTOR
        
         */
        String sql = null;

        if (principal.getUsuarioLogado().getPapelUsuario().equals("gestor")) {
            sql = "SELECT * FROM tb_contrato;";
        } else {
            sql = "SELECT * FROM tb_contrato "
                    + "WHERE departamentoResponsavel = '"
                    + principal.getUsuarioLogado().getDepartamento() + "'"
                    + "OR id_funcionarioGestor = "
                    +principal.getUsuarioLogado().getIdUsuario()+";";
        }

        ResultSet rs = fabrica.executaQuerieResultSet(conn, sql);

        return extraiListaContratos(conn, rs);

    }

    public Contrato findContrato(Connection conn, int id) throws ClassNotFoundException, SQLException {

      
        Contrato contrato = new Contrato();
        String sql = null;
        
            
            sql = "SELECT * FROM tb_contrato WHERE id_contrato = "
                + id + " ;";
       
            
        ResultSet rs = null;

        try{
         rs = fabrica.executaQuerieResultSet(conn, sql);
        }catch(NullPointerException npe){
            
            System.out.println("Usuário sem permissão para visualisar contrato"
                    + "de outro departamento!");
        }
        
        if (rs != null){
        contrato = this.extraiContratoResultSet(conn, rs);    
        }
        return contrato;

    }

   
    public Contrato extraiContratoResultSet(Connection conn, ResultSet rs) throws SQLException, ClassNotFoundException {
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Contrato contrato = new Contrato();
        
        while (rs.next()) {

            contrato.setIdContrato(rs.getInt("id_contrato"));
            contrato.setObjetoContrato(rs.getString("objetoContrato"));
            contrato.setEmpresaContratada(rs.getString("empresaContratada"));
            contrato.setAtivo(rs.getBoolean("ativo"));
            contrato.setDepartamentoResponsavel(rs.getString("departamentoResponsavel"));
            contrato.setOrcamentoComprometido(rs.getDouble("orcamentoComprometido"));
            int idFuncionarioGestor = rs.getInt("id_FuncionarioGestor");
            Usuario funcionarioGestor = usuarioDAO.findUsuario(conn, idFuncionarioGestor);
            contrato.setFuncionarioGestor(funcionarioGestor);
        
        }

        rs.close();

        return contrato;
    }

    private List<Contrato> extraiListaContratos(Connection conn, ResultSet rs) throws SQLException, ClassNotFoundException {

        List<Contrato> listaContratos = new ArrayList();

        while (rs.next()) {

            Contrato contrato = new Contrato();

            contrato.setIdContrato(rs.getInt("id_contrato"));
            contrato.setObjetoContrato(rs.getString("objetoContrato"));
            contrato.setEmpresaContratada(rs.getString("empresaContratada"));
            contrato.setAtivo(rs.getBoolean("ativo"));
            contrato.setDepartamentoResponsavel(rs.getString("departamentoResponsavel"));
            contrato.setOrcamentoComprometido(rs.getDouble("orcamentoComprometido"));
            int idFuncionarioGestor = rs.getInt("id_FuncionarioGestor");
            Usuario funcionarioGestor = usuarioDAO.findUsuario(conn, idFuncionarioGestor);
            contrato.setFuncionarioGestor(funcionarioGestor);
            
            listaContratos.add(contrato);

        }

        rs.close();

        return listaContratos;

    }

}
