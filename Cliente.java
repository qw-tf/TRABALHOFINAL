
public class Cliente {
    // Atributos unificados (Usuario + Cliente)
    private String nome;
    private String senha;

    // Construtor simplificado (sem CPF)
    public Cliente(String nome, String senha) {
        this.nome = nome.toUpperCase();
        this.senha = senha;
    }

    // Getters e Setters
    public String getNome() {
        return nome; 
    }
    public String getSenha() { 
        return senha; 
    }

    public void setNome(String nome) {
        this.nome = nome; 
    }
    public void setSenha(String senha) { 
        this.senha = senha; 
    }

    public void exibirDados() {
        System.out.println("Nome: " + nome);
    }
}