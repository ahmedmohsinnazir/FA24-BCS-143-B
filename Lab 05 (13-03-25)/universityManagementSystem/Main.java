public class Main 
{
	public static void main(String args[]) 
	{
        	Person director = new Person("Dr. Syed Asad Hussain", "Director");

        Department[] departments = new Department[10];
        departments[0] = new Department("Computer Science", "Muhammad Ammar", "HOD");
        departments[1] = new Department("Architecture", "Asif Khan", "HOD");
        departments[2] = new Department("Management Sciences", "Ayesha Ali", "HOD");
        
	Lab[] labs = new Lab[20];
        for (int i = 0; i < 5; i++) 
	{
        	Person labStaff = new Person("Farooq Khan", "Lab Attendant");
            	labs[i] = new Lab("Lab " + (i + 1), labStaff);
            
            	labs[i].numOfPC = new PC[50];
            	for (int j = 0; j < 10; j++) 
		{
                labs[i].numOfPC[j] = new PC("PC" + (j + 1), "Intel i7", 16, 512);
         	}
        }
        
        for (int i = 0; i < 3; i++) 
	{
        	if (departments[i] != null) 
		{
                	departments[i].labs = labs;
            	}
        }
        
        University myUniversity = new University("COMSATS University", departments, "Dr. Syed Asad Hussain", 
	"Director", "Defence Road", "Lahore");

        myUniversity.displayUniversityDetails();
        for (Department dept : departments) 
	{
        	if (dept != null) 
		{
                	dept.displayDepartmentDetails();
            	}
        }
        for (Lab lab : labs) 
	{
        	if (lab != null) 
		{
                	lab.displayLabDetails();
            	}
        }
    }
}
