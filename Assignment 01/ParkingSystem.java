public class ParkingSystem 
{
    private static ParkingSystem instance = null;
    private String campusName;
    private Supervisor supervisor;
    private ParkingZone parkingZones[];
    private int zoneCount;
    private PermitHolder permitHolders[];
    private int permitHolderCount;
    private static String registeredLicensePlates[] = new String[100];
    private static int licensePlateCount = 0;

    private ParkingSystem(String campusName, Supervisor supervisor) 
	{
        this.campusName = campusName;
        this.supervisor = supervisor;
        this.parkingZones = new ParkingZone[2];
        this.zoneCount = 0;
        this.permitHolders = new PermitHolder[2];
        this.permitHolderCount = 0;
    }

    public static ParkingSystem getInstance(String campusName, Supervisor supervisor) 
	{
        if(instance == null) 
		{
            instance = new ParkingSystem(campusName, supervisor);
        } 
		else 
		{
            System.out.println("Warning: ParkingSystem instance already exists. Returning existing instance.");
        }
        return instance;
    }

    public void addZone(ParkingZone zone) 
	{
        if (zoneCount < parkingZones.length) 
		{
            parkingZones[zoneCount++] = zone;
        } 
		else 
		{
            System.out.println("Cannot add more parking zones.");
        }
    }

    public void addPermitHolder(PermitHolder holder) 
	{
        if(permitHolderCount < permitHolders.length) 
		{
            permitHolders[permitHolderCount++] = holder;
        } 
		else 
		{
            System.out.println("Cannot add more permit holders.");
        }
    }

    public static boolean isLicensePlateExists(String licensePlate) 
	{
        for(int i = 0; i < licensePlateCount; i++) 
		{
            if(registeredLicensePlates[i].equals(licensePlate)) 
			{
                return true;
            }
        }
        return false;
    }

    public static void registerLicensePlate(String licensePlate) 
	{
        registeredLicensePlates[licensePlateCount++] = licensePlate;
    }

	@Override
	public String toString() 
	{
		String result = "Campus: " + campusName + "\n";
		result += "Supervisor: " + supervisor + "\n";
		result += "Zones: [";
	
		for(int i = 0; i < zoneCount; i++) 
		{
			result += parkingZones[i];
			if (i < zoneCount - 1) {
				result += ", \n           ";
			}
		}
		result += "]\n";
		result += "Permit Holders: [";
		for(int i = 0; i < permitHolderCount; i++) 
		{
			result += permitHolders[i];
			if (i < permitHolderCount - 1) 
			{
				result += ", ";
			}
		}
		result += "]";
		return result;
	}
}