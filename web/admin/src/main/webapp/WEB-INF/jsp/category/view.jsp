<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="b" %>
<!DOCTYPE html>
<html>
	<head>
		<title><fmt:message key="title.category"/></title>
		
		<c:import url="../head.jsp"/>
	</head>
	
	<body>
		<c:import url="../header.jsp"/>
		
		<div class="container">
			<ol class="breadcrumb">
				<li><a href="${linkTo[DashboardController].dashboard}"><fmt:message key="title.dashboard"/></a></li>
				<li class="active">${category.description}</li>
			</ol>
			
			<h1>${category.description}</h1>
			
			<section class="panel panel-default">
				<div class="panel-heading">
					<h2 class="panel-title"><fmt:message key="title.poster"/></h2>
				</div>
				
				<div class="panel-body">
					<c:choose>
						<c:when test="${category.poster == null}">
							<img id="poster" alt="${category.description}" src="<c:url value='/img/category.png'/>" class="img-responsive center-block">
						</c:when>
						
						<c:otherwise>
							<img id="poster" alt="${category.description}" src="${linkTo[CategoryController].poster(category.id)}" class="img-responsive center-block">
						</c:otherwise>
					</c:choose>
				</div>
			</section>
			
			<section class="panel panel-default">
				<div class="panel-heading">
					<h2 class="panel-title"><fmt:message key="title.details"/></h2>
				</div>
				
				<div class="panel-body">
					<h3><fmt:message key="title.description"/></h3>
					<p id="description">${category.description}</p>
				</div>
				
				<div class="panel-footer">
					<a id="edit" href="${linkTo[CategoryController].edit(category.id)}" class="btn btn-primary"><fmt:message key="button.edit"/></a>
				</div>
			</section>
			
			<section class="panel panel-default">
				<div class="panel-heading">
					<h2 class="panel-title"><fmt:message key="title.ideas"/></h2>
				</div>
				
				<div class="panel-body">
					<a id="newIdea" href="${linkTo[IdeaController].form(category.id)}" class="btn btn-primary"><fmt:message key="title.newIdea"/></a>
				</div>
				
				<div class="panel-footer">
					<c:choose>
						<c:when test="${empty category.ideas}">
							<p class="alert alert-info"><fmt:message key="info.idea"/></p>
						</c:when>
						
						<c:otherwise>
							<div id="ideas" class="row">
								<c:forEach var="idea" items="${category.ideas}">
									<div class="col-sm-6 col-md-4">
										<a href="${linkTo[IdeaController].view(category.id, idea.id)}" class="thumbnail">
											<figure>
												<c:choose>
													<c:when test="${idea.poster == null}">
														<img alt="${idea.description}" src="<c:url value='/img/idea.png'/>" class="img-responsive center-block">
													</c:when>
			
													<c:otherwise>
														<img alt="${idea.description}" src="${linkTo[IdeaController].poster(category.id, idea.id)}" class="img-responsive center-block">
													</c:otherwise>
												</c:choose>
												
												<figcaption class="caption">${idea.description}</figcaption>
											</figure>
										</a>
									</div>
								</c:forEach>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</section>

		    <form id="form-remove-category" action="${linkTo[CategoryController].remove(category.id)}" method="post">
				<p class="alert alert-warning"><fmt:message key="info.remove.category"/></p>
				
				<input type="hidden" name="_method" value="DELETE">
				
				<input type="submit" value="<fmt:message key="button.remove"/>" class="btn btn-warning">
			</form>
		</div>
		
		<c:import url="../footer.jsp"/>
	</body>
</html>