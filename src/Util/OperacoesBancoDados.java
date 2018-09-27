/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emm
 */
public class OperacoesBancoDados {

    private String URL;
    private String USER = "root";
    private String PASSWORD = "root";

    public OperacoesBancoDados() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    


    public void criaInfraestrutura() throws SQLException, ClassNotFoundException {


        ArrayList<String> listaSQLs = new ArrayList();

    
        String sql1 = "CREATE TABLE IF NOT EXISTS tb_usuario "
                + "(id_usuario INTEGER AUTO_INCREMENT NOT NULL, "
                + "nome VARCHAR(255) NOT NULL UNIQUE, "
                + "departamento VARCHAR(255) NOT NULL, "
                + "dataAdmissao DATE NOT NULL, "
                + "ATIVO TINYINT(1) default 1, "
                + "login VARCHAR(10) NOT NULL, "
                + "password  VARCHAR(10) NOT NULL ,"
                + "papelUsuario ENUM('servidor', 'gestor') NOT NULL,  "
                + "PRIMARY KEY (id_usuario));";

        listaSQLs.add(sql1);

    
        String sql2 = "CREATE TABLE IF NOT EXISTS tb_contrato (id_contrato INTEGER AUTO_INCREMENT NOT NULL, "
                + " objetoContrato VARCHAR(255) NOT NULL, orcamentoComprometido DOUBLE NOT NULL, "
                + "ATIVO TINYINT(1) default 0, empresaContratada VARCHAR(255) NOT NULL, "
                + "departamentoResponsavel VARCHAR(255), "
                + "id_funcionarioGestor INTEGER REFERENCES tb_usuario(id_usuario), "
                + "PRIMARY KEY (id_contrato));";
        listaSQLs.add(sql2);
        
        
        String sql3 = "INSERT INTO tb_contrato(objetoContrato, orcamentoComprometido, "
                + "ATIVO, empresaContratada, departamentoResponsavel, id_FuncionarioGestor) "
                + "VALUES('Manutenção parque tecnológico', 100000, true, "
                + "'Organizações Tabajara Ltda.', 'Diretoria de Administração', 1);";

        listaSQLs.add(sql3);

        
        String sql4 = "INSERT INTO tb_contrato(objetoContrato, orcamentoComprometido, "
                + "ATIVO, empresaContratada, departamentoResponsavel, id_FuncionarioGestor) "
                + "VALUES('Limpeza', 50000, true, "
                + "'Organizações Tabajara Ltda.', 'Manutenção', "
                + "1);";
        listaSQLs.add(sql4);

        String sql5 = "INSERT INTO tb_contrato(objetoContrato, orcamentoComprometido, "
                + "ATIVO, empresaContratada, departamentoResponsavel, id_FuncionarioGestor) "
                + "VALUES('Segurança', 200000, true, "
                + "'Organizações Tabajara Ltda.', 'Setor de Gestão de Contratos', "
                + "1);";
        listaSQLs.add(sql5);

        String sql6 = "INSERT INTO tb_contrato(objetoContrato, orcamentoComprometido, "
                + "ATIVO, empresaContratada, departamentoResponsavel, id_FuncionarioGestor) "
                + "VALUES('Manutenção geral', 30000, true, "
                + "'Organizações Tabajara Ltda.', 'Manutenção', "
                + "2);";
        listaSQLs.add(sql6);


        String sql7 = "INSERT INTO tb_usuario (nome, departamento, dataAdmissao, "
                + "ATIVO, login, password, papelUsuario) "
                + "VALUES ('Fulano', 'Setor de Gestão de Contratos', '2018-01-01', "
                + "true, 'fulano', '123', 'gestor');";
        listaSQLs.add(sql7);
        
        
        String sql8 = "INSERT INTO tb_usuario (nome, departamento, dataAdmissao, "
                + "ATIVO, login, password, papelUsuario) "
                + "VALUES ('Sicrano', 'Manutenção', '2018-01-01', "
                + "true, 'sicrano', '123', 'servidor');";
        listaSQLs.add(sql8);

        String sql9 = "INSERT INTO tb_usuario (nome, departamento, dataAdmissao, "
                + "ATIVO, login, password, papelUsuario) "
                + "VALUES ('Beltrano', 'Diretoria', '2018-01-01', "
                + "false, 'beltrano', '123', 'gestor');";
        listaSQLs.add(sql9);

         

        String sql10 = "INSERT INTO tb_contrato"
                + "(objetoContrato, "
                + "orcamentoComprometido, "
                + "ATIVO, "
                + "empresaContratada, "
                + "departamentoResponsavel, "
                + "id_funcionarioGestor)"
                + "VALUES ('Manutenção Predial', 49000, true, 'WCA Ltda.', 'Manutenção', 2);";
        listaSQLs.add(sql10);
        
        
        String sql11 = "INSERT INTO tb_contrato"
                + "(objetoContrato, "
                + "orcamentoComprometido, "
                + "ATIVO, "
                + "empresaContratada, "
                + "departamentoResponsavel, "
                + "id_funcionarioGestor)"
                + "VALUES ('Fornecimento de impressoras multifuncionais', "
                + "67000, true, 'Orion Ltda.', 'Departamento de TI', 1);";
        listaSQLs.add(sql11);
        
        String sql12 = "INSERT INTO tb_contrato"
                + "(objetoContrato, "
                + "orcamentoComprometido, "
                + "ATIVO, "
                + "empresaContratada, "
                + "departamentoResponsavel, "
                + "id_funcionarioGestor)"
                + "VALUES ('Fornecimento de computadores', "
                + "27500, true, 'NETUNO Ltda.', 'Departamnto de TI', 2);";
        listaSQLs.add(sql12);

        Connection conn = criaConexao();
        System.out.println("Criando toda a infraestrutura");
        executaBatchUpdate(conn, listaSQLs);
            
            
        
    }
    

