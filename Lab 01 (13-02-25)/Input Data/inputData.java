import java.util.Scanner;
public class inputData
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		
		int i = 0;
		System.out.println("Enter an integer: ");
		i = sc.nextInt();
		System.out.printf("Value entered: %d \n", i);

		// sc.nextByte();
		// sc.nextShort();
		// sc.nextDouble();
		// sc.next(); (for a single word)
		// sc.nextLine(); (for a complete line)
		
		sc.nextLine();

		String name;
		System.out.println("Enter your name: ");
		name = sc.nextLine();
		System.out.println("Your name is: " + name);
	}
}