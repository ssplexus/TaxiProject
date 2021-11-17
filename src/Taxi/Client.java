package Taxi;

/**
 * Класс клиента
 */
public class Client implements Runnable
{
    // Константа времени ожидания такси
    private static final int WAITING_TIME = 3000;
    // Идентификатор клиента
    private int clientId;
    // Признак выполнения заказа (клиент едет в такси)
    private boolean orderProcessed;
    // Признак, что такси назначено
    private boolean taxiAssigned;

    /**
     * Конструктор клиента
     * @param clientId - идентификатор
     */
    public Client(int clientId)
    {
        orderProcessed = false;
        taxiAssigned = false;
        this.clientId = clientId;
        System.out.printf("Client %d ordered taxi\n", clientId);
    }

    /**
     * Установить признак, что заказ обрабатывается
     * @param orderProcessed
     */
    public void setProcessed(boolean orderProcessed)
    {
        this.orderProcessed = orderProcessed;
    }

    /**
     * Получить признак назначено ли такси
     * @return - признак
     */
    public boolean isTaxiAssigned()
    {
        return taxiAssigned;
    }

    /**
     * Вернуть идентификатор клиента
     * @return - идентификатор
     */
    public int getClientId()
    {
        return clientId;
    }

    @Override
    public void run()
    {
        taxiAssigned = true;
        try
        {
            Thread.sleep(WAITING_TIME); // Клиент ждёт приезда такси
            if(!orderProcessed) // Если в течение времени ожидания заказ не взят в обработку, то клиент отменяет заказ
                   taxiAssigned = false;
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
