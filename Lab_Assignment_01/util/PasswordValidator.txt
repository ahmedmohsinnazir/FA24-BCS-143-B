package util;

public class PasswordValidator
{
	class Rules
	{
		boolean isValid(String password)
		{
			if(password != null)
			{
				return true;	
			}
			else
			{
				return false;
			}	
		}
	}
}