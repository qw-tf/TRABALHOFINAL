import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class GerarLogs {
  
    private final Logger log = Logger.getLogger(GerarLogs.class.getName());

    public GerarLogs() { 
        try {
            // Cria o diretório "logs" se ele não existir
            java.io.File logDir = new java.io.File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Configura o FileHandler apenas uma vez
            FileHandler fileHandler = new FileHandler("logs/exclusao_produtos.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            log.addHandler(fileHandler);
            log.setUseParentHandlers(false);

        } catch (IOException e) {
            System.err.println("Erro ao configurar o logger: " + e.getMessage());
        }
    }

    public void logAutomatic(String corpo) {
        try {
           log.info("ação realizada ->"+corpo+"<- |");
        } catch (Exception e) {
            System.err.println("Erro ao registrar log: " + e.getMessage());
        }
    }
}