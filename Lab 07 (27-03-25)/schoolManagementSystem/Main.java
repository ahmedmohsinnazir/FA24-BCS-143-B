public class Main 
{
	public static void main(String[] args) 
	{
        	Principal principal = new Principal("Mr. Asad", 40, 15);
        	School school = new School("International School", "55 Avenue, Lahore", principal);

        	Teacher t1 = new Teacher("Ms. Sana", 40, "Maths", "TCH1");
        	Teacher t2 = new Teacher("Mr. Saeed", 25, "Computer Science", "TCH2");

        	Classroom c1 = new Classroom("Class 8", "C08", t1);
        	Classroom c2 = new Classroom("Class 9", "C09", t2);

        	school.addClassroom(c1);
        	school.addClassroom(c2);

        	Student s1 = new Student("Ahmed", 14, "S1");
        	Student s2 = new Student("Rumaan", 13, "S2");
        	Student s3 = new Student("Luqman", 13, "S3");
        	Student s4 = new Student("Abdullah", 12, "S4");
        	Student s5 = new Student("Hamzah", 12, "S5");
        	Student s6 = new Student("Azeem", 14, "S6");

        	class1.addStudent(s1);
        	class1.addStudent(s2);
        	class1.addStudent(s3);
        	class1.addStudent(s4);
        	class1.addStudent(s5);
        	class1.addStudent(s6); 

        	class2.addStudent(new Student("Usman", 14, "S7"));
        	class2.addStudent(new Student("Asad", 14, "S8"));

        	System.out.println(school);

        	Student s9 = new Student("Hadi", 14, "S9");
        	System.out.println("\nComparing Students: " + s5.equals(s7));

        	Teacher t3 = new Teacher("Mr. Khan", 48, "Physics", "TCH3");
        	System.out.println("Comparing Teachers: " + t2.equals(3));
    	}
}
