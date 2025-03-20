public class Ticket
{
	private int ticketID;
	private Customer customer;
	private int screenNumber;
	private int seatNumber;
	private String movieTitle;
	private double price;

	public Ticket(int ticketID, Customer customer, int screenNumber, int seatNumber, String movieTitle,
	double price)
	{
		this.ticketID = ticketID;
		this.customer = customer;
		this.screenNumber = screenNumber;
		this.seatNumber = seatNumber;
		this.movieTitle = movieTitle;
		this.price = price;
	}

	public void displayTicket()
	{
		System.out.println("Ticket Details:");
		System.out.println("Ticket ID: " + ticketID);
		System.out.println("Screen Number: " + screenNumber);
		System.out.println("Seat Number: " + seatNumber);
		System.out.println("Movie Title: " + movieTitle);
		System.out.println("Ticket Price :" + price);
		System.out.println("Customer Details:");
		customer.displayCustomer();
	}
}