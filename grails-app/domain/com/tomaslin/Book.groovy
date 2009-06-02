package com.tomaslin



import javax.jdo.annotations.*;
// import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
class Book {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Long id

	@Persistent
	String title
	String author
	String description
	String url
	String imageUrl

    static constraints = {
    	id( visible:false)
		title()
		author()
		description(widget:'textarea')
		url(url:true)
		imageUrl( url:true)
	}
}
