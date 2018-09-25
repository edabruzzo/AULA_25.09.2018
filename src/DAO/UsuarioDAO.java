/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Usuario;
import Util.OperacoesBancoDados;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emm
 */
public class UsuarioDAO {

    OperacoesBancoDados fabrica = new OperacoesBancoDados();


    public List<Usuario> consultaUsuarios(Connection conn) throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM tb_usuario;";

        ResultSet rs = fabrica.executaQuerieResultSet(conn, sql);

        return this.extrairListaUsuarios(rs);
                
                
    }

    public Usuario findUsuario(Connection conn, int id_usuario) throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM tb_usuario WHERE id_usuario = "
                + id_usuario + ";";

        ResultSet rs = fabrica.executaQuerieResultSet(conn, sql);
        
        return this.extraiUsuarioResultSet(rs);
       
    }

    
    public Usuario findByLoginSenha(Connection conn, String login, String senha) throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM tb_usuario WHERE LOGIN LIKE '"
                + login + "' AND PASSWORD LIKE '" + senha + "'";

        ResultSet rs = fabrica.executaQuerieResultSet(conn, sql);
        
        return this.extraiUsuarioResultSet(rs);
        
    }


    public Usuario extraiUsuarioResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {

        Usuario usuario = new Usuario();

        while (rs.next()) {

            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setDepartamento(rs.getString("departamento"));
            usuario.setLogin(rs.getString("login"));
            usuario.setPassword(rs.getString("password"));
            usuario.setDataAdmissao(rs.getDate("dataAdmissao"));
            usuario.setAtivo(rs.getBoolean("ATIVO"));
            usuario.setPapelUsuario(rs.getString("papelUsuario"));
            
        }

        rs.close();

        return usuario;
    }



    public List<Usuario> extrairListaUsuarios(ResultSet rs) throws SQLException, ClassNotFoundException {

        List<Usuario> listaUsuarios = new ArrayList();

        while (rs.next()) {

            Usuario usuario = new Usuario();

            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setDepartamento(rs.getString("departamento"));
            usuario.setLogin(rs.getString("login"));
            usuario.setPassword(rs.getString("password"));
            usuario.setDataAdmissao(rs.getDate("dataAdmissao"));
            usuario.setAtivo(rs.getBoolean("ATIVO"));
            usuario.setPapelUsuario(rs.getString("papelUsuario"));

            listaUsuarios.add(usuario);

        }

        rs.close();

        return listaUsuarios;

    }

}
