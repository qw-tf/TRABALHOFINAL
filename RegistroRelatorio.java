
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RegistroRelatorio {
    private int codigo;
    private String tipo, nome, data, conteudo;
    

    // Construtor que gera o relatorio de forma padronizada.
    public RegistroRelatorio(String tipo, String nome, String conteudo) {
        this.codigo = gerarCodigo(); //variável que atribui o código.
        this.tipo = tipo; // se é por produto ou cliente.
        this.nome = nome; // nome do registro que o usuário deseja salvar. 
        this.conteudo = conteudo; // aqui o usuário pode adicionar um comentário extra, caso queira.
        this.data = obterDataAtual(); // essa função chama o método para definir data e hora atual e escreve no relátorio.
    }

    // Gera um código único de 6 dígitos para a manipulção em outros métodos. (Ex: Apagar relátorio.)
    private int gerarCodigo() {
        Random rand = new Random();
        return 100000 + rand.nextInt(900000); // define o limite de 6 dígitos aqui. (De min. 100000 ~ máx. 900000.)
    }

    // Obter data atual formatada. 
    private String obterDataAtual() {
        //Método abaixo: Serve para estabelecer horário e data para o arquivo de forma padrão para todos os relatórios.
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // refere-se a formatação que foi definidanos parâmetros.
        return formatoData.format(new Date()); // new date() - Refere-se ao novo obejto "Date", que representa as datas e horas atuais.
    } // 

    // Bloco de formatação: 
    @Override
    public String toString() {
        return String.format("CÓDIGO: %d\nRELATÓRIO - %s (%s)\nDATA: %s\n%s\n---\n",
                codigo, tipo.toUpperCase(), nome, data, conteudo);
    } /*Nesse bloco é onde ocorre a padronização do relatório
    em um só arquivo, por exemplo: 

            [CÓDIGO: 806714
            RELATÓRIO - PRODUTO (exemplo)
            DATA: 06/02/2025 13:48:03
            Essa é uma demostração de como funciona o relatório.]
    
    */

    public int getCodigo() { // Aqui é um get para o código (6 digitos) do relatório. 
        return codigo; // retorna o código que foi resgatado.
    }
}
