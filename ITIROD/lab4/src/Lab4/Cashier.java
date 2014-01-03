package Lab4;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 08.12.13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class Cashier {
	private static Logger log = Logger.getLogger(Cashier.class);

	private int cashierNumber;
	private Bank bank;


	public Cashier(int cashierNumber) {
		this.cashierNumber = cashierNumber;
	}

	public void processClient(final Client client)
	{
		if (client == null)
			return;
        Operation operation = client.getOperation();
		Account from = operation.getFromAccount();
		Account to = operation.getToAccount();
		int moneyToTransfer = operation.getMoneyToTransfer();

        if (from.isSameAccountState(to))
        {
            if (from.getMoney() >= moneyToTransfer)
            {
                from.addToMoney(-moneyToTransfer);
                to.addToMoney(moneyToTransfer);
                operation.setStatus(Operation.Status.OK);
                log.debug("Processed operation of client #" + client.getClientNumber() + " by cashier #" + cashierNumber + ": " + operation.toString());

            }
            else
            {
                operation.setStatus(Operation.Status.FAILED);
                log.debug("Failed operation of client #" + client.getClientNumber() + " by cashier #" + cashierNumber + ": " + operation.toString());
            }
        }
        else
        {
            operation.setStatus(Operation.Status.SKIPPED);
            log.debug("Skipped operation of client #" + client.getClientNumber() + " by cashier #" + cashierNumber + ": " + operation.toString());
        }


		bank.addCashierToQueue(this);
	}

	public int getCashierNumber() {
		return cashierNumber;
	}

	public void setCashierNumber(int cashierNumber) {
		this.cashierNumber = cashierNumber;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Override
	public String toString() {
		return "Cashier{" +
				"cashierNumber=" + cashierNumber +
				'}';
	}
}
