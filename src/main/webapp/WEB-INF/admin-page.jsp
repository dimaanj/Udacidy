<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Carousel Template · Bootstrap</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>Checkout example · Bootstrap</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<tag:navbar/>

<main role="main" class="container">
    <tag:userInfoTag/>

    <c:if test="${not empty usersWithRequests}">
        <div class="my-3 p-3 bg-white rounded-lg shadow mx-auto">
            <h5 class="border-bottom border-gray pb-2 mb-0">Queries</h5>
            <c:forEach items="${usersWithRequests}" var="entry">
                <div class="pt-3" id="userRequestItem${entry.value.getConference().getId()}">
                    <div class="border-bottom border-gray">
                        <div class="d-flex bd-highlight mb-3">
                            <div class="mr-auto p-2 bd-highlight">
                                <strong class="text-dark">
                                    <c:out value="${entry.key.getFirstName()}"/>
                                    <c:out value="${entry.key.getLastName()}"/>
                                    <h6 class="text-muted">
                                        Conference id: <c:out value="${entry.value.getConference().getId()}"/>
                                    </h6>
                                </strong>
                            </div>
                            <div class="p-2 bd-highlight">
                                <button name="viewBody"
                                        class="btn btn-outline-info"
                                        id="viewBodyRequest${entry.value.getConference().getId()}"
                                        conferenceId="${entry.value.getConference().getId()}"
                                        userId="${entry.key.getId()}"
                                        type="button"
                                        data-toggle="modal"
                                        data-target="#confirmationActionModal">
                                    View body
                                </button>
                            </div>
                            <div class="p-2 bd-highlight">
                                <button name="acceptRequest"
                                        class="btn btn-outline-success"
                                        id="acceptRequest${entry.value.getConference().getId()}"
                                        conferenceId="${entry.value.getConference().getId()}"
                                        userId="${entry.key.getId()}"
                                        type="button"
                                        data-toggle="modal"
                                        data-target="#confirmationActionModal">
                                    Accept
                                </button>
                            </div>
                            <div class="p-2 bd-highlight">
                                <button name="rejectRequest"
                                        class="btn btn-outline-dark"
                                        id="rejectRequest${entry.value.getConference().getId()}"
                                        conferenceId="${entry.value.getConference().getId()}"
                                        userId="${entry.key.getId()}"
                                        type="button"
                                        data-toggle="modal"
                                        data-target="#confirmationActionModal">
                                    Reject
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>

    <c:if test="${not empty askingUsersWithTheirsConversations}">
        <div class="my-3 p-3 bg-white rounded-lg shadow mx-auto">
            <h5 class="border-bottom border-gray pb-2 mb-0">Questions</h5>
            <c:forEach items="${askingUsersWithTheirsConversations}" var="entry">
                <div class="pt-3" id="userQuestionItem${entry.value.getId()}">
                    <div class="border-bottom border-gray">
                        <div class="d-flex bd-highlight mb-3">
                            <div class="mr-auto p-2 bd-highlight">
                                <strong class="text-dark">
                                    <c:out value="${entry.key.getFirstName()}"/>
                                    <c:out value="${entry.key.getLastName()}"/>
                                    <h6 class="text-muted">
                                        Last message: "<c:out
                                            value="${entry.value.getLastMessage().getText()}"/>",
                                        by <c:out
                                            value="${entry.value.getLastMessage().getCreator().getEmail()}"/>
                                    </h6>
                                </strong>
                            </div>
                            <div class="p-2 bd-highlight">
                                <a class="btn btn-outline-success"
                                   href="${pageContext.request.contextPath}/udacidy/conversation?conversationId=${entry.value.getId()}">
                                    Answer
                                </a>
                            </div>
                            <div class="p-2 bd-highlight">
                                <button name="removeConversation"
                                        class="btn btn-outline-dark"
                                        id="removeConversation${entry.value.getId()}"
                                        type="button"
                                        data-toggle="modal"
                                        data-target="#confirmationModal">
                                    Remove conversation
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

<%--Confirmation remove converstaion modal--%>
<div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmationRemoveRequestLabel">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you really want to remove this conversation?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-primary" id="confirmationRemoveConferenceButton">Yes</button>
                <input type="hidden" name="conversationIdHiddenParam" id="conversationIdHiddenParam"/>
            </div>
        </div>
    </div>
</div>

<%--Confirmation action modal--%>
<div class="modal fade" id="confirmationActionModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmationActionLabel">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you really want to do this action?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-primary" id="confirmationActionButton">Yes</button>
            </div>
        </div>
    </div>
</div>

<tag:footer/>
<script src="../js/adminPage.js"></script>
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
