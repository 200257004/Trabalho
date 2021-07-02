<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="b" %>
<!DOCTYPE html>
<html>
	<head>
		<title><fmt:message key="title.newCategory"/></title>
		
		<c:import url="../head.jsp"/>
	</head>
	
	<body>
		<c:import url="../header.jsp"/>
		
		<div class="container">
			<c:choose>
				<c:when test="${category.id == null}">
					<ol class="breadcrumb">
						<li><a href="${linkTo[DashboardController].dashboard}"><fmt:message key="title.dashboard"/></a></li>
						<li class="active"><fmt:message key="title.newCategory"/></li>
					</ol>
					
					<h1><fmt:message key="title.newCategory"/></h1>
				</c:when>
				
				<c:otherwise>
					<ol class="breadcrumb">
						<li><a href="${linkTo[DashboardController].dashboard}"><fmt:message key="title.dashboard"/></a></li>
						<li class="active"><fmt:message key="title.editCategory"/></li>
					</ol>
					
					<h1><fmt:message key="title.editCategory"/></h1>
				</c:otherwise>
			</c:choose>
			
			<div class="panel panel-default">
				<div class="panel-body">
					<form id="add-category" action="${linkTo[CategoryController].add}" method="post">
						<b:messages name="error"/>
						
						<input type="hidden" name="category.id" value="${category.id}">
						
						<div class="form-group">
							<label for="description"><fmt:message key="label.description"/></label>
							<input id="description" name="category.description" value="${category.description}" placeholder="<fmt:message key="watermark.category.description"/>" class="form-control" autofocus>
						</div>
						
						<c:choose>
							<c:when test="${category.id == null}">
								<input type="submit" value="<fmt:message key="button.add"/>" class="btn btn-primary">
							</c:when>
							
							<c:otherwise>
								<input type="submit" value="<fmt:message key="button.update"/>" class="btn btn-primary">
							</c:otherwise>
						</c:choose>
					</form>
				</div>
			</div>
		</div>
		
		<c:import url="../footer.jsp"/>
	</body>
</html>