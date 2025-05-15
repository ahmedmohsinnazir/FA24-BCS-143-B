public interface PropertySearchable 
{
    Property[] searchByLocation(String location);
    Property[] searchByPrice(double minPrice, double maxPrice);
    Property[] searchByType(String type);
}