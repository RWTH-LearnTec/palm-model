package de.rwth.i9.palm.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity
@Table( name = "publication" )
@Indexed
@AnalyzerDef( 
		name = "customanalyzer", 
		tokenizer = @TokenizerDef( factory = StandardTokenizerFactory.class ), 
		filters = { 
			@TokenFilterDef( factory = LowerCaseFilterFactory.class ), 
			@TokenFilterDef( factory = SnowballPorterFilterFactory.class, params = { @Parameter( name = "language", value = "English" ) } ) 
			} 
		)
public class Publication extends PersistableResource
{
	@Column( nullable = false )
	@Field( index = Index.YES, analyze = Analyze.YES, store = Store.YES )
	@Boost( 3.0f )
	@Lob
	private String title;
	
	@Column
	private Date publicationDate;
	
	@Column( length = 10 )
	private String publicationDateFormat;

	@Column
	@Lob
	@Field( index = Index.YES, termVector = TermVector.WITH_POSITION_OFFSETS, store = Store.YES )
	@Analyzer( definition = "customanalyzer" )
	private String abstractText;

	@Enumerated( EnumType.STRING )
	@Column( length = 20, columnDefinition = "varchar(20) default 'NOT_COMPLETE'" )
	private CompletionStatus abstractStatus;

	@Column
	@Lob
	@Field( index = Index.YES, termVector = TermVector.WITH_POSITION_OFFSETS, store = Store.YES )
	@Analyzer( definition = "customanalyzer" )
	private String contentText;

	@Column
	@Lob
	private String keywordText;

	@Enumerated( EnumType.STRING )
	@Column( length = 20, columnDefinition = "varchar(20) default 'NOT_COMPLETE'" )
	private CompletionStatus keywordStatus;

	@Column
	@Lob
	private String referenceText;

	@Column
	private String publisher;

	@Column( length = 5 )
	private String volume;

	@Column( length = 20 )
	private String issue;

	@Column( length = 20 )
	private String pages;

	@Enumerated( EnumType.STRING )
	@Column( length = 16 )
	private PublicationType publicationType;

	@Column
	@Lob
	private String additionalInformation;

	@Column
	@Lob
	@Field( index = Index.YES, termVector = TermVector.WITH_POSITION_OFFSETS, store = Store.YES )
	private String citationText;

	@Column( columnDefinition = "int default 0" )
	private int citedBy;

	@Column( columnDefinition = "bit default 1" )
	private boolean contentUpdated = true;

	@Column( columnDefinition = "bit default 0" )
	private boolean pdfExtracted = false;

	@Column( columnDefinition = "varchar(15) default 'english'" )
	private String language;

