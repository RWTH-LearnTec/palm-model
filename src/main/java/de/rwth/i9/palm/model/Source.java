package de.rwth.i9.palm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity
@Table( name = "source" )
public class Source extends PersistableResource
{
	@Column( nullable = false )
	private String name;
	
	@Column
	@Lob
	private String description;

	@Enumerated( EnumType.STRING )
	@Column( length = 16 )
	private SourceType SourceType;

	public void setDescription( String description )
	{
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public SourceType getSourceType()
	{
		return SourceType;
	}

	public void setSourceType( SourceType sourceType )
	{
		SourceType = sourceType;
	}

}
