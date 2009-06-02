<%@ page import="com.tomaslin.Book" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Book List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Book</g:link></span>
        </div>
        <div class="body">
            <h1>Book List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="title" title="Title" />
                        
                   	        <g:sortableColumn property="author" title="Author" />
                        
                   	        <g:sortableColumn property="description" title="Description" />
                        
                   	        <g:sortableColumn property="url" title="Url" />
                        
                   	        <g:sortableColumn property="imageUrl" title="Image Url" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bookInstanceList}" status="i" var="bookInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bookInstance.id}">${fieldValue(bean:bookInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:bookInstance, field:'title')}</td>
                        
                            <td>${fieldValue(bean:bookInstance, field:'author')}</td>
                        
                            <td>${fieldValue(bean:bookInstance, field:'description')}</td>
                        
                            <td>${fieldValue(bean:bookInstance, field:'url')}</td>
                        
                            <td>${fieldValue(bean:bookInstance, field:'imageUrl')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bookInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
