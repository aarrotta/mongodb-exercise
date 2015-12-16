package it.andrea.mongodb.validators;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import it.andrea.mongodb.model.Address;
import it.andrea.mongodb.model.Student;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class StudentValidatorTest
{

	private static final String VALID_NAME = "Andrea";
	private static final String INVALID_NAME = "Andrea11&%dsf";
	@InjectMocks
	private final StudentValidator validator = new StudentValidator();

	@Mock
	private AddressValidator addressValidator;
	@Mock
	private Student student;
	@Mock
	private Errors errors;
	@Mock
	private Address address1;
	@Mock
	private Address address2;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddressesShouldBeValidated()
	{
		// GIVEN
		given(student.getAddresses()).willReturn(Arrays.asList(address1, address2));

		// WHEN
		validator.validate(student, errors);

		// THEN
		verify(addressValidator).validate(address1, errors);
		verify(addressValidator).validate(address2, errors);
	}

	@Test
	public void testNameValidIfContainsLetters()
	{
		// GIVEN
		given(student.getName()).willReturn(VALID_NAME);

		// WHEN
		validator.validate(student, errors);

		// THEN
		verify(errors, never()).rejectValue(eq("name"), anyString());
	}

	@Test
	public void testNameInvalidIfContainsWrongCharacters()
	{
		// GIVEN
		given(student.getName()).willReturn(INVALID_NAME);

		// WHEN
		validator.validate(student, errors);

		// THEN
		verify(errors).rejectValue(eq("name"), anyString());
	}

}