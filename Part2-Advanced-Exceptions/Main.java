import java.util.Random;

class BankException extends Exception {
    private String className;
    private String operation;
    private String reason;

    public BankException(String className, String operation, String reason) {
        this.className = className;
        this.operation = operation;
        this.reason = reason;
    }

    @Override
    public String toString() {
        String separator = reason.startsWith(";") ? "" : " ";
        return className + ": Cannot " + operation + separator + reason;
    }

    @Override
    public String getMessage() {
        return this.toString();
    }
}

abstract class BankAccount {
    protected String id;
    protected float balance;

    public BankAccount(String id) {
        this.id = id;
        this.balance = 0.0f;
    }

    public String getId() {
        return id;
    }

    public float getBalance() {
        return balance;
    }

    protected boolean canWithdraw(float amount) {
        return this.balance >= amount;
    }

    public void deposit(float amount) throws BankException {
        if (amount < 0) {
            throw new BankException(this.getClass().getSimpleName(), "deposit", "; amount is negative.");
        }
        this.balance += amount;
    }

    public void withdraw(float amount) throws BankException {
        if (amount < 0) {
            throw new BankException(this.getClass().getSimpleName(), "withdraw", "; amount is negative.");
        }
        if (!canWithdraw(amount)) {
            throw new BankException(this.getClass().getSimpleName(), "withdraw", "due to inadequate funds");
        }
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return id + " has a balance of " + balance;
    }
}

class CheckingAccount extends BankAccount {
    private float overdraftLimit;

    public CheckingAccount(String id, float overdraftLimit) {
        super(id);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    protected boolean canWithdraw(float amount) {
        return (this.balance + this.overdraftLimit) >= amount;
    }

    @Override
    public void deposit(float amount) throws BankException {
        super.deposit(amount);
    }

    @Override
    public void withdraw(float amount) throws BankException {
        super.withdraw(amount);
    }

    @Override
    public String toString() {
        return super.toString() + ", overdraftLimit " + this.overdraftLimit;
    }
}

class FXAccount extends BankAccount {
    private float exchangeRate;

    public FXAccount(String id, float exchangeRate) {
        super(id);
        this.exchangeRate = exchangeRate;
    }

    @Override
    public void deposit(float amount) throws BankException {
        super.deposit(amount * exchangeRate);
    }

    @Override
    public void withdraw(float amount) throws BankException {
        super.withdraw(amount * exchangeRate);
    }

    @Override
    public String toString() {
        return super.toString() + ", exchangeRate " + this.exchangeRate;
    }
}

abstract class Bank {
    protected float balance;

    public Bank() {
        this.balance = 0.0f;
    }

    public abstract boolean transfer(BankAccount sender, BankAccount recipient, float amount) throws BankException;

    @Override
    public String toString() {
        return "Bank Balance: " + this.balance;
    }
}

class CommissionBank extends Bank {
    protected float commissionRate;

    public CommissionBank(float commissionRate) {
        super();
        this.commissionRate = commissionRate;
    }

    @Override
    public boolean transfer(BankAccount sender, BankAccount recipient, float amount) throws BankException {
        float commission = amount * commissionRate;
        float totalAmountToWithdraw = amount + commission;

        try {
            sender.withdraw(totalAmountToWithdraw);
            recipient.deposit(amount);
            this.balance += commission;
            return true;
        } catch (BankException e) {
            throw new BankException("CommissionBank", "transfer", "from " + sender.getId() + " to " + recipient.getId());
        }
    }
}

class MinCommissionBank extends Bank {
    private float commissionRate;
    private float minCommission;

    public MinCommissionBank(float commissionRate, float minCommission) {
        super();
        this.commissionRate = commissionRate;
        this.minCommission = minCommission;
    }

    @Override
    public boolean transfer(BankAccount sender, BankAccount recipient, float amount) throws BankException {
        float calculatedCommission = amount * commissionRate;
        float actualCommission = Math.max(calculatedCommission, minCommission);
        float totalAmountToWithdraw = amount + actualCommission;

        try {
            sender.withdraw(totalAmountToWithdraw);
            recipient.deposit(amount);
            this.balance += actualCommission;
            return true;
        } catch (BankException e) {
            throw new BankException("MinComissionBank", "transfer", "from " + sender.getId() + " to " + recipient.getId());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CheckingAccount account1 = new CheckingAccount("account1", 100f);
        FXAccount account2 = new FXAccount("account2", 1.5f);
        MinCommissionBank bank = new MinCommissionBank(0.05f, 10.0f);

        System.out.println("--- Exception Test Çıktıları ---");

        try {
            account1.withdraw(500f); 
        } catch (BankException e) {
            System.out.println(e.toString());
        }

        try {
            bank.transfer(account1, account2, 200f);
        } catch (BankException e) {
            System.out.println(e.toString());
        }

        try {
            account2.deposit(-50f);
        } catch (BankException e) {
            System.out.println(e.toString());
        }
    }
}