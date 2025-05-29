package example.com.hautemaison;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Property implements Serializable {
    private String type;
    private String location;
    private double price;
    private List<String> imagePaths;
    private String status;
    private double size;
    private double roadSize;
    private double plotWidth;
    private double plotDepth;
    private double roadDepth;
    private String facing;
    private boolean electricWiring;
    private boolean corner;
    private boolean nearMasjid;
    private boolean nearMarket;
    private boolean nearSchool;
    private boolean nearHospital;
    private boolean nearTransport;
    private boolean parkFacing;
    private boolean isActive;
    // New fields for House
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private boolean hasGarage;
    // New fields for Apartment
    private int floorNumber;
    private int numberOfRooms;
    private boolean hasElevatorAccess;
    // New fields for Plot
    private boolean isCommercial;

    public Property() {
        this.imagePaths = new ArrayList<>();
        this.isActive = true;
    }

    public Property(String type, String location, double price, List<String> imagePaths, String status) {
        this.type = type;
        this.location = location;
        this.price = price;
        this.imagePaths = imagePaths != null ? new ArrayList<>(imagePaths) : new ArrayList<>();
        this.status = status;
        this.isActive = true;
    }

    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<String> getImagePaths() { return imagePaths; }
    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths != null ? new ArrayList<>(imagePaths) : new ArrayList<>();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getSize() { return size; }
    public void setSize(double size) { this.size = size; }

    public double getRoadSize() { return roadSize; }
    public void setRoadSize(double roadSize) { this.roadSize = roadSize; }

    public double getPlotWidth() { return plotWidth; }
    public void setPlotWidth(double plotWidth) { this.plotWidth = plotWidth; }

    public double getPlotDepth() { return plotDepth; }
    public void setPlotDepth(double plotDepth) { this.plotDepth = plotDepth; }

    public double getRoadDepth() { return roadDepth; }
    public void setRoadDepth(double roadDepth) { this.roadDepth = roadDepth; }

    public String getFacing() { return facing; }
    public void setFacing(String facing) { this.facing = facing; }

    public boolean isElectricWiring() { return electricWiring; }
    public void setElectricWiring(boolean electricWiring) { this.electricWiring = electricWiring; }

    public boolean isCorner() { return corner; }
    public void setCorner(boolean corner) { this.corner = corner; }

    public boolean isNearMasjid() { return nearMasjid; }
    public void setNearMasjid(boolean nearMasjid) { this.nearMasjid = nearMasjid; }

    public boolean isNearMarket() { return nearMarket; }
    public void setNearMarket(boolean nearMarket) { this.nearMarket = nearMarket; }

    public boolean isNearSchool() { return nearSchool; }
    public void setNearSchool(boolean nearSchool) { this.nearSchool = nearSchool; }

    public boolean isNearHospital() { return nearHospital; }
    public void setNearHospital(boolean nearHospital) { this.nearHospital = nearHospital; }

    public boolean isNearTransport() { return nearTransport; }
    public void setNearTransport(boolean nearTransport) { this.nearTransport = nearTransport; }

    public boolean isParkFacing() { return parkFacing; }
    public void setParkFacing(boolean parkFacing) { this.parkFacing = parkFacing; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    // New getters and setters for House
    public int getNumberOfBedrooms() { return numberOfBedrooms; }
    public void setNumberOfBedrooms(int numberOfBedrooms) { this.numberOfBedrooms = numberOfBedrooms; }

    public int getNumberOfBathrooms() { return numberOfBathrooms; }
    public void setNumberOfBathrooms(int numberOfBathrooms) { this.numberOfBathrooms = numberOfBathrooms; }

    public boolean isHasGarage() { return hasGarage; }
    public void setHasGarage(boolean hasGarage) { this.hasGarage = hasGarage; }

    // New getters and setters for Apartment
    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }

    public int getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public boolean isHasElevatorAccess() { return hasElevatorAccess; }
    public void setHasElevatorAccess(boolean hasElevatorAccess) { this.hasElevatorAccess = hasElevatorAccess; }

    // New getters and setters for Plot
    public boolean isCommercial() { return isCommercial; }
    public void setCommercial(boolean commercial) { isCommercial = commercial; }
}