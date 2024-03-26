package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Account;
import com.example.demo.repo.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountRepository repo;
	@Override
	public Account createAccount(Account account) {
		Account account_saved=repo.save(account);
		return account_saved;
	}

	@Override
	public Account getAccountDetailsByAccountNumber(Long accountNumber) {
		Optional<Account> account=repo.findById(accountNumber);
		if(account.isEmpty())
			throw new RuntimeException("account not present");
		Account account_found=account.get();
		return account_found;
	}

	@Override
	public List<Account> getAllAccountDetails() {
		List<Account> ListOfAccounts=repo.findAll();
		return ListOfAccounts;
	}

	@Override
	public Account depositAmount(Long accountNumber, Double amount){
		Optional<Account> account=repo.findById( accountNumber);
		if(account.isEmpty()) {
			throw new RuntimeException("Account is not present");
		}
		Account account1=account.get();
		Double totalBalance=account1.getAccount_balance()+amount;
		account1.setAccount_balance(totalBalance);
		repo.save(account1);
		return account1;
	}

	@Override
	public Account withdrawAmount(Long accountNumber, Double amount) {
		Optional<Account> account=repo.findById( accountNumber);
		if(account.isEmpty()) {
			throw new RuntimeException("Account is not present");
		}
		Account account1=account.get();
		Double totalBalance=account1.getAccount_balance()-amount;
		account1.setAccount_balance(totalBalance);
		repo.save(account1);
		return account1;
	}

	@Override
	public void closeAccount(Long accountNumber) {
		repo.deleteById(accountNumber);
		
	}

}
