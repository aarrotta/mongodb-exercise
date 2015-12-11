package it.andrea.mongodb.services;

import it.andrea.mongodb.dtos.StudentsResult;
import it.andrea.mongodb.model.Student;

/**
 * Created by andrea on 12/10/15.
 */
public interface StudentService
{
	/**
	 * Stores the student information on the DB
	 *
	 * @param student
	 */
	void save(final Student student);

	/**
	 * Returns all the existing students with a given name
	 *
	 * @param name the name
	 * @return an instance of {@link it.andrea.mongodb.dtos.StudentsResult} containing the result
	 */
	StudentsResult get(final String name);

	/**
	 * Returns the existing students with a given name using the pagination
	 *
	 * @param name       the name
	 * @param pageNumber the page number
	 * @param pageSize   the page size
	 * @return an instance of {@link it.andrea.mongodb.dtos.StudentsResult} containing the result
	 */
	StudentsResult get(final String name, final int pageNumber, final int pageSize);
}
