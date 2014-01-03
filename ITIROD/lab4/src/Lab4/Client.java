package Lab4;

import org.apache.log4j.Logger;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 08.12.13
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
public class Client {
	private static Logger log = Logger.getLogger(Client.class);

	private int clientNumber;
	private Account wallet;
	private Operation operation;
	private Random random;

	public Client(int clientNumber, Account wallet) {
		this.clientNumber = clientNumber;
		this.wallet = wallet;
		random = new Random();
	}

	public void generateNewOperation(Bank bank)
	{

		int accountsCount = bank.getBankAccountsCount();
		Account from, to;
		int money;
		boolean fromWallet = false;
		boolean toWallet = false;
		if (wallet.getMoney() > 0)
		{
			fromWallet = true;
		}
		else if (random.nextInt(100) < 25)
		{
			toWallet = true;
		}

		if (fromWallet)
		{
			from = wallet;
			to = bank.getBankAccountByNumber(random.nextInt(accountsCount));
			money = wallet.getMoney();
		}
		else if (toWallet)
		{
			from = bank.getBankAccountByNumber(random.nextInt(accountsCount));
			to = wallet;
			money = random.nextInt((int)(bank.getMaxMoneyCount() / accountsCount / 10));
		}
		else
		{
			from = bank.getBankAccountByNumber(random.nextInt(accountsCount));
			to = bank.getBankAccountByNumber(random.nextInt(accountsCount));
			money = random.nextInt((int)(bank.getMaxMoneyCount() / accountsCount / 10));
		}

        Operation t = new Operation(from, to, money);
		this.operation = t;
		log.debug("New operation on client #" + clientNumber + " " + t.toString());
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public Account getWallet() {
		return wallet;
	}

	public Operation getOperation() {
		return operation;
	}

	@Override
	public String toString() {
		return "Client{clientNumber=" + clientNumber +", wallet=" + wallet +'}';
	}
}
