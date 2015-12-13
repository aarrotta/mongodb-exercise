package it.andrea.mongodb.services.impl;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
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
	@Resource
	private MongoTemplate mt;

	@Override
	public void save(final Student student)
	{
		repo.save(student);
	}

	@Override
	public StudentsResult get(final String name, final String educationLevel)
	{
		final Query query = new Query();
		query.addCriteria(buildCriteriaDefinition(name, educationLevel));
		return getResult(query);
	}

	@Override
	public StudentsResult get(final String name, final String educationLevel, final int pageNumber, final int pageSize)
	{
		final Query query = new Query();
		final CriteriaDefinition criteriaDefinition = buildCriteriaDefinition(name, educationLevel);
		query.addCriteria(criteriaDefinition);
		query.skip(pageNumber * pageSize - pageSize);
		query.limit(pageSize);
		return getResult(query);
	}

	private StudentsResult getResult(final Query query)
	{
		final List<Student> students = mt.find(query, Student.class);
		return new StudentsResult(CollectionUtils.isNotEmpty(students) ? students.size() : 0, students);
	}

	private CriteriaDefinition buildCriteriaDefinition(final String name, final String educationLevel)
	{
		Criteria criteria = null;
		boolean whereClauseCreated = false;
		if (name != null)
		{
			criteria = Criteria.where("name").is(name);
			whereClauseCreated = true;
		}
		if (educationLevel != null)
		{
			if (whereClauseCreated)
			{
				criteria.and("education." + educationLevel).ne(null);
			}
			else
			{
				criteria = Criteria.where("education." + educationLevel).ne(null);
			}
		}
		return criteria;
	}

}
