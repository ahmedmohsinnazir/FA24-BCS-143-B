public class Circle extends Shape2D
{
    private double radius;
    
    Circle(String name)
    {
        super(name);
    }
    
    void setRadius(double radius)
    {
        this.radius = radius;
    }
    
    double getRadius()
    {
        return radius;
    }
    
    @Override
    double area()
    {
        return Math.PI * radius * radius;
    }
    
    @Override
    public String toString()
    {
        return super.toString() + "\nRadius: " + radius + "\nArea: " + area();
    }
    
    @Override
    public void draw() 
    {
        System.out.println("Drawing a circle");
    }
}