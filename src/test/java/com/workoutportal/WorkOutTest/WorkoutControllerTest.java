package com.workoutportal.WorkOutTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.workoutportal.WorkOut.WorkoutController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.workoutportal.WorkoutPortalApplication;
import com.workoutportal.User.User;
import com.workoutportal.WorkOut.WorkOut;
import com.workoutportal.WorkOut.WorkoutService;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WorkoutPortalApplication.class)
@WebMvcTest(value = WorkoutController.class, secure = false)
public class WorkoutControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WorkoutController workoutController;

	@MockBean
	WorkoutService workoutService;
	
	RestTemplate restTeamplate= new RestTemplate();
	
	User sampleUser = new User((long) 1, "password", "userName");
	WorkOut workout= new WorkOut((long)1, (double)123, "Jumping", sampleUser);
	
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.workoutController).build();
		MockitoAnnotations.initMocks(this);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testDefineWorkout() throws Exception {
		given(workoutService.defineWorkout(Mockito.any(WorkOut.class))).willReturn(workout);
		String content = new Gson().toJson(workout);
		ResultActions result = mockMvc
          .perform(post("/workout").content(content).contentType(APPLICATION_JSON_UTF8))
          .andExpect(status().isOk());
		assertThat(result.andReturn().equals(workout));		
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testGetWorkoutDetails() throws Exception {
		List<WorkOut> sampleWorkoutResponse = new ArrayList<WorkOut>();
		sampleWorkoutResponse.add(workout);
		Mockito.when(workoutService.getWorkoutDetails((long) 1)).thenReturn(sampleWorkoutResponse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/workout/"+workout.getWorkoutId()).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertThat(result.getResponse().equals(workout));
	}
	
	@Test
	public void testMultiHitsGetWorkoutDetails() throws Exception {
		MockRestServiceServer server = MockRestServiceServer.bindTo(restTeamplate).build();
		server.expect(ExpectedCount.manyTimes(), requestTo("/workout")).andExpect(method(HttpMethod.GET))
	     .andRespond(withSuccess("{ \"workoutId\" : \"1\", \"calBurntPerUnitTime\" : \"123\", \"title\" : \"123\",\"Jumping\" : \"null\"}", MediaType.APPLICATION_JSON));
	}
}
