public class Teacher extends Person
{
	private String subject;
	private String ID;

	public Teacher(String name, int age, String subject, String ID)
	{
		super(name, age);
		this.subject = subject;
		this.ID = ID;
	}

	@Override
	public String toString()
	{
		return String.format("Name: %s, Age: %d, ID: %s, Subject: %s", name, age, ID, subject);
	}

	@Override
	public boolean equals(Teacher obj)
	{
		Teacher teacher = (Teacher) obj;
		if(ID.equals(teacher.ID))
		{
			return true;
		}	
		else
		{
			return false;
		}
	}
}