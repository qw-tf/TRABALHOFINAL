public class Usuario {
    private String nome;
    private String cpf;
    private String senha;

    public Usuario(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;

    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setCPF(String cpf){
        this.cpf = cpf;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
}
