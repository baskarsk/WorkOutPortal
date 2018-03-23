package com.workoutportal.WorkOutTransactionTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.workoutportal.WorkOutTransaction.WorkOutTransactionController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.workoutportal.WorkoutPortalApplication;
import com.workoutportal.User.User;
import com.workoutportal.WorkOut.WorkOut;
import com.workoutportal.WorkOutTransaction.WorkOutTransactions;
import com.workoutportal.WorkOutTransaction.WorkoutTransactionService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WorkoutPortalApplication.class)
@WebMvcTest(value = WorkOutTransactionController.class, secure = false)
public class WorkOutTransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WorkOutTransactionController workOutTransactionController;

	@MockBean
	WorkoutTransactionService workoutTxnService;

	User sampleUser = new User((long) 1, "password", "userName");
	WorkOut workout = new WorkOut((long) 1, (double) 123, "Jumping", sampleUser);
	WorkOutTransactions workoutTxn = new WorkOutTransactions((long) 1, LocalDateTime.now(), LocalDateTime.now(),
			workout);
	

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.workOutTransactionController).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCalculateduration() throws Exception {
		given(workoutTxnService.updateWorkoutTxnDetails(Mockito.any(WorkOutTransactions.class))).willReturn(workoutTxn);
		String content = new Gson().toJson(workoutTxn);
		mockMvc.perform(post("/workoutTxn").content(content).contentType(APPLICATION_JSON_UTF8)).andReturn();
	}
	
	@Test
	public void testGetWorkoutTxnDetails() {
		List<WorkOutTransactions> response= new ArrayList<>();
		response.add(workoutTxn);
		given(workoutTxnService.getWorkoutTxnDetails(workout.getWorkoutId())).willReturn(response);
		List<WorkOutTransactions> workoutTxnList=workoutTxnService.getWorkoutTxnDetails((long) 1);
		assertNotNull(workoutTxnList);
	}

}
