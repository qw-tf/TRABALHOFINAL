public class ClienteVip extends Cliente {

    private String cpf;
    public ClienteVip(String nome, String cpf, String senha) {
        super(nome, senha);
        this.cpf = cpf;
    }

    //gets e sets do cpf
    public String getCpf() { 
        return cpf; 
    }
    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    // Métodos de desconto
    public double calcularDesconto(double precoProduto) {
        return precoProduto * 0.05;
    }

    public double precoComDesconto(double precoProduto) {
        return precoProduto - calcularDesconto(precoProduto);
    }

    public void exibirDados() {
        super.exibirDados();
        System.out.println("CPF: " + cpf);
        System.out.println("Cliente VIP (Desconto de 5% nas compras!)");
    }

}