<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmr" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title><fmt:message key="up.title"/></title>
    <script src="../js/common/changelocale.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="../css/responsiveImage.css">
</head>
<body>

<tag:navbar/>
<main role="main" class="container">
    <tag:userInfoTag/>

    <c:if test="${not empty requestsWithConferences}">
        <div class="my-4 p-3 bg-white rounded-lg shadow-lg mx-auto">
            <h5 class="border-bottom border-gray pb-2 mb-0"><fmr:message key="up.myRequests"/></h5>
            <c:forEach items="${requestsWithConferences}" var="entry">
                <div class="pt-3" id="requestItem${entry.value.getId()}">
                    <div class="border-bottom border-gray">
                        <div class="d-flex bd-highlight mb-3">
                            <div class="mr-auto p-2 bd-highlight">
                                <strong class="text-gray-dark"><fmr:message key="up.conferenceId"/> <c:out
                                        value="${entry.value.getId()}"/></strong>
                                <span class="d-block text-muted"><fmr:message key="up.author"/> @ <c:out
                                        value="${entry.value.getAuthor().getFirstName()}"/>
                                    <c:out value="${entry.value.getAuthor().getLastName()}"/>,
                                </span>
                                <c:choose>
                                    <c:when test="${entry.key.getRequestStatus().toString() eq 'SHIPPED'}">
                                        <span class="card-title text-primary"><fmr:message key="up.requestStatus"/>:
                                            <fmr:message key="up.requestStatusShipped"/>
                                        </span>
                                    </c:when>
                                    <c:when test="${entry.key.getRequestStatus().toString() eq 'ACCEPTED'}">
                                        <span class="card-title text-success"><fmr:message key="up.requestStatus"/>:
                                            <fmr:message key="up.requestStatusAccepted"/>
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="card-title text-dark"><fmr:message key="up.requestStatus"/>:
                                            <fmr:message key="up.requestStatusRejected"/>
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="p-2 bd-highlight">
                                <button name="viewDetailsButton"
                                        requestId="${entry.key.getId()}"
                                        id="viewDetailsButton${entry.value.getId()}"
                                        type="button" class="btn btn-outline-primary" data-toggle="modal"
                                        data-target="#viewDetails">
                                    <fmr:message key="up.viewDetails"/>
                                </button>
                            </div>
                            <div class="p-2 bd-highlight">
                                <button name="removeRequestButton"
                                        id="removeRequestButton${entry.value.getId()}"
                                        type="button" class="btn btn-outline-dark" data-toggle="modal"
                                        data-target="#confirmationModal">
                                    <c:choose>
                                        <c:when test="${entry.key.getRequestStatus().toString() eq 'SHIPPED'}">
                                            <fmr:message key="up.removeRequest"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmr:message key="up.removeThisNotification"/>
                                        </c:otherwise>
                                    </c:choose>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</main>

<tag:changePasswordModalTag/>

<%--Confirmation remove request modal--%>
<div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmationRemoveRequestLabel"><fmr:message key="up.confirmHeader"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <fmr:message key="up.confirmBody"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmr:message key="up.confirmNo"/></button>
                <button type="submit" class="btn btn-primary" id="confirmationButton"><fmr:message key="up.confirmYes"/></button>
            </div>
        </div>
    </div>
</div>

<%--View details modal--%>
<div class="modal fade" id="viewDetails" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle"><fmr:message key="up.viewDetailsHeader"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="data-container">
            </div>
        </div>
    </div>
</div>

<tag:footer/>

<div id="localMessagesToJs">
    <input type="hidden" id="itemWasSuccessfullyRemoved" name="itemWasSuccessfullyRemoved" value="<fmt:message key="up.itemWasSuccessfullyRemoved"/>">
    <input type="hidden" id="sorry" name="sorry" value="<fmt:message key="up.sorry"/>">
</div>

<script src="../js/userPage.js"></script>
<script src="../js/common/changePassword.js"></script>
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
