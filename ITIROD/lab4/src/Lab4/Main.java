package Lab4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Yuri Krylov
 * The main class includes common operations like creating and maintaining bank activity.
 */
public class Main {


	static final int cashiersCount = 10;
	static final int maxMoneyCount = 1000;
    static final int accountsCount = 20;
    static final int clientsCount = 10;


	public static void main(String[] args) throws IOException {

		final Bank bank = new Bank(maxMoneyCount, clientsCount, cashiersCount, accountsCount);
		BankObserver observer = new BankObserver(bank);

		List<Client> clients = new ArrayList<Client>(clientsCount);
		for (int i = 0; i < clientsCount; i++)
		{
			Account wallet = new Account(i, 0);
			Client c = new Client(i, wallet);
			bank.addNewClient(c);
			clients.add(c);
		}

		for (int i = 0; i < cashiersCount; i++) {
            Cashier cashier = new Cashier(i);
			bank.registerCashier(cashier);
		}

		bank.open();
		observer.startChecking(200);

		List<Thread> clientThreads = new ArrayList<Thread>(clientsCount);
		for (int i = 0; i < clientsCount; i++)
		{
			final Client client = clients.get(i);

			Thread clientThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (!bank.isClosed())
					{
						client.generateNewOperation(bank);
						if (!bank.processClient(client))
							break;
						try {
							Thread.sleep(100);
						} catch (Exception e) {
						}
					}
				}
			});
			clientThreads.add(clientThread);
			clientThread.start();
		}

		System.in.read();
		bank.close();
		observer.stop();



		for (int i = 0; i < clientsCount; i++)
		{
			try {
				Thread clientThread = clientThreads.get(i);
				synchronized (clientThread)
				{
					clientThread.notify();
				}
				clientThread.join();
			} catch (InterruptedException e) {

			}
		}

		bank.logStats();
	}
}
