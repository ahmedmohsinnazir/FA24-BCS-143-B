public class Book
{
	private String title;
	private String issn;
	private Date publicationDate;
	private Person author;

	Book(String title, String issn, Date publicationDate, Person author)
	{
		this.title = title;
		this.issn = issn;
		this.publicationDate = publicationDate;
		this.author = author;	
	}

	Book(Book other) 
	{
    		this.title = other.title;
    		this.issn = other.issn;
    		this.publicationDate = new Date(other.publicationDate); 
    		this.author = new Person(other.author); 
	}


	public void showBookDetails() 
	{
    		System.out.println("Book title: " + title);
    		System.out.println("Book ISSN: " + issn);
    		System.out.print("Book publication date: ");
    		publicationDate.showDate();
    		System.out.println("Book author: ");
    		author.showPersonDetails(); 
	}


	public boolean equals(Object obj) 
	{
        	Book book = (Book) obj;
    		if(issn.equals(book.issn))
		{
			return true;	
		}
		else
		{
			return false;	
		}
	}


	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getIssn()
	{
		return issn;
	}

	public void setIssn(String issn)
	{
		this.issn = issn;
	}
}












