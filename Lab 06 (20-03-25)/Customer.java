public class Customer
{
	private int customerID;
	private String name;
	private String phoneNumber;
	private String email;

	public Customer(int customerID, String name, String phoneNumber, String email)
	{
		this.customerID = customerID;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public boolean equals(Customer obj)
	{
		Customer customer = (Customer) obj;
		if(name.equals(customer.name))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void displayCustomer()
	{
		System.out.println("Customer Details:");
		System.out.println("Customer ID:" + customerID);
		System.out.println("Customer Name:" + name);
		System.out.println("Customer phoneNumber:" + phoneNumber);
		System.out.println("Customer Email:" + email);
	}
}








