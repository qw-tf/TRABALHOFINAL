import java.util.ArrayList;
import java.util.List;

public class GerenciamentoCliente {
    private List<Cliente> clientes; // Alterado para Cliente

    public GerenciamentoCliente() {
        this.clientes = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
    
    // Método para adicionar cliente
    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        System.out.println("Cliente " + cliente.getNome() + " adicionado com sucesso!");
    }

    // Método para listar clientes
    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("Lista de Clientes:");
        for (Cliente cliente : clientes) {
            cliente.exibirDados();
            System.out.println("-------------------");
        }
    }

    // Método para buscar cliente por nome
    public Cliente buscarClientePorNome(String nome) {
        for (Cliente cliente : clientes) {
            if (cliente.getNome().trim().equalsIgnoreCase(nome.trim())) {
                return cliente;
            }
        }
        return null;
    }

    // Método para verificar se o cliente é VIP
    public boolean verificarClienteVip(String nome) {
        Cliente cliente = buscarClientePorNome(nome);
        return cliente instanceof ClienteVip;
    }
}