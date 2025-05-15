public class Appointment 
{
    private int appointmentId;
    private Date dateTime;
    private Customer customer;
    private Broker broker;
    private Property property;
    private String status;  // scheduled/confirmed/cancelled/completed
    
    public Appointment(int appointmentId, Date dateTime, Customer customer, 
    Broker broker, Property property) 
    {
        this.appointmentId = appointmentId;
        this.dateTime = dateTime;
        this.customer = customer;
        this.broker = broker;
        this.property = property;
        this.status = "scheduled";
    }
    
    public void reschedule(Date newDateTime) 
    {
        if ("cancelled".equals(status)) 
        {
            throw new AppointmentException("Cannot reschedule a cancelled appointment");
        }
        else
        {
            this.dateTime = newDateTime;
            System.out.println("Appointment rescheduled to: " + newDateTime);
        }

    }
    
    public void cancel() 
    {
        this.status = "cancelled";
        System.out.println("Appointment cancelled");
    }
    
    public void confirm() 
    {
        if ("cancelled".equals(status)) {
            throw new AppointmentException("Cannot confirm a cancelled appointment");
        }
        this.status = "confirmed";
        System.out.println("Appointment confirmed");
    }
    
    public void complete() 
    {
        if (!"confirmed".equals(status)) {
            throw new AppointmentException("Only confirmed appointments can be completed");
        }
        this.status = "completed";
        System.out.println("Appointment marked as completed");
    }
    
    public int getAppointmentId() 
    {
        return appointmentId;
    }
    
    public Date getDateTime() 
    {
        return dateTime;
    }
    
    public Customer getCustomer() 
    {
        return customer;
    }
    
    public Broker getBroker() 
    {
        return broker;
    }
    
    public Property getProperty() 
    {
        return property;
    }
    
    public String getStatus() 
    {
        return status;
    }
}