package com.fitnessAssessment.services.rest;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fitnessAssessment.services.rest.Assessment;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Candidate {
	

	  private long candidate_id;
	  

	  private Set<Assessment> assessments = new HashSet();	  
	  
	  @NotNull
	  private String email;
	  @NotNull
	  private String firstName;
	  @NotNull
	  private String lastName;	  
	  
	  public Candidate() {}
	  
	  public Candidate(long candidate_id)
	  {
	    this.candidate_id = candidate_id;
	  }
	  
	  
	  public Candidate(String email, String firstName, String lastName)
	  {
	    this.email = email;
	    this.firstName = firstName;
	    this.lastName = lastName;
	  }

	public long getCandidate_id() {
		return candidate_id;
	}

	public void setCandidate_id(long candidate_id) {
		this.candidate_id = candidate_id;
	}

	public Set<Assessment> getAssessments() {
		return assessments;
	}

	public void setAssessments(Set<Assessment> assessments) {
		this.assessments = assessments;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	  
	  

}
