package groupJASS.ISA_2022.Exceptions;

public class QrCodeReadingException extends Exception {
    public  QrCodeReadingException(String message){
        super(message);
    }
    public  QrCodeReadingException(){
        super();
    }
}
