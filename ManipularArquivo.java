import java.io.*;
import java.util.List;

public class ManipularArquivo {
    private String nomeArquivo;
    private ControladorDeEstoque controlador; // instancia privada do manipulador para ser usada dentro da classe


    //construtor base que recebe uma instancia de controlador para poder acessar e mudar as variaveis da mesma instancia da main
    public ManipularArquivo(String nomeArquivo, ControladorDeEstoque controlador){
        this.nomeArquivo = nomeArquivo;
        this.controlador = controlador;
        criarArquivo(); //chama o metodo para criar o arquivo
    }

    private void criarArquivo(){ //metodo para ter certeza que um arquivo é criado quando o objeto manipularArquivo é criado
        File arquivo = new File(nomeArquivo);
        try{
            if(arquivo.createNewFile()){
                System.out.println("Arquivo criado com sucesso: "  + arquivo.getName());
            }
        }catch(IOException e){
            System.out.println("Erro ao criar o arquivo! " + e.getMessage());
        }
    }

    public void carregarProdutos() { // le o arquivo csv ja existente e carrega o que tem nele para os produtos do programa
        try (BufferedReader maca = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            boolean cabecalho = true;  //variavel para pular o cabeçalho
            int maiorCodigo = 1000; //para ter certeza que os codigos dos produtos estao sendo carregados corretamente
            while ((linha = maca.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false;
                    continue; // Pula o cabeçalho
                }
                //apos verificar a linha, atribue o conteudo da linha (que estao separadas por ",") às variaveis
                String[] dados = linha.split(","); 
                int codigo = Integer.parseInt(dados[0]);
                if(codigo > maiorCodigo){
                    maiorCodigo = codigo; // checa se o codigo do produto que esta sendo carregado é maior que 1000,
                                        // para ter certeza que 
                }
                String nome = dados[1];
                int quantidade = Integer.parseInt(dados[2]);
                double preco = Double.parseDouble(dados[3]);
                String descricao = dados[4].isEmpty() ? null : dados[4];;
                int limiteEstoque = Integer.parseInt(dados[5]);
                String dataDeValidade = dados.length > 6 ? dados[6] : null;
                //checa se existem apenas 6 variaveis na linha, se sim, setta data de validade como null, indicando prod nao perecivel
    
                Produto produto;
                if (dataDeValidade != null && !dataDeValidade.isEmpty()) { // checa se data de validade for nula, se nao for, é perecivel
                    produto = new ProdutoPerecivel(nome, quantidade, preco, dataDeValidade, limiteEstoque);
                } else {// cria produto comum
                    produto = new Produto(nome, quantidade, preco, limiteEstoque);
                }
                produto.setCodigo(codigo);
                produto.setDescricao(descricao);
                Produto.setProximoCodigo(maiorCodigo + 1); //para o proximo produtos carregado ter o codigo correto
                controlador.aumentarLista(produto);//adiciona o produto carregado do arquivo a lista do controlador para ser modificado
                controlador.checarEstoqueBaixo(produto); //checa se o produto esta perto de se acabar (abaixo de 20%)
            }
        }catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    public void salvarArquivo() { //para salvar o arquivo com os produtos da lista
        List<Produto> produtos = controlador.getProdutos(); //pega a lista para guardar
        try (BufferedWriter pera = new BufferedWriter(new FileWriter(nomeArquivo, false))) { 
            //isso aqui cria uma instancia local do buffered writer que é fechada automaticamente 
            //o append false diz que ele reescreve o arquivo inteiro toda vez, ao invez de adicionar ao que ja tem
            pera.write("Código,Nome,Quantidade,Preço,Descrição,Limite de Estoque,Data de Validade");
            pera.newLine(); // escreve o cabeçalho do CSV
    
            // escreve os dados de cada produto
            for (Produto produto : produtos) {
                String descricao = produto.getDescricao() != null ? produto.getDescricao() : ""; 
                String linha = produto.getCodigo() + "," +
                               produto.getNome() + "," +
                               produto.getQuantidade() + "," +
                               produto.getPreco() + "," +
                               descricao + "," +
                               produto.getLimiteEstoque();

    
                // se o produto for perecível, adiciona a data de validade
                if (produto instanceof ProdutoPerecivel) {
                    linha += "," + ((ProdutoPerecivel) produto).getDataDeValidade();
                } else {
                    linha += ","; // adiciona uma vírgula vazia para produtos não perecíveis
                }
                pera.write(linha);
                pera.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao sobrescrever o arquivo: " + e.getMessage());
        }
    }

}
