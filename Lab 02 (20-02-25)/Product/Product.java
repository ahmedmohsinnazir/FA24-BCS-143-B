public class Product
{
	static int count = 0;
	static String ID = "E0";
	private String name;
	private int quantity;
	private double price;
	private String category;
	private int id;

	Product(String name, int quantity, double price, String category)
	{
		this.name = name;	
		this.quantity = quantity;
		this.price = price;
		this.category = category;
		this.id = count++;
			
	}

	void setName(String name)
	{
		this.name = name;
	}

	void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	void setPrice(double price)
	{
		this.price = price;
	}

	void setCategory(String category)
	{
		this.category = category;
	}	

	String getName()
	{
		return name;
	}

	int getQuantity()
	{
		return quantity;
	}

	double getPrice()
	{
		return price;
	}

	String getCategory()
	{
		return name;
	}

	void displayDetails()
	{
		System.out.printf("\n Product ID: %s", (ID + id) + "\n Name: " + name + "\n Quantity: " 
		+ quantity + "\n Price: Rs." + price + "\n Category: " + category + "\n");
	}	

	public static void main(String args[])
	{
		Product p1 = new Product("Headphone", 4, 1000, "Electronics");
		p1.displayDetails();
		Product p2 = new Product("Speaker", 2, 2000, "Electronics");
	        p2.displayDetails();
		Product p3 = new Product("Microphone", 5, 500, "Electronics");
		p3.displayDetails();
		Product p4 = new Product("Monitor", 1, 5000, "Electronics");
		p4.displayDetails();
		Product p5 = new Product("Mouse", 8, 1000, "Electronics");
		p5.displayDetails();
		p1.displayDetails();

	}
}