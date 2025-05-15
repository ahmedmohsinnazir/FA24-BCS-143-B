public class Amenity 
{
    private String name;
    private String description;
    private boolean isPremium;
    
    public Amenity(String name, String description, boolean isPremium) 
    {
        this.name = name;
        this.description = description;
        this.isPremium = isPremium;
    }
    
    public String getName() 
    {
        return name;
    }
    
    public String getDescription() 
    {
        return description;
    }
    
    public boolean isPremium() 
    {
        return isPremium;
    }
}