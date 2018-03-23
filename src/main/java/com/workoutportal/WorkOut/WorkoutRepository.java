package com.workoutportal.WorkOut;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.workoutportal.WorkOut.WorkOut;

public interface WorkoutRepository extends CrudRepository<WorkOut, Integer> {
	
	@Query("SELECT WO.workoutId,WO.calBurntPerUnitTime,WO.title,WO.unitTime,WO.user.userName FROM WorkOut WO WHERE WO.user.userId= :userId")
	List <WorkOut> findByUserId(@Param("userId") Long userId);

}
