package it.andrea.mongodb.validators;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.andrea.mongodb.model.Address;
import it.andrea.mongodb.model.Student;

/**
 * Created by andrea on 12/16/15.
 */
@Component
public class StudentValidator implements Validator
{

	@Resource
	AddressValidator addressValidator;

	@Override
	public boolean supports(final Class<?> aClass)
	{
		return Student.class.equals(aClass);
	}

	@Override
	public void validate(final Object o, final Errors errors)
	{
		final Student student = (Student) o;
		for(Address address : student.getAddresses())
		{
			getAddressValidator().validate(address, errors);
		}
	}

	public AddressValidator getAddressValidator()
	{
		return addressValidator;
	}

}
