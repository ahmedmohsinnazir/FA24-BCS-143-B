// Helper classes for GUI and API development
public class PropertyFilter 
{
    private String location;
    private double minPrice;
    private double maxPrice;
    private String propertyType;
    private int minBedrooms;
    private int maxBedrooms;
    
    public PropertyFilter() 
    {
        this.minPrice = 0;
        this.maxPrice = Double.MAX_VALUE;
        this.minBedrooms = 0;
        this.maxBedrooms = Integer.MAX_VALUE;
    }
    
    // Builder pattern for easy filtering
    public PropertyFilter withLocation(String location) 
    {
        this.location = location;
        return this;
    }
    
    public PropertyFilter withPriceRange(double minPrice, double maxPrice) 
    {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        return this;
    }
    
    public PropertyFilter withPropertyType(String propertyType) 
    {
        this.propertyType = propertyType;
        return this;
    }
    
    public PropertyFilter withBedroomRange(int minBedrooms, int maxBedrooms) 
    {
        this.minBedrooms = minBedrooms;
        this.maxBedrooms = maxBedrooms;
        return this;
    }
    
    public String getLocation() 
    {
        return location;
    }
    
    public double getMinPrice() 
    {
        return minPrice;
    }
    
    public double getMaxPrice() 
    {
        return maxPrice;
    }
    
    public String getPropertyType() 
    {
        return propertyType;
    }
    
    public int getMinBedrooms() 
    {
        return minBedrooms;
    }
    
    public int getMaxBedrooms() 
    {
        return maxBedrooms;
    }
}