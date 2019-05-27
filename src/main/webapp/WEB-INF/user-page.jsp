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

${pageContext.response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate")}
${pageContext.response.setHeader("Pragma", "no-cache")}
${pageContext.response.setHeader("Expires", "0")}

<tag:navbar/>
<main role="main" class="container-fluid">
    <div id="removeRequestMessagePlace"></div>
    <div class="row mt-4 justify-content-md-center">
        <div class="col-sm-7 shadow-lg rounded-lg">
            <div class="card-body" id="profileBody">
                <h5 class="card-title">
                    <c:out value="${sessionScope.user.getFirstName()}"/> <c:out
                        value="${sessionScope.user.getLastName()}"/>
                </h5>
                <h6>Email address: ${sessionScope.user.getEmail()}</h6>
                <h6 class="card-subtitle mb-2 text-muted">
                    Status: client
                </h6>
                <button class="btn btn-primary" data-toggle="modal" data-target="#changePasswordModal">Change password
                </button>
            </div>
        </div>
    </div>

    <c:if test="${not empty conferences}">
        <div class="row mt-4 justify-content-md-center">
            <div class="col-sm-7 shadow-lg rounded-lg">
                <div class="my-3 p-3">
                    <h6 class="border-bottom border-gray pb-2 mb-0">My Requests</h6>
                    <c:forEach items="${conferences}" var="conference">
                        <div class="media text-muted pt-3">
                            <div class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
                                <div class="d-flex justify-content-between align-items-center w-100">
                                    <strong class="text-gray-dark">Conference id <c:out
                                            value="${conference.getId()}"/></strong>
                                    <div class="d-flex flex-row-reverse bd-highlight">
                                        <div class="p-2 bd-highlight">
                                            <button name="removeRequestButton"
                                                    id="removeRequestButton${conference.getId()}"
                                                    type="button" class="mt-1 btn btn-dark btn-sm" data-toggle="modal"
                                                    data-target="#confirmationModal">Remove request
                                            </button>

                                        </div>
                                        <div class="p-2 bd-highlight">
                                            <button name="viewDetailsButton" id="viewDetailsButton${conference.getId()}"
                                                    type="button" class="btn btn-link" data-toggle="modal"
                                                    data-target="#viewDetails">View details
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <span class="d-block">author @ <c:out value="${conference.getAuthor().getFirstName()}"/>
                                    <c:out value="${conference.getAuthor().getLastName()}"/>,
                                </span>
                                <c:choose>
                                    <c:when test="${conference.getRequestStatus().toString() eq 'SHIPPED'}">
                                        <span class="card-title text-primary">Request status:
                                            <c:out value="${conference.getRequestStatus().toString().toLowerCase()}"/>
                                        </span>
                                    </c:when>
                                    <c:when test="${conference.getRequestStatus().toString() eq 'ACCEPTED'}">
                                        <span class="card-title text-success">Request status:
                                            <c:out value="${conference.getRequestStatus().toString().toLowerCase()}"/>
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="card-title text-secondary">Request status:
                                            <c:out value="${conference.getRequestStatus().toString().toLowerCase()}"/>
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>
</main>

<%--Change password modal--%>
<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Change password</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="change-password-modal-body">
                <div class="form-group">
                    <label for="previousPassword">Previous password</label>
                    <input type="password" name="password" class="form-control" id="previousPassword"
                           placeholder="previous">
                </div>
                <div class="form-group">
                    <label for="newPassword">New password</label>
                    <input type="password" name="password" class="form-control" id="newPassword" placeholder="new">
                </div>
                <div class="form-group">
                    <label for="confirmedPassword">Confirm new password</label>
                    <input type="password" name="password" class="form-control" id="confirmedPassword"
                           placeholder="confirm">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="changePasswordButton">Save changes</button>
            </div>
        </div>
    </div>
</div>


<%--Confirmation remove request modal--%>
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
                Are you really want to remove this request?
            </div>
            <div class="modal-footer">
                <form method="post" id="removeRequestForm" action="${pageContext.request.contextPath}/udacidy/profile">
                    <input type="hidden" name="command" value="removeRequest"/>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                    <button type="submit" class="btn btn-primary" id="confirmationButton">Yes</button>
                </form>
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
                <h5 class="modal-title" id="exampleModalLongTitle">Conference content</h5>
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


<script src="../js/userPage.js"></script>

<script>
    function clearUriParameters() {
        var uri = window.location.toString();
        if (uri.indexOf("?") > 0) {
            var clean_uri = uri.substring(0, uri.indexOf("?"));
            window.history.replaceState({}, document.title, clean_uri);
        }
    }

    <c:if test="${not empty requestWasSentMessage}">
    window.onload = function () {
        clearUriParameters();

        var modal = $("#viewDetails");
        modal.modal('show');

        let conferenceToShow = ${conferenceToShow};
        let requestSectionsIds = ${requestSectionsIds};
        let message = '${requestWasSentMessage}';

        let conference = createConference(conferenceToShow, requestSectionsIds);

        let modalDataContainer = document.getElementById('data-container');

        let alert = createAlertWithTextAndType(message, 'alert-success');
        alert.classList.add('mt-3');
        alert.classList.add('w-75');
        alert.classList.add('mx-auto');

        modalDataContainer.appendChild(alert);
        modalDataContainer.appendChild(conference);
    };
    </c:if>

    <c:if test="${not empty removeRequestMessage}">
    window.onload = function () {
        clearUriParameters();
        let removeRequestMessage = '${removeRequestMessage}';
        let alert = createAlertWithTextAndType(removeRequestMessage, 'alert-success');
        alert.classList.add('mt-3');
        alert.classList.add('w-75');
        alert.classList.add('mx-auto');
        document.getElementById('removeRequestMessagePlace').appendChild(alert);
    };
    </c:if>
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
