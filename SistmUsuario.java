import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import excessoes.InvalidCPFException;
import excessoes.InvalidNameException;
import excessoes.InvalidSenhaException;

public class SistmUsuario {
    private List<Usuario> usuarios = new ArrayList<>();
    Verificador verificador = new Verificador();
    Scanner scan = new Scanner(System.in);
    
    String nome;
    String cpf;
    String senha;

    public List<Usuario> getUsuarios(){
        return usuarios;
    }

    public Usuario cadastrarUser(Scanner scanner) {
        while(true){
            try{
                System.out.println("Digite seu nome:");
                nome = scanner.nextLine();
                verificador.verificarNome(nome);
                System.out.println("Digite o CPF:");
                cpf = scanner.nextLine();
                verificador.verificarCPF(cpf);
                for(Usuario usuario : usuarios){
                    if (usuario.getCpf().equals(cpf)){
                        System.out.println("Este CPF ja esta em uso!");
                        return null;
                    }
                }
                System.out.println("Digite a senha(max de 5 digitos): ");
                senha = scanner.nextLine();
                verificador.validarSenha(senha);
    
            }
        catch(InvalidNameException e){
            System.out.println(e.getMessage());
            continue;

        }catch(InvalidCPFException e){
            System.out.println(e.getMessage());
            continue;

        }catch(InvalidSenhaException e){
            System.out.println(e.getMessage());
            continue;
        }

        Usuario novoUsuario = new Usuario(nome, cpf, senha);
        usuarios.add(novoUsuario);
        System.out.println("Usuário " + nome + " cadastrado com sucesso!");
        return novoUsuario;
     }
    }

    public void autenticar() {
        String cpf;
        String senha;
        try{
            for (Usuario user : usuarios) {
                System.out.println(("--Login--"));
                System.out.println("CPF:");
                cpf = scan.nextLine();
                verificador.verificarCPF(cpf);
                System.out.println("Senha:");
                senha = scan.nextLine();
                verificador.validarSenha(senha);
                if (user.getCpf().equals(cpf) && user.getSenha().equals(senha)) {
                    System.out.println("Bem-vindo(a), " + user.getNome() + "!");
                    break;
                }
            }
        }catch(InvalidCPFException | InvalidSenhaException e){
         System.out.println(e.getMessage());
        }
    }
}
