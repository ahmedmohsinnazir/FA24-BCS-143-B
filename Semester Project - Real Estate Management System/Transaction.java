public abstract class Transaction // Abstract Parent Class for All Transactions
{
    private int transactionId;
    private Date transactionDate;
    private User seller;
    private User buyer;
    private Property property;
    private double amount;
    private TransactionStatus status;
    private Invoice invoice;  // Composition
    
    public Transaction(int transactionId, Date transactionDate, User seller, User buyer, 
    Property property, double amount) 
    {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.seller = seller;
        this.buyer = buyer;
        this.property = property;
        this.amount = amount;
        this.status = TransactionStatus.PENDING;
    }
    
    public boolean processTransaction() 
    {
        try 
        {
            this.invoice = new Invoice(generateInvoiceId(), new Date(), calculateDueDate(), amount);
            updateStatus(TransactionStatus.APPROVED);
            return true;
        } 
        catch (Exception e) 
        {
            System.out.println("Transaction processing failed: " + e.getMessage());
            updateStatus(TransactionStatus.FAILED);
            return false;
        }
    }
    
    public abstract double calculateFees(); // Abstract Method
    
    public void updateStatus(TransactionStatus status) 
    {
        this.status = status;
        System.out.println("Transaction status updated to: " + status);
    }
    
    private int generateInvoiceId() 
    {
        return this.transactionId + 1000;
    }
    
    private Date calculateDueDate() 
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(transactionDate);
        calendar.add(Calendar.DATE, 30);
        return calendar.getTime();
    }
    
    public int getTransactionId() 
    {
        return transactionId;
    }
    
    public Date getTransactionDate() 
    {
        return transactionDate;
    }
    
    public User getSeller() 
    {
        return seller;
    }
    
    public User getBuyer() 
    {
        return buyer;
    }
    
    public Property getProperty() 
    {
        return property;
    }
    
    public double getAmount() 
    {
        return amount;
    }
    
    public TransactionStatus getStatus() 
    {
        return status;
    }
    
    public Invoice getInvoice() 
    {
        return invoice;
    }
}