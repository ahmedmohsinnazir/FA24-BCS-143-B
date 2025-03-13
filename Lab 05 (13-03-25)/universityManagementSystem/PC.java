class PC 
{
	String id;
    	String processor;
    	int ram;
    	int disk;

    	PC(String id, String processor, int ram, int disk) 
	{
        	this.id = id;
        	this.processor = processor;
        	this.ram = ram;
        	this.disk = disk;
    	}

    	public void displayPCDetails() 
	{
        	System.out.println("PC ID: " + id + ", Processor: " + processor + ", RAM: " + 
		ram + "GB, Disk: " + disk + "GB");
    	}
}