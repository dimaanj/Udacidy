<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    <link rel="stylesheet" type="text/css" href="../css/halfResponsiveImage.css">
    <link rel="stylesheet" type="text/css" href="../css/messages.css">
</head>
<body>

<tag:navbar/>
<c:if test="${not empty firstUserEnter}">
    <h4 class="display-4 font-weight-normal text-center" id="firstUserEnter">Here you can ask our admins...</h4>
</c:if>
<main role="main" class="container-fluid" id="main-tag">
    <input type="hidden" id="userId" name="userId" value="${sessionScope.user.getId()}">
    <input type="hidden" id="conversationId" name="conversationId" value="${conversationId}">

    <c:choose>
        <c:when test="${not empty showViewMoreButton}">
            <button class="btn btn-primary btn-lg btn-block w-50 mx-auto shadow" type="submit" id="view-more-button">
                View more
            </button>
        </c:when>
        <c:otherwise>
            <button class="btn btn-primary btn-lg btn-block w-50 mx-auto shadow" type="submit" id="view-more-button" hidden>
                View more
            </button>
        </c:otherwise>
    </c:choose>
</main>

<footer class="footer" id="footer">
    <div class="container">
        <form class="input-group w-auto p-3" id="send-message-form" enctype="multipart/form-data">
            <div class="input-group">
                <input id="message" name="message" type="text" class="form-control" placeholder="Input message"
                       aria-label="Recipient's username" aria-describedby="button-addon2">
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="inputGroupFile04"
                           aria-describedby="inputGroupFileAddon04" accept="image/x-png">
                    <label class="custom-file-label" for="inputGroupFile04">Choose image</label>
                </div>
                <div class="input-group-append">
                    <button class="btn btn-primary" type="submit" id="button-addon2">
                        Send
                    </button>
                </div>
            </div>
        </form>
    </div>
</footer>

<script src="../js/messages.js"></script>

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
