import java.util.InputMismatchException;
import java.util.Scanner;

public class AppERP {
    public static void main(String[] args){
        ControladorDeEstoque controlador = new ControladorDeEstoque();
        SistemCliente sistema = new SistemCliente();
        ManipularArquivo manipuladorProdutos = new ManipularArquivo("produtos.csv", controlador);
        ManipularArquivo manipularUsuarios = new ManipularArquivo("usuarios.csv", sistema);
        manipuladorProdutos.carregarProdutos();
        manipularUsuarios.carregarDadosCliente();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem vindo ao Sistema ERP do Supermercado Cosmus.");
        while(true){
            try{
            System.out.println("1-Acessar sistema de estoque.\n2-Acessar sistema de vendas.\n" +
            "3-Acessar Sistema de Usuarios.\n4-Salvar e sair.");
            int esclh = scanner.nextInt();
            scanner.nextLine();
            boolean c = true, c1 = true;;
            switch (esclh) {
                case 1:
                System.out.println("Sistema de estoque:");
                    while(c){
                        System.out.println("1-Adicionar produto\n2-Excluir produto\n3-Imprimir estoque" + 
                        "\n4-Atualizar produtos\n5-Voltar");
                        esclh = scanner.nextInt();
                        switch (esclh) {
                            case 1:
                                controlador.adicionarProduto();
                                break;
                            case 2:
                                controlador.excluirProduto();
                                break;
                            case 3:
                                controlador.imprimirProdutos();
                                break;
                            case 4:
                            if(controlador.getProdutos().isEmpty()){
                                System.out.println("Nao ha produtos para atualizar!");
                                continue;    
                            }
                            System.out.println("Atualizar produtos:");
                            while(c1){
                                System.out.println("1-Atualizar nome\n2-Atualizar quantidade\n3-Atualizar preco\n4-Atualizar limite"+
                                "\n5-Atualizar descricao\n6-Atualizar data de validade(somente para pereciveis)\n7-Voltar");
                                esclh = scanner.nextInt();
                                scanner.nextLine();
                                switch (esclh) {
                                    case 1:
                                        controlador.atualizarNome();
                                        break;
                                    case 2:
                                        controlador.atualizarQuantidade();
                                        break;
                                    case 3:
                                        controlador.atualizarPreco();
                                        break;
                                    case 4:
                                        controlador.atualizarLimite();
                                        break;
                                    case 5:
                                        controlador.atualizarDescricao();
                                        break;
                                    case 6:
                                        controlador.atualizarDataDeValidade();
                                        break;
                                    case 7:
                                        c1 = false;
                                        break;
                                    default:
                                        System.err.println("Opcao invalida!");
                                        break;
                                }
                            }
                                break;
                            case 5:
                                c = false;
                                break;
                            default:
                                System.err.println("Opcao invalida!");
                                break;
                        }
                    }
                    break;
                case 2:

                    break;
                case 3:
                    break;
                case 4:
                    manipuladorProdutos.salvarProdutos();
                    manipularUsuarios.salvarDadoUsuario();
                    scanner.close();
                    System.exit(0);
                default:
                    System.err.println("Opcao invalida!");
                    break;
            }
            }catch(InputMismatchException e){
                System.out.println("Erro, opcao invalida: InputMismatchException");
                scanner.nextLine();
            }

        }
    }
}

// while(true){
//     try{
//     System.out.println("1-Cadastrar novo Usuario\n2-Logar em Usuario ja existente\n3-Salvar e sair");
//     int esclh = scanner.nextInt(), subEsclh;
//     scanner.nextLine();
//     switch (esclh) {
//         case 1:
//             sistema.cadastrarUser(scanner);
//             break;
//         case 2:
//             if(sistema.loggarUser()){
//                 System.out.println("");
//                 subEsclh = scanner.nextInt();
//                 scanner.nextLine();
//                 switch (subEsclh) {
//                     case 1:
//                         break;
//                     case 2:
//                         break;
//                     default:
//                         break;
//             }
//             }
//             break;
//         case 3:
//             manipuladorProdutos.salvarProdutos();
//             manipularUsuarios.salvarDadoUsuario();
//         default:
//             System.err.println("Opcao invalida!");
//             break;
//     }
//     }catch(InputMismatchException e){
//         System.out.println("Erro, opcao invalida: InputMismatchException");
//         scanner.nextLine();
//     }

// }