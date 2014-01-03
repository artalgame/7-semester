package Lab4;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Bank {
	private static Logger log = Logger.getLogger(Bank.class);

	private volatile boolean isClosed;
	private int totalOperationsCount;
	private int successOperationsCount;
	private int failedOperationsCount;
	private int skippedOperationsCount;

	private int maxMoneyCount;
	private int clientsCount;
	private int cashiersCount;
	private int bankAccountsCount;

	private List<Client> clients;
	private List<Cashier> cashiers;

	private List<Account> bankAccounts;
	private List<Account> clientWallets;

	private LinkedBlockingQueue<Client> clientsQueue;
	private LinkedBlockingQueue<Cashier> cashiersQueue;


	public Bank(int maxMoneyCount, int clientsCount, int cashiersCount, int bankAccountsCount) {
		clients = new ArrayList<Client>(clientsCount);
        cashiers = new ArrayList<Cashier>(cashiersCount);

		bankAccounts = new ArrayList<Account>(bankAccountsCount);
		clientWallets = new ArrayList<Account>(clientsCount);

		clientsQueue = new LinkedBlockingQueue<Client>();
        cashiersQueue = new LinkedBlockingQueue<Cashier>();

		this.maxMoneyCount = maxMoneyCount;
		this.clientsCount = clientsCount;
		this.cashiersCount = cashiersCount;
		this.bankAccountsCount = bankAccountsCount;
		isClosed = true;
		totalOperationsCount = 0;
	}

	public void addNewClient(Client client)
	{
		if (client == null)
			return;
		clients.add(client);
		clientWallets.add(client.getWallet());

		log.debug("New client:"+client.getClientNumber());
	}

	public void registerCashier(Cashier cashier)
	{
		if (cashier == null)
			return;
        cashiers.add(cashier);
        cashier.setBank(this);
		log.debug("Registered cashier #"+cashier.getCashierNumber());
	}

	public void open()
	{
		if (isClosed)
		{
			for (int i = 0; i < cashiersCount; i++)
			{
				addCashierToQueue(cashiers.get(i));
			}

			for (int i = 0; i < bankAccountsCount; i++)
			{
				Account a = new Account(i, maxMoneyCount / bankAccountsCount);
				bankAccounts.add(a);
			}


			isClosed = false;
			log.info("Bank opened");
		}
	}

	public void close()
	{
		if (!isClosed)
		{
			isClosed = true;
			log.info("Bank closed");
		}
	}

	public boolean processClient(Client client)
	{
		if (!isClosed)
		{
			log.debug("Processing client #" + client.getClientNumber());
			try {
                Cashier cashier = cashiersQueue.take();

                cashier.processClient(client);

				switch (client.getOperation().getStatus())
				{
					case OK:
						successOperationsCount++;
						break;
					case FAILED:
						failedOperationsCount++;
						break;
					case SKIPPED:
						skippedOperationsCount++;
						break;
				}
				totalOperationsCount++;
				return true;

			} catch (InterruptedException e) {
				log.warn("Bank interrupted");
				return false;
			}
		}
		return false;
	}
	public boolean addClientToQueue(Client client)
	{
		if (!isClosed)
		{
			clientsQueue.offer(client);
			log.debug("Queued " + client.toString());
			return true;
		}
		return false;
	}

	public void addCashierToQueue(Cashier cashier)
	{
        cashiersQueue.offer(cashier);
		log.debug("Queued " + cashier.toString());
	}

	public int getMaxMoneyCount()
	{
		return maxMoneyCount;
	}

	public int getClientsCount()
	{
		return clientsCount;
	}

	public int getBankAccountsCount()
	{
		return bankAccountsCount;
	}

	public Account getBankAccountByNumber(int accountNumber)
	{
		return bankAccounts.get(accountNumber);
	}

	public Account getClientWalletByNumber(int clientNumber)
	{
		return clientWallets.get(clientNumber);
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void logStats()
	{
		int totalMoney;
		int totalBankMoney = 0;
		int totalClientsMoney = 0;
		for (Account a : bankAccounts)
		{
			totalBankMoney += a.getMoney();
		}
		for (Account a : clientWallets)
		{
			totalClientsMoney += a.getMoney();
		}
		totalMoney = totalBankMoney + totalClientsMoney;

		log.info("Clients count = " + clientsCount);
		log.info("Cashiers count = " + cashiersCount);
		log.info("Accounts count = " + bankAccountsCount);
		log.info("Total money = " + totalMoney);
		log.info("Total money in bank = " + totalBankMoney);
		log.info("Total money in clients' wallets = " + totalClientsMoney);
		log.info("Total operations = " + totalOperationsCount);
		log.info("Successful operation = " + successOperationsCount);
		log.info("Skipped operations = " + skippedOperationsCount);
		log.info("Failed operations = " + failedOperationsCount);

	}
}
