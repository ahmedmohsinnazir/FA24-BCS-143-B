class University 
{
	String universityName;
    	Department departments[];
    	Person director;
    	Address universityAddress;

    	University(String universityName, Department departments[], String name, 
	String role, String street, String city) 
	{
        	this.universityName = universityName;
        	this.departments = departments;
        	this.director = new Person(name, role);
        	this.universityAddress = new Address(street, city);
    	}

    	public void displayUniversityDetails() 
	{
        	System.out.println("University Name: " + universityName);
        	director.displayPersonDetails();
        	universityAddress.showAddress();
    	}
}