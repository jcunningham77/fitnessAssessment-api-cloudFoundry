package com.fitnessAssessment.services.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fitnessAssessment.services.model.CandidateDAO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;

import com.fitnessAssessment.services.model.Assessment;
import com.fitnessAssessment.services.exception.InvalidRequestBodyException;
import com.fitnessAssessment.services.exception.ResourceNotFoundException;
import com.fitnessAssessment.services.rest.ErrorMessage;
import com.fitnessAssessment.services.model.AssessmentDAO;
import org.springframework.stereotype.Controller;


@Controller("fitnessAssessmentController")
public class FitnessAssessmentController {
	
	  @Autowired
	  private CandidateDAO candidateDao;
	  @Autowired
	  private AssessmentDAO assessmentDao;
	  
	  private static Logger logger = Logger.getLogger(FitnessAssessmentController.class);
	  
	  @RequestMapping(value={"rest/handle"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  @ApiOperation(value="Hello World", notes="Test Method")
	  @ApiResponses({@io.swagger.annotations.ApiResponse(code=200, message="Hello World", response=String.class)})
	  public ResponseEntity<String> handle()
	  {
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("MyResponseHeader", "MyValue");
	    return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.OK);
	  }	  
	  
	  @RequestMapping(value={"rest/candidate"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  @ApiOperation(value="createCandidate", notes="Accepts a POST method to create a candidate")
	  public ResponseEntity<Object> createCandidate(@ApiParam(value="RequestBody with a JSON RestCandidate - ID as well as Assessment fields will be ignored by this method, since this is for creating NEW candidate records", required=true) @RequestBody com.fitnessAssessment.services.rest.Candidate inputRestCandidate)
	  throws Exception
	  {
		HttpHeaders responseHeaders = new HttpHeaders();
		try {

		    com.fitnessAssessment.services.model.Candidate candidate = null;
		    
		    candidate = new com.fitnessAssessment.services.model.Candidate(inputRestCandidate.getEmail(), inputRestCandidate.getFirstName(), inputRestCandidate.getLastName());
		    this.candidateDao.save(candidate);
		    com.fitnessAssessment.services.rest.Candidate restCandidate = new com.fitnessAssessment.services.rest.Candidate();
		    restCandidate.setCandidate_id(candidate.getCandidate_id());
		    restCandidate.setEmail(candidate.getEmail());
		    restCandidate.setFirstName(candidate.getFirstName());
		    restCandidate.setLastName(candidate.getLastName());
		    restCandidate.setAssessments(null);
		    return new ResponseEntity<Object>(restCandidate, responseHeaders, HttpStatus.OK);
		} catch (ConstraintViolationException ex) {
			logger.info("Caught ConstraintViolationException ex  =" + ex.getMessage());
	        throw new InvalidRequestBodyException("Required field not set on request", "Required field not set on request body"); 	    
	  	}catch (Exception ex) {
	      ErrorMessage errorMessage = new ErrorMessage();
	      errorMessage.setMessage(ex.getMessage());
	      return new ResponseEntity<Object>(errorMessage, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	  }	  
	  
	  @RequestMapping(value={"rest/candidate/{candidate-id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  @ApiOperation(value="GetCandidate", notes="Accepts a GET method to retrieve a candidate by candidate ID")
	  public ResponseEntity<Object> getCandidate(@ApiParam(value="UniqueKey for Candidate", required=true) @PathVariable("candidate-id") Long candidateId)
	    throws Exception
	  {
	    logger.info("entering");
	    HttpHeaders responseHeaders = new HttpHeaders();
	    com.fitnessAssessment.services.model.Candidate candidate = new com.fitnessAssessment.services.model.Candidate();
	    com.fitnessAssessment.services.rest.Candidate restCandidate = new com.fitnessAssessment.services.rest.Candidate();
	    
	    candidate = (com.fitnessAssessment.services.model.Candidate)this.candidateDao.findOne(candidateId);
	    if (candidate == null) {
	      throw new ResourceNotFoundException("Resource Not Found", "No candidate with id = " + candidateId.toString() + " found in repository");
	    }
	    restCandidate.setCandidate_id(candidate.getCandidate_id());
	    restCandidate.setEmail(candidate.getEmail());
	    restCandidate.setFirstName(candidate.getFirstName());
	    restCandidate.setLastName(candidate.getLastName());
	    
	    Set<com.fitnessAssessment.services.model.Assessment> assessments = candidate.getAssessments();
	    Iterator<com.fitnessAssessment.services.model.Assessment> iter = assessments.iterator();
	    Set<com.fitnessAssessment.services.rest.Assessment> restAssessments = new HashSet<com.fitnessAssessment.services.rest.Assessment>();
	    while (iter.hasNext())
	    {
	      com.fitnessAssessment.services.rest.Assessment restAssessment = new com.fitnessAssessment.services.rest.Assessment();
	      com.fitnessAssessment.services.model.Assessment assessment = (com.fitnessAssessment.services.model.Assessment)iter.next();
	      logger.info("assessment.assessment_id " + assessment.getAssessment_id());
	      restAssessment.setGoals(assessment.getGoals());
	      restAssessment.setExistingConditions(assessment.getExistingConditions());
	      restAssessment.setAssessment_id(assessment.getAssessment_id());
	      restAssessment.setWeight(assessment.getWeight());
	      restAssessment.setHeight(assessment.getHeight());
	      restAssessments.add(restAssessment);
	    }
	    restCandidate.setAssessments(restAssessments);
	    
	    return new ResponseEntity<Object>(restCandidate, responseHeaders, HttpStatus.OK);
	  }	  
	  
	  @RequestMapping(value={"rest/assessment"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  @ApiOperation(value="createAssessment", notes="Accepts a POST method to create a assessment")
	  public ResponseEntity<Object> createAssessment(@ApiParam(value="RequestBody with a JSON RestAssessment", required=true) @RequestBody com.fitnessAssessment.services.rest.Assessment inputRestAssessment)
	    throws Exception
	  {
	    HttpHeaders responseHeaders = new HttpHeaders();
	    com.fitnessAssessment.services.model.Assessment assessment = null;
	    com.fitnessAssessment.services.rest.Assessment restAssessment = new com.fitnessAssessment.services.rest.Assessment();
	    com.fitnessAssessment.services.rest.Candidate restCandidate = new com.fitnessAssessment.services.rest.Candidate();
	    try
	    {
	      com.fitnessAssessment.services.model.Candidate candidate = (com.fitnessAssessment.services.model.Candidate)this.candidateDao.findOne(inputRestAssessment.getCandidate().getCandidate_id());
	      if (candidate == null)
	      {
	        logger.error("candidate not found, throwing Resource Not Found exception");
	        logger.info("candidate not found, throwing Resource Not Found exception");
	        throw new ResourceNotFoundException("Resource Not Found - no candidate with candidate_id: " + inputRestAssessment.getCandidate().getCandidate_id(), "No candidate with id = " + inputRestAssessment.getCandidate().getCandidate_id() + " found in repository");
	      }
	      assessment = new com.fitnessAssessment.services.model.Assessment(candidate, inputRestAssessment.getGoals(), inputRestAssessment.getExistingConditions(), inputRestAssessment.getHeight(), inputRestAssessment.getWeight());
	      
	      
	      logger.info("About to save assessment:" + assessment.toString());
	      this.assessmentDao.save(assessment);
	      
	      restAssessment.setAssessment_id(assessment.getAssessment_id());
	      restAssessment.setGoals(assessment.getGoals());
	      restAssessment.setExistingConditions(assessment.getExistingConditions());
	      restAssessment.setWeight(assessment.getWeight());
	      restAssessment.setHeight(assessment.getHeight());
	      restCandidate.setEmail(candidate.getEmail());
	      restCandidate.setFirstName(candidate.getFirstName());
	      restCandidate.setLastName(candidate.getLastName());
	      restCandidate.setCandidate_id(candidate.getCandidate_id());
	      restCandidate.setAssessments(null);
	      restAssessment.setCandidate(restCandidate);
	    } catch (ConstraintViolationException ex) {
	    	throw new InvalidRequestBodyException("Required Field not set", "Required field not set in the request"); 
	 	} catch (InvalidRequestBodyException ex) {
	        ErrorMessage errorMessage = new ErrorMessage();
	        errorMessage.setMessage(ex.getMessage());
	        return new ResponseEntity<Object>(errorMessage, responseHeaders, HttpStatus.BAD_REQUEST);	    
	  	} catch (Exception ex) {
	      ErrorMessage errorMessage = new ErrorMessage();
	      errorMessage.setMessage(ex.getMessage());
	      return new ResponseEntity<Object>(errorMessage, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    return new ResponseEntity<Object>(restAssessment, responseHeaders, HttpStatus.CREATED);
	  }	  
	  
	  @RequestMapping(value={"rest/assessment/{assessment-id}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  @ApiOperation(value="GetAssessment", notes="Accepts a GET method to retrieve a assessment by assessment ID")
	  public ResponseEntity<Object> getAssessment(@ApiParam(value="UniqueKey for Assessment", required=true) @PathVariable("assessment-id") Long assessmentId)
	    throws Exception
	  {
	    logger.info("entering");
	    HttpHeaders responseHeaders = new HttpHeaders();
	    com.fitnessAssessment.services.model.Assessment assessment = new com.fitnessAssessment.services.model.Assessment();
	    com.fitnessAssessment.services.rest.Assessment restAssessment = new com.fitnessAssessment.services.rest.Assessment();
	    com.fitnessAssessment.services.rest.Candidate restCandidate = new com.fitnessAssessment.services.rest.Candidate();
	    
	    assessment = (com.fitnessAssessment.services.model.Assessment)this.assessmentDao.findOne(assessmentId);
	    if (assessment == null) {
	      throw new ResourceNotFoundException("Resource Not Found", "No assessment with id = " + assessmentId.toString() + " found in repository");
	    }
	    com.fitnessAssessment.services.model.Candidate candidate = assessment.getCandidate();
	    restAssessment.setAssessment_id(assessment.getAssessment_id());
	    restAssessment.setExistingConditions(assessment.getExistingConditions());
	    restAssessment.setGoals(assessment.getGoals());
	    restAssessment.setHeight(assessment.getHeight());
	    restAssessment.setWeight(assessment.getWeight());
	    restCandidate.setEmail(candidate.getEmail());
	    restCandidate.setFirstName(candidate.getFirstName());
	    restCandidate.setCandidate_id(candidate.getCandidate_id());
	    //don't need to display all the assessments in this response
	    restCandidate.setAssessments(null);
	    restAssessment.setCandidate(restCandidate);
	    
	    return new ResponseEntity<Object>(restAssessment, responseHeaders, HttpStatus.OK);
	  }	  
	  
	  

}
