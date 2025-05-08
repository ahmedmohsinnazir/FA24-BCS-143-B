public class Rectangle extends Shape2D
{
    private double length;
    private double width;
    private Point topLeft;
    
    Rectangle(String name, double x, double y)
    {
        super(name);
        this.topLeft = new Point(x, y);
    }
    
    @Override
    double area()
    {
        return length * width;
    }
    
    public void setWidth(double width)
    {
        this.width = width;
    }
    
    public double getWidth()
    {
        return width;
    }
    
    public void setLength(double length)
    {
        this.length = length;
    }
    
    public double getLength()
    {
        return length;
    }
    
    public Point getTopLeft()
    {
        return topLeft;
    }
    
    @Override
    public String toString()
    {
        return super.toString() + "\nLength: " + length + "\nWidth: " + width + "\nArea: " + area();
    }
    
    @Override
    public void draw()
    {
        System.out.println("Drawing a rectangle");
    }
}