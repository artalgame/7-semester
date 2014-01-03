package Lab4;

public class Account {

	private int accountNumber;
	private int money;

	private volatile boolean isChecked;

	public Account(int accountNumber, int money) {
		this.accountNumber = accountNumber;
		this.money = money;
		this.isChecked = false;
	}

	public int getMoney() {
		return money;
	}

    public void addToMoney(int sum)
    {
        money += sum;
    }


    public void changeAccountState()
	{
		isChecked = !isChecked;
	}

	public boolean isSameAccountState(Account otherAccount)
	{
		return this.isChecked == otherAccount.isChecked;
	}

	@Override
	public String toString() {
		return "Account{accountNumber=" + accountNumber +", money=" + money +'}';
	}
}

