import java.util.InputMismatchException;
import java.util.Scanner;

public class AppERP {
    public static void main(String[] args){
        
        ControladorDeEstoque controlador = new ControladorDeEstoque();
        GerenciamentoCliente gerenciamento = new GerenciamentoCliente();
        SistemCliente sistema = new SistemCliente(gerenciamento);
        ManipularArquivo manipularUsuarios = new ManipularArquivo("usuarios.csv", sistema);
        ManipularArquivo manipuladorProdutos = new ManipularArquivo("produtos.csv", controlador);
        Pedido pedido = new Pedido(sistema.getClientes(), controlador);
        RelatorioDeVendas relatorioVendas = new RelatorioDeVendas();
        ManipularArquivoVendas manipuladorVendas = new ManipularArquivoVendas();

        manipuladorProdutos.carregarProdutos();
        manipularUsuarios.carregarDadosCliente();
        ManipularArquivoVendas.verificarOuCriarArquivo();
        FeedbackCliente.verificarOuCriarArquivo();
        RelatorioDeVendas.verificarOuCriarArquivo();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem vindo ao Sistema ERP do Supermercado Cosmus.");
        while(true){
            try{
            System.out.println("1-Acessar sistema de estoque.\n2-Acessar sistema de vendas.\n" +
            "3-Acessar Sistema de Usuarios.\n4-Salvar e sair.");
            int esclh = scanner.nextInt();
            scanner.nextLine();
            boolean c = true, c1 = true, c2 = true, c3 = true, c4 = true;;
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
                try{
                        System.out.println("Sistema de vendas: ");
                        while(c2){
                            System.out.println("1-Cadastrar novo Usuario\n2-Logar em Usuario ja existente\n3-Voltar");
                            esclh = scanner.nextInt();
                            scanner.nextLine();
                            switch (esclh) {
                                case 1:
                                    sistema.cadastrarUser(scanner);
                                    break;
                                case 2:
                                Cliente clienteLoggado = sistema.loggarUser();
                                    if(clienteLoggado!=null){
                                        System.out.println("Opcoes para vendas: ");
                                        while(c3){
                                            System.out.println("1-Exibir produtos disponiveis\n2-Processar pedido de venda" +
                                            "\n3-Gerar relatorio de vendas\n4-Criar Feedback\n5-Ler Feedbacks\n6-Voltar");
                                            esclh = scanner.nextInt();
                                            scanner.nextLine();
                                            switch (esclh) {
                                                case 1:
                                                    controlador.exibirProdutosDisponiveis();
                                                    break;
                                                case 2:
                                                    pedido.processarPedido(scanner, gerenciamento, clienteLoggado);
                                                    break;
                                                case 3:
                                                    relatorioVendas.adicionarRelatorio(scanner);
                                                    break;
                                                case 4:
                                                    FeedbackCliente.escritorDeTexto();
                                                    break;
                                                case 5:
                                                    FeedbackCliente.lerFeedback();
                                                    break;
                                                case 6:
                                                    c3 = false;
                                                    break;
                                                default:
                                                    System.err.println("Opcao invalida!");
                                                    break;
                                            }
                                        }
                                    
                                    }
                                    break;
                                case 3:
                                    c2 = false;
                                    break;
                                default:
                                    System.err.println("Opcao invalida!");
                                    break;
                            }
                        }
                        }catch(InputMismatchException e){
                            System.out.println("Erro, opcao invalida: InputMismatchException");
                            scanner.nextLine();
                        }
                    break;
                case 3:
                        System.out.println("Sistema de Usuarios:");
                        while(c4){
                            System.out.println("1-Imprimir usuarios\n2-Excluir usuarios\n3-Voltar");
                            esclh = scanner.nextInt();
                            scanner.nextLine();
                            switch (esclh) {
                                case 1:
                                    gerenciamento.listarClientes();
                                    break;
                                case 2:
                                    sistema.excluirCliente();
                                    break;
                                case 3:
                                    c4 = false;
                                    break;
                                default:
                                    System.err.println("Opcao invalida!");
                                    break;
                            }
                        }
                    break;
                case 4:
                    manipuladorProdutos.salvarProdutos();
                    manipularUsuarios.salvarDadoUsuario();
                    manipuladorVendas.escreverCSV(pedido);
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