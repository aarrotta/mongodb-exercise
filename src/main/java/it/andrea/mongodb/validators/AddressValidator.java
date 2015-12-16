package it.andrea.mongodb.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.andrea.mongodb.model.Address;

/**
 * Created by andrea on 12/16/15.
 */
@Component
public class AddressValidator implements Validator
{
	@Override
	public boolean supports(final Class<?> aClass)
	{
		return Address.class.equals(aClass);
	}

	@Override
	public void validate(final Object o, final Errors errors)
	{
		Address address = (Address) o;
		if (StringUtils.isBlank(address.getCountry()))
		{
			errors.rejectValue("country", "Attribute 'country' of the address cannot be empty.");
		}
		if (StringUtils.isBlank(address.getNumber()))
		{
			errors.rejectValue("number", "Attribute 'number' of the address cannot be empty.");
		}
		else if (!StringUtils.isNumeric(address.getNumber()))
		{
			errors.rejectValue("number", "Attribute 'number' can only contain numbers.");
		}
		if (StringUtils.isBlank(address.getStreet()))
		{
			errors.rejectValue("street", "Attribute 'street' of the address cannot be empty.");
		}
	}
}
