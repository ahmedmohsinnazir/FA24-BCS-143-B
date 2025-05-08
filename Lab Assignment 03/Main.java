public class Main 
{
    public static void main(String[] args) 
    {
        // Create shapes
        Circle circle = new Circle("MyCircle");
        circle.setRadius(10.5);
        
        Rectangle rect1 = new Rectangle("Rect1", 0, 0);
        rect1.setLength(5);
        rect1.setWidth(3);
        
        Rectangle rect2 = new Rectangle("Rect2", 2, 1);
        rect2.setLength(4);
        rect2.setWidth(2);
        
        Cube cube = new Cube("MyCube", 7);
        
        // Store shapes in array
        Shape[] shapes = new Shape[8];
        shapes[0] = circle;
        shapes[1] = rect1;
        shapes[2] = rect2;
        shapes[3] = cube;
        
        // Fill remaining slots with some shapes
        for (int i = 4; i < shapes.length; i++)
        {
            if (i % 2 == 0)
            {
                shapes[i] = new Rectangle("Rect" + (i+1), i, i);
                ((Rectangle)shapes[i]).setLength(i+1);
                ((Rectangle)shapes[i]).setWidth(i+2);
            }
            else
            {
                shapes[i] = new Circle("Circle" + (i+1));
                ((Circle)shapes[i]).setRadius(i);
            }
        }
        
        // Test ShapeUtils
        ShapeUtils utils = new ShapeUtils();
        
        System.out.println("Original shapes:");
        utils.printShapes(shapes);
        
        System.out.println("\nIncreasing rectangle lengths by 2:");
        utils.increaseRectangleLength(shapes, 2);
        utils.printShapes(shapes);
        
        System.out.println("\nChecking rectangle intersections:");
        Rectangle testRect = new Rectangle("TestRect", 1, 1);
        testRect.setLength(3);
        testRect.setWidth(2);
        
        boolean intersects = utils.checkRectangleIntersection(shapes, testRect);
        System.out.println("Intersection exists: " + intersects);
    }
}