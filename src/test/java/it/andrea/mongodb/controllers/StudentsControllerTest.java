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
import org.springframework.validation.BindingResult;

import it.andrea.mongodb.dtos.StudentsResult;
import it.andrea.mongodb.model.Student;
import it.andrea.mongodb.services.StudentService;
import it.andrea.mongodb.validators.StudentValidator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class StudentsControllerTest
{

	private static final String STUDENT_NAME = "name";
	private static final int PAGE_NUMBER = 2;
	private static final int PAGE_SIZE = 3;
	private static final String EDUCATION_LEVEL = "educationLevel";

	@InjectMocks
	private StudentsController controller = new StudentsController();

	@Mock
	private StudentService studentService;
	@Mock
	private StudentValidator studentValidator;
	@Mock
	private Student student;
	@Mock
	private StudentsResult studentsResult;
	@Mock
	private BindingResult bindingResult;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testShouldCreateTheStudent() throws Exception
	{
		// GIVEN
		given(bindingResult.hasErrors()).willReturn(Boolean.FALSE);

		// WHEN
		final Object result = controller.create(student, bindingResult);

		// THEN
		verify(studentValidator).validate(student, bindingResult);
		Mockito.verify(studentService).save(student);
		assertThat(result, equalTo(HttpStatus.CREATED));
	}

	@Test
	public void testShouldNoCreateTheStudentInCaseOfErrors() throws Exception
	{
		// GIVEN
		given(bindingResult.hasErrors()).willReturn(Boolean.TRUE);

		// WHEN
		final Object result = controller.create(student, bindingResult);

		// THEN
		verify(studentValidator).validate(student, bindingResult);
		Mockito.verify(studentService, never()).save(student);
		assertThat(result, not(equalTo(HttpStatus.CREATED)));
	}

	@Test
	public void testShouldGetStudents() throws Exception
	{
		// GIVEN
		given(studentService.get(STUDENT_NAME, null)).willReturn(studentsResult);
		given(studentsResult.getTotalCount()).willReturn(1);
		given(studentsResult.getData()).willReturn(Arrays.asList(student));

		// WHEN
		final Object students = controller.getStudents(STUDENT_NAME, null, null, null);

		// THEN
		assertThat(students, equalTo(studentsResult));
	}

	@Test
	public void testShouldGetStudentsForEducationLevel() throws Exception
	{
		// GIVEN
		given(studentService.get(null, EDUCATION_LEVEL)).willReturn(studentsResult);
		given(studentsResult.getTotalCount()).willReturn(1);
		given(studentsResult.getData()).willReturn(Arrays.asList(student));

		// WHEN
		final Object students = controller.getStudents(null, EDUCATION_LEVEL, null, null);

		// THEN
		assertThat(students, equalTo(studentsResult));
	}

	@Test
	public void testShouldReturnAnErrorForNoResults() throws Exception
	{
		// GIVEN
		given(studentService.get(STUDENT_NAME, null)).willReturn(studentsResult);
		given(studentsResult.getTotalCount()).willReturn(0);
		given(studentsResult.getData()).willReturn(Collections.EMPTY_LIST);

		// WHEN
		final Object students = controller.getStudents(STUDENT_NAME, null, null, null);

		// THEN
		assertThat(students, equalTo(HttpStatus.NO_CONTENT));
	}

	@Test
	public void testShouldGetStudentsWithThePagination() throws Exception
	{
		// GIVEN
		given(studentService.get(STUDENT_NAME, null, PAGE_NUMBER, PAGE_SIZE)).willReturn(studentsResult);
		given(studentsResult.getTotalCount()).willReturn(1);
		given(studentsResult.getData()).willReturn(Arrays.asList(student));

		// WHEN
		final Object students = controller.getStudents(STUDENT_NAME, null, PAGE_NUMBER, PAGE_SIZE);

		// THEN
		assertThat(students, equalTo(studentsResult));
	}

	@Test
	public void testShouldReturnAnErrorForNoResultsWithThePagination() throws Exception
	{
		// GIVEN
		given(studentService.get(STUDENT_NAME, null, PAGE_NUMBER, PAGE_SIZE)).willReturn(studentsResult);
		given(studentsResult.getTotalCount()).willReturn(0);
		given(studentsResult.getData()).willReturn(Collections.EMPTY_LIST);

		// WHEN
		final Object students = controller.getStudents(STUDENT_NAME, null, PAGE_NUMBER, PAGE_SIZE);

		// THEN
		assertThat(students, equalTo(HttpStatus.NO_CONTENT));
	}

}