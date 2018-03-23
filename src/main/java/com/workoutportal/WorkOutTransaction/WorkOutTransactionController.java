package com.workoutportal.WorkOutTransaction;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/workoutTxn")
@CrossOrigin
public class WorkOutTransactionController {

	@Autowired
	private WorkoutTransactionService workoutTxnService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<WorkOutTransactions> calculateduration(@RequestBody WorkOutTransactions workoutTxn) {
		workoutTxn.setDuration(Duration.between(workoutTxn.getStartTime(), workoutTxn.getStopTime()));
		workoutTxn.setCalBurnt(calBurnt(workoutTxn.getDuration(), workoutTxn.getWorkout().getCalBurntPerUnitTime()));
		WorkOutTransactions workoutTxns = workoutTxnService.updateWorkoutTxnDetails(workoutTxn);
		return new ResponseEntity<WorkOutTransactions>(workoutTxns, HttpStatus.OK);
	}

	public Double calBurnt(Duration duration, Double calBurntPerUnitTime) {
		long nanos = (long) duration.toNanos();
		Double calBurnt = (calBurntPerUnitTime) * (NANOSECONDS.toSeconds(nanos));
		return calBurnt;

	}
	
	@RequestMapping(method = RequestMethod.GET, value="{workoutId}")
	public ResponseEntity<List<WorkOutTransactions>> getWorkoutDetails(@PathVariable Long workoutId) {
		List<WorkOutTransactions> workoutTxnDetails = workoutTxnService.getWorkoutTxnDetails(workoutId);
        return new ResponseEntity<List<WorkOutTransactions>>(workoutTxnDetails, HttpStatus.OK);
    }

	
	
}
