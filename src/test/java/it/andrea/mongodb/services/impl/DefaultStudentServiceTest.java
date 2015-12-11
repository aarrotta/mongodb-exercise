package it.andrea.mongodb.services.impl;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import it.andrea.mongodb.dtos.StudentsResult;
import it.andrea.mongodb.model.Student;
import it.andrea.mongodb.repository.StudentRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class DefaultStudentServiceTest
{

	private static final String STUDENT_NAME = "name";
	private static final int PAGE_NUMBER = 5;
	private static final int PAGE_SIZE = 10;
	private static final int FIRST_PAGE = 1;

	@InjectMocks
	private DefaultStudentService service = new DefaultStudentService();

	@Mock
	private StudentRepository repo;
	@Mock
	private MongoTemplate mt;

	@Mock
	private Student student1;
	@Mock
	private Student student2;
	@Mock
	private Pageable pageable;

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
		verify(repo).save(student1);
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

	@Test
	public void testGetExistingStudentsWithPagination() throws Exception
	{
		// GIVEN
		given(mt.find(any(Query.class), eq(Student.class))).willReturn(Arrays.asList(student1, student2));

		// WHEN
		final StudentsResult studentsResult = service.get(STUDENT_NAME, PAGE_NUMBER, PAGE_SIZE);

		// THEN
		ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		verify(mt).find(queryCaptor.capture(), eq(Student.class));
		assertThat(queryCaptor.getValue().getSkip(), equalTo(40));
		assertThat(queryCaptor.getValue().getLimit(), equalTo(10));
		assertThat(studentsResult.getTotalCount(), equalTo(2));
		assertThat(studentsResult.getData(), hasItems(student1, student2));
	}

	@Test
	public void testGetFirstStudentsWithPagination() throws Exception
	{
		// GIVEN
		given(mt.find(any(Query.class), eq(Student.class))).willReturn(Arrays.asList(student1, student2));

		// WHEN
		final StudentsResult studentsResult = service.get(STUDENT_NAME, FIRST_PAGE, PAGE_SIZE);

		// THEN
		ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		verify(mt).find(queryCaptor.capture(), eq(Student.class));
		assertThat(queryCaptor.getValue().getSkip(), equalTo(0));
		assertThat(queryCaptor.getValue().getLimit(), equalTo(10));
		assertThat(studentsResult.getTotalCount(), equalTo(2));
		assertThat(studentsResult.getData(), hasItems(student1, student2));
	}

}