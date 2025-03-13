class Address 
{
	String street;
    	String city;

    	Address(String street, String city) 
	{
        	this.street = street;
        	this.city = city;
    	}

    	public void showAddress() 
	{
        	System.out.println("Street: " + street);
        	System.out.println("City: " + city);
    	}
}