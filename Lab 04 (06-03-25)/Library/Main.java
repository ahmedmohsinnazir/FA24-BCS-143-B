public class Main
{
	public static void main(String args[])
	{
		Address a1 = new Address("5", "Lahore");
		Address a2 = new Address("22", "Lahore");
		Address a3 = new Address("1", "Lahore");

		Person p1 = new Person("Muhammad Ali", "Author", a1);
		Person p2 = new Person("Eric Smith", "Incharge", a2);
		Person p3 = new Person("Dwight Clark", "Staff", a3);

		Date d1 = new Date(1, 3, 2025);

		Book b1 = new Book("The Martian", "E2398H", d1, p1);

		Library l1 = new Library("Pine Avenue Library", b1, p2, p3);

		l1.showLibraryDetails();
	}
}