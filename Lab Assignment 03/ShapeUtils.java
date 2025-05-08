public class ShapeUtils 
{
    public void printShapes(Shape[] shapes)
    {
        for (Shape shape : shapes)
        {
            if (shape != null)
            {
                System.out.println(shape);
                shape.draw();
            }
        }
    }
    
    public void increaseRectangleLength(Shape[] shapes, double increment)
    {
        for (Shape shape : shapes)
        {
            if (shape instanceof Rectangle)
            {
                Rectangle rect = (Rectangle) shape;
                rect.setLength(rect.getLength() + increment);
            }
        }
    }
    
    public boolean checkRectangleIntersection(Shape[] shapes, Rectangle other)
    {
        for (Shape shape : shapes)
        {
            if (shape instanceof Rectangle)
            {
                Rectangle rect = (Rectangle) shape;
                if (doRectanglesIntersect(rect, other))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean doRectanglesIntersect(Rectangle r1, Rectangle r2)
    {
        double r1Left = r1.getTopLeft().getX();
        double r1Right = r1Left + r1.getLength();
        double r1Top = r1.getTopLeft().getY();
        double r1Bottom = r1Top + r1.getWidth();
        
        double r2Left = r2.getTopLeft().getX();
        double r2Right = r2Left + r2.getLength();
        double r2Top = r2.getTopLeft().getY();
        double r2Bottom = r2Top + r2.getWidth();
        
        return !(r2Left > r1Right || 
                r2Right < r1Left || 
                r2Top > r1Bottom ||
                r2Bottom < r1Top);
    }
}