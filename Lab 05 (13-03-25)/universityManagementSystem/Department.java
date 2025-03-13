class Department 
{
	String departmentName;
    	Lab labs[];
    	Person departmentIncharge;

    	Department(String departmentName, String name, String role) 
	{
        	this.departmentName = departmentName;
        	this.labs = new Lab[50];
        	this.departmentIncharge = new Person(name, role);
    	}

    	public void displayDepartmentDetails() 
	{
        	System.out.println("Department: " + departmentName);
        	departmentIncharge.displayPersonDetails();
    	}
}