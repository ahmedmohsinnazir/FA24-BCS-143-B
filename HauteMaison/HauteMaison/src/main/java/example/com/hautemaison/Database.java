package example.com.hautemaison;

import java.io.*;
import java.util.*;

public class Database {
    private static final String FILE_PATH = "brokers.db";

    public static List<Broker> loadBrokers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<Broker>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveBrokers(List<Broker> brokers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(brokers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}