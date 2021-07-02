<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<meta charset="utf-8">
		
		<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
		<link rel="stylesheet" href="<c:url value='/css/header.css'/>">
		<link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
		
		<link rel="icon" href="<c:url value='/img/favicon.ico'/>">
		
		<style>
			span {
				display: block;
			}
			
			img[class*="img-circle"] {
				height: 100px;
				width: 100px;
			}
			
			a[id^="new"] {
				display: block;
				max-width: 200px;
				margin: 0 auto;
			}
			
			a[class*="thumbnail"] {
				text-align: center;
				text-decoration: none;
			}
		</style>