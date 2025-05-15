public class Customer extends User 
{
    private Property favorites[];  // Array of favorite properties (Composition)
    private Transaction transHistory[];  // Array of transaction history (Composition)
    private int favoriteCount;
    private int transactionCount;
    private static final int MAX_FAVORITES = 20;
    private static final int MAX_TRANSACTIONS = 50;
    
    public Customer(int userId, String name, String email, String password, String phone) 
    {
        super(userId, name, email, password, phone);
        this.favorites = new Property[MAX_FAVORITES];
        this.transHistory = new Transaction[MAX_TRANSACTIONS];
        this.favoriteCount = 0;
        this.transactionCount = 0;
    }
    
    public void addFavorite(Property property) 
    {
        if (favoriteCount < MAX_FAVORITES) 
        {
            favorites[favoriteCount] = property;
            favoriteCount++;
            System.out.println("Property added to favorites");
        } 
        else 
        {
            System.out.println("Favorites list is full");
        }
    }
    
    public void removeFavorite(Property property) 
    {
        boolean found = false;
        for (int i = 0; i < favoriteCount; i++) 
        {
            if (favorites[i].getPropertyId() == property.getPropertyId()) 
            {
                found = true;
                for (int j = i; j < favoriteCount - 1; j++) 
                {
                    favorites[j] = favorites[j + 1];
                }
                favorites[favoriteCount - 1] = null;
                favoriteCount--;
                System.out.println("Property removed from favorites");
                break;
            }
        }
        
        if (!found) 
        {
            System.out.println("Property not found in favorites");
        }
    }
    
    public Property[] getTransactionHistory() 
    {
        Property history[] = new Property[transactionCount];
        for (int i = 0; i < transactionCount; i++) 
        {
            history[i] = transHistory[i].getProperty();
        }
        return history;
    }
    
    public void addTransaction(Transaction transaction) 
    {
        if (transactionCount < MAX_TRANSACTIONS) 
        {
            transHistory[transactionCount] = transaction;
            transactionCount++;
        } 
        else 
        {
            System.out.println("Transaction history is full");
        }
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Customer: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone: " + getPhone());
        System.out.println("Favorites: " + favoriteCount);
        System.out.println("Transaction History: " + transactionCount);
    }
}