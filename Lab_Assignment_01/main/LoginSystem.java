package main;
import util.PasswordValidator;
import users.UserManager;
import java.io.Console;
import java.util.Scanner;

public class LoginSystem
{
	public static void main(String args[])
	{
		Console console = System.console();
		System.out.println("Enter username: ");
		String username = console.read();

		System.out.println("Enter password: ");
		String password = console.readPassword();
		
		if(UserManager.authenticate(username, password))
		{
			System.out.println("Access Granted.");
		}
		else
		{
			System.out.println("Access denied.");
		}

		if(PasswordValidator.isValid(password))
		{
			System.out.println("Password Validated.");
		}
		else
		{
			System.out.println("Password Not Validated.");
		}
	}
}