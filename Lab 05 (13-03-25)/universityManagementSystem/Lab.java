class Lab 
{
	String labName;
    	Person staff;
    	PC numOfPC[];

    	Lab(String labName, Person staff) 
	{
        	this.labName = labName;
        	this.staff = staff;
        	this.numOfPC = new PC[50];
    	}

    	public void displayLabDetails() 
	{
        	System.out.println("Lab Name: " + labName);
        	staff.displayPersonDetails();
    	}
}