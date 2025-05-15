public class Broker extends User implements PropertySearchable 
{
    private double commissionRate;
    private Property managedProperties[]; // Composition with Property
    private Appointment appointments[]; // Composition with Appointments
    private int propertyCount;
    private int appointmentCount;
    private static final int MAX_PROPERTIES = 50;
    private static final int MAX_APPOINTMENTS = 100;
    
    public Broker(int userId, String name, String email, String password, String phone, 
    double commissionRate) 
    {
        super(userId, name, email, password, phone);
        this.commissionRate = commissionRate;
        this.managedProperties = new Property[MAX_PROPERTIES]; 
        this.appointments = new Appointment[MAX_APPOINTMENTS];
        this.propertyCount = 0;
        this.appointmentCount = 0;
    }
    
    public void addProperty(Property property) 
    {
        if (propertyCount < MAX_PROPERTIES)
        {
            managedProperties[propertyCount] = property;
            propertyCount++;
            System.out.println("Property added to broker's portfolio");
        } 
        else 
        {
            System.out.println("Property portfolio is full");
        }
    }
    
    public void removeProperty(Property property) 
    {
        boolean found = false;
        for (int i = 0; i < propertyCount; i++) 
        {
            if (managedProperties[i].getPropertyId() == property.getPropertyId()) 
            {
                found = true;
                for (int j = i; j < propertyCount - 1; j++) 
                {
                    managedProperties[j] = managedProperties[j + 1];
                }
                managedProperties[propertyCount - 1] = null;
                propertyCount--;
                System.out.println("Property removed from broker's portfolio");
                break;
            }
        }
        
        if (!found) 
        {
            System.out.println("Property not found in broker's portfolio");
        }
    }
    
    public double calculateCommission(double amount) 
    {
        return amount * commissionRate;
    }
    
    public void scheduleAppointment(Appointment appointment) 
    {
        if (appointmentCount < MAX_APPOINTMENTS) 
        {
            appointments[appointmentCount] = appointment;
            appointmentCount++;
            System.out.println("Appointment scheduled");
        } 
        else 
        {
            System.out.println("Appointment schedule is full");
        }
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Broker: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone: " + getPhone());
        System.out.println("Commission Rate: " + (commissionRate * 100) + "%");
        System.out.println("Managed Properties: " + propertyCount);
        System.out.println("Scheduled Appointments: " + appointmentCount);
    }
    
    @Override
    public Property[] searchByLocation(String location) // Implementation of PropertySearchable Interface
    {
        Property[] result = new Property[propertyCount];
        int count = 0;
        
        for (int i = 0; i < propertyCount; i++) 
        {
            if (managedProperties[i].getAddress().getCity().equalsIgnoreCase(location) || 
                managedProperties[i].getAddress().getState().equalsIgnoreCase(location)) 
                {
                result[count] = managedProperties[i];
                count++;
            }
        }
        
        // Create a new array with the exact size of results
        Property[] finalResult = new Property[count];
        for (int i = 0; i < count; i++) 
        {
            finalResult[i] = result[i];
        }
        return finalResult;
    }
    
    @Override
    public Property[] searchByPrice(double minPrice, double maxPrice) 
    {
        Property[] result = new Property[propertyCount];
        int count = 0;
        
        for (int i = 0; i < propertyCount; i++) 
        {
            double price = managedProperties[i].getPrice();
            if (price >= minPrice && price <= maxPrice) 
            {
                result[count] = managedProperties[i];
                count++;
            }
        }
        
        // Create a new array with the exact size of results
        Property[] finalResult = new Property[count];
        for (int i = 0; i < count; i++) 
        {
            finalResult[i] = result[i];
        }
        return finalResult;
    }
    
    @Override
    public Property[] searchByType(String type) 
    {
        Property[] result = new Property[propertyCount];
        int count = 0;
        
        for (int i = 0; i < propertyCount; i++) 
        {
            if ((type.equalsIgnoreCase("Residential") && managedProperties[i] instanceof ResidentialProperty) ||
                (type.equalsIgnoreCase("Commercial") && managedProperties[i] instanceof CommercialProperty) ||
                (type.equalsIgnoreCase("Land") && managedProperties[i] instanceof Land)) 
                {
                result[count] = managedProperties[i];
                count++;
            }
        }
        
        // Create a new array with the exact size of results
        Property[] finalResult = new Property[count];
        for (int i = 0; i < count; i++) 
        {
            finalResult[i] = result[i];
        }
        return finalResult;
    }
}