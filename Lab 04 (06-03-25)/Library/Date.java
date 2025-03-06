package Library;

public class Date
{
	private int day;
	private int month;
	private int year;

	Date(int day, int month, int year)
	{
		this.day = day;
		this.month = month;
		this.year = year;
	}

	Date(Date other) // Copy constructor
	{
		this.day = other.day;
		this.month = other.month;
		this.year = other.year;
	}

	public void showDate()
	{
		System.out.println("Day: " + day);
		System.out.println("Month: " + month);
		System.out.println("Year: " + year);
		
	}

	public boolean equals(Date obj)
	{
		if((this.day == obj.day) && (this.month == obj.month) && (this.year == obj.year))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;		
	}	
}