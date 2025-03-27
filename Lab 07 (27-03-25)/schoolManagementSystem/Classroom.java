public class Classroom
{
	private String name;
	private String code;
	private Teacher teacher;
	private Student students[];
	private int studentCount = 0;

	public Classroom(String name, String code, Teacher teacher)
	{
		this.name = name;
		this.code = code;
		this.teacher = teacher;
		this.students = new Student[5];
	}

    	public void addStudent(Student student) 
	{
		if (studentCount >= 5) 
		{
            		System.out.println("Cannot add student. Class is full.");
        	} 
		else 
		{
            		students[studentCount++] = student;
            		System.out.println("Student " + student.name + " added to " + name);
        	}
    	}

	@Override
    	public String toString() 
	{
        	StringBuilder builder = new StringBuilder();
        	builder.append(String.format("\nClassroom: %s (%s)\n", name, code));
        	builder.append(teacher.toString()).append("\n");
        	builder.append("Students:\n");
        	for (int i = 0; i < studentCount; i++) 
		{
            		builder.append((i + 1)).append(". ").append(students[i].toString()).append("\n");
        	}
        	return builder.toString();
    	}
}
