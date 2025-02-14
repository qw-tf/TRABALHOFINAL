
import java.util.logging.FileHandler;
import java.util.logging.Logger; // bibliotecas padrão do Java para logs
import java.util.logging.SimpleFormatter;
import java.io.*;
import java.util.Scanner;

public class FeedbackCliente {
    
    // Bloco estático adicionado para configurar o FileHandler sem alterar a estrutura original
    static {
        try {
            // Cria o diretório "logs" se não existir
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            // Cria o FileHandler para o arquivo "logs/app.log" (modo append)
            FileHandler fileHandler = new FileHandler("logs/app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            // Adiciona o FileHandler ao logger da classe
            Logger.getLogger(FeedbackCliente.class.getName()).addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Erro ao configurar o logger: " + e.getMessage());
        }
    }
    
    // Seu código original permanece inalterado abaixo:
    private static final Logger log = Logger.getLogger(FeedbackCliente.class.getName()); // inicializa o logger.
    static Scanner feedbackSc = new Scanner(System.in);
    private static String caminhoFeedback = "Feedback.txt"; // criando o arquivo, com o caminho específico.
    
    public static void verificarOuCriarArquivo() {
        File feedback = new File(caminhoFeedback);
        try {
            if (feedback.createNewFile()) {
                log.info("Arquivo de feedback criado.");
            }
        } catch (IOException e) {
            log.severe("Erro ao criar o arquivo de feedback.");
        }
    }
    
    public static void escritorDeTexto() { // escreve o corpo da mensagem de feedback que você deseja.
        log.info("Digite o título do seu feedback.");
        String titulo = feedbackSc.nextLine();
        log.info("Digite o texto do seu feedback.");
        String texto = feedbackSc.nextLine();
        escreverFeedback(caminhoFeedback, titulo, texto);
    }
    
    private static void escreverFeedback(String nomeDoArquivo, String titulo, String texto) { // passa como parametros o caminho, titulo e texto digitados.
        try (BufferedWriter escritorFeed = new BufferedWriter(new FileWriter(nomeDoArquivo, true))) {
            escritorFeed.write("\t=== " + titulo + " ===\n"); // formatação padronizada para mensagem de tiulo
            escritorFeed.write(texto + "\n\n"); // escreve texto.
        } catch (IOException e) {
            log.severe("Erro ao escrever no arquivo: " + e.getMessage()); // erro se não puder escrever a mensagem.
        }
    }
    
    public static void lerFeedback() { // método para verificar se há algum arquivo existente
        File arquivo = new File(caminhoFeedback);
        if (!arquivo.exists()) {
            log.info("Nenhum relatório encontrado.");
            return;
        }
        try (Scanner leitor = new Scanner(arquivo)) { // aqui serve para ler o arquivo até houver linhas não nulas.
            while (leitor.hasNextLine()) {
                log.info(leitor.nextLine());
            }
        } catch (FileNotFoundException e) {
            log.severe("Erro ao ler o arquivo de feedback."); // tratamento caso ocorra algum outro erro.
        }
    }
}