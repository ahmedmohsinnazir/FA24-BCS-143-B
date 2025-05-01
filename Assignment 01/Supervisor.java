public class Supervisor extends Person 
{
    private int yearsOfExperience;

    public Supervisor(String name, int yearsOfExperience) 
	{
        super(name);
        setYearsOfExperience(yearsOfExperience);
    }

    public void setYearsOfExperience(int yearsOfExperience) 
	{
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getYearsOfExperience() 
	{
        return yearsOfExperience;
    }

    @Override
    public String toString() 
	{
        return super.toString() + "\nYears of Experience: " + yearsOfExperience;
    }
}