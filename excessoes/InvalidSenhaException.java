package excessoes;
public class InvalidSenhaException extends Exception{
    public InvalidSenhaException(){
        super();
    }
    public InvalidSenhaException(String msg){
        super(msg);
    }
}
