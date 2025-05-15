public class RentalTransaction extends Transaction 
{
    private int leaseTerm;  // In months
    private double securityDeposit;
    private boolean isRenewable;
    
    public RentalTransaction(int transactionId, Date transactionDate, User owner, User tenant, 
    Property property, double amount, int leaseTerm, double securityDeposit, boolean isRenewable) 
    {
        super(transactionId, transactionDate, owner, tenant, property, amount);
        this.leaseTerm = leaseTerm;
        this.securityDeposit = securityDeposit;
        this.isRenewable = isRenewable;
    }
    
    @Override
    public double calculateFees() 
    {
        double baseFee = getAmount() * 0.10;  // One month's rent as fee (standard)
        
        // Adjustments based on lease term
        if (leaseTerm <= 6) 
        {
            baseFee += getAmount() * 0.05;  // Additional fee for short-term leases
        } 
        else if (leaseTerm >= 24) 
        {
            baseFee -= getAmount() * 0.02;  // Discount for long-term leases
        }
        
        return baseFee;
    }
    
    public void generateLeaseAgreement() 
    {
        try 
        {
            System.out.println("Generating lease agreement for property: " + getProperty().getPropertyId());
            // Logic to generate a lease agreement document
        } 
        catch (Exception e) 
        {
            System.out.println("Error generating lease agreement: " + e.getMessage());
            throw new PropertyTransactionException("Failed to generate lease agreement: " + e.getMessage());
        }
    }
    
    public int getLeaseTerm() 
    {
        return leaseTerm;
    }
    
    public double getSecurityDeposit() 
    {
        return securityDeposit;
    }
    
    public boolean isRenewable() 
    {
        return isRenewable;
    }
}