package Taxi;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс таксопарка
 */
public class TaxiStation extends Thread
{
    // Список такси которые работают в таксопарке
    private List<String> taxiList = new ArrayList<String>();

    /**
     * Метод добавления нового такси в таксопарк
     * @param name - название такси
     */
    public void addNewTaxi(String name)
    {
        taxiList.add(name);
    }

    /**
     * Метод выпуска такси из таксопарка
     */
    public void runTaxis()
    {
        for(String taxiName: taxiList)
        {
            new Taxi(taxiName).start();
        }

    }

    /**
     *  Метод добавления новых клиентов
     * @param clientId - идентификатор клиента
     */
    public void addClient(int clientId)
    {
        Taxi.clients.add(new Client(clientId));
    }

    @Override
    public void run()
    {
        runTaxis(); // выпускаем такси из таксопрака

        while (!Taxi.taxis.isEmpty()) // завершение работы таксопарка когда все такси вернутся в парк
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("Taxi station closed");
    }
}
