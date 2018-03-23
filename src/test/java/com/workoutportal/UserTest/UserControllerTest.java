package com.workoutportal.UserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.workoutportal.User.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.workoutportal.WorkoutPortalApplication;
import com.workoutportal.User.User;
import com.workoutportal.User.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WorkoutPortalApplication.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	UserController userController;

	@MockBean
	UserService userService;

	User sampleUser = new User((long) 1, "password", "userName");
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateUser() throws Exception {
		User sampleUserResponse= sampleUser;
		given(userService.createUser(Mockito.any(User.class))).willReturn(sampleUserResponse);
		String content = new Gson().toJson(sampleUser);
		ResultActions result = mockMvc
          .perform(post("/user/register").content(content).contentType(APPLICATION_JSON_UTF8))
          .andExpect(status().isOk());
		assertThat(result.andReturn().equals(sampleUserResponse));
	}


	@Test
	public void testAuthenticateUser() throws Exception {
		given(userService.authenticateUser("userName", "password")).willReturn((long) 1);
		this.mockMvc
				.perform(get("/user/login").contentType(MediaType.APPLICATION_JSON).header("userName", "sandhya")
						.header("password", "password"))
				.andExpect(status().isOk())
				.andReturn();
	}

}
