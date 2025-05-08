public class Cube extends Shape3D
{
    private double side;
    
    Cube(String name, double side)
    {
        super(name);
        this.side = side;
    }
    
    @Override
    double volume()
    {
        return side * side * side;
    }
    
    @Override
    double area()
    {
        return 6 * side * side;
    }
    
    @Override
    public String toString()
    {
        return super.toString() + "\nSide: " + side + "\nArea: " + area() + "\nVolume: " + volume();
    }
    
    @Override
    public void draw()
    {
        System.out.println("Drawing a cube");
    }
}