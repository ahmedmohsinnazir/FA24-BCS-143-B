public abstract class Shape implements Drawable 
{
    private String name;
    
    Shape(String name)
    {
        this.name = name;
    }
    
    abstract double area();

    @Override
    public String toString() 
    {
        return "Name: " + name;
    }
}