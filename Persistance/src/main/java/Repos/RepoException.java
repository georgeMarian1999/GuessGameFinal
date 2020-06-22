package Repos;

public class RepoException extends Exception{
    public RepoException(){

    }
    public RepoException(String message){super(message);}
    public RepoException(String message,Throwable cause){
        super(message,cause);
    }
}
