public class CommercialProperty extends Property 
{
    private CommercialType type;
    private double maintenanceCost;
    private boolean isParkingAvailable;
    
    public CommercialProperty(int propertyId, double price, double area, PropertyStatus status, 
    Address address, CommercialType type, double maintenanceCost, boolean isParkingAvailable) 
    {
        super(propertyId, price, area, status, address);
        this.type = type;
        this.maintenanceCost = maintenanceCost;
        this.isParkingAvailable = isParkingAvailable;
    }
    
    @Override
    public String getDescription() 
    {
        String parking = isParkingAvailable ? "with" : "without";
        return type + " commercial property, " + getArea() + " sq ft, " + parking + 
        " parking, located at " + getAddress().getFullAddress();
    }
    
    public double calculateMaintenancePerSqFt() 
    {
        return maintenanceCost / getArea();
    }
    
    public CommercialType getType() 
    {
        return type;
    }
    
    public double getMaintenanceCost() 
    {
        return maintenanceCost;
    }
    
    public boolean isParkingAvailable() 
    {
        return isParkingAvailable;
    }
    
    @Override
    public double calculateValue() 
    {
        double baseValue = super.calculateValue();
        
        // Adjust value based on property type
        double typeMultiplier = 1.0;
        switch (type) 
        {
            case OFFICE:
                typeMultiplier = 1.3;
                break;
            case RETAIL:
                typeMultiplier = 1.2;
                break;
            case INDUSTRIAL:
                typeMultiplier = 1.1;
                break;
            case WAREHOUSE:
                typeMultiplier = 0.9;
                break;
        }
        
        // Parking adds value
        double parkingPremium = isParkingAvailable ? 50000 : 0;
        
        // Higher maintenance cost reduces value
        double maintenanceFactor = 1.0 - (calculateMaintenancePerSqFt() * 0.1);
        
        return (baseValue * typeMultiplier * maintenanceFactor) + parkingPremium;
    }
}