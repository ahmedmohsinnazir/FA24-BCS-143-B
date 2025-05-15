public class Payment 
{
    private int paymentId;
    private double amount;
    private Date paymentDate;
    private PaymentMethod method;
    private PaymentStatus status;

    public Payment(int paymentId, double amount, Date paymentDate, PaymentMethod method) 
    {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
        this.status = PaymentStatus.PENDING;
    }

    public boolean processPayment() 
    {
        try 
        {
            // Payment processing logic based on method
            System.out.println("Processing payment of $" + amount + " via " + method);

            boolean success = true;

            if (success) 
            {
                status = PaymentStatus.COMPLETED;
                System.out.println("Payment completed successfully");
            } 
            else 
            {
                status = PaymentStatus.FAILED;
                System.out.println("Payment processing failed");
            }

            return success;
        } 
        catch (Exception e) 
        {
            status = PaymentStatus.FAILED;
            System.out.println("Payment processing error: " + e.getMessage());
            return false;
        }
    }

    public void generateReceipt() 
    {
        if (status == PaymentStatus.COMPLETED) 
        {
            System.out.println("Generating receipt for payment: " + paymentId);
        } 
        else 
        {
            throw new PaymentException("Cannot generate receipt for incomplete payment");
        }
    }

    public int getPaymentId() 
    {
        return paymentId;
    }

    public double getAmount() 
    {
        return amount;
    }

    public Date getPaymentDate() 
    {
        return paymentDate;
    }

    public PaymentMethod getMethod() 
    {
        return method;
    }

    public PaymentStatus getStatus() 
    {
        return status;
    }
}
