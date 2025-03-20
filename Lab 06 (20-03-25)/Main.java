public class Main
{
	public static void main(String args[])
	{
		Cinema c1 = new Cinema("Cinepax", "Lake City, Lahore", 5);
		Screen s1 = new Screen(12, "Interstellar", 5, 5);
		Screen s2 = new Screen(13, "Inception", 5, 5);
		c1.addScreen(0, s1);
		c1.addScreen(1, s2);
		c1.displayCinema();

		Customer cust1 = new Customer(110, "Muhammad Ali", "12345678910", "ali@gmail.com");
		Ticket ticket1 = new Ticket(120, cust1, 1, 5, "Interstellar", 9.99);
		ticket1.displayTicket();

		
		
	}
}