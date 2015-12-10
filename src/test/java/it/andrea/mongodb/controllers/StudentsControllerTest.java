package it.andrea.mongodb.controllers;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import it.andrea.mongodb.dtos.StudentsResult;
import it.andrea.mongodb.model.Student;
import it.andrea.mongodb.services.StudentService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

public class StudentsControllerTest
{

	private static final String STUDENT_NAME = "name";

	@InjectMocks
	private StudentsController controller = new StudentsController();

	@Mock
	private StudentService studentService;
	@Mock
	private Student student;
	@Mock
	private StudentsResult studentsResult;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testShouldCreateTheStudent() throws Exception
	{
		// WHEN
		controller.create(student);

		// THEN
		Mockito.verify(studentService).save(student);
	}

	@Test
	public void testShouldGetStudents() throws Exception
	{
		// GIVEN
		given(studentService.get(STUDENT_NAME)).willReturn(studentsResult);
		given(studentsResult.getTotalCount()).willReturn(1);
		given(studentsResult.getData()).willReturn(Arrays.asList(student));

		// WHEN
		final Object students = controller.getStudents(STUDENT_NAME);

		// THEN
		assertThat(students, equalTo(studentsResult));
	}

	@Test
	public void testShouldReturnAnErrorForNoResults() throws Exception
	{
		// GIVEN
		given(studentService.get(STUDENT_NAME)).willReturn(studentsResult);
		given(studentsResult.getTotalCount()).willReturn(0);
		given(studentsResult.getData()).willReturn(Collections.EMPTY_LIST);

		// WHEN
		final Object students = controller.getStudents(STUDENT_NAME);

		// THEN
		assertThat(students, equalTo(HttpStatus.NO_CONTENT));
	}

}