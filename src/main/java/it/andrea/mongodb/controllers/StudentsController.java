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

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void create(@RequestBody Student student)
	{
		studentService.save(student);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object getStudents(@RequestParam(value = "name") final String name)
	{
		final StudentsResult studentsResult = studentService.get(name);
		if (studentsResult.getTotalCount() == 0)
		{
			return HttpStatus.NO_CONTENT;
		}
		return studentsResult;
	}

}
