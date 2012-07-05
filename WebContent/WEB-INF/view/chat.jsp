<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>

<head>
    <title>Chatter: Mellis Spring Chatter</title>
</head>
<body>
<h2>Mellis Chat</h2>
	<form:form modelAttribute="chat" method="post" action="${submit_view}">
 
    <table>
    
    <tr>
        <td><form:label path="sender">Sender</form:label></td>
        <td><form:input path="sender" style="border : solid 2px #cccccc; padding : 4px; width : 200;"/></td>
    </tr>
    <tr>
        <td><form:label path="message">Message</form:label></td>
        <td><form:textarea path="message"  style="border : solid 2px #cccccc; padding : 4px; height : 100px; width : 500; overflow : auto;"/></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" name="add" value="Send Message" />
        
        </td>
    </tr>
	</table> 
	
	<hr/>
	<input type="submit" name="update" value="Update" />
	</form:form>
	<div id="protocol" style="border : solid 2px #cccccc; background : #000000; color : #ffffff; padding : 4px; height : 300px; overflow : auto;">${protocol} </div>
</body>
</html>