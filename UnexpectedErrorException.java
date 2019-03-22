package IA;

public class UnexpectedErrorException
    extends RuntimeException
{
    public UnexpectedErrorException(Exception cause)
    {
        super(cause);
    }
}
