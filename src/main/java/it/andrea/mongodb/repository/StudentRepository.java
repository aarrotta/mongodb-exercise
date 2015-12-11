package it.andrea.mongodb.repository;

import java.lang.String;
import java.util.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import it.andrea.mongodb.model.Student;

/**
 * Created by andrea on 12/8/15.
 */
public interface StudentRepository extends MongoRepository<Student, String>
{

	/**
	 * Finds all the students with a given name
	 *
	 * @param name the name
	 * @return a {@link java.util.Collection} of {@link it.andrea.mongodb.model.Student}s
	 */
	Collection<Student> findByName(@Param("name") final String name);

}
