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
    <div id="container">

    </div>
<%--    <c:if test="${not empty conferences}">--%>
<%--        --%>
<%--    </c:if>--%>
<%--    <div class="row mt-4 justify-content-md-center">--%>

<%--        <div class="py-2">--%>
<%--            <h2>Requests</h2>--%>
<%--        </div>--%>
<%--        --%>
<%--        <c:if test="${not empty conferences}">--%>
<%--            <c:forEach items="${conferences}" var="conference">--%>
<%--                <div class="col-sm-7 shadow-lg rounded-lg">--%>
<%--                    <div name="container">--%>
<%--                        <c:out value="${conference.getContent().getContent()}"/>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </c:forEach>--%>
<%--        </c:if>--%>
<%--    </div>--%>

</main>
<tag:footer/>

<script>
    window.onload = function () {
        <c:if test="${not empty conferences}">
            <c:forEach items="${conferences}" var="conference">
                var conference = ${conference};
                console.log(conference);
                var row = createConference(conference);

            </c:forEach>
        </c:if>
    };
    function createConference(jsonConference) {
        var conferenceId = jsonConference.id;
        var conferenceContent = jsonConference.content;
        var conferenceSections = jsonConference.jsonSections;

        var row = document.createElement('div');
        row.classList.add('row');
        row.classList.add('mt-4');
        row.classList.add('justify-content-md-center');
        row.id = conferenceId;

        var col = document.createElement('div');
        col.classList.add('col-sm-7');
        col.classList.add('shadow-lg');
        col.classList.add('rounded-lg');
        col.classList.add('p-5');

        var bodyData = document.createElement('div');
        bodyData.setAttribute('id', 'bodyData');
        bodyData.innerHTML = conferenceContent;
        var images = bodyData.getElementsByTagName('img');
        for(var k=0; k<images.length; k++){
            images[k].setAttribute('id', 'responsive-image');
        }

        col.appendChild(bodyData);

        var flexR = document.createElement('div');
        flexR.classList.add('d-flex');
        flexR.classList.add('flex-row');
        flexR.classList.add('bd-highlight');
        flexR.classList.add('mb-3');

        var flexItem = document.createElement('div');
        flexItem.classList.add('p-2');
        flexItem.classList.add('bd-highlight');

        var removeRequestButton = removeRequestButtonBuilder();
        flexItem.appendChild(removeRequestButton);

        flexR.appendChild(flexItem);
        col.appendChild(flexR);

        var collapse = chooseSectionsCollapseElementBuilder(conferenceSections);
        col.appendChild(collapse);

        row.appendChild(col);
        return row;
    }

    var removeRequestButtonBuilder = function () {
        var removeRequestButton = document.createElement('button');
        removeRequestButton.classList.add('btn');
        removeRequestButton.classList.add('btn-dark');
        removeRequestButton.setAttribute('name', 'removeRequest');
        removeRequestButton.appendChild(document.createTextNode('Remove request'));
        removeRequestButton.setAttribute('data-toggle', 'modal');
        removeRequestButton.setAttribute('data-target', '#confirmationModal');
        return removeRequestButton;
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
