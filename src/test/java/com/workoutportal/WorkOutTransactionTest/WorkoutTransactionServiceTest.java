package com.workoutportal.WorkOutTransactionTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDateTime;
import java.util.List;

import com.workoutportal.WorkOutTransaction.WorkoutTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.workoutportal.User.User;
import com.workoutportal.WorkOut.WorkOut;
import com.workoutportal.WorkOutTransaction.WorkOutTransactions;
import com.workoutportal.WorkOutTransaction.WorkoutTransactionsRepo;

public class WorkoutTransactionServiceTest {
	
	@InjectMocks
    private WorkoutTransactionService service;

    @Mock
    private WorkoutTransactionsRepo repository;
    
	RestTemplate restTeamplate= new RestTemplate();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    User sampleUser = new User((long) 1, "password", "userName");
    WorkOut workout= new WorkOut((long)1, (double)123, "Jumping", sampleUser);
    
	WorkOutTransactions workoutTxnResponse= new WorkOutTransactions((long) 1,LocalDateTime.now(), LocalDateTime.now(),workout);
	WorkOutTransactions workoutTxnInput= new WorkOutTransactions((long) 1,LocalDateTime.now(), LocalDateTime.now(),workout);

	@Test
	public void testUpdateWorkoutDuration() {
		when(repository.save(workoutTxnInput)).thenReturn(workoutTxnResponse);
		WorkOutTransactions respose=service.updateWorkoutTxnDetails(workoutTxnInput);
		assertThat(respose.equals(workoutTxnResponse));
	}
	
	@Test
	public void testGetWorkoutTxnDetails() {
		List<WorkOutTransactions> workoutTxnList=service.getWorkoutTxnDetails((long) 1);
		assertNotNull(workoutTxnList);
	}
	
	@Test
	public void testMultiHitsGetWorkoutTxnDetails() throws Exception {
		MockRestServiceServer server = MockRestServiceServer.bindTo(restTeamplate).build();
		server.expect(ExpectedCount.manyTimes(), requestTo("/workoutTxn/1")).andExpect(method(HttpMethod.GET))
	     .andRespond(withSuccess());
	}

}
