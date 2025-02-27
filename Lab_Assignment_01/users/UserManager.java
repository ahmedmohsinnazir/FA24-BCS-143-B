package users;

public class UserManager
{
	static final String storedUsername = "ahmedmohsinnazir";
	static final String storedPassword = "ahmed123";

	static class LoginProcessor
	{
		static boolean authenticate(String username, String password)
		{
			if(storedUsername.equals(username))
			{
				if(storedPassword.equals(password))
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		}
	}
}