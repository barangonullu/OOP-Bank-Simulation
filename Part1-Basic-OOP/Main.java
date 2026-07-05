class BankAccount {
    private String id;
    private float balance;

    public BankAccount(String id) {
        this.id = id;
        this.balance = 0;
    }

    public void Deposit(float amount) {
        this.balance += amount;
    }

    public void Withdraw(float amount) {
        this.balance -= amount;
    }

    public boolean CanWithdraw(float amount) {
        return this.balance >= amount;
    }

    public float getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return this.id + " has a balance of " + this.balance;
    }
}

class CheckingAccount extends BankAccount {
    private float overdraftLimit;

    public CheckingAccount(String id, float overdraftLimit) {
        super(id);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean CanWithdraw(float amount) {
        return (getBalance() + overdraftLimit) >= amount;
    }

    @Override
    public String toString() {
        return super.toString() + ", overdraftLimit " + this.overdraftLimit;
    }
}

class Bank {
    public Bank() {
    }

    @Override
    public String toString() {
        return "BANK AVAILABLE";
    }

    public boolean Transfer(BankAccount sender, BankAccount recipient, float amount) {
        if (sender.CanWithdraw(amount)) {
            sender.Withdraw(amount);
            recipient.Deposit(amount);
            return true;
        } else {
            return false;
        }
    }
}

class CommissionBank extends Bank {
    private float commissionRate;
    private float balance;

    public CommissionBank(float commissionRate) {
        this.commissionRate = commissionRate;
        this.balance = 0;
    }

    @Override
    public boolean Transfer(BankAccount sender, BankAccount recipient, float amount) {
        float commission = amount * this.commissionRate;
        float totalDeduction = amount + commission;

        if (sender.CanWithdraw(totalDeduction)) {
            sender.Withdraw(totalDeduction);
            recipient.Deposit(amount);
            this.balance += commission;
            return true;
        }
        return false;
    }
    
    public float getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return "Bank has a commission rate of " + (int)(commissionRate * 100) + "%";
    }
}

public class Main {
    public static void main(String[] args) {
        CommissionBank bank = new CommissionBank(0.10f);
        CheckingAccount ali = new CheckingAccount("Ali", 50);
        BankAccount ayse = new BankAccount("Ayse");

        ali.Deposit(100);
        ayse.Deposit(300);

        System.out.println("Before transfer");
        System.out.println(bank.toString());
        System.out.println(ali.toString());
        System.out.println(ayse.toString());
        System.out.println("Bank has a balance of " + bank.getBalance());

        System.out.println("\nAfter Ali sends 50 to Ayse");
        bank.Transfer(ali, ayse, 50);
        System.out.println(ali.toString());
        System.out.println(ayse.toString());
        System.out.println("Bank has a balance of " + bank.getBalance());
        
        System.out.println("\n---------------------------------\n");

        bank = new CommissionBank(0.10f);
        ali = new CheckingAccount("Ali", 50);
        ayse = new BankAccount("Ayse");

        ali.Deposit(100);
        ayse.Deposit(300);

        System.out.println("Before transfer");
        System.out.println(bank.toString());
        System.out.println(ali.toString());
        System.out.println(ayse.toString());
        System.out.println("Bank has a balance of " + bank.getBalance());

        System.out.println("\nAfter Ali sends 100 to Ayse");
        bank.Transfer(ali, ayse, 100);
        System.out.println(ali.toString());
        System.out.println(ayse.toString());
        System.out.println("Bank has a balance of " + bank.getBalance());
    }
}