    public void criaBaseDados() {

        //REGISTRANDO O DRIVER NO CONSTRUTOR E NÃO EM CADA MÉTODO
        //Class.forName("com.mysql.jdbc.Driver");
        this.setURL("jdbc:mysql://localhost:3306/");

        Connection conn = null;
        Statement stmt = null;
        //STEP 3: Open a connection
        System.out.println("Conectando ao servidor com a seguinte URL : " + this.URL);

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            stmt.execute("CREATE DATABASE IF NOT EXISTS sistema_controle_contratos");
            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex);
            }
            fecharConexao(conn);
        
        }

    }

    public Connection criaConexao()  {

        this.setURL("jdbc:mysql://localhost:3306/sistema_controle_contratos");

        Connection conexao = null;
        Statement stmt = null;
        //STEP 3: Open a connection
        System.out.println("Conectando ao servidor com a seguinte URL : " + this.URL);

       
        try {
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conexao;

    }

    public void fecharConexao(Connection conn) {

        System.out.println("Fechando a conexão com o banco de dados");
        try {
            if (conn != null) {
                conn.close();

                System.out.println("Conexão com o banco de dados fechada com sucesso");
            }

        } catch (SQLException ex) {
            Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("********ATENÇÃO ! Conexão com o banco de dados não foi fechada !");

        }
    }

    public ResultSet executaQuerieResultSet(Connection conn, String sql) throws ClassNotFoundException, SQLException {

        Statement stmt = conn.createStatement();

        ResultSet rs = null;
        try {
            System.out.println("Executando a seguinte query .....");
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            System.out.println("Executada com sucesso!");

        } catch (SQLException ex) {
            System.out.println("Query não executada!");
            Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;

    }

    public void executaQuerieSemResultado(Connection conn, String sql) throws ClassNotFoundException, SQLException {

       
        Statement stmt = conn.createStatement();

        try {
            System.out.println("Executando a seguinte query .....");
            System.out.println(sql);
            stmt.execute(sql);
            System.out.println("Executada com sucesso!");

        } catch (SQLException ex) {

            System.out.println("Query não executada!");
            Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            stmt.close();
            fecharConexao(conn);

        }

    }

    public void executaQuerieUpdate(Connection conn, String sql) throws ClassNotFoundException, SQLException {

       
        Statement stmt = conn.createStatement();
        
        conn.setAutoCommit(false);

        try {
            System.out.println("Executando commit da seguinte query .....");
            System.out.println(sql);
            stmt.executeUpdate(sql);
            conn.commit();
           System.out.println("Executada com sucesso!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Query não executada! Efetuando rollback");
            conn.rollback();
            Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            stmt.close();
            fecharConexao(conn);

        }

    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void executaBatchUpdate(Connection conn, ArrayList listaSQLs) throws SQLException, ClassNotFoundException {

        conn = criaConexao();
        Statement stmt = conn.createStatement();
        conn.setAutoCommit(false);
        for (int i = 0; i < listaSQLs.size(); i++) {
            System.out.println(listaSQLs.get(i));
            stmt.addBatch((String) listaSQLs.get(i));
        }

        try {
           System.out.println("Executando commit em lote .....");
            stmt.executeBatch();
            conn.commit();
           System.out.println("Executada com sucesso!");

        } catch (SQLException ex) {

           System.out.println("Query não executada! Efetuando rollback");
            conn.rollback();
            Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            stmt.close();
            fecharConexao(conn);
        }
    }

    
    

    public void deletaBanco() throws ClassNotFoundException, SQLException{
        
        this.setURL("jdbc:mysql://localhost:3306/");

        Connection conn = null;
        Statement stmt = null;
        //STEP 3: Open a connection
        System.out.println("Conectando ao servidor com a seguinte URL : " + this.URL);

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            System.out.println("DELETANDO BASE DE DADOS sistema_controle_contratos");
            stmt.execute("DROP DATABASE IF EXISTS sistema_controle_contratos");
            System.out.println("BASE DE DADOS sistema_controle_contratos DELETADA COM SUCESSO!");
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            Logger.getLogger(OperacoesBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            fecharConexao(conn);
            stmt.close();
            
           }
    }

    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
