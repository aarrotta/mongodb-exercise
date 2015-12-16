package it.andrea.mongodb.validators;

import javax.print.DocFlavor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import it.andrea.mongodb.model.Address;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class AddressValidatorTest
{

	private static final String COUNTRY = "country";
	private static final String NUMBER = "12";
	private static final String STREET = "street";
	private static final String INVALID_NUMBER = "12aaafsd332&";

	@InjectMocks
	private final AddressValidator validator = new AddressValidator();

	@Mock
	private Address address;
	@Mock
	private Errors errors;

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCompleteAddressShouldGiveNoError()
	{
		// GIVEN
		given(address.getCountry()).willReturn(COUNTRY);
		given(address.getNumber()).willReturn(NUMBER);
		given(address.getStreet()).willReturn(STREET);

		// WHEN
		validator.validate(address, errors);

		// THEN
		verify(errors, never()).rejectValue(anyString(), anyString());
		verify(errors, never()).rejectValue(anyString(), anyString(), anyString());
		verify(errors, never()).rejectValue(anyString(), anyString(), any(), anyString());
	}

	@Test
	public void testMissingAttributesShouldGiveAnError()
	{
		// GIVEN
		given(address.getCountry()).willReturn(null);
		given(address.getNumber()).willReturn(null);
		given(address.getStreet()).willReturn(null);

		// WHEN
		validator.validate(address, errors);

		// THEN
		verify(errors).rejectValue(eq("country"), anyString());
		verify(errors).rejectValue(eq("number"), anyString());
		verify(errors).rejectValue(eq("street"), anyString());
	}

	@Test
	public void testInvalidNumberShouldGiveAnError()
	{
		// GIVEN
		given(address.getCountry()).willReturn(COUNTRY);
		given(address.getNumber()).willReturn(INVALID_NUMBER);
		given(address.getStreet()).willReturn(STREET);

		// WHEN
		validator.validate(address, errors);

		// THEN
		verify(errors).rejectValue(eq("number"), anyString());
	}

}