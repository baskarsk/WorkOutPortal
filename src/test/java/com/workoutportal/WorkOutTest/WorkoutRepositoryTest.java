package com.workoutportal.WorkOutTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import com.workoutportal.WorkOut.WorkoutRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.workoutportal.WorkOut.WorkOut;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkoutRepositoryTest {
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private WorkoutRepository workoutRepository;

	@Test
	public void testFindByUserId() {
		Cache worekouts = this.cacheManager.getCache("Workout");
		assertThat(worekouts).isNotNull();
		worekouts.clear();
		Iterable<WorkOut> response = this.workoutRepository.findByUserId((long)1);
		assertNotNull(response);
	}

}
