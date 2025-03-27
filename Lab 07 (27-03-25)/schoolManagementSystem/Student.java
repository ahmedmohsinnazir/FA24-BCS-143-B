public class Student extends Person
{
	private String rollNum;
	
	public Student(String name, String age, int rollNum)
	{
		super(name, age);
		this.rollNum = rollNum;
	}	

	@Override
	public String toString()
	{
		return String.format("Name: %s, Age: %d, Roll Number: %s", name, age, rollNum);
	}

	@Override
	public boolean equals(Student obj)
	{
		Student student = (Student) obj;
		if(rollNum.equals(student.rollNum))
		{
			return true;
		}	
		else
		{
			return false;
		}
	}
}