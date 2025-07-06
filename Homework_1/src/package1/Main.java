package package1;

public class Main {
    public static void main(final String[] args) {
        Vehicle airplane = new Vehicle(true, true, true, true);
        Vehicle helicopter = new Vehicle(true, true, false, true);
        Vehicle speedboat = new Vehicle(false, true, false, true);
        Vehicle tanker = new Vehicle(false, true, false, true);
        Vehicle truck = new Vehicle(true, false, false, true);
        Vehicle taxi = new Vehicle(true, false, false, false);
    }
}
