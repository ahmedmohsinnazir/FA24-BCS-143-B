class Person 
{
	private String name;
    	private String role;

    	Person(String name, String role) 
	{
        	this.name = name;
        	this.role = role;
    	}

    	public void displayPersonDetails() 
	{
        	System.out.println("Name: " + name + "\nRole: " + role);
    	}
}