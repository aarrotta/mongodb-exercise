package it.andrea.mongodb.services.impl;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import it.andrea.mongodb.dtos.StudentsResult;
import it.andrea.mongodb.model.Student;
import it.andrea.mongodb.repository.StudentRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

public class DefaultStudentServiceTest
{

	private static final String STUDENT_NAME = "name";

	@InjectMocks
	private DefaultStudentService service = new DefaultStudentService();

	@Mock
	private StudentRepository repo;

	@Mock
	private Student student1;
	@Mock
	private Student student2;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveStudent() throws Exception
	{
		// WHEN
		service.save(student1);

		// THEN
		Mockito.verify(repo).save(student1);
	}

	@Test
	public void testGetExistingStudents() throws Exception
	{
		// GIVEN
		given(repo.findByName(STUDENT_NAME)).willReturn(Arrays.asList(student1, student2));

		// WHEN
		final StudentsResult studentsResult = service.get(STUDENT_NAME);

		// THEN
		assertThat(studentsResult.getTotalCount(), equalTo(2));
		assertThat(studentsResult.getData(), hasItems(student1, student2));
	}

	@Test
	public void testGetWhenNoStudentsAreAvailable() throws Exception
	{
		// GIVEN
		given(repo.findByName(STUDENT_NAME)).willReturn(Collections.EMPTY_LIST);

		// WHEN
		final StudentsResult studentsResult = service.get(STUDENT_NAME);

		// THEN
		assertThat(studentsResult.getTotalCount(), equalTo(0));
		assertThat(studentsResult.getData().size(), equalTo(0));
	}

}