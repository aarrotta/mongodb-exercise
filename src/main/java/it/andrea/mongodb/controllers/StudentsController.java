package it.andrea.mongodb.controllers;

import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import it.andrea.mongodb.dtos.StudentsResult;
import it.andrea.mongodb.model.Student;
import it.andrea.mongodb.services.StudentService;

/**
 * Created by andrea on 12/8/15.
 */
@RestController
@RequestMapping("/ex1/alumni")
public class StudentsController
{

	@Resource
	private StudentService studentService;

	/**
	 * This method creates a student with the given information
	 *
	 * @param student an instance of {@link it.andrea.mongodb.model.Student} containing all the information
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void create(@RequestBody Student student)
	{
		studentService.save(student);
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
	public Object getStudents(@RequestParam(value = "name") final String name,
	                          @RequestParam(value = "page", required = false) final Integer pageNumber,
	                          @RequestParam(value = "size", required = false) final Integer pageSize)
	{
		StudentsResult studentsResult = null;
		if (pageNumber != null && pageSize != null)
		{
			studentsResult = studentService.get(name, pageNumber, pageSize);
		}
		else
		{
			studentsResult = studentService.get(name);
		}
		if (studentsResult.getTotalCount() == 0)
		{
			return HttpStatus.NO_CONTENT;
		}
		return studentsResult;
	}

}
