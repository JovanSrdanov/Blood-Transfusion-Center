package groupJASS.ISA_2022.Exceptions;

public class BadRequestException extends  Exception {
    public BadRequestException(String errorMessage)
    {
       super(errorMessage);
    }
}
