public class ResidentialProperty extends Property 
{
    private int bedrooms;
    private int bathrooms;
    private ResidentialType type;
    private Amenity amenities[];  // Composition
    private int amenityCount;
    private static final int MAX_AMENITIES = 10;
    
    public ResidentialProperty(int propertyId, double price, double area, PropertyStatus status, 
    Address address, int bedrooms, int bathrooms, ResidentialType type) 
    {
        super(propertyId, price, area, status, address);
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.type = type;
        this.amenities = new Amenity[MAX_AMENITIES];
        this.amenityCount = 0;
    }
    
    @Override
    public String getDescription() 
    {
        return type + " residential property with " + bedrooms + " bedrooms, " + bathrooms + 
        " bathrooms, " + getArea() + " sq ft, located at " + getAddress().getFullAddress();
    }
    
    public void addAmenity(Amenity amenity) 
    {
        if (amenityCount < MAX_AMENITIES) 
        {
            amenities[amenityCount] = amenity;
            amenityCount++;
            System.out.println("Amenity added: " + amenity.getName());
        } 
        else 
        {
            System.out.println("Maximum number of amenities reached");
        }
    }
    
    public Amenity[] getAmenities() 
    {
        Amenity result[] = new Amenity[amenityCount];
        for (int i = 0; i < amenityCount; i++) 
        {
            result[i] = amenities[i];
        }
        return result;
    }
    
    public int getBedrooms() 
    {
        return bedrooms;
    }
    
    public int getBathrooms() 
    {
        return bathrooms;
    }
    
    public ResidentialType getType() 
    {
        return type;
    }
    
    @Override
    public double calculateValue() 
    {
        double baseValue = super.calculateValue();
        double amenityValue = 0;
        
        for (int i = 0; i < amenityCount; i++) 
        {
            if (amenities[i].isPremium()) 
            {
                amenityValue += 10000;  // Premium amenities add more value
            }
            else 
            {
                amenityValue += 5000;   // Standard amenities add less value
            }
        }
        
        // Adjust value based on property type and rooms
        double typeMultiplier = 1.0;
        switch (type) 
        {
            case SINGLE_FAMILY:
                typeMultiplier = 1.2;
                break;
            case CONDO:
                typeMultiplier = 1.0;
                break;
            case TOWNHOUSE:
                typeMultiplier = 1.1;
                break;
            case APARTMENT:
                typeMultiplier = 0.9;
                break;
        }
        
        return (baseValue * typeMultiplier) + amenityValue;
    }
}