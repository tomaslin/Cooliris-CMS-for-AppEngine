<html>
    <head>
        <title>Welcome to My Library</title>
    </head>
    <body>
        <object id="o" 
		  classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" 
		  width="100%" 
		  height="100%">
		    <param name="movie"
		      value="http://apps.cooliris.com/embed/cooliris.swf" />
		    <param name="allowFullScreen" value="true" />
		    <param name="allowScriptAccess" value="always" />
		    <param name="flashvars" value="feed=http://${grailsApplication.config.google.appengine.application}.appspot.com/book/list.xml&numRows=3" />
		    <embed type="application/x-shockwave-flash"
		      src="http://apps.cooliris.com/embed/cooliris.swf"
		      flashvars="feed=http://${grailsApplication.config.google.appengine.application}.appspot.com/book/list.xml&numRows=3"
		      width="100%" 
		      height="100%" 
		      allowFullScreen="true"
		      allowScriptAccess="always">
		    </embed>
		</object>
    </body>
</html>