<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="b" %>
<!DOCTYPE html>
<html>
	<head>
		<title><fmt:message key="title.login"/></title>
		
		<c:import url="../head.jsp"/>
	</head>
	
	<body>
		<c:import url="../header.jsp"/>
		
		<div class="container">
			<ol class="breadcrumb">
				<li class="active"><fmt:message key="title.login"/></li>
			</ol>
		
			<h1><fmt:message key="title.login"/></h1>
			
			<div class="panel panel-default">
				<div class="panel-body">
					<form id="signin" action="${linkTo[SessionController].signin}" method="post">
						<b:messages name="signin"/>
						
						<div class="form-group">
							<label for="email"><fmt:message key="label.email"/></label>
							<input id="email" type="email" name="email" value="${email}" placeholder="<fmt:message key="watermark.user.email"/>" class="form-control" autofocus>
							<b:messages name="email"/>
						</div>
						
						<div class="form-group">
							<label for="password"><fmt:message key="label.password"/></label>
							<input id="password" type="password" name="password" placeholder="<fmt:message key="watermark.user.password"/>" class="form-control">
							<b:messages name="password"/>
						</div>
						
						<input type="submit" value="<fmt:message key="button.signin"/>" class="btn btn-lg btn-primary btn-block">
					</form>
				</div>
			</div>
		</div>
		
		<c:import url="../footer.jsp"/>
	</body>
</html>