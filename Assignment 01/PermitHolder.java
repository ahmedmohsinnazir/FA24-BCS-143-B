public class PermitHolder extends Person 
{
    private static int idCounter = 1;
    private String permitId;

    public PermitHolder(String name) 
	{
        super(name);
        this.permitId = "PH" + String.format("%d", idCounter++);
    }

    public String getPermitId() 
	{
        return permitId;
    }

    @Override
    public String toString() 
	{
        return super.toString() + "\nID: " + permitId;
    }

	@Override
	public boolean equals(Object obj)
	{
		PermitHolder permitHolder = (PermitHolder) obj;
		if(permitId.equals(permitHolder.getPermitId()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}