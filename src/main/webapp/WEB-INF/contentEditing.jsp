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
    <title><fmt:message key="ce.title"/></title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../js/jquery.twbsPagination.min.js"></script>
    <script src="https://cloud.tinymce.com/5/tinymce.min.js?apiKey=n6pc28z5n87xrwjz2invt1y20ws32djsc2jyd67as953ymf6"></script>
    <script src="../js/common/changelocale.js"></script>

    <link rel="stylesheet" type="text/css" href="../css/tinymceEditorStyles.css">
    <link rel="stylesheet" type="text/css" href="../css/responsiveImage.css">
    <link rel="stylesheet" type="text/css" href="../css/pagination.css">
</head>
<body>
<tag:navbar/>
<main role="main" class="container-fluid" id="main-tag">
    <input type="hidden" id="conferencesNumber" value="${conferencesNumber}"/>
    <div id="data-container"></div>

    <div class="text-center">
        <ul id="pagination-demo" class="pagination mt-4">
        </ul>
    </div>
</main>

<div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="ce.modalHeader"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <fmt:message key="ce.modalBody"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="ce.modalNo"/></button>
                <button type="button" class="btn btn-primary" id="confirmationButton"><fmt:message key="ce.modalYes"/></button>
            </div>
        </div>
    </div>
</div>
<tag:footer/>

<div id="localMessageToJs">
    <input type="hidden" id="deleteConferenceButton" name="deleteConferenceButton" value="<fmt:message key="ce.deleteConferenceButton"/>"/>
    <input type="hidden" id="editConferenceButton" name="editConferenceButton" value="<fmt:message key="ce.editConferenceButton"/>"/>
    <input type="hidden" id="paginationNext" name="paginationNext" value="<fmt:message key="ce.paginationNext"/>"/>
    <input type="hidden" id="paginationPrev" name="paginationPrev" value="<fmt:message key="ce.paginationPrev"/>"/>
    <input type="hidden" id="paginationFirst" name="paginationFirst" value="<fmt:message key="ce.paginationFirst"/>"/>
    <input type="hidden" id="paginationLast" name="paginationLast" value="<fmt:message key="ce.paginationLast"/>"/>
    <input type="hidden" id="submitChangesButton" name="submitChangesButton" value="<fmt:message key="ce.sumbitChangesButton"/>"/>

    <input type="hidden" id="updateContentSuccess" name="updateContentSuccess" value="<fmt:message key="ce.updateContentSuccess"/>"/>
    <input type="hidden" id="updateContentError" name="updateContentError" value="<fmt:message key="ce.updateContentError"/>"/>
    <input type="hidden" id="removeConferenceSuccess" name="removeConferenceSuccess" value="<fmt:message key="ce.removeConferenceSuccess"/>"/>
    <input type="hidden" id="removeConferenceError" name="removeConferenceError" value="<fmt:message key="ce.removeConferenceError"/>"/>
</div>

<script src="../js/contentEditor.js"></script>
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

