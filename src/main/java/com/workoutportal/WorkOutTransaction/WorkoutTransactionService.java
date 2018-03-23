package com.workoutportal.WorkOutTransaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.workoutportal.WorkOutTransaction.WorkOutTransactions;
import com.workoutportal.WorkOutTransaction.WorkoutTransactionsRepo;

@Service
public class WorkoutTransactionService {

	@Autowired
	private WorkoutTransactionsRepo workoutTransactionRepo;
	
	
	@CachePut(value = "transactions", unless="#result==null")
	public WorkOutTransactions updateWorkoutTxnDetails(WorkOutTransactions workoutTransactions) {
		workoutTransactionRepo.save(workoutTransactions);
		return workoutTransactions;
	}
	
	//@Cacheable(value = "transactions")
	public List<WorkOutTransactions> getWorkoutTxnDetails(Long workoutId) {
		List<WorkOutTransactions> workoutTxnList=workoutTransactionRepo.findByWorkId(workoutId);
		return workoutTxnList;
	}

}
