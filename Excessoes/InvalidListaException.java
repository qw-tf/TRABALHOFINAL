package Excessoes;
public class InvalidListaException extends Exception{
    public InvalidListaException(){
    }

    public InvalidListaException(String msg){
        super(msg);
    }
}
//checa se a lista onde os produtos estao armazenados esta vazia