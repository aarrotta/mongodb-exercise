package it.andrea.mongodb.services;

import it.andrea.mongodb.dtos.StudentsResult;
import it.andrea.mongodb.model.Student;

/**
 * Created by andrea on 12/10/15.
 */
public interface StudentService
{
	void save(final Student student);

	StudentsResult get(final String name);
}
