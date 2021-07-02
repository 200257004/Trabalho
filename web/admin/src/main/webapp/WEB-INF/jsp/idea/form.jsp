<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="b" %>
<!DOCTYPE html>
<html>
	<head>
		<title><fmt:message key="title.newIdea"/></title>
		
		<c:import url="../head.jsp"/>
	</head>
	
	<body>
		<c:import url="../header.jsp"/>
		
		<div class="container">
			<c:choose>
				<c:when test="${idea.id == null}">
					<ol class="breadcrumb">
						<li><a href="${linkTo[DashboardController].dashboard}"><fmt:message key="title.dashboard"/></a></li>
						<li><a href="${linkTo[CategoryController].view(category.id)}">${category.description}</a></li>
						<li class="active"><fmt:message key="title.newIdea"/></li>
					</ol>
					
					<h1><fmt:message key="title.newIdea"/></h1>
				</c:when>
				
				<c:otherwise>
					<ol class="breadcrumb">
						<li><a href="${linkTo[DashboardController].dashboard}"><fmt:message key="title.dashboard"/></a></li>
						<li><a href="${linkTo[CategoryController].view(category.id)}">${category.description}</a></li>
						<li class="active"><fmt:message key="title.editIdea"/></li>
					</ol>
					
					<h1><fmt:message key="title.editIdea"/></h1>
				</c:otherwise>
			</c:choose>
			
			<div class="panel panel-default">
				<div class="panel-body">
					<form id="add-idea" action="${linkTo[IdeaController].add(category.id)}" method="post">
						<b:messages name="error"/>
						
						<input type="hidden" name="idea.id" value="${idea.id}">
						
						<div class="form-group">
							<label for="description"><fmt:message key="label.description"/></label>
							<input id="description" name="idea.description" value="${idea.description}" placeholder="<fmt:message key="watermark.idea.description"/>" class="form-control" autofocus>
							<b:messages name="idea.description"/>
						</div>
						
						<c:choose>
							<c:when test="${idea.id == null}">
								<input type="submit" value="<fmt:message key="button.add"/>" class="btn btn-primary">
							</c:when>
							
							<c:otherwise>
								<input type="submit" value="<fmt:message key="button.update"/>" class="btn btn-primary">
							</c:otherwise>
						</c:choose>
					</form>
				</div>
				
				<c:if test="${idea.id != null}">
					<div class="panel-footer">
						    <form id="form-remove-idea" action="${linkTo[IdeaController].remove(category.id, idea.id)}" method="post">
								<p class="alert alert-warning"><fmt:message key="info.remove.idea"/></p>
								
								<input type="hidden" name="_method" value="DELETE">
								
								<input type="submit" value="<fmt:message key="button.remove"/>" class="btn btn-warning">
							</form>
					</div>
				</c:if>
			</div>
		</div>
		
		<c:import url="../footer.jsp"/>
	</body>
</html>