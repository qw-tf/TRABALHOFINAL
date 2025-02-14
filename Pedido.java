import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Pedido {

    private List<Cliente> clientes; // Lista de clientes associados ao pedido
    private List<ItemPedido> itensPedido; // Lista dos itens do pedido
    private double total;
    private ControladorDeEstoque controlador;

    // Construtor que recebe uma lista de clientes
    public Pedido(List<Cliente> clientes, ControladorDeEstoque controladorEstoque) {
        this.clientes = clientes;
        this.itensPedido = new ArrayList<>();
        this.total = 0.0;
        this.controlador = controladorEstoque;
    }

    // Método para adicionar produto ao pedido
    public void adicionarProduto(Produto produto, int quantidade) {
        try {
            if (quantidade <= 0) {
                System.out.println("Quantidade inválida!");
                return;
            }

            // Verifica se há estoque disponível
            if (controlador.verificarDisponibilidade(produto, quantidade)) {
                itensPedido.add(new ItemPedido(produto, quantidade));
                total += produto.getPreco() * quantidade; // Atualiza o total do pedido
                System.out.println("Produto " + produto.getNome() + " adicionado ao pedido. Quantidade: " + quantidade);
            } else {
                System.out.println("Estoque insuficiente para " + produto.getNome());
            }
        } catch (Exception e) {
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    // Método para finalizar o pedido
    public void finalizarPedido() {
        try {
            // Remove os produtos do estoque
            for (ItemPedido item : itensPedido) {
                controlador.removerProduto(item.getProduto().getNome(), item.getQuantidade());
            }
            System.out.println("Pedido finalizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao finalizar pedido: " + e.getMessage());
        }
    }

    // Método para exibir os detalhes do pedido
    public void exibirPedido(Cliente cliente) {
        System.out.println("Pedido do cliente:");
        System.out.println("- " + cliente.getNome());
        System.out.println("Produtos no pedido:");
        for (ItemPedido item : itensPedido) {
            System.out.println("- " + item.getProduto().getNome() + " | Quantidade: " + item.getQuantidade() + " | R$ "
                    + item.getProduto().getPreco());
        }

        // Aplica desconto se algum cliente for VIP
        double descontoTotal = 0.0;
            if (cliente instanceof ClienteVip) {
                double desconto = ((ClienteVip) cliente).calcularDesconto(total);
                descontoTotal += desconto;
            }

        if (descontoTotal > 0) {
            System.out.println("Desconto VIP (5%): R$ " + descontoTotal);
            System.out.println("Total com desconto: R$ " + (total - descontoTotal));
        }

        System.out.println("Total do pedido: R$ " + total);
    }

    // Método para processar o pedido
    public void processarPedido(Scanner scanner, GerenciamentoCliente gerenciamento, Cliente cliente) {

        if(controlador.getProdutos().isEmpty()){
            System.out.println("Nao ha produtos para fazer um pedido!");
            return;
        }
        System.out.println("\n--- Processamento de Pedido ---");

        // Loop para adicionar produtos ao pedido
        boolean pedidoEmAndamento = true;
        while (pedidoEmAndamento) {
            System.out.print("Digite o nome do produto ou 'sair' para finalizar o pedido: ");
            String produtoNome = scanner.nextLine();

            if (produtoNome.equalsIgnoreCase("sair")) {
                pedidoEmAndamento = false; // Finaliza o pedido
                this.finalizarPedido();
                System.out.println("Pedido finalizado.");
                this.exibirPedido(cliente); // Exibe os detalhes do pedido
            } else {
                System.out.print("Digite a quantidade: ");
                int quantidade = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                // Busca o produto no estoque
                Produto produto = controlador.buscarProduto(produtoNome);
                if (produto != null) {
                    // Adiciona o produto ao pedido
                    this.adicionarProduto(produto, quantidade);
                    System.out.println("Produto adicionado ao pedido.");
                } else {
                    System.out.println("Produto não encontrado.");
                }
            }
        }
    }

    // Getter para o total do pedido
    public double getTotal() {
        return total;
    }

    // Manipulador de arquivo CSV
    ManipularArquivoVendas manipulador = new ManipularArquivoVendas();

    // Método para formatar o pedido como uma linha CSV
    public String formatarArqVendas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Clientes: ");
        for (Cliente cliente : clientes) {
            sb.append(cliente.getNome()).append(",");
        }
        sb.append(";");

        for (ItemPedido item : itensPedido) {
            sb.append(item.getProduto().getNome()).append("-");
            sb.append(item.getQuantidade()).append(",");
        }

        sb.append(";").append(total); // Total do pedido
        return sb.toString();
    }

    // Método para salvar o pedido no arquivo CSV
    public void salvarPedidoCSV() {
        manipulador.escreverCSV(this);
    }
}