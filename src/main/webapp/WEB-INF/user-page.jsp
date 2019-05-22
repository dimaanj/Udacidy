<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>Checkout example · Bootstrap</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="../css/responsiveImage.css">
</head>
<body>

<tag:navbar/>
<main role="main" class="container-fluid">
    <div class="row mt-4 justify-content-md-center">
        <div class="col-sm-7 shadow-lg rounded-lg">
            <div class="card-body">
                <h5 class="card-title">
                    <c:out value="${sessionScope.user.getFirstName()}"/> <c:out
                        value="${sessionScope.user.getLastName()}"/>
                </h5>
                <h6 class="card-subtitle mb-2 text-muted">
                    <c:choose>
                        <c:when test="${sessionScope.user.isAdmin()}">
                            Status: admin
                        </c:when>
                        <c:otherwise>
                            Status: client
                        </c:otherwise>
                    </c:choose>
                </h6>
                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the
                    card's content.</p>
                <a href="#" class="card-link">Card link</a>
                <a href="#" class="card-link">Another link</a>
            </div>
        </div>
    </div>
    <c:if test="${not empty conferences}">
        <div class="row mt-4 justify-content-md-center">
            <div class="py-2">
                <h2>Requests</h2>
            </div>
        </div>
    </c:if>
    <div id="container">
    </div>
</main>
<div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you really want to remove this request?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-primary" id="confirmationButton">Yes</button>
            </div>
        </div>
    </div>
</div>
<tag:footer/>

<script src="../js/userPage.js"></script>
<script>
    window.onload = function () {
        <c:if test="${not empty conferences}">
        <c:forEach var="i" begin="0" end="${conferences.size()-1}">
        var conference = ${conferences.get(i)};
        console.log(conference);
        var row = createConference(conference);
        document.getElementById('container').appendChild(row);
        </c:forEach>
        </c:if>
    };
</script>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
