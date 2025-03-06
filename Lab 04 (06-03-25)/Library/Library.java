public class Library
{
	private String name;
	private Book book;
	private Person incharge;
	private Person staff;


	Library(String name, Book book, Person incharge, Person staff)
	{
		this.name = name;
		this.book = book;
		this.incharge = incharge;
		this.staff = staff;
	}

	Library(Library other)
	{
		this.name = other.name;
		this.book = other.book;
		this.incharge = other.incharge;
		this.staff = other.staff;
	}

	public void showLibraryDetails()
	{
		System.out.println("Name: " + name);
		System.out.println("Book: " + book);
		System.out.println("Incharge: " + incharge);
		System.out.println("Staff: " + staff);
	}

	public boolean equals(Library obj)
	{
		if((this.name == obj.name) && (this.incharge == obj.incharge) && (this.staff == obj.staff))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}