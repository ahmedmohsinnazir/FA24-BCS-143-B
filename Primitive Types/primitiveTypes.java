public class primitiveTypes
{
	public static void main(String args[])
	{	
		// primitive types
		int i = 0;
		short s = 5;
		byte b = 100;
		long l = 300;
		float f = 10.0f;
		double d = 400.0;
		char ch = 'c';
		boolean bool = true; // or false

		System.out.println("Value in int: " + i);
		System.out.printf("Value in int: %d \n", i);
		System.out.printf("Value in short: %d \n", s);
		System.out.printf("Value in long: %d \n", l);
		System.out.printf("Value in byte: %d \n", b);
		System.out.printf("Value in float: %.2f \n", f);
		System.out.printf("Value in double: %.2f \n", d);
		System.out.printf("Value in boolean: %b \n", bool);
		System.out.printf("Value in char: %c \n", ch);

		System.out.printf("%03d\t%-10s\t%.2f\n", 1, "Ahmed Mohsin Nazir", 3.51);
		System.out.printf("%03d\t%-10s\t%.2f\n", 2, "Abdullah Khalid", 3.22);
		System.out.printf("%03d\t%-10s\t%.2f\n", 3, "Rumaan Adeel", 3.24);
		System.out.printf("%03d\t%-10s\t%.2f\n", 4, "Muhammad Luqman", 4.0);
		System.out.printf("%03d\t%-10s\t%.2f\n", 5, "Hamzah Fasih Butt", 3.33);
		
	}
}