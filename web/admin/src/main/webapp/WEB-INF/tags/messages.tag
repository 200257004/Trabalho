<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="name" required="true"%>
				<c:if test="${not empty errors.from(name)}">
					<c:forEach var="message" items="${errors.from(name)}">
						<span class="alert alert-danger">${message}</span>
					</c:forEach>
				</c:if>