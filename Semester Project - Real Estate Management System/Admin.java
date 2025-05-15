public class Admin extends User implements PropertySearchable 
{
    private String role;
    
    public Admin(int userId, String name, String email, String password, String phone, 
    String role) 
    {
        super(userId, name, email, password, phone);
        this.role = role;
    }
    
    public void manageUsers() // User management logic
    {
        System.out.println("Managing users...");

    }
    
    public void generateReports() 
    {
        System.out.println("Generating reports..."); // Report generation logic
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Admin: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone: " + getPhone());
        System.out.println("Role: " + role);
    }
    
    // Implementation of PropertySearchable interface (global search across all properties)
    @Override
    public Property[] searchByLocation(String location) 
    {
        // This would typically connect to a database
        // Simplified implementation for now
        System.out.println("Searching properties by location: " + location);
        return new Property[0];
    }
    
    @Override
    public Property[] searchByPrice(double minPrice, double maxPrice) {
        // This would typically connect to a database or repository
        System.out.println("Searching properties by price range: " + minPrice + " - " + maxPrice);
        return new Property[0];
    }
    
    @Override
    public Property[] searchByType(String type) {
        // This would typically connect to a database or repository
        System.out.println("Searching properties by type: " + type);
        return new Property[0];
    }
}