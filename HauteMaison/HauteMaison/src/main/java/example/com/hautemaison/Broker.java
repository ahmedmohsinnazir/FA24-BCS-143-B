package example.com.hautemaison;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Broker implements Serializable {
    private String username;
    private String password;
    private List<Property> properties = new ArrayList<>();

    public Broker(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Property> getProperties() { return properties; }
    public void setProperties(List<Property> properties) { this.properties = properties; }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }
}