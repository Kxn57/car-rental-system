package model;
public class Customer {
    private String id;
    private String name;
    private String contact;
    private int points;

    public Customer(String id, String name,  String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.points = 0; 
    }

    public int getPoints() {
        return points;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void usePoints(int pts) {
    this.points -= pts;
    }


    @Override
    public String toString() {
        return name + ", Contact: " + contact + ", Points: " + points + ")";
    }
}
