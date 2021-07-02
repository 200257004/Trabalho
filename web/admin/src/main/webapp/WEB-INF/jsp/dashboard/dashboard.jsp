<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<title><fmt:message key="title.dashboard"/></title>
		
		<c:import url="../head.jsp"/>
	</head>
	
	<body>
		<c:import url="../header.jsp"/>
		
		<div class="container">
			<ol class="breadcrumb">
				<li class="active"><fmt:message key="title.dashboard"/></li>
			</ol>
			
			<h1><fmt:message key="title.dashboard"/></h1>
			
			<section class="panel panel-default">
				<div class="panel-heading">
					<h2 class="panel-title"><fmt:message key="title.categories"/></h2>
				</div>
				
				<div class="panel-body">
					<a id="newCategory" href="${linkTo[CategoryController].form}" class="btn btn-primary"><fmt:message key="title.newCategory"/></a>
				</div>
			
				<div class="panel-footer">
					<c:choose>
						<c:when test="${empty categories}">
							<p class="alert alert-info"><fmt:message key="info.category"/></p>
						</c:when>
						
						<c:otherwise>
							<div id="categories" class="row">
								<c:forEach var="category" items="${categories}">
									<div class="col-sm-6 col-md-4">
										<a href="${linkTo[CategoryController].view(category.id)}" class="thumbnail">
											<figure>
												<c:choose>
													<c:when test="${category.poster == null}">
														<img alt="${category.description}" src="<c:url value='/img/category.png'/>" class="img-responsive center-block">
													</c:when>
			
													<c:otherwise>
														<img alt="${category.description}" src="${linkTo[CategoryController].poster(category.id)}" class="img-responsive center-block">
													</c:otherwise>
												</c:choose>
												
												<figcaption class="caption">${category.description}</figcaption>
											</figure>
										</a>
									</div>
								</c:forEach>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</section>
		</div>
		
		<c:import url="../footer.jsp"/>
	</body>
</html>