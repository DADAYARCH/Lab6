package exceptions;

public class RecursionException extends Exception{
    public RecursionException(){
        super("При выполнении скрипта возникает рекурсия");
    }
}
