<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>404</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>Checkout example · Bootstrap</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="../css/cover.css">
</head>

<body class="text-center">
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
    <main role="main" class="inner cover">
        <h1 class="cover-heading">Sorry.</h1>
        <p class="lead">The page you requested could not be found, either contact your webmaster or try again. Use your
            browsers Back button to navigate to the page you have previously come from
            Or you could just press this neat little button.
        </p>
        <p class="lead">
            <c:choose>
                <c:when test="${not empty sessionScope.user and sessionScope.user.isAdmin()}">
                    <a href="${pageContext.request.contextPath}/udacidy/contentediting" class="btn btn-lg btn-primary">Go main page</a>
                </c:when>
                <c:when test="${not empty sessionScope.user and not sessionScope.user.isAdmin()}">
                    <a href="${pageContext.request.contextPath}/udacidy/conferences" class="btn btn-lg btn-primary">Go main page</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/udacidy/" class="btn btn-lg btn-primary">Go main page</a>
                </c:otherwise>
            </c:choose>
        </p>
    </main>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>