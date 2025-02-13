package excessoes;
public class InvalidCPFException extends Exception{
    public InvalidCPFException(){
        super();
    }
    public InvalidCPFException(String msg){
        super(msg);
    }
}
