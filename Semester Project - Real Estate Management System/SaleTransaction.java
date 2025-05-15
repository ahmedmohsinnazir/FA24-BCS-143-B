public class SaleTransaction extends Transaction 
{
    private boolean isFinanced;
    private double downPayment;
    
    public SaleTransaction(int transactionId, Date transactionDate, User seller, User buyer, 
    Property property, double amount, boolean isFinanced, double downPayment) 
    {
        super(transactionId, transactionDate, seller, buyer, property, amount);
        this.isFinanced = isFinanced;
        this.downPayment = downPayment;
    }
    
    @Override
    public double calculateFees()
    {
        double baseFee = getAmount() * 0.06;  // 6% standard real estate commission
        
        if (isFinanced) 
        {
            baseFee += getAmount() * 0.01;  // 1% loan origination fee
        }
        
        return baseFee;
    }
    
    public void generateDeed() 
    {
        try 
        {
            System.out.println("Generating deed for property: " + getProperty().getPropertyId());
        } 
        catch (Exception e) 
        {
            System.out.println("Error generating deed: " + e.getMessage());
            throw new PropertyTransactionException("Failed to generate deed: " + e.getMessage());
        }
    }
    
    public boolean isFinanced() 
    {
        return isFinanced;
    }
    
    public double getDownPayment() 
    {
        return downPayment;
    }
}