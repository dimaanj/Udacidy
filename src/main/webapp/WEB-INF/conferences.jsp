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

<tag:navbar/>
<main role="main" class="container-fluid" id="main-tag">
    <c:if test="${not empty conferencesWithRequests}">
        <c:forEach items="${conferencesWithRequests}" var="entry">
            <div class="mx-auto w-75 rounded-lg shadow-lg p-5 mt-4 rounded" id="${entry.key.getId()}">
                <div id="bodyData${entry.key.getId()}">
                        ${entry.key.getContent().getContent()}
                </div>
                <c:choose>
                    <c:when test="${not empty entry.value.getId()}">
                        <span>
                            <a href="${pageContext.request.contextPath}/udacidy/profile">Go profile</a>
                            to check details of your request
                        </span>
                    </c:when>
                    <c:otherwise>
                        <button id="chooseSectionsButton${entry.key.getId()}"
                                class="btn btn-primary"
                                name="chooseSectionsButton"
                                type="button"
                                data-toggle="collapse"
                                data-target="#collapseSections${entry.key.getId()}"
                                aria-expanded="false"
                                aria-controls="collapseSections${entry.key.getId()}">
                            Choose sections
                        </button>
                    </c:otherwise>
                </c:choose>
                <div class="collapse mt-3"
                     id="collapseSections${entry.key.getId()}">
                    <div class="card">
                        <div class="card-body">
                            <c:if test="${not empty entry.key.getSections()}">
                                <ul class="tox-checklist" id="sectionsUl${entry.key.getId()}">
                                    <c:forEach items="${entry.key.getSections()}" var="section">
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
                                    id="submitRequestButton${entry.key.getId()}">
                                Submit request
                            </button>
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

