public abstract class User // Abstract Parent Class for All Users
{
    private int userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    
    public User(int userId, String name, String email, String password, String phone) 
    {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    
    public void login() // Authentication logic
    {
        System.out.println("User " + name + " logged in");
    }
    
    public void logout() // Logout logic 
    {
        System.out.println("User " + name + " logged out");
    }
    
    public abstract void displayInfo(); // Abstract method
    
    public int getUserId() 
    {
        return userId;
    }
    
    public String getName() 
    {
        return name;
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }
    
    public String getEmail() 
    {
        return email;
    }
    
    public void setEmail(String email) 
    {
        this.email = email;
    }
    
    public String getPhone() 
    {
        return phone;
    }
    
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }
    
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public boolean verifyPassword(String inputPassword) // Method to check password
    {
        return this.password.equals(inputPassword);
    }
}