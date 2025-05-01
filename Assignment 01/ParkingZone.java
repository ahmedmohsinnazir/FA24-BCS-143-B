public class ParkingZone 
{
    private static int zoneCounter = 1;
    private String zoneId;
    private Vehicle vehicles[];
    private int currentCount;

    public ParkingZone() 
	{
        this.zoneId = "Z" + zoneCounter++;
        this.vehicles = new Vehicle[5];
        this.currentCount = 0;
    }

    public String getZoneId() 
	{
        return zoneId;
    }

    public void addVehicle(Vehicle vehicle) 
	{
        if(ParkingSystem.isLicensePlateExists(vehicle.getLicensePlate())) 
		{
            System.out.println("Error: Duplicate license plate '" + vehicle.getLicensePlate() + "' is not allowed.");
            return;
        }

        if(currentCount < vehicles.length) 
		{
            vehicles[currentCount++] = vehicle;
            ParkingSystem.registerLicensePlate(vehicle.getLicensePlate());
        } 
		else 
		{
            System.out.println("Parking Zone Full.");
        }
    }

	@Override
	public String toString() 
	{
		String result = "Zone ID: " + zoneId + ", Vehicles: [";
		for(int i = 0; i < vehicles.length; i++) 
		{
			if(vehicles[i] != null) 
			{
				result += vehicles[i];
			} 
			else
			{
				result += "null";
			}
			if(i < vehicles.length - 1) 
			{
				result += ", ";
			}
		}
		result += "]";
		return result;
	}
}