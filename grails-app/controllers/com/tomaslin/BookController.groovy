package com.tomaslin

import com.google.appengine.api.images.*;
import java.io.*;
import grails.converters.*
import groovy.xml.MarkupBuilder

class BookController {

	def persistenceManager
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
		def query = persistenceManager.newQuery( Book )
		def  bookInstanceList = query.execute()
		def total = 0
		if(  bookInstanceList &&  bookInstanceList.size() > 0){
			total =  bookInstanceList.size()
		}
		withFormat{
			html{ [ bookInstanceList :  bookInstanceList,  bookInstanceTotal: total ] }
			xml{
				//				render bookInstanceList as XML
					render(contentType: 'application/rss+xml') {
						rss(version:2.0, 'xmlns:media':"http://search.yahoo.com/mrss/", 'xmlns:atom':"http://www.w3.org/2005/Atom"
							){
							channel{
								title('Flex on Grails Library')
								description('A wonderful collection of books related to Flex on Grails')
								link('http://www.flexongrails.com')
								bookInstanceList.each(){ book ->
									item{
										title( book.title + '-' + book.author )
										description( book.description )
										link( book.url )
										if( book.imageUrl.trim() != ''){
											'media:thumbnail'( url: "http://grailsgae.appspot.com/book/proxy?url=${book.imageUrl}" )
											'media:content'( url: "http://grailsgae.appspot.com/book/proxy?url=${book.imageUrl}" )
										}
									}
								}
							}
						}
					}
				}
		}

    }

	def proxy = {
		def webImage = new ByteArrayOutputStream()
		webImage << new URL( "http://ecx.images-amazon.com/images/I/" + params.url ).openStream()		
		Image image = ImagesServiceFactory.makeImage(webImage.toByteArray());
		response.contentType = "image/png";
		response.outputStream << image.imageData;
		response.outputStream.flush();
	}

    def show = {
	    def bookInstance = persistenceManager.getObjectById( Book.class, Long.parseLong( params.id )  )
        if(!bookInstance) {
            flash.message = "Book not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ bookInstance : bookInstance ] }
    }

    def delete = {
	    def bookInstance = persistenceManager.getObjectById( Book.class, Long.parseLong( params.id )  )
        if(bookInstance) {
            try {
                persistenceManager.deletePersistent(bookInstance)
                flash.message = "Book ${params.id} deleted"
                redirect(action:list)
            }
            catch(Exception e) {
                flash.message = "Book ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Book not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
	    def bookInstance = persistenceManager.getObjectById( Book.class, Long.parseLong( params.id )  )
		if(!bookInstance) {
            flash.message = "Book not found with id ${params.id}"
            redirect(action:list)
        }
        else {
			bookInstance = persistenceManager.detachCopy( bookInstance )    
        	return [ bookInstance : bookInstance ]
        }
    }

    def update = {
	 	def bookInstance = persistenceManager.getObjectById( Book.class, Long.parseLong( params.id )  )
    
    	if(bookInstance) {
            bookInstance.properties = params
            if(!bookInstance.hasErrors()){
	
				try{
					persistenceManager.makePersistent(bookInstance)
				} catch( Exception e ){
				   	render(view:'edit',model:[bookInstance:bookInstance])
				}finally{
					flash.message = "Book ${params.id} updated"
	                redirect(action:show,id:bookInstance.id)
				}        
 			}
            else {
                render(view:'edit',model:[bookInstance:bookInstance])
            }
        }
        else {
            flash.message = "Book not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def bookInstance = new Book()
        bookInstance.properties = params
        return ['bookInstance':bookInstance]
    }

    def save = {
        def bookInstance = new Book(params)
		if(!bookInstance.hasErrors() ) {
			try{
				persistenceManager.makePersistent(bookInstance)
			} finally{
				flash.message = "Book ${bookInstance.id} created"
				redirect(action:show,id:bookInstance.id)	
			}
		}
   
		render(view:'create',model:[bookInstance:bookInstance])
        
    }
}
