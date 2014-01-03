package Lab4;

import org.apache.log4j.Logger;

/**
 * Logs bank state.
 */
public class BankObserver {
	private boolean isChecking;
	private Thread observerThread;
	private Bank bank;

    private static Logger log = Logger.getLogger(BankObserver.class);

	public BankObserver(Bank bank) {
		this.bank = bank;
		this.isChecking = false;
	}

    /**
     * Checking bank's and clients accounts money and
     * @return true, if overall money = start value else false.
     */
	public boolean checkMoney()
	{
		long maxMoney = bank.getMaxMoneyCount();
		long totalMoney = 0;

		for (int i = 0; i < bank.getBankAccountsCount(); i++)
		{
			Account bankAccount = bank.getBankAccountByNumber(i);
			synchronized (bankAccount)
			{
				bankAccount.changeAccountState();
				long money = bankAccount.getMoney();
				if (money < 0)
					return false;
				totalMoney += money;
			}
		}

		for (int i = 0; i < bank.getClientsCount(); i++)
		{
			Account clientAccount = bank.getClientWalletByNumber(i);
			synchronized (clientAccount)
			{
				clientAccount.changeAccountState();
				long money = clientAccount.getMoney();
				if (money < 0)
					return false;
				totalMoney += money;
			}
		}
		return maxMoney == totalMoney;
	}

    /**
     * log information about account checking .
     * @param period - frequency of logging.
     */
	public void startChecking(final int period)
	{
		if (isChecking)
			return;
		isChecking = true;
		observerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isChecking)
				{
					boolean result = checkMoney();
					log.info("Result of bank checking = " + result);
					if (!result)
						log.error("TOTAL MONEY COUNT DISCREPANCY");
					try {
						Thread.sleep(period);
					} catch (InterruptedException e) {
						log.debug("Observer is interrupted");
						break;
					}
				}

			}
		});
		observerThread.setName("Obs thread");
		observerThread.start();
		log.info("Observer started");
	}

	public void stop()
	{
		if (!isChecking)
			return;
		isChecking = false;
		synchronized (observerThread)
		{
			observerThread.notify();
		}

		log.info("Observer stopped");
	}

	public boolean isChecking() {
		return isChecking;
	}
}
