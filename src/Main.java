import java.io.*;
import java.util.Scanner;
//import java.io.IOException;
import java.FileInputStream;
import java.FileOutPutStream;

// Interfaces
interface RadioTV {
    void watchTelevision();
    void playRadio();
}

interface AirConditioner {
    void manageTemperature();
}

  // init exception
class SeatNumberException extends Exception {
    public SeatNumberException(String message) {

        super(message);
    }
}

      // class car
class Car implements RadioTV, Serializable {
    private static final long serialVersionUID = 1L;

    // Class car properties
    private String color;
    private int numberOfSeats;
    private int minHeight;
    private int maxHeight;

    public Car(String color, int numberOfSeats, int minHeight, int maxHeight) {
        this.color = color;
        this.numberOfSeats = numberOfSeats;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public void watchTelevision() {

        System.out.println("Watching television in the car.");
    }

    @Override
    public void playRadio() {

        System.out.println("Play radio in the car.");
    }

}

class Toyota extends Car {
    public Toyota(String color, int numberOfSeats, int minHeight, int maxHeight) {
        super(color, numberOfSeats, minHeight, maxHeight);
    }
}

class Benz extends Car implements AirConditioner {
    public Benz(String color, int numberOfSeats, int minHeight, int maxHeight) throws SeatNumberException {
        super(color, numberOfSeats, minHeight, maxHeight);
        if (numberOfSeats > 100) {
            throw new SeatNumberException("The number of seats should be less than 100");
        }
    }
    @Override
    public void manageTemperature() {

        System.out.println("Manage temperature in Benz car!");
    }
}

         // let's try to create some objects and give them values
public class Main {
    public static void main(String[] args) {
        // objects to use
        Toyota toyota = new Toyota("Blue", 3, 190, 290);
        Car car = new Car("Green", 7, 170, 350);

        try {
            Benz benz = new Benz("White", 120, 200, 500);

            //Catch SeatNumberException
        } catch (SeatNumberException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // Serialization
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("car.ser"))) {
            objectOutputStream.writeObject(car);
            objectOutputStream.writeObject(toyota);
        } catch (IOException e) {
            e.getMessage();
        }

        // Read file and display content
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("car.ser"))) {
            while (true) {
                try {
                    Car readCar = (Car) objectInputStream.readObject();
                    System.out.println(readCar);
                } catch (EndOfException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.toString();
        }
    }
}
