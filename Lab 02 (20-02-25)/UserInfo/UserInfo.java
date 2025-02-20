import java.util.Scanner;

public class UserInfo
{
	static String username = "ahmedmohsinnazir";
	static String password = "ahmed123";
	
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
	
		System.out.print("Enter username: ");
		String userName = sc.nextLine();
		if(userName.equals(username));

			System.out.print("Enter password: ");
			String userPassword = sc.nextLine();
			if(userPassword.equals(password));
	}

}
