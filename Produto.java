import Excessoes.InvalidPrecoException;
import Excessoes.InvalidQuantidadeException;
import Excessoes.LimiteEstoqueException;

public class Produto{
    //atributos pedidos no arquivo,
    //escolhemos adicionar código do produto e limite maximo de estoque.
    private String nome, descricao;
    private int quantidade, codigo, limiteEstoque;
    private double preco;

    //variavel static para contar a quantidade total de produtos, e a
    //variavel para ajudar a carregar os codigos dos produtos corretamente"
    private static int proximoCodigo = 1000;

    //instancia novo verificador para checar as variaveis
    Verificador verificador = new Verificador();

    //construtor
    public Produto(String nome, int quantidade, double preco, int limiteEstoque){
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.limiteEstoque = limiteEstoque;

        codigo = proximoCodigo++; //automaticamente da um novo codigo a um produto
    }

    //gets e sets dos atributos
    public int getQuantidade() {
        return quantidade;
    }
    public void diminuirQuantidade() throws InvalidQuantidadeException{
       if(quantidade == 0){
            throw new InvalidQuantidadeException("Quantidade do produto eh igual a zero!");
       } 
        quantidade -= 1; // caso alguma venda seja feita
    }
    public int getCodigo() {
        return codigo;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getNome() {
        return nome;
    }
    public double getPreco() {
        return preco;
    }
    public int getLimiteDeEstoque(){
        return limiteEstoque;
    }
    public int getLimiteEstoque() {
        return limiteEstoque;
    }
    public int getProximoCodigo(){
        return proximoCodigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setPreco(double preco) throws InvalidPrecoException{
        verificador.verificarPreco(preco);
        this.preco = preco;
    }
    public void setQuantidade(int quantidade) throws InvalidQuantidadeException{
        verificador.verificarQuantidade(quantidade, limiteEstoque);
        this.quantidade = quantidade;
    }
    public void setLimiteDeEstoque(int limiteEstoque) throws LimiteEstoqueException{
        verificador.verificarLimiteDeEstoque(limiteEstoque, quantidade);
        this.limiteEstoque = limiteEstoque;
    }
    public static void setProximoCodigo(int codigo) {
        proximoCodigo = codigo;
    }
}