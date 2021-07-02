<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
		<header class="container">
			<c:choose>
				<c:when test="${session.user == null}">
					<a href="${linkTo[IndexController].login}">
						<img id="logo" alt="Tizito" src="<c:url value='/img/logo.png'/>">
					</a>
					
					<nav class="menu">
						<ul class="nav nav-pills">
							<li><a id="about" href="${linkTo[IndexController].about}"><fmt:message key="title.about"/></a></li>
							<li><a id="login" href="${linkTo[IndexController].login}"><fmt:message key="title.login"/></a></li>
						</ul>
					</nav>
				</c:when>
				
				<c:otherwise>
					<a href="${linkTo[DashboardController].dashboard}">
						<img id="logo" alt="Tizito" src="<c:url value='/img/logo.png'/>">
					</a>
					
					<p class="user">${session.user.name}</p>
					
					<nav class="menu">
						<ul class="nav nav-pills">
							<li><a id="dashboard" href="${linkTo[DashboardController].dashboard}"><fmt:message key="title.dashboard"/></a></li>
							<li><a id="signout" href="${linkTo[SessionController].signout}"><fmt:message key="title.signout"/></a></li>
						</ul>
					</nav>
				</c:otherwise>
			</c:choose>
		</header>