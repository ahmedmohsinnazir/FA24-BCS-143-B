public class InvoiceItem 
{
    private String description;
    private double amount;
    private int quantity;
    
    public InvoiceItem(String description, double amount, int quantity) 
    {
        this.description = description;
        this.amount = amount;
        this.quantity = quantity;
    }
    
    public double getAmount() 
    {
        return amount * quantity;
    }
    
    public String getDescription() 
    {
        return description;
    }
    
    public int getQuantity() 
    {
        return quantity;
    }
    
    public double getUnitAmount() 
    {
        return amount;
    }
}