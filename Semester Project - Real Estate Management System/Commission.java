public class Commission 
{
    private int commissionId;
    private Broker broker;
    private double amount;
    private boolean isPaid;
    
    public Commission(int commissionId, Broker broker, double amount) 
    {
        this.commissionId = commissionId;
        this.broker = broker;
        this.amount = amount;
        this.isPaid = false;
    }
    
    public void markAsPaid() 
    {
        isPaid = true;
        System.out.println("Commission marked as paid for broker: " + broker.getName());
    }
    
    public double calculateTax() 
    {
        return amount * 0.20;
    }
    
    public int getCommissionId() 
    {
        return commissionId;
    }
    
    public Broker getBroker() 
    {
        return broker;
    }
    
    public double getAmount() 
    {
        return amount;
    }
    
    public boolean isPaid() 
    {
        return isPaid;
    }
}