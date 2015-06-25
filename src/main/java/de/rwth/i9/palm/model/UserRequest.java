package de.rwth.i9.palm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity
@Table( name = "user_request" )
public class UserRequest extends PersistableResource
{

	@Column
	private Date requestDate;

	@Column
	private String queryString;

	@Enumerated( EnumType.STRING )
	@Column( columnDefinition = "VARCHAR(24)" )
	private RequestType requestType;

	// getter / setter

	public RequestType getWitgetCondition()
	{
		return requestType;
	}

	public void setWitgetCondition( RequestType requestType )
	{
		this.requestType = requestType;
	}

	public Date getRequestDate()
	{
		return requestDate;
	}

	public void setRequestDate( Date requestDate )
	{
		this.requestDate = requestDate;
	}

	public String getQueryString()
	{
		return queryString;
	}

	public void setQueryString( String queryString )
	{
		this.queryString = queryString;
	}

	public RequestType getRequestType()
	{
		return requestType;
	}

	public void setRequestType( RequestType requestType )
	{
		this.requestType = requestType;
	}

}