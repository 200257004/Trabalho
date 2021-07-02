<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<title><fmt:message key="page.not.found"/></title>
		
		<c:import url="head.jsp"/>
	</head>
	
	<body>
		<c:import url="header.jsp"/>
		
		<div class="container">
			<h1><fmt:message key="page.not.found"/></h1>
		</div>
		
		<c:import url="footer.jsp"/>
	</body>
</html>