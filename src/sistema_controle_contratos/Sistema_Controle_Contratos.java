/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_controle_contratos;

import Model.Usuario;

/**
 *
 * @author Emm
 */
public class Sistema_Controle_Contratos {

    
    public static Usuario usuarioLogado;

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 
    logarNoSistema();


// TODO code application logic here
    }

    private static void logarNoSistema() {
        
    String login = null;
    String senha = null;
        
    validarLogin(login, senha);
    
    
    }

    private static void validarLogin(String login, String senha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

   public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        Sistema_Controle_Contratos.usuarioLogado = usuarioLogado;
    }
 






}
