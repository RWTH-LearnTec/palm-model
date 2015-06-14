package de.rwth.i9.palm.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity
@Table( name = "institution" )
@Indexed
@AnalyzerDef( 
		name = "institutionanalyzer", 
		tokenizer = @TokenizerDef( factory = StandardTokenizerFactory.class ), 
		filters = { 
			@TokenFilterDef( factory = LowerCaseFilterFactory.class ) 
			} 
		)
public class Institution extends PersistableResource
{
	@Column
	@Field( index = Index.YES, analyze = Analyze.YES, store = Store.YES )
	@Analyzer( definition = "institutionanalyzer" )
	private String name;

	@OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private Location location;

	@ContainedIn
	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "institutions" )
	private List<Author> authors;


	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation( Location location )
	{
		this.location = location;
	}

	public List<Author> getAuthors()
	{
		return authors;
	}

	public void setAuthors( List<Author> authors )
	{
		this.authors = authors;
	}

}
