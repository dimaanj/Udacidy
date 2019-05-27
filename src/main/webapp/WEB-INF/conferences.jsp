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
    <link rel="stylesheet" type="text/css" href="../css/checkList.css">
    <link rel="stylesheet" type="text/css" href="../css/responsiveImage.css">
</head>
<body>

${pageContext.response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate")}
${pageContext.response.setHeader("Pragma", "no-cache")}
${pageContext.response.setHeader("Expires", "0")}

<tag:navbar/>
<main role="main" class="container-fluid" id="main-tag">
    <c:if test="${not empty conferences}">
        <c:forEach items="${conferences}" var="conference">
            <div class="row mt-4 justify-content-md-center" id="${conference.getId()}">
                <div class="col-sm-7 shadow-lg rounded-lg p-5">
                    <div id="bodyData${conference.getId()}">
                            ${conference.getContent().getContent()}
                    </div>
                    <div class="d-flex flex-row bd-highlight mt-3" id="flex-row${conference.getId()}">
                        <div class="p-2 bd-highlight">
                            <c:choose>
                                <c:when test="${not empty conference.getRequestStatus()}">
                            <span><a href="${pageContext.request.contextPath}/udacidy/profile">Go profile</a>
                                to check details of your request</span>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-primary"
                                            name="chooseSectionsButton"
                                            type="button"
                                            data-toggle="collapse"
                                            data-target="#collapseSections${conference.getId()}"
                                            aria-expanded="false"
                                            aria-controls="collapseSections${conference.getId()}">
                                        Choose sections
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="collapse"
                         id="collapseSections${conference.getId()}">
                        <div class="card">
                            <div class="card-body">
                                <c:if test="${not empty conference.getSections()}">
                                    <ul class="tox-checklist" id="sectionsUl${conference.getId()}">
                                        <c:forEach items="${conference.getSections()}" var="section">
                                            <li name="section" id="${section.getId()}">
                                                    ${section.getContent().getContent()}
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </div>
                            <div class="card-footer border-white">
                                <button class="btn btn-primary"
                                        name="submitRequestButton"
                                        type="button"
                                        form="sendRequestForm"
                                        id="submitRequestButton${conference.getId()}">
                                    Submit request
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${not hideViewMoreButton}">
        <button class="btn btn-primary btn-lg btn-block w-50 mx-auto shadow-lg mt-3" type="button"
                id="view-more-button">View more
        </button>
    </c:if>
</main>
<form method="post" id="sendRequestForm" action="${pageContext.request.contextPath}/udacidy/profile">
    <input type="hidden" name="command" value="sendClientRequest"/>

</form>

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
<script src="../js/conferences.js"></script>

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
