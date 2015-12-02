package com.fitnessAssessment.services.model;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import com.fitnessAssessment.services.model.Candidate;

@Transactional
public abstract interface CandidateDAO extends CrudRepository<Candidate, Long>{

}
