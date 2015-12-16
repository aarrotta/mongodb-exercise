package it.andrea.mongodb.controllers;

import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.andrea.mongodb.dtos.StudentsResult;
import it.andrea.mongodb.model.Student;
import it.andrea.mongodb.services.StudentService;
import it.andrea.mongodb.validators.StudentValidator;

/**
 * Created by andrea on 12/8/15.
 */
@RestController
@RequestMapping("/ex1/alumni")
public class StudentsController
{

	@Resource
	private StudentService studentService;
	@Resource
	private StudentValidator studentValidator;

	/**
	 * This method creates a student with the given information
	 *
	 * @param student an instance of {@link it.andrea.mongodb.model.Student} containing all the information
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus
	public Object create(final @RequestBody Student student, final BindingResult bindingResult)
	{
		studentValidator.validate(student, bindingResult);
		if(bindingResult.hasErrors())
		{
			return bindingResult.getModel();
		}
		studentService.save(student);
		return HttpStatus.CREATED;
	}

	/**
	 * This method returns all the students with a given name. If pageNumber and pageSize are not null, the search
	 * is done using the pagination.
	 *
	 * @param name       the name to search
	 * @param pageNumber the page number for the pagination
	 * @param pageSize   the page size for the pagination
	 * @return an instance of {@link it.andrea.mongodb.dtos.StudentsResult} or an Http 204 error code if no students are found
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object getStudents(@RequestParam(value = "name", required = false) final String name,
	                          @RequestParam(value = "educationLevel", required = false) final String educationLevel,
	                          @RequestParam(value = "page", required = false) final Integer pageNumber,
	                          @RequestParam(value = "size", required = false) final Integer pageSize)
	{
		StudentsResult studentsResult = null;
		if (pageNumber != null && pageSize != null)
		{
			studentsResult = studentService.get(name, educationLevel, pageNumber, pageSize);
		}
		else
		{
			studentsResult = studentService.get(name, educationLevel);
		}
		if (studentsResult.getTotalCount() == 0)
		{
			return HttpStatus.NO_CONTENT;
		}
		return studentsResult;
	}

}
