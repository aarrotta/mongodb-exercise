package it.andrea.mongodb.services.impl;

import java.util.Collection;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import it.andrea.mongodb.dtos.StudentsResult;
import it.andrea.mongodb.model.Student;
import it.andrea.mongodb.repository.StudentRepository;
import it.andrea.mongodb.services.StudentService;

/**
 * Created by andrea on 12/10/15.
 */
@Service("studentService")
public class DefaultStudentService implements StudentService
{

	@Resource
	private StudentRepository repo;

	@Override
	public void save(final Student student)
	{
		repo.save(student);
	}

	@Override
	public StudentsResult get(final String name)
	{
		final Collection<Student> students = repo.findByName(name);
		return new StudentsResult(CollectionUtils.isNotEmpty(students) ? students.size() : 0, students);
	}
}
