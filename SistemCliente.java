import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import excessoes.InvalidCPFException;
import excessoes.InvalidNameException;
import excessoes.InvalidSenhaException;

public class SistemCliente {
    private List<Cliente> clientes = new ArrayList<>();
    private Verificador verificador = new Verificador();
    private Scanner scan = new Scanner(System.in);


    public List<Cliente> getClientes(){
        return clientes;
    }
    // Método de cadastro
    public Cliente cadastrarUser(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Digite seu nome:");
                String nome = scanner.nextLine();
                verificador.verificarNome(nome);

                System.out.println("Deseja adicionar CPF? (s/n)");
                String opcao = scanner.nextLine();
                verificador.verificarResposta(opcao);

                if (opcao.equalsIgnoreCase("s")) {
                    System.out.println("Digite o CPF:");
                    String cpf = scanner.nextLine();
                    verificador.verificarCPF(cpf);

                    // Verificar se o CPF já está em uso
                    for (Cliente cliente : clientes) {
                        if (cliente instanceof ClienteVip && ((ClienteVip) cliente).getCpf().equals(cpf)) {
                            System.out.println("Este CPF já está em uso!");
                            return null;
                        }
                    }

                    System.out.println("Digite a senha (máx 5 dígitos):");
                    String senha = scanner.nextLine();
                    verificador.validarSenha(senha);

                    // Criar cliente VIP
                    ClienteVip clienteVip = new ClienteVip(nome, cpf, senha);
                    clientes.add(clienteVip);
                    System.out.println("Cliente VIP " + nome + " cadastrado com sucesso!");
                    return clienteVip;
                } else {
                    System.out.println("Digite a senha (máx 5 dígitos):");
                    String senha = scanner.nextLine();
                    verificador.validarSenha(senha);

                    // Criar cliente normal
                    Cliente cliente = new Cliente(nome, senha);
                    clientes.add(cliente);
                    System.out.println("Cliente " + nome + " cadastrado com sucesso!");
                    return cliente;
                }

            } catch (InvalidNameException | InvalidCPFException | InvalidSenhaException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // Método de login
    public boolean loggarUser() {
        try {
            if (clientes.isEmpty()) {
                System.out.println("Nenhuma conta cadastrada ainda!");
                return false;
            }

            System.out.println("--Login--");
            System.out.println("Nome:");
            String nome = scan.nextLine();
            System.out.println("Senha:");
            String senha = scan.nextLine();
            verificador.validarSenha(senha);

            for (Cliente cliente : clientes) {
                if (cliente.getNome().equalsIgnoreCase(nome) && cliente.getSenha().equals(senha)) {
                    System.out.println("Bem-vindo(a), " + cliente.getNome() + "!");
                    return true;
                }
            }

            System.out.println("Nome ou senha incorretos!");
            return false;

        } catch (InvalidSenhaException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    //metodo para excluir clientes da lista
    public void excluirCliente() {
		@SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        String nome;
        while(true){ //while para repetir esse segmneto caso o cliente der um valor invalido
            try {
                if(clientes.isEmpty()){
                    System.out.println("Nenhuma conta cadastrada ainda!");
                    return;
                }
                System.out.println("Digite o nome do cliente que quer excluir ou '0' para sair: ");
                nome = scanner.nextLine();
                if (nome.equals("0")) {
                    System.out.println("Saindo...");
                    return;
                } else {
                    nome = nome.toUpperCase();
    
                    Iterator<Cliente> iterator = clientes.iterator(); // iterator para percorrer pela lista e remover os
                                                                      // clientes corretamente
                    while (iterator.hasNext()) {
                        Cliente cliente = iterator.next();
                        if (cliente.getNome() == nome) {
                            System.out.println("Tem certeza que quer excluir (" + cliente.getNome() +") (s/n)?");
                            String opcao = scanner.nextLine();
                            verificador.verificarResposta(opcao); //um ultimo check para ter certeza que o
                                                                //cliente realmente quer remover o cliente
                            if(opcao.equals("n")){
                                System.out.println("Saindo...");
                                return;
                            }
                            iterator.remove(); // Remove o produto da lista
                            System.out.println("Cliente removido com sucesso!");
                            return;
                        }
                    }
                }
                System.out.println("Cliente com esse nome nao existe!"); //caso ele de um nome que nao
                                                                            //esta atrelaçado com um cliente 
    
                //catchs para mandar a mensagem de erro e recomeçar o while
            } catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                continue;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                continue;
            }
        }
    }

}

