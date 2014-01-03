package Lab4;

/**
 * Saves information about operation sender and receiver accounts, operation status and money to transfer.
 */
public class Operation {

	private Account fromAccount;
	private Account toAccount;
	private int moneyToTransfer;
    private Status status;

	public Operation(Account fromAccount, Account toAccount, int moneyToTransfer) {
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.moneyToTransfer = moneyToTransfer;

		this.status = Status.PENDING;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public int getMoneyToTransfer() {
		return moneyToTransfer;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Operation{" +
				"from=" + fromAccount +
				", to=" + toAccount +
				", money=" + moneyToTransfer +
				", status=" + status +
				'}';
	}

    /**
     * Describes status of operation.
     */
    public enum Status {
        OK,
        PENDING,
        FAILED,
        SKIPPED
    }
}
