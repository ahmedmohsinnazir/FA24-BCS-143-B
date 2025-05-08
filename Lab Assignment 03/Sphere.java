public class Sphere extends Shape3D{
    double radius;
    Sphere(String name, double radius){
        super(name);
        this.radius=radius;

    }
    @Override
    double volume() {
        return 4/3*Math.PI*radius*radius * radius;
    }

    @Override
    double area() {
        return (double) 4 * Math.PI*radius * radius;
    }
    public String toString(){
        return super.toString() + "\nRadius: " + radius + "\nArea: " + area()+ "\nVolume: " + volume();
    }

    @Override
    public void draw() {
        System.out.println("Draw method of sphere");
    }
}