public class Person
{
	private String name;
	private String role;
	private Address address;


	Person(String name, String role, Address address)
	{
		this.name = name;
		this.role = role;
		this.address = address;
	}

	Person(Person other)
	{
		this.name = other.name;
		this.role = other.role;
		this.address = other.address;
	}

	public void showPersonDetails()
	{
		System.out.println("Name: " + name);
		System.out.println("Role: " + role);
		System.out.println("Address");
		System.out.println("Street: " + address.getStreet());
		System.out.println("City: " + address.getCity());		
	}

	public boolean equals(Person obj)
	{
		if((this.name == obj.name) && (this.role == obj.role))
		{
			return true;
		}
		else
		{
			return false;	
		}		
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;	
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;	
	}
}