package com.workoutportal.UserTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import com.workoutportal.User.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
 
	@Autowired
    private UserRepository repository;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Test
	public void testFindByEmailId() {
		Cache users = this.cacheManager.getCache("user");
		assertThat(users).isNotNull();
		users.clear();
		assertThat(users.get("sandy@cts.com")).isNull();
		Long be = this.repository.findByEmailId("sandy@cts.com");
		assertNotNull(be);
	}

}
