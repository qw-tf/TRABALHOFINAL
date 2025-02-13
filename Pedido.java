import java.util.List;
import java.util.ArrayList;

public class Pedido {

    private static int contadorPedidos = 1001;
    private int id;
    private Cliente cliente; // Alterado para Cliente (pode ser Cliente ou ClienteVip)
    private List<ItemPedido> itensPedido; // Lista dos itens do pedido
    private double total;
    private ControladorDeEstoque controladorEstoque;

    public Pedido(Cliente cliente, ControladorDeEstoque controladorEstoque) {
        this.id = contadorPedidos++;
        this.cliente = cliente;
        this.itensPedido = new ArrayList<>();
        this.total = 0.0;
        this.controladorEstoque = controladorEstoque;
    }

    // Método para adicionar produto ao pedido
    public void adicionarProduto(Produto produto, int quantidade) {
        try {
            if (quantidade <= 0) {
                System.out.println("Quantidade inválida! Faça direito, meu chapa!");
                return;
            }

            // Verifica se há estoque disponível
            if (controladorEstoque.verificarDisponibilidade(produto, quantidade)) {
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
                controladorEstoque.removerProduto(item.getProduto().getNome(), item.getQuantidade());
            }
            System.out.println("Pedido #" + id + " finalizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao finalizar pedido: " + e.getMessage());
        }
    }

    // Método para exibir os detalhes do pedido
    public void exibirPedido() {
        System.out.println("Pedido #" + id + " do cliente: " + cliente.getNome());
        System.out.println("Produtos no pedido:");
        for (ItemPedido item : itensPedido) {
            System.out.println("- " + item.getProduto().getNome() + " | Quantidade: " + item.getQuantidade() + " | R$ "
                    + item.getProduto().getPreco());
        }

        // Aplica desconto se o cliente for VIP
        if (cliente instanceof ClienteVip) {
            double desconto = ((ClienteVip) cliente).calcularDesconto(total);
            System.out.println("Desconto VIP (5%): R$ " + desconto);
            System.out.println("Total com desconto: R$ " + (total - desconto));
        }

        System.out.println("Total do pedido: R$ " + total);
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
        sb.append(id).append(";");
        sb.append(cliente.getNome()).append(";");

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