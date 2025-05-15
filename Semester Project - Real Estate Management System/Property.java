public abstract class Property // Abstract Parent Class for All Properties 
{
    private int propertyId;
    private double price;
    private double area;
    private PropertyStatus status;
    private Address address;  // Composition with Address
    
    public Property(int propertyId, double price, double area, PropertyStatus 
    status, Address address) 
    {
        this.propertyId = propertyId;
        this.price = price;
        this.area = area;
        this.status = status;
        this.address = address;
    }
    
    public double calculateValue() 
    {
        return price * area;
    }
    
    public void updateStatus(PropertyStatus status) 
    {
        this.status = status;
        System.out.println("Property status updated to: " + status);
    }
    
    public abstract String getDescription(); // Abstract Method
    
    public int getPropertyId() 
    {
        return propertyId;
    }
    
    public double getPrice() 
    {
        return price;
    }
    
    public void setPrice(double price) 
    {
        this.price = price;
    }
    
    public double getArea() 
    {
        return area;
    }
    
    public PropertyStatus getStatus() 
    {
        return status;
    }
    
    public Address getAddress() 
    {
        return address;
    }
}