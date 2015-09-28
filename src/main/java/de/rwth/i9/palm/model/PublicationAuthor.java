package de.rwth.i9.palm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.rwth.i9.palm.persistence.PersistableType;

@Entity
@Table( name = "publication_author" )
public class PublicationAuthor extends PersistableType
{
	@ManyToOne
	@JoinColumn( name = "publication_id" )
	private Publication publication;

	@ManyToOne
	@JoinColumn( name = "author_id" )
	private Author author;

	@Column( name = "position_", columnDefinition = "int default 0" )
	private int position;

	public Publication getPublication()
	{
		return publication;
	}

	public void setPublication( Publication publication )
	{
		this.publication = publication;
	}

	public Author getAuthor()
	{
		return author;
	}

	public void setAuthor( Author author )
	{
		this.author = author;
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition( int position )
	{
		this.position = position;
	}

}
