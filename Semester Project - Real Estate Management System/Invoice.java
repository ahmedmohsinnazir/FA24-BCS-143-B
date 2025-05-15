public class Invoice 
{
    private int invoiceId;
    private Date creationDate;
    private Date dueDate;
    private double totalAmount;
    private InvoiceItem items[]; // Composition with InvoiceItem
    private int itemCount;
    private static final int MAX_ITEMS = 20;
    
    public Invoice(int invoiceId, Date creationDate, Date dueDate, double totalAmount) 
    {
        this.invoiceId = invoiceId;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.items = new InvoiceItem[MAX_ITEMS];
        this.itemCount = 0;
    }
    
    public void addItem(InvoiceItem item) 
    {
        if (itemCount < MAX_ITEMS) 
        {
            items[itemCount] = item;
            itemCount++;
            recalculateTotal();
        } 
        else 
        {
            System.out.println("Maximum number of invoice items reached");
        }
    }
    
    public double calculateTotal() 
    {
        double total = 0.0;
        for (int i = 0; i < itemCount; i++) 
        {
            total += items[i].getAmount();
        }
        return total;
    }
    
    private void recalculateTotal() 
    {
        totalAmount = calculateTotal();
    }
    
    public void generatePDF() 
    {
        try 
        {
            System.out.println("Generating PDF invoice: " + invoiceId);
            // Logic to generate PDF invoice
        } 
        catch (Exception e) 
        {
            System.out.println("Error generating invoice PDF: " + e.getMessage());
        }
    }
    
    public int getInvoiceId() 
    {
        return invoiceId;
    }
    
    public Date getCreationDate() 
    {
        return creationDate;
    }
    
    public Date getDueDate() 
    {
        return dueDate;
    }
    
    public double getTotalAmount() 
    {
        return totalAmount;
    }
    
    public InvoiceItem[] getItems() 
    {
        InvoiceItem[] result = new InvoiceItem[itemCount];
        for (int i = 0; i < itemCount; i++) 
        {
            result[i] = items[i];
        }
        return result;
    }
}