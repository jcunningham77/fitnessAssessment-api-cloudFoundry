package com.fitnessAssessment.services.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fitnessAssessment.services.model.CandidateDAO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

import com.fitnessAssessment.services.model.AssessmentDAO;
import org.springframework.stereotype.Controller;


@Controller("fitnessAssessmentController")
public class FitnessAssessmentController {
	
	  @Autowired
	  private CandidateDAO userDao;
	  @Autowired
	  private AssessmentDAO betDao;
	  
	  @RequestMapping(value={"rest/handle"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  @ApiOperation(value="Hello World", notes="Test Method")
	  @ApiResponses({@io.swagger.annotations.ApiResponse(code=200, message="Hello World", response=String.class)})
	  public ResponseEntity<String> handle()
	  {
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("MyResponseHeader", "MyValue");
	    return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.OK);
	  }	  

}
