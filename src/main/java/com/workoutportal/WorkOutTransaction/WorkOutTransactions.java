package com.workoutportal.WorkOutTransaction;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.workoutportal.WorkOut.WorkOut;
import org.springframework.cache.annotation.CacheConfig;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "WorkoutTransactions")
@CacheConfig(cacheNames = "WorkoutTransactions")
public class WorkOutTransactions {

	private interface Table {
		String TXNID = "TXNID";
		String START_TIME = "START_TIME";
		String STOP_TIME = "STOP_TIME";
		String DURATION = "DURATION";
		String CALSBURNT = "CALSBURNT";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = Table.TXNID)
	private Long txnId;

	@Column(name = Table.START_TIME)
	private LocalDateTime startTime;

	@Column(name = Table.STOP_TIME)
	private LocalDateTime stopTime;

	@Column(name = Table.DURATION)
	private Duration duration;
	
	@Column(name = Table.CALSBURNT)
	private Double calBurnt;

	public Double getCalBurnt() {
		return calBurnt;
	}

	public void setCalBurnt(Double calBurnt) {
		this.calBurnt = calBurnt;
	}

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public java.time.LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(java.time.LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public java.time.LocalDateTime getStopTime() {
		return stopTime;
	}

	public void setStopTime(java.time.LocalDateTime stopTime) {
		this.stopTime = stopTime;
	}

	public java.time.Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public WorkOut getWorkout() {
		return workout;
	}

	public void setWorkout(WorkOut workout) {
		this.workout = workout;
	}

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "workoutId")
	@JsonBackReference
	private WorkOut workout;
	
	public WorkOutTransactions(Long txnId, LocalDateTime startTime, LocalDateTime stopTime,WorkOut workout
			) {
		super();
		this.txnId = txnId;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.workout=workout;
	}

	public WorkOutTransactions() {
		super();
	}

}
