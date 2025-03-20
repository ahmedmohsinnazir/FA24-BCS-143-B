public class Screen
{
	private int screenNumber;
	private String movieTitle;
	private Seat seats[][];

	public Screen(int screenNumber, String movieTitle, int rows, int cols)
	{
		this.screenNumber = screenNumber;
		this.movieTitle = movieTitle;
		this.seats = new Seat[rows][cols];
	}

	public boolean bookSeat(int row, int col, Customer customer)
	{
		return seats[row][col].bookSeat();
	}

	public void displayScreen()
	{
		System.out.println("Screen Details: ");
		System.out.println("Screen Number: " + screenNumber);
		System.out.println("Movie Title: " + movieTitle);
	}
}