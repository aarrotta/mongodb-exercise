package it.andrea.mongodb.dtos;

import java.util.Collection;

import it.andrea.mongodb.model.Student;

/**
 * Created by andrea on 12/10/15.
 */
public class StudentsResult
{
	private Integer totalCount;
	private Collection<Student> data;

	public StudentsResult(final Integer totalCount, final Collection<Student> data)
	{
		this.totalCount = totalCount;
		this.data = data;
	}

	public Integer getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(final Integer totalCount)
	{
		this.totalCount = totalCount;
	}

	public Collection<Student> getData()
	{
		return data;
	}

	public void setData(final Collection<Student> data)
	{
		this.data = data;
	}
}
