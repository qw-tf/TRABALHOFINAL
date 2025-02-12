public class app {
    public static void main(String[] args) {
        ControladorDeEstoque controlador = new ControladorDeEstoque();
        ManipularArquivo manipulador = new ManipularArquivo("produtos.csv", controlador);
        manipulador.carregarProdutos();
        controlador.adicionarProduto();
        manipulador.salvarArquivo();
    }
}