	// relations
	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinTable( name = "publication_keyword", joinColumns = @JoinColumn( name = "publication_id" ), inverseJoinColumns = @JoinColumn( name = "keyword_id" ) )
	private Set<Subject> subjects;

	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "publication", orphanRemoval = true )
	private Set<PublicationTopic> publicationTopics;
	
	@ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn( name = "event_id" )
	private Event event;
	
	@ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn( name = "dataset_id" )
	private Dataset dataset;

	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinTable( name = "publication_author", joinColumns = @JoinColumn( name = "publication_id" ), inverseJoinColumns = @JoinColumn( name = "author_id" ) )
	private Set<Author> coAuthors;

	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinTable( name = "publication_cites", joinColumns = @JoinColumn( name = "publication_id" ), inverseJoinColumns = @JoinColumn( name = "publication_cites_id" ) )
	private Set<Publication> publicationCitess;

	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinTable( name = "publication_citedby", joinColumns = @JoinColumn( name = "publication_id" ), inverseJoinColumns = @JoinColumn( name = "publication_citedby_id" ) )
	private Set<Publication> publicationCitedBys;

	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "publication", orphanRemoval = true  )
	private Set<Reference> references;

	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "publication", orphanRemoval = true )
	private Set<PublicationHistory> publicationHistories;

	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "publication", orphanRemoval = true )
	private Set<PublicationSource> publicationSources;

	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "publication", orphanRemoval = true )
	private Set<PublicationFile> publicationFiles;

	public Event getEvent()
	{
		return event;
	}

	public void setEvent( Event event )
	{
		this.event = event;
	}

	public Set<Reference> getReferences()
	{
		return references;
	}

	public void setReferences( Set<Reference> references )
	{
		this.references = references;
	}

	public Publication addReference( Reference reference )
	{
		if ( this.references == null )
			this.references = new LinkedHashSet<Reference>();
		this.references.add( reference );

		return this;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public String getAbstractText()
	{
		return abstractText;
	}

	public void setAbstractText( String abstractText )
	{
		this.abstractText = abstractText;
	}

	public String getContentText()
	{
		return contentText;
	}

	public void setContentText( String contentText )
	{
		this.contentText = contentText;
	}

	public Set<Subject> getKeywords()
	{
		return subjects;
	}

	public void setKeywords( Set<Subject> subjects )
	{
		this.subjects = subjects;
	}

	public Publication addKeyword( final Subject subject )
	{
		if ( this.subjects == null )
			this.subjects = new LinkedHashSet<Subject>();

		subjects.add( subject );
		return this;
	}

	public Event getVenue()
	{
		return event;
	}

	public void setVenue( Event event )
	{
		this.event = event;
	}

	public Set<Author> getCoAuthors()
	{
		return coAuthors;
	}

	public void setCoAuthors( Set<Author> coAuthors )
	{
		this.coAuthors = coAuthors;
	}

	public Publication addCoAuthor( final Author author )
	{
		if ( this.coAuthors == null )
			this.coAuthors = new LinkedHashSet<Author>();

		if( !this.coAuthors.contains( author ))
			this.coAuthors.add( author );
		
		return this;
	}

	public Set<Publication> getPublicationCitess()
	{
		return publicationCitess;
	}

	public void setPublicationCitess( Set<Publication> publicationCitess )
	{
		this.publicationCitess = publicationCitess;
	}

	public Publication addPublicationCites( final Publication publicationCites )
	{
		if ( this.publicationCitess == null )
			this.publicationCitess = new LinkedHashSet<Publication>();

		this.publicationCitess.add( publicationCites );
		return this;
	}

	public Set<Publication> getPublicationCitedBys()
	{
		return publicationCitedBys;
	}

	public void setPublicationCitedBys( Set<Publication> publicationCitedBys )
	{
		this.publicationCitedBys = publicationCitedBys;
	}

	public Publication addPublicationCiteBy( final Publication publicationCiteBy )
	{
		if ( this.publicationCitedBys == null )
			this.publicationCitedBys = new LinkedHashSet<Publication>();

		this.publicationCitedBys.add( publicationCiteBy );
		return this;
	}

	public String getCitationText()
	{
		return citationText;
	}

	public void setCitationText( String citationText )
	{
		this.citationText = citationText;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage( String language )
	{
		this.language = language;
	}

	public Set<Subject> getSubjects()
	{
		return subjects;
	}

	public void setSubjects( Set<Subject> subjects )
	{
		this.subjects = subjects;
	}

	public Publication addSubject( Subject subject )
	{
		if ( this.subjects == null )
			this.subjects = new LinkedHashSet<Subject>();

		this.subjects.add( subject );

		return this;
	}

	public Set<PublicationHistory> getPublicationHistories()
	{
		return publicationHistories;
	}

	public void setPublicationHistories( Set<PublicationHistory> publicationHistories )
	{
		this.publicationHistories = publicationHistories;
	}

	public Publication addPublicationHistory( PublicationHistory publicationHistory )
	{
		if ( this.publicationHistories == null )
			this.publicationHistories = new LinkedHashSet<PublicationHistory>();
		this.publicationHistories.add( publicationHistory );
		return this;
	}

	public Set<PublicationSource> getPublicationSources()
	{
		return publicationSources;
	}

	public void setPublicationSources( Set<PublicationSource> publicationSources )
	{
		if ( this.publicationSources == null )
			this.publicationSources = new LinkedHashSet<PublicationSource>();
		this.publicationSources.clear();
		this.publicationSources.addAll( publicationSources );
	}

	public Publication addPublicationSource( PublicationSource publicationSource )
	{
		if ( this.publicationSources == null )
			this.publicationSources = new LinkedHashSet<PublicationSource>();
		this.publicationSources.add( publicationSource );
		return this;
	}

	public void removeNonUserInputPublicationSource()
	{
		if ( this.publicationSources != null )
		{
			for ( Iterator<PublicationSource> ps = this.publicationSources.iterator(); ps.hasNext(); )
			{
				PublicationSource publicationSource = ps.next();
				if ( publicationSource.getSourceType() != SourceType.USER )
					ps.remove();
			}
		}
	}

	public Set<PublicationTopic> getPublicationTopics()
	{
		return publicationTopics;
	}

	public void setPublicationTopics( Set<PublicationTopic> publicationTopics )
	{
		this.publicationTopics = publicationTopics;
	}

	public Publication addPublicationTopic( PublicationTopic publicationTopic )
	{
		if ( this.publicationTopics == null )
			this.publicationTopics = new LinkedHashSet<PublicationTopic>();
		this.publicationTopics.add( publicationTopic );
		return this;
	}

	public void removeAllPublicationTopic()
	{
		this.publicationTopics = null;
	}

	public Dataset getDataset()
	{
		return dataset;
	}

	public void setDataset( Dataset dataset )
	{
		this.dataset = dataset;
	}

	public Date getPublicationDate()
	{
		return publicationDate;
	}

	public void setPublicationDate( Date publicationDate )
	{
		this.publicationDate = publicationDate;
	}

	public String getPublisher()
	{
		return publisher;
	}

	public void setPublisher( String publisher )
	{
		this.publisher = publisher;
	}

	public String getVolume()
	{
		return volume;
	}

	public void setVolume( String volume )
	{
		this.volume = volume;
	}

	public String getIssue()
	{
		return issue;
	}

	public void setIssue( String issue )
	{
		this.issue = issue;
	}

	public String getPages()
	{
		return pages;
	}

	public void setPages( String pages )
	{
		this.pages = pages;
	}

	public int getCitedBy()
	{
		return citedBy;
	}

	public void setCitedBy( int citedBy )
	{
		this.citedBy = citedBy;
	}

	public PublicationType getPublicationType()
	{
		return publicationType;
	}

	public void setPublicationType( PublicationType publicationType )
	{
		this.publicationType = publicationType;
	}

	public boolean isContentUpdated()
	{
		return contentUpdated;
	}

	public void setContentUpdated( boolean contentUpdated )
	{
		this.contentUpdated = contentUpdated;
	}

	public boolean isPdfExtracted()
	{
		return pdfExtracted;
	}

	public void setPdfExtracted( boolean pdfExtracted )
	{
		this.pdfExtracted = pdfExtracted;
	}

	public String getKeywordText()
	{
		return keywordText;
	}

	public void setKeywordText( String keywordText )
	{
		this.keywordText = keywordText;
	}

	public String getReferenceText()
	{
		return referenceText;
	}

	public void setReferenceText( String referenceText )
	{
		this.referenceText = referenceText;
	}

	public Set<PublicationFile> getPublicationFiles()
	{
		return publicationFiles;
	}

	public void setPublicationFiles( Set<PublicationFile> publicationFiles )
	{
		this.publicationFiles = publicationFiles;
	}

	public Publication addPublicationFile( PublicationFile publicationFile )
	{
		if ( this.publicationFiles == null )
			this.publicationFiles = new HashSet<PublicationFile>();

		this.publicationFiles.add( publicationFile );
		return this;
	}

	public String getAdditionalInformation()
	{
		return additionalInformation;
	}

	public void setAdditionalInformation( String additionalInformation )
	{
		this.additionalInformation = additionalInformation;
	}

	public String getPublicationDateFormat()
	{
		return publicationDateFormat;
	}

	public void setPublicationDateFormat( String publicationDateFormat )
	{
		this.publicationDateFormat = publicationDateFormat;
	}

	public Set<String> getSourceFiles()
	{
		Set<String> sourceFileUrl = new HashSet<String>();
		if ( this.publicationFiles == null || publicationFiles.isEmpty() )
			return Collections.emptySet();
		else
			for ( PublicationFile pubFile : this.publicationFiles )
			{
				sourceFileUrl.add( pubFile.getUrl() );
			}

		return sourceFileUrl;
	}

	public Set<PublicationFile> getPublicationFilesPdf()
	{
		if ( this.publicationFiles == null || publicationFiles.isEmpty() )
			return Collections.emptySet();
		else
		{
			Set<PublicationFile> publicationFilePdf = new LinkedHashSet<>();
			for ( PublicationFile publicationFile : this.publicationFiles )
			{
				if ( publicationFile.getFileType().equals( FileType.PDF ) )
					publicationFilePdf.add( publicationFile );
			}
			return publicationFilePdf;
		}
	}

	public Set<PublicationFile> getPublicationFilesHtml()
	{
		if ( this.publicationFiles == null || publicationFiles.isEmpty() )
			return Collections.emptySet();
		else
		{
			Set<PublicationFile> publicationFileHtml = new LinkedHashSet<>();
			for ( PublicationFile publicationFile : this.publicationFiles )
			{
				if ( publicationFile.getFileType().equals( FileType.HTML ) )
					publicationFileHtml.add( publicationFile );
			}
			return publicationFileHtml;
		}
	}

	public CompletionStatus getAbstractStatus()
	{
		return abstractStatus;
	}

	public void setAbstractStatus( CompletionStatus abstractStatus )
	{
		this.abstractStatus = abstractStatus;
	}

	public CompletionStatus getKeywordStatus()
	{
		return keywordStatus;
	}

	public void setKeywordStatus( CompletionStatus keywordStatus )
	{
		this.keywordStatus = keywordStatus;
	}

}

