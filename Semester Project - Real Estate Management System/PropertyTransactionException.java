public class PropertyTransactionException extends RuntimeException 
{
    public PropertyTransactionException(String message)
    {
        super(message);
    }
    
    public PropertyTransactionException(String message, Throwable cause) 
    {
        super(message, cause);
    }
}