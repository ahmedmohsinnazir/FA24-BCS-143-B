public class Land extends Property 
{
    private LandType type;
    private boolean isZoned;
    private String zoneType;
    
    public Land(int propertyId, double price, double area, PropertyStatus status, 
    Address address, LandType type, boolean isZoned, String zoneType) 
    {
        super(propertyId, price, area, status, address);
        this.type = type;
        this.isZoned = isZoned;
        this.zoneType = zoneType;
    }
    
    @Override
    public String getDescription() 
    {
        String zoning = isZoned ? "zoned for " + zoneType : "not zoned";
        return type + " land, " + getArea() + " sq ft, " + 
        zoning + ", located at " + getAddress().getFullAddress();
    }
    
    public boolean checkDevelopmentPotential() 
    {
        // Logic to determine development potential based on zoning and type
        if (!isZoned) 
        {
            return false;
        }
        
        switch (type) 
        {
            case RESIDENTIAL:
                return zoneType.equalsIgnoreCase("residential") || 
                       zoneType.equalsIgnoreCase("mixed");
            case COMMERCIAL:
                return zoneType.equalsIgnoreCase("commercial") || 
                       zoneType.equalsIgnoreCase("mixed");
            case AGRICULTURAL:
                return zoneType.equalsIgnoreCase("agricultural");
            case RECREATIONAL:
                return zoneType.equalsIgnoreCase("recreational") || 
                       zoneType.equalsIgnoreCase("mixed");
            default:
                return false;
        }
    }
    
    public LandType getType() 
    {
        return type;
    }
    
    public boolean isZoned() 
    {
        return isZoned;
    }
    
    public String getZoneType() 
    {
        return zoneType;
    }
    
    @Override
    public double calculateValue() 
    {
        // Land value calculation
        double baseValue = super.calculateValue();
        
        // Adjust value based on land type
        double typeMultiplier = 1.0;
        switch (type) 
        {
            case RESIDENTIAL:
                typeMultiplier = 1.2;
                break;
            case COMMERCIAL:
                typeMultiplier = 1.5;
                break;
            case AGRICULTURAL:
                typeMultiplier = 0.8;
                break;
            case RECREATIONAL:
                typeMultiplier = 0.9;
                break;
        }
        
        // Zoning adds value
        double zoningMultiplier = isZoned ? 1.3 : 1.0;
        
        return baseValue * typeMultiplier * zoningMultiplier;
    }
}