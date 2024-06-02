package myproj;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import exceptions.InsufficientFundsException;
import models.*;
import services.*;

public class AllTests {

    static List<Account> accounts;
    static AccountService accountService;

    @Before
    public void setUp() {
        accounts = new ArrayList<>();
        accountService = new AccountServiceImpl(accounts);
    }

    @Test
    public void createAccount_Test_Success() {
        Account expectedResult = new Account(accounts.size(), AccountType.checkings, "Martin Akindiya", 90000);
        Account actualResult = accountService.createAccount(expectedResult);
        assertEquals(expectedResult, actualResult);
        accounts.remove(expectedResult);
    }

    @Test
    public void createAccount_Test_Failure() {
        Account nullAccount = null;
        assertNull(accountService.createAccount(nullAccount));
    }

    @Test
    public void getBalanceByAccountID_Test_Success() {
        Account account = new Account(1, AccountType.savings, "John Snow", 5000);
        accountService.createAccount(account);
        assertEquals(5000, accountService.getBalanceByAccountID(1), 0.0);
    }

    @Test
    public void getBalanceByAccountID_Test_Failure() {
        assertEquals(0, accountService.getBalanceByAccountID(999), 0.0);
    }

    @Test
    public void withdraw_Test_Success() throws InsufficientFundsException {
        Account accountToTest = new Account(accounts.size() + 1, AccountType.savings, "Jack Fox", 110);
        accounts.add(accounts.size(), accountToTest);
        double initialBalance = accountToTest.getBalance();
        double amountToWithdraw = 100;
        double expectedResult = initialBalance - amountToWithdraw;
        double actualResult = accountService.withdraw(accountToTest.getAccountID(), amountToWithdraw);
        assertEquals(expectedResult, actualResult, 0.0);
        accounts.remove(accountToTest);
    }

    @Test
    public void withdraw_Test_Failure() {
        Account accountToTest = new Account(accounts.size() + 1, AccountType.savings, "Jack Fox", 50);
        accounts.add(accounts.size(), accountToTest);
        assertThrows(InsufficientFundsException.class, () -> accountService.withdraw(accountToTest.getAccountID(), 100));
        accounts.remove(accountToTest);
    }

    @Test
    public void getAllAccounts_Test_Success() {
        Account account1 = new Account(1, AccountType.checkings, "Martin Akindiya", 5000);
        Account account2 = new Account(2, AccountType.savings, "John Snow", 10000);
        accountService.createAccount(account1);
        accountService.createAccount(account2);
        List<Account> allAccounts = accountService.getAllAccounts();
        assertEquals(2, allAccounts.size());
        assertTrue(allAccounts.contains(account1));
        assertTrue(allAccounts.contains(account2));
    }

    @Test
    public void deleteAccount_Test_Success() {
        Account accountToDelete = new Account(1, AccountType.checkings, "Martin Akindiya", 5000);
        accountService.createAccount(accountToDelete);
        Account deletedAccount = accountService.deleteAccount(accountToDelete.getAccountID());
        assertEquals(accountToDelete, deletedAccount);
    }

    @Test
    public void deleteAccount_Test_Failure() {
        assertNull(accountService.deleteAccount(999));
    }

    @Test
    public void updateAccount_Test_Success() {
        Account accountToUpdate = new Account(1, AccountType.checkings, "Martin Akindiya", 5000);
        accountService.createAccount(accountToUpdate);
        Account updatedAccount = new Account(1, AccountType.savings, "John Snow Updated", 6000);
        Account result = accountService.updateAccount(updatedAccount);
        assertEquals(updatedAccount.getAccountType(), result.getAccountType());
        assertEquals(updatedAccount.getAccountOwnerName(), result.getAccountOwnerName());
        assertEquals(updatedAccount.getBalance(), result.getBalance(), 0.0);
    }

    @Test
    public void updateAccount_Test_Failure() {
        Account accountToUpdate = new Account(999, AccountType.checkings, "Non-existent", 5000);
        assertNull(accountService.updateAccount(accountToUpdate));
    }

    @Test
    public void withdraw_Test_EdgeCase() {
        Account accountToTest = new Account(accounts.size() + 1, AccountType.savings, "Edge Case", 0);
        accounts.add(accounts.size(), accountToTest);
        assertThrows(InsufficientFundsException.class, () -> accountService.withdraw(accountToTest.getAccountID(), 1));
        accounts.remove(accountToTest);
    }

    @Test
    public void createAccount_Test_EdgeCase() {
        Account account = new Account(Integer.MAX_VALUE, AccountType.checkings, "Edge Case Owner", Double.MAX_VALUE);
        Account createdAccount = accountService.createAccount(account);
        assertEquals(account, createdAccount);
        accounts.remove(createdAccount);
    }
}
