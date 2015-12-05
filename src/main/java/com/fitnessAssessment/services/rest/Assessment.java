package com.fitnessAssessment.services.rest;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Assessment {
	

	  private long assessment_id;
	  

	  private Candidate candidate;	  
	  
	  private String goals;
	  private String existingConditions;
	  //weight in pounds
	  private int weight;
	  //height in inches
	  private int height;
	public long getAssessment_id() {
		return assessment_id;
	}
	public void setAssessment_id(long assessment_id) {
		this.assessment_id = assessment_id;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	public String getGoals() {
		return goals;
	}
	public void setGoals(String goals) {
		this.goals = goals;
	}
	public String getExistingConditions() {
		return existingConditions;
	}
	public void setExistingConditions(String existingConditions) {
		this.existingConditions = existingConditions;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	  
	  

}
