package com.workoutportal.WorkOutTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.workoutportal.WorkOut.WorkoutService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.workoutportal.User.User;
import com.workoutportal.WorkOut.WorkOut;
import com.workoutportal.WorkOut.WorkoutRepository;

public class WorkoutServiceTest {
	
	@InjectMocks
    private WorkoutService service;

    @Mock
    private WorkoutRepository repository;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    User sampleUser = new User((long) 1, "password", "userName");
    WorkOut workout= new WorkOut((long)1, (double)123, "Jumping", sampleUser);

	@Test
	public void testDefineWorkout() {
		when(repository.save(workout)).thenReturn(workout);
		WorkOut respose=service.defineWorkout(workout);
		assertThat(respose.equals(workout));
	}

	@Test
	public void testGetWorkoutDetails() {
		List<WorkOut> workoutRes= new ArrayList<WorkOut>();
		workoutRes.add(workout);
		when(repository.findByUserId(workout.getUser().getUserId())).thenReturn(workoutRes);
		WorkOut respose=service.defineWorkout(workout);
		assertThat(respose.equals(workout));
	}

}
