package com.workoutportal.WorkOut;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.workoutportal.User.UnitTime;
import com.workoutportal.User.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Workout")
@CacheConfig(cacheNames = "Workout")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkOut {

	private interface Table {
		String WORKOUT_ID = "WORKOUT_ID";
		String TITLE = "TITLE";
		String CALBURNTPERUNITTIME = "CALBURNTPERUNITTIME";
		String UNIT_TIME = "UNIT_TIME";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = Table.WORKOUT_ID)
	private Long workoutId;

	@Column(name = Table.TITLE)
	private String title;

	@Column(name = Table.CALBURNTPERUNITTIME)
	private Double calBurntPerUnitTime;

	@Enumerated(EnumType.STRING)
	@Column(name = Table.UNIT_TIME)
	private UnitTime unitTime;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	@JsonManagedReference
	private User user;

	public WorkOut() {
		super();
	}

	public Long getWorkoutId() {
		return workoutId;
	}

	public void setWorkoutId(Long workoutId) {
		this.workoutId = workoutId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UnitTime getUnitTime() {
		return unitTime;
	}

	public void setUnitTime(UnitTime unitTime) {
		this.unitTime = unitTime;
	}

	public Double getCalBurntPerUnitTime() {
		return calBurntPerUnitTime;
	}

	public void setCalBurntPerUnitTime(Double calBurntPerUnitTime) {
		this.calBurntPerUnitTime = calBurntPerUnitTime;
	}



	public WorkOut(Long workoutId, Double calBurntPerUnitTime, String title, UnitTime unitTime) {
		super();
		this.workoutId = workoutId;
		this.calBurntPerUnitTime = calBurntPerUnitTime;
		this.title = title;
		this.unitTime = unitTime;
	}

	public WorkOut(Long workoutId, Double calBurntPerUnitTime, String title, User user) {
		super();
		this.workoutId = workoutId;
		this.title = title;
		this.calBurntPerUnitTime = calBurntPerUnitTime;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
