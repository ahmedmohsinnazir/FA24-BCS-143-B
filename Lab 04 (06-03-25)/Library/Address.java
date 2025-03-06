public class Address
{
	private String street;
	private String city;

	Address(String street, String city)
	{
		this.street = street;
		this.city = city;
	}

	Address(Address other)
	{
		this.street = other.street;
		this.city = other.city;
	}

	public void showAddress()
	{
		System.out.println("Street: " + street);
		System.out.println("City: " + city);
	}

	public boolean equals(Address obj)
	{
		if((this.street == obj.street) && (this.city == obj.city))
		{
			return true;
		}
		else
		{
			return false;
		}
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

	public void setCity()
	{
		this.city = city;	
	}
}