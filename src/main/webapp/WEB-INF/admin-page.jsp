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

<main role="main" class="container-fluid">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">Messages</h4>
            <p class="card-text">2 new messages</p>
            <p>
                <button class="btn btn-primary" type="button" data-toggle="collapse"
                        data-target="#collapseExample"
                        aria-expanded="false" aria-controls="collapseExample">
                    View messages
                </button>
            </p>
            <div class="collapse" id="collapseExample">
                <c:if test="${not empty askingUsersWithTheirsConversations}">
                    <c:forEach items="${askingUsersWithTheirsConversations}" var="entry">
                        <div class="card mb-4">
                            <div class="card-header">
                                <h6><c:out value="${entry.key.getFirstName()}"/> <c:out
                                        value="${entry.key.getLastName()}"/></h6>
                            </div>
                            <form method="post" class="card-body">
                                <input type="hidden" name="command"
                                       value="${pageContext.request.contextPath}/udacidy/conversation"/>
                                <input type="hidden" name="conversationId" value="${entry.value.getId()}"/>
                                <button class="btn btn-secondary btn-sm" type="submit">
                                    Go messaging
                                </button>
                            </form>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>


    <c:if test="${not empty askingUsersWithTheirsConversations}">
        <div class="row mt-4 justify-content-md-center">
            <div class="col-sm-7 rounded-lg">
                <div class="my-3 p-3 bg-white rounded shadow">
                    <h5 class="border-bottom border-gray pb-2 mb-0">Questions</h5>
                    <c:forEach items="${askingUsersWithTheirsConversations}" var="entry">
                        <div class="media text-muted pt-3">
                            <div class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                                <div class="d-flex justify-content-between align-items-center w-100">
                                    <div class="d-flex flex-row bd-highlight mb-3">
                                        <div class="bd-highlight mr-3">
                                            <strong class="text-gray-dark">
                                                <c:out value="${entry.key.getFirstName()}"/>
                                                <c:out value="${entry.key.getLastName()}"/>
                                            </strong>
                                        </div>
                                        <div class="bd-highlight text-info">
                                            <h6>
                                                Last message: "<c:out
                                                    value="${entry.value.getLastMessage().getText()}"/>",
                                                by <c:out
                                                    value="${entry.value.getLastMessage().getCreator().getEmail()}"/>
                                            </h6>
                                        </div>
                                    </div>
                                    <div class="d-flex flex-row-reverse bd-highlight">
                                        <div class="bd-highlight">
                                            <button name="removeConversation" class="mt-1 btn btn-dark btn-sm"
                                                    id="${entry.value.getId()}"
                                                    type="button">
                                                Remove conversation
                                            </button>
                                        </div>
                                        <div class="p-2 bd-highlight">
                                            <a href="${pageContext.request.contextPath}/udacidy/conversation?conversationId=${entry.value.getId()}">
                                                Answer
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div id="data-container">
            </div>
        </div>
    </c:if>
</main>

<tag:footer/>
<script src="../js/adminPage.js"></script>

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
