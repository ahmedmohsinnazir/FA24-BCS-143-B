public class Vehicle
{
	private String licensePlate;
	private String type;
	private Owner owner;

	public Vehicle(String licensePlate, String type, Owner owner)
	{
		setLicensePlate(licensePlate);
		setType(type);
		this.owner = owner;
	}

	public String getLicensePlate()
	{
		return licensePlate;
	}

	public String getType()
	{
		return type;
	}

	public Owner getOwner()
	{
		return owner;
	}

	public void setLicensePlate(String licensePlate)
	{
		this.licensePlate = licensePlate;
	}


	public void setType(String type)
	{
		this.type = type;
	}

	public void shallowCopy(Vehicle other)
	{
		this.licensePlate = other.licensePlate;
		this.type = other.type;
		this.owner = other.owner;
	}

	public void deepCopy(Vehicle other, String name)
	{
		this.licensePlate = other.licensePlate;
		this.type = other.type;
		this.owner = new Owner(name);
	}

	@Override
	public String toString() 
	{
		return "License Plate: " + licensePlate + ", Type: " + type + ", Owner: " + owner;
	}	
}