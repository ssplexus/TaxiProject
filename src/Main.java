import Taxi.TaxiStation;

public class Main
{
    public static void main(String[] args) throws InterruptedException {
        TaxiStation taxiStation = new TaxiStation();
        taxiStation.addNewTaxi("Black Kia 125");
        taxiStation.addNewTaxi("Red BMW 151");
        taxiStation.addNewTaxi("Green Mercedes 234");
        taxiStation.addNewTaxi("Blue Hyundai 452");
        taxiStation.addNewTaxi("Whit Kia 167");

        taxiStation.start();
        int clientsCount = 0;
        while (taxiStation.isAlive())
        {
            if(clientsCount < 10) taxiStation.addClient(++clientsCount);
            Thread.sleep(100);
        }
        System.out.println("Exit");
    }
}
