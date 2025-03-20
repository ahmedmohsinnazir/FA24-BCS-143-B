public class Seat
{
	private int seatNumber;
	private int rowNumber;
	private String seatType;
	private double price;
	private boolean isBooked;

	public Seat(int seatNumber, int rowNumber, String seatType, double price)
	{
		this.seatNumber = seatNumber;
		this.rowNumber = rowNumber;
		this.seatType = seatType;
		this.price = price;
		this.isBooked = false;
	}

	public boolean bookSeat()
	{
		if(!(isBooked))
			isBooked = true;
			return isBooked;
	}

	public void display()
	{
		System.out.println("Seat Details:");
		System.out.println("Seat Number:" + seatNumber);
		System.out.println("Seat Row:" + rowNumber);
		System.out.println("Seat Type:" + seatType);
		System.out.println("Seat Price:" + price);
		if(isBooked)
		{
			System.out.println("Seat not Available");
		}
		else
		{
			System.out.println("Seat Available");
		}
	}
}