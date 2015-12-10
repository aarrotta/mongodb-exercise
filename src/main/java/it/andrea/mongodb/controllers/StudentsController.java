package it.andrea.mongodb.controllers;

import java.lang.String;
import java.util.Collection;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.andrea.mongodb.dtos.StudentsResult;
import it.andrea.mongodb.model.Student;
import it.andrea.mongodb.repository.StudentRepository;

/**
 * Created by andrea on 12/8/15.
 */
@RestController
@RequestMapping("/ex1/alumni")
public class StudentsController
{

	@Autowired
	private StudentRepository repo;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void create(@RequestBody Student student)
	{
		repo.save(student);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Object getStudents(@RequestParam(value = "name") final String name)
	{
		final Collection<Student> students = repo.findByName(name);
		if (CollectionUtils.isEmpty(students))
		{
			return HttpStatus.NO_CONTENT;
		}
		return new StudentsResult(students.size(), students);
	}

}
