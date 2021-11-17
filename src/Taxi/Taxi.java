package Taxi;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Класс такси
 */
public class Taxi extends Thread
{
    /**
     * Статические списки для такси и клиентов
     */
    public static List<Taxi> taxis = Collections.synchronizedList(new ArrayList<Taxi>());
    public static LinkedBlockingDeque<Client> clients = new LinkedBlockingDeque<>();

    // Константа максимального времени бездействия такси
    private static final int MAX_IDLE = 10000;
    // Константа минимального времени в пути для имитации времени в пути
    private static final int MIN_ROUTE_TIME = 2000;
    // Название такси
    private String taxiName;
    // Счётчик времени бездействия такси
    private int timeIdle;
    // Всего клиентов за рабочий день
    private int totalClients;
    // Ссылка для хранения текущего клиента
    private Client client;

    /**
     * Конструктор  такси
     * @param taxiName - название такси
     */
    public Taxi(String taxiName)
    {
        this.taxiName = taxiName;
        timeIdle = 0;
        client = null;
        totalClients = 0;
        taxis.add(this); // Автоматически добавляем в список такси при создании объекта
    }

    /**
     * Назначить клиенту такси
     * @param client - объект клиента
     */
    public void setClient(Client client)
    {
        if(client == null) return;
        this.client = client;
        new Thread(client).start(); // Клиент ждёт приезда назначенного такси
        System.out.println(taxiName + " assigned to client " + this.client.getClientId());
    }

    @Override
    public void run()
    {
        System.out.println(taxiName + " on the route");

        // Такси работает пока время бездействия не превышает максимального времени бездействия
        while (timeIdle < MAX_IDLE)
        {
            try
            {
                // Назначаем клиента если есть, если нет, то ждём пока не появится в течении 1 сек
                setClient(clients.poll(1000, TimeUnit.MILLISECONDS));

                if(client != null) // если клиент назначен
                {
                    timeIdle = 0;
                    sleep(MIN_ROUTE_TIME + new Random().nextInt(MIN_ROUTE_TIME)); // время в пути до клиента
                    if(client.isTaxiAssigned()) // если за это время клиент не отменил заказ
                    {
                        client.setProcessed(true); // такси везёт клиента
                        sleep(MIN_ROUTE_TIME + new Random().nextInt(MIN_ROUTE_TIME)); // время в пути до точки
                        System.out.printf("%s: %d order completed successfully!\n", taxiName, client.getClientId());
                        totalClients++;
                        client = null;
                    }
                    else
                    {
                        System.out.printf("%s: %d client canceled order!\n", taxiName, client.getClientId());
                        clients.put(client); // если клиент отменил заказ то он возвращается в список активных клиентов
                        client  = null;
                    }
                }
                else timeIdle += 1000;  // если клиент не назначен, то увеличиваем счётчик времени бездействия
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                break;
            }
        }
        System.out.println(taxiName + " left, total clients - " + totalClients);
        taxis.remove(this); // если такси долго бездействует, то уходит с маршрута
    }
}
