public class Cinema
{
	private String name;
	private String location;
	private Screen screens[];

	public Cinema(String name, String location, int totalScreens)
	{
		this.name = name;
		this.location = location;
		this.screens = new Screen[totalScreens];
	}

	public void addScreen(int index, Screen screen)
	{
		this.screens[index] = screen; 
	}

	public void displayCinema()
	{
		System.out.println("Cinema Details:");
		System.out.println("Cinema Name:" + name);
		System.out.println("Cinema Location:" + location);
		for(int i = 0; i < screens.length; i++)
		{
			if(screens[i]!=null)
			{
				screens[i].displayScreen();
			}
		}
		
	}
}