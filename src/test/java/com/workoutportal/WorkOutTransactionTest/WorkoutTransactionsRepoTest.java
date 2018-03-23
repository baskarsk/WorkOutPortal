package com.workoutportal.WorkOutTransactionTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import com.workoutportal.WorkOutTransaction.WorkoutTransactionsRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.workoutportal.WorkOutTransaction.WorkOutTransactions;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkoutTransactionsRepoTest {

	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private WorkoutTransactionsRepo workoutTxnsRepo;
	
	
	@Test
	public void testFindByWorkId() {
		Cache WorkoutTxns = this.cacheManager.getCache("WorkoutTransactions");
		assertThat(WorkoutTxns).isNotNull();
		WorkoutTxns.clear();
		assertThat(WorkoutTxns.get("sandy@cts.com")).isNull();
		Iterable<WorkOutTransactions> WorkoutTxnsRes = this.workoutTxnsRepo.findByWorkId((long)2);
		assertNotNull(WorkoutTxnsRes);
	}

}
