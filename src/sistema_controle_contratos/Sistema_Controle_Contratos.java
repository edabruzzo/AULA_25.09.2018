/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_controle_contratos;

import DAO.ContratoDAO;
import DAO.UsuarioDAO;
import Model.Contrato;
import Model.Usuario;
import Util.OperacoesBancoDados;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Emm lucas_mart27@hotmail.com
 */
public class Sistema_Controle_Contratos {

    public static Usuario usuarioLogado;

    public static boolean validarLogin(String login, String senha) {

        OperacoesBancoDados fabrica = new OperacoesBancoDados();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Connection conexao = fabrica.criaConexao();
        boolean loginComSucesso = false;
        try {

            Usuario usuarioEncontrado = usuarioDAO.findByLoginSenha(conexao, login, senha);

            if (usuarioEncontrado == null) {
                System.out.println("Erro no processo de login!!!");
                return false;

            } else {
                loginComSucesso = true;
                usuarioLogado = usuarioEncontrado;

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Sistema_Controle_Contratos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro no processo de login!!!");
            return false;

        } catch (SQLException ex) {
            System.out.println("Erro no processo de login!!!");
            Logger.getLogger(Sistema_Controle_Contratos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return loginComSucesso;

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        mostrarMenu();

// TODO code application logic here
    }

    public static boolean logarNoSistema() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Digite seu login: ");
        String login = scan.next();
        Scanner scan2 = new Scanner(System.in);
        System.out.println("Digite sua senha: ");
        String senha = scan2.next();

        boolean logadoComSucesso = false;
        logadoComSucesso = validarLogin(login, senha);

        return logadoComSucesso;

    }

    private static void mostrarMenu() throws SQLException, ClassNotFoundException {

        int opcaoMenuPrincipal;
        Scanner leitor = new Scanner(System.in);
        boolean logadoComSucesso = false;
        OperacoesBancoDados fabrica = new OperacoesBancoDados();

        System.out.println("===================== MENU PRINCIPAL =======================\n");
        System.out.println("1 - Cadastrar\n");
        System.out.println("2 - Mostrar Todos Cadastros\n");
        System.out.println("3 - Pesquisar Cadastro Específico\n");
        System.out.println("4 - Excluir Cadastro Específico\n");
        System.out.println("5 - Alterar Cadastro Específico\n");
        System.out.println("6 - Sair\n");
        System.out.println("7 - Criar banco e infraestrutura, caso já não tenham sido criados\\\n");
        System.out.println("8 - Deletar banco e infraestrutura, caso já não tenham sido criados\\\n");
        System.out.println("===============================================================\n");

        System.out.println("\nDigite sua opção:\n");
        opcaoMenuPrincipal = leitor.nextInt();

        if (opcaoMenuPrincipal == 8) {

            fabrica.deletaBanco();
            mostrarMenu();
            

        } else if (opcaoMenuPrincipal == 7) {

            fabrica.criaBaseDados();
            fabrica.criaInfraestrutura();
            mostrarMenu();

        } else if (opcaoMenuPrincipal == 6) {
            
            System.out.println("========= O PROGRAMA FOI ENCERRADO =========");
            leitor.close();
            System.exit(0);

        } else {

            logadoComSucesso = logarNoSistema();
            System.out.println("Necessário fazer logon no sistema!\n");

            if (logadoComSucesso) {

                ContratoDAO contratoDAO = new ContratoDAO();

                switch (opcaoMenuPrincipal) {

                    case 1:
                        System.out.println("========= CADASTRAR ============");
                        Contrato novoContrato = new Contrato();
                        novoContrato.setAtivo(true);
                        novoContrato.setDepartamentoResponsavel("Operações Portuárias");
                        novoContrato.setEmpresaContratada("Organizações Tabajara");
                        novoContrato.setFuncionarioGestor(usuarioLogado);
                        novoContrato.setObjetoContrato("operações de exportação");
                        novoContrato.setOrcamentoComprometido(500000.00);

                        String mensagem1 = contratoDAO.criarContrato(novoContrato);
                        System.out.println(mensagem1);

                        Contrato novoContrato2 = new Contrato();
                        novoContrato2.setAtivo(true);
                        String departamento = JOptionPane.showInputDialog("Depártamento responsável: ");
                        novoContrato2.setDepartamentoResponsavel(departamento);
                        String empresa = JOptionPane.showInputDialog("Empresa contratada: ");
                        novoContrato2.setEmpresaContratada(empresa);
                        novoContrato2.setFuncionarioGestor(usuarioLogado);
                        String objeto = JOptionPane.showInputDialog("Qual o objeto do contrato (produto ou serviço contratado)?");
                        novoContrato2.setObjetoContrato(objeto);
                        String orcamento = JOptionPane.showInputDialog("Orçamento comprometido: ");
                        double orcamentoComprometido = Double.parseDouble(orcamento);
                        novoContrato2.setOrcamentoComprometido(orcamentoComprometido);

                        String mensagem2 = contratoDAO.criarContrato(novoContrato2);
                        System.out.println(mensagem2);
                        mostrarMenu();
                        break;
                    case 2:
                        System.out.println("========= MOSTRAR TODOS ============");
                        List<Contrato> listaContratos = contratoDAO.consultaContratos();
                        for (Contrato contrato : listaContratos) {
                            System.out.println("========= CONTRATO ============");
                            System.out.println("Id do contrato: " + contrato.getIdContrato());
                            System.out.println("Departamento responsável: " + contrato.getDepartamentoResponsavel());
                            System.out.println("Empresa contratada :" + contrato.getEmpresaContratada());
                            System.out.println("Objeto do contrato (Produto ou serviço contratado) :" + contrato.getObjetoContrato());
                            System.out.println("Empresa contratada :" + contrato.getOrcamentoComprometido());
                            System.out.println("Funcionario gestor do contrato :" + contrato.getFuncionarioGestor().getNome());
                            System.out.println("Departamento do funcionário responsável :" + contrato.getFuncionarioGestor().getDepartamento());
                            System.out.println("Papel do funcionário responsável :" + contrato.getFuncionarioGestor().getPapelUsuario());
                            System.out.println("Data de admissão do funcionário responsável :" + contrato.getFuncionarioGestor().getDataAdmissao());
                            System.out.println("========= ======================================== ============");

                        }
                        mostrarMenu();
                        break;
                    case 3:
                        System.out.println("========= PESQUISAR ============");
                        //https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#input
                        String id = JOptionPane.showInputDialog("Informe o id do contrato a ser pesquisado:\n");
                        int id_contrato = Integer.parseInt(id);
                        Contrato contrato = contratoDAO.findContrato(id_contrato);
                        if(contrato.getIdContrato() == null) {
                            System.out.println("CONTRATO NÃO ENCONTRADO !!!!");
                            mostrarMenu();
                        }
                        System.out.println("========= CONTRATO ENCONTRADO ============");
                        System.out.println("Id do contrato: " + contrato.getIdContrato());
                        System.out.println("Departamento responsável: " + contrato.getDepartamentoResponsavel());
                        System.out.println("Empresa contratada :" + contrato.getEmpresaContratada());
                        System.out.println("Objeto do contrato (Produto ou serviço contratado) :" + contrato.getObjetoContrato());
                        System.out.println("Empresa contratada :" + contrato.getOrcamentoComprometido());
                        System.out.println("Funcionario gestor do contrato :" + contrato.getFuncionarioGestor().getNome());
                        System.out.println("Departamento do funcionário responsável :" + contrato.getFuncionarioGestor().getDepartamento());
                        System.out.println("Papel do funcionário responsável :" + contrato.getFuncionarioGestor().getPapelUsuario());
                        System.out.println("Data de admissão do funcionário responsável :" + contrato.getFuncionarioGestor().getDataAdmissao());
                        System.out.println("========= ======================================== ============");
                        System.out.println("=================================");
                        mostrarMenu();
                        break;
                    case 4:
                        System.out.println("========= EXCLUIR ============");
                        String idContrato = JOptionPane.showInputDialog("Informe o id do contrato a ser removido:\n");
                        int id_contrato_remocao = Integer.parseInt(idContrato);
                        Contrato contratoRemocao = contratoDAO.findContrato(id_contrato_remocao);
                        if(contratoRemocao.getIdContrato() == null) {
                            System.out.println("CONTRATO NÃO ENCONTRADO !!!!");
                            mostrarMenu();
                        }

                        /*
                        NECESSÁRIO PASSAR O CONTRATO INTEIRO COMO PARÂMETRO PARA 
                        VALIDAÇÃO DE REGRAS DE NEGÓCIO                        
                         */
                        System.out.println("Tem certeza de que pretende remover o contrato? S/N");
                        String opcao = leitor.next();
                        if (opcao.equalsIgnoreCase("N")) {
                            mostrarMenu();
                        } else {
                            String mensagem = contratoDAO.removerContrato(contratoRemocao);
                            System.out.println(mensagem);
                        }
                        mostrarMenu();
                        break;
                    case 5:
                        System.out.println("========= ALTERAR ============");
                        String id5 = JOptionPane.showInputDialog("Informe o id do contrato a ser pesquisado:\n");
                        int id_contrato5 = Integer.parseInt(id5);
                        Contrato contrato5 = contratoDAO.findContrato(id_contrato5);
                        if(contrato5.getIdContrato() == null) {
                            System.out.println("CONTRATO NÃO ENCONTRADO !!!!");
                            mostrarMenu();
                        }
                        System.out.println("========= CONTRATO ENCONTRADO ============");
                        System.out.println("Id do contrato: " + contrato5.getIdContrato());
                        System.out.println("Departamento responsável: " + contrato5.getDepartamentoResponsavel());
                        System.out.println("Empresa contratada :" + contrato5.getEmpresaContratada());
                        System.out.println("Objeto do contrato (Produto ou serviço contratado) :" + contrato5.getObjetoContrato());
                        System.out.println("Empresa contratada :" + contrato5.getOrcamentoComprometido());
                        System.out.println("Funcionario gestor do contrato :" + contrato5.getFuncionarioGestor().getNome());
                        System.out.println("Departamento do funcionário responsável :" + contrato5.getFuncionarioGestor().getDepartamento());
                        System.out.println("Papel do funcionário responsável :" + contrato5.getFuncionarioGestor().getPapelUsuario());
                        System.out.println("Data de admissão do funcionário responsável :" + contrato5.getFuncionarioGestor().getDataAdmissao());
                        System.out.println("========= ======================================== ============");
                        System.out.println("=================================");

                        String ativo5 = JOptionPane.showInputDialog("Contrato ativo? S/N: ");
                        if (ativo5.equalsIgnoreCase("S")) {
                            contrato5.setAtivo(true);
                        } else {
                            contrato5.setAtivo(false);
                        }

                        String departamento5 = JOptionPane.showInputDialog("Depártamento responsável: ");
                        contrato5.setDepartamentoResponsavel(departamento5);
                        String empresa5 = JOptionPane.showInputDialog("Empresa contratada: ");
                        contrato5.setEmpresaContratada(empresa5);
                        contrato5.setFuncionarioGestor(usuarioLogado);
                        String objeto5 = JOptionPane.showInputDialog("Qual o objeto do contrato (produto ou serviço contratado)?");
                        contrato5.setObjetoContrato(objeto5);
                        String orcamento5 = JOptionPane.showInputDialog("Orçamento comprometido: ");
                        double orcamentoComprometido5 = Double.parseDouble(orcamento5);
                        contrato5.setOrcamentoComprometido(orcamentoComprometido5);

                        String mensagem5 = contratoDAO.editarContrato(contrato5);
                        System.out.println(mensagem5);
                        mostrarMenu();
                        break;
                    
                }

            }
        }
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        Sistema_Controle_Contratos.usuarioLogado = usuarioLogado;
    }

}
