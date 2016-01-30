package de.rwth.i9.palm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity
@Table( name = "academic_event_group" )
@Indexed
@AnalyzerDef( 
		name = "eventanalyzer", 
		tokenizer = @TokenizerDef( factory = StandardTokenizerFactory.class ), 
		filters = { 
			@TokenFilterDef( factory = LowerCaseFilterFactory.class ), 
			@TokenFilterDef( factory = SnowballPorterFilterFactory.class, params = { @Parameter( name = "language", value = "English" ) } ) 
			} 
		)
public class EventGroup extends PersistableResource
{
	@Column
	@Field( index = Index.YES, analyze = Analyze.NO, store = Store.YES )
	@Analyzer( definition = "eventanalyzer" )
	private String name;

	@Column
	@Lob
	private String description;

	@Enumerated( EnumType.STRING )
	@Column( length = 16 )
	private PublicationType publicationType;

	@Column
	@Field( index = Index.YES, analyze = Analyze.NO, store = Store.YES )
	@Boost( 3.0f )
	private String notation;

	@Column
	private String dblpUrl;

	@ContainedIn
	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "eventGroup" )
	private List<Event> events;

	@Column( columnDefinition = "bit default 0" )
	private boolean added = false;

	@Column
	private java.sql.Timestamp requestDate;

	public PublicationType getPublicationType()
	{
		return publicationType;
	}

	public void setPublicationType( PublicationType publicationType )
	{
		this.publicationType = publicationType;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public List<Event> getEvents()
	{
		return events;
	}

	public void setEvents( List<Event> events )
	{
		this.events = events;
	}


	public EventGroup addEvent( Event event )
	{
		if ( this.events == null )
			this.events = new ArrayList<Event>();
		// check if event volume or year duplicated
		boolean isEventExist = false;

		Event tempEvent = null;
		if ( !this.events.isEmpty() )
		{
			for ( Event eachEvent : this.events )
			{
				if ( eachEvent.getDblpUrl() != null && event.getDblpUrl() != null )
				{
					if ( eachEvent.getDblpUrl().contains( "#" ) )
						eachEvent.setDblpUrl( eachEvent.getDblpUrl().split( "#" )[0] );
					if ( event.getDblpUrl().contains( "#" ) )
						event.setDblpUrl( event.getDblpUrl().split( "#" )[0] );

					if ( eachEvent.getDblpUrl().equals( event.getDblpUrl() ) )
						isEventExist = true;
				}
				else
				{
					if ( this.publicationType.equals( PublicationType.JOURNAL ) )
					{
						if ( eachEvent.getVolume() != null && eachEvent.getYear() != null )
						{
							if ( eachEvent.getVolume().equals( event.getVolume() ) && eachEvent.getYear().equals( event.getYear() ) )
								isEventExist = true;
						}
					}
					else
					{
						if ( eachEvent.getVolume() != null && eachEvent.getYear() != null )
						{
							if ( eachEvent.getVolume().equals( event.getVolume() ) && eachEvent.getYear().equals( event.getYear() ) )
								isEventExist = true;
						}
						if ( eachEvent.getYear() != null && eachEvent.getYear().equals( event.getYear() ) )
							isEventExist = true;
					}
				}

				if ( isEventExist )
				{
					tempEvent = eachEvent;
					break;
				}
			}
		}

		if ( !isEventExist )
		{
			event.setEventGroup( this );
			this.events.add( event );
		}
		else
		{
			tempEvent.setName( event.getName() );
			if ( event.getVolume() != null )
				tempEvent.setVolume( event.getVolume() );
			if ( event.getAdditionalInformation() != null )
				tempEvent.setAdditionalInformation( event.getAdditionalInformation() );
		}
		return this;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getDblpUrl()
	{
		return dblpUrl;
	}

	public void setDblpUrl( String dblpUrl )
	{
		this.dblpUrl = dblpUrl;
	}

	public String getNotation()
	{
		return notation;
	}

	public void setNotation( String notation )
	{
		this.notation = notation;
	}

	public java.sql.Timestamp getRequestDate()
	{
		return requestDate;
	}

	public void setRequestDate( java.sql.Timestamp requestDate )
	{
		this.requestDate = requestDate;
	}

	public boolean isAdded()
	{
		return added;
	}

	public void setAdded( boolean added )
	{
		this.added = added;
	}

}
