
import java.io.*; // importa todas as classe de entrada e saída. (File, FileWriter,BufferedReader  )
import java.util.ArrayList;
import java.util.List; // para o uso de listas.
import java.util.Scanner;

public class RelatorioDeVendas {
    private static final String caminhoArquivo = "relatorios.txt";

    // Verifica se o arquivo existe, senão cria um.
    //Bloco de verificação:
    public static void verificarOuCriarArquivo() {
        File arquivo = new File(caminhoArquivo);
        try { // aqui, nesse 'try', ele vai executar o bloco de verificação. 
            if (arquivo.createNewFile()) {
                System.out.println("Arquivo de relatórios criado.");
            }
        } catch (IOException e) { // vai capturar a execeção caso ocorra algum erro fora dos padrões do bom uso.
            System.out.println("Erro ao criar o arquivo.");// vai exibir essa mensagem caso caia na execeção.
        }
    }

    // Adiciona um novo relatório no mesmo arquivo.
    //Bloco de adição:
    public void adicionarRelatorio(Scanner sc) { // Passar o 'Scanner' para evitar criar múltiplos objetos e evitar várias instâncias.
        System.out.println("Digite o tipo do relatório (Usuário/Produto): "); // digita o TIPO do relatório.
        String tipo = sc.nextLine();

        System.out.println("Digite o nome do usuário/produto: ");// digita o NOME do relatório.
        String nome = sc.nextLine();

        System.out.println("Escreva a descrição do relatório:"); // texto adiconal.
        String conteudo = sc.nextLine();

        RegistroRelatorio relatorio = new RegistroRelatorio(tipo, nome, conteudo); // cria um objeto 'Resgitrorelatorio', com os parâmetros informados.

        try (FileWriter escritor = new FileWriter(caminhoArquivo, true)) {
            escritor.write(relatorio.toString());
            System.out.println("Relatório adicionado com sucesso!\nCódigo: " + relatorio.getCodigo());
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo.");
        }
    }

    // Ler todos os relatórios
    public void lerRelatorios() {
        File arquivo = new File(caminhoArquivo); // Aponta para o objeto da classe file que representa o caminho, no caso atual (relatorios.txt)
        if (!arquivo.exists()) { // nesse bloco, ele verifica se o arquivo NÃO existe. Se não existir, ele não retorna nada.
            System.out.println("Nenhum relatório encontrado.");
            return;
        }

        try (Scanner leitor = new Scanner(arquivo)) { // Abre o arquivo para leitura
            while (leitor.hasNextLine()) { // verifica se ainda há linhas para ler.
                System.out.println(leitor.nextLine());// retorna a linha lida e retorna como string.
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler o arquivo."); // trata a exceção caso caia aqui.
        }
    }

    // Modificar um relatório pelo código
    public void modificarRelatorio(Scanner sc) {
        System.out.println("Digite o código do relatório que deseja modificar:"); // pede para o usuário digitar o código.
        int codigoAlvo = sc.nextInt(); // variável que atribui o código do relatório.
        sc.nextLine(); // Consumir a quebra de linha, para evitar problemas na leitura futura.

        boolean encontrado = false;
        StringBuilder novoConteudo = new StringBuilder();

        try (Scanner leitor = new Scanner(new File(caminhoArquivo))) { // Nesse 'try', foi passado como parâmetro o caminho do arquivo.
            while (leitor.hasNextLine()) { // continua lendo enquato  há linhas para serem lidas.
                String linha = leitor.nextLine();
                if (linha.startsWith("CÓDIGO: " + codigoAlvo)) { // busca a linha que começa com o código do arquivo.
                    encontrado = true; // retorna 'true', caso começe.
                    System.out.println("Digite a nova descrição do relatório:"); // então, começa pede para o usuário digitar a descrição do relatório.
                    String novaDescricao = sc.nextLine();
                    while (leitor.hasNextLine()) { // aqui verifica se há nova linna, e vai ler equanto houver.
                        String proximaLinha = leitor.nextLine(); // verificar se a próxima linha começa com '----', que significa que o relatório chegou ao fim.
                        if (proximaLinha.startsWith("---")) break; // vai parar ao chegar ao fim do relatório.
                    }
                    novoConteudo.append(linha).append("\n").append(novaDescricao).append("\n---\n"); 
                } else { // nesse bloco de 'Append', é adcionado o que foi escrito a String já existente. 
                    novoConteudo.append(linha).append("\n"); // A função appende serve para atribui uma string ao final de uma
                }                                                   // inserida no StringBuilder, nesse caso por exemplo, pode adicionar
            }                                                           // os caracteres '----', que é padronizado.
        } catch (IOException e) {
            System.out.println("Erro ao modificar o arquivo."); // tratamento da exceção caso caia nesse bloco.
        }

        if (encontrado) { // verifica se o código do relatório foi encotrado.
            try (FileWriter escritor = new FileWriter(caminhoArquivo)) { //Se sim, cai no 'try', onde reescreve o relatório.
                escritor.write(novoConteudo.toString());
                System.out.println("Relatório atualizado com sucesso.");
            } catch (IOException e) {
                System.out.println("Erro ao reescrever o arquivo.");
            }
        } else {
            System.out.println("Código não encontrado."); // tratamento da exceção caso caia o relatório não seja encontrado.
        }
    }

    // Deleta um relatório pelo código.
    public void deletarRelatorio(Scanner sc) {
        System.out.println("Digite o código do relatório que deseja excluir:"); // pega o código do arquivo para exclusão.
        int codigoAlvo = sc.nextInt();
        sc.nextLine(); // Consome a quebra de linha para evitar erros em leituras futuras.

        List<String> linhas = new ArrayList<>(); // Essa lista é responsável por armazenar todas as linhas não escluídas, ou seja, os outros relatórios.
        boolean encontrado = false; // variável por sinalizar se o arquivo foi encontrado.

        try (Scanner leitor = new Scanner(new File(caminhoArquivo))) { // abre o arquivo para leitura, assim como seus semelhantes.
            while (leitor.hasNextLine()) { // verificação se há linhas para leitura.
                String linha = leitor.nextLine();
                if (linha.equals("CÓDIGO: " + codigoAlvo)) { // define que encontrou o começo do relatório. 
                    encontrado = true;
                    while (leitor.hasNextLine() && !leitor.nextLine().startsWith("---")) {} // pula todas as lin has até encontrar '----'.
                } else {
                    linhas.add(linha);// Armazena apenas as linhas que NÃO fazem parte do relatório deletado.
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao processar o arquivo."); // tratamento de exceção para processamento de arquivo.
        }

        if (encontrado) { 
            try (FileWriter escritor = new FileWriter(caminhoArquivo)) { /*  Este bloco serve para a reescrita dos relatórios que não foram apagados,*/
                for (String linha : linhas) {                           //Sobrescrevendo-o.
                    escritor.write(linha + "\n");
                }
                System.out.println("Relatório deletado com sucesso.");
            } catch (IOException e) {
                System.out.println("Erro ao reescrever o arquivo.");
            }
        } else {
            System.out.println("Código não encontrado."); //tratamento caso não encontre o código.
        }
    }
}

