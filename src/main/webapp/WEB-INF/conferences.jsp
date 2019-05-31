<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title><fmt:message key="c.title"/></title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../js/jquery.twbsPagination.min.js"></script>
    <script src="../js/common/changelocale.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/checkList.css">
    <link rel="stylesheet" type="text/css" href="../css/responsiveImage.css">
    <link rel="stylesheet" type="text/css" href="../css/pagination.css">
</head>
<body>

<tag:navbar/>
<main role="main" class="container-fluid" id="main-tag">
    <input type="hidden" id="conferencesNumber" value="${conferencesNumber}"/>
    <div id="page-content">
    </div>

    <div class="text-center">
        <ul id="pagination-demo" class="pagination mt-4">
        </ul>
    </div>
</main>
<tag:footer/>

<div id="localeMessagesToJs">
    <input type="hidden" id="profileLink" name="profileLink" value="<fmt:message key="c.profileLink"/>"/>
    <input type="hidden" id="profileSpanContinuation" name="profileSpanContinuation" value="<fmt:message key="c.profileSpanContinuation"/>"/>
    <input type="hidden" id="chooseSectionsButton" name="chooseSectionsButton" value="<fmt:message key="c.chooseSectionsButton"/>"/>
    <input type="hidden" id="submitRequestButton" name="submitRequestButton" value="<fmt:message key="c.submitRequestButton"/>"/>
    <input type="hidden" id="successRequestLabel" name="successRequestLabel" value="<fmt:message key="c.successReqeustLabel"/>"/>
    <input type="hidden" id="paginationNext" name="paginationNext" value="<fmt:message key="c.paginationNext"/>"/>
    <input type="hidden" id="paginationPrev" name="paginationPrev" value="<fmt:message key="c.paginationPrev"/>"/>
    <input type="hidden" id="paginationFirst" name="paginationFirst" value="<fmt:message key="c.paginationFirst"/>"/>
    <input type="hidden" id="paginationLast" name="paginationLast" value="<fmt:message key="c.paginationLast"/>"/>
    <input type="hidden" id="dangerAlertChooseSections" name="dangerAlertChooseSections" value="<fmt:message key="c.dangerAlertChooseSections"/>"/>
</div>

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

