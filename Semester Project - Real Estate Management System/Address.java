public class Address // Composition with Property
{
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    
    public Address(String street, String city, String state, String zipCode, String country) 
    {
        setStreet(street);
        setCity(city);
        setState(state);
        setZipCode(zipCode);
        setCountry(country);
    }
    
    public String getFullAddress() 
    {
        return street + ", " + city + ", " + state + " " + zipCode + ", " + country;
    }
    
    public String getStreet() 
    {
        return street;
    }
    
    public void setStreet(String street) 
    {
        this.street = street;
    }
    
    public String getCity() 
    {
        return city;
    }
    
    public void setCity(String city) 
    {
        this.city = city;
    }
    
    public String getState() 
    {
        return state;
    }
    
    public void setState(String state) 
    {
        this.state = state;
    }
    
    public String getZipCode() 
    {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) 
    {
        this.zipCode = zipCode;
    }
    
    public String getCountry() 
    {
        return country;
    }
    
    public void setCountry(String country) 
    {
        this.country = country;
    }
}