public abstract class Shape3D extends Shape
{
    Shape3D(String name)
    {
        super(name);
    }
    
    abstract double volume();
}