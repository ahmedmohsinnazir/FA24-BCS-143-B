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
    		this.book = new Book(other.book);
    		this.incharge = new Person(other.incharge);
    		this.staff = new Person(other.staff); 
	}

	public void showLibraryDetails() 
	{
    		System.out.println("Library Name: " + name);
    		book.showBookDetails(); 
    		incharge.showPersonDetails();
    		staff.showPersonDetails(); 
	}

	public boolean equals(Object obj) 
	{
    		Library library = (Library) obj;
    		if((name.equals(library.name)) && (book.equals(library.book)) && (incharge.equals(library.incharge)) && (staff.equals(library.staff)))
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
