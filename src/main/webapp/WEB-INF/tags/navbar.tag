<%@ tag description="Header" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    /* Show it is fixed to the top */
    body {
        /*min-height: 75rem;*/
        padding-top: 4.5rem;
    }
</style>

<nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light shadow">
    <c:choose>
        <c:when test="${not empty sessionScope.user and sessionScope.user.isAdmin()}">
            <h6 class="mt-1">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/udacidy/contentediting">Udacidy</a>
            </h6>
        </c:when>
        <c:when test="${not empty sessionScope.user and not sessionScope.user.isAdmin()}">
            <h6 class="mt-1">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/udacidy/conferences">Udacidy</a>
            </h6>
        </c:when>
        <c:otherwise>
            <h6 class="mt-1">
                <a class="navbar-brand" href="${pageContext.request.contextPath}">Udacidy</a>
            </h6>
        </c:otherwise>
    </c:choose>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02"
            aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <c:choose>
                <c:when test="${not empty sessionScope.user and sessionScope.user.isAdmin()}">
                    <h6 class="mt-1">
                        <a class="p-2 text-dark" href="${pageContext.request.contextPath}/udacidy/addconference">
                            Add conference
                        </a>
                    </h6>
                </c:when>
                <c:when test="${not empty sessionScope.user and not sessionScope.user.isAdmin()}">
                    <h6 class="mt-1">
<%--                        <a class="p-2 text-dark" href="${pageContext.request.contextPath}/udacidy/help">--%>
<%--                            Help--%>
<%--                        </a>--%>
                        <button class="btn btn-link" form="helpUserCommand">
                            Help
                        </button>
                    </h6>
                </c:when>
            </c:choose>
        </ul>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <c:choose>
                    <c:when test="${not empty sessionScope.user and not sessionScope.user.isAdmin()}">
                        <h6 class="mt-1">
                            <a class="p-2 text-dark mr-3"
                               href="${pageContext.request.contextPath}/udacidy/profile"><c:out
                                    value="${sessionScope.user.getFirstName()}"/></a>
                        </h6>
                    </c:when>
                    <c:when test="${not empty sessionScope.user and sessionScope.user.isAdmin()}">
                        <h6 class="mt-1">
                            <a class="p-2 text-dark mr-3" href="${pageContext.request.contextPath}/udacidy/admin">
                                <c:out value="${sessionScope.user.getFirstName()}"/> (Admin)
                            </a>
                        </h6>
                    </c:when>
                </c:choose>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#logoutModal">Logout
                </button>
            </c:when>
            <c:otherwise>
                <a class="btn btn-outline-secondary mr-2"
                   href="${pageContext.request.contextPath}/udacidy/registration">Sign up</a>
                <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/udacidy/login">Sign in</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>

<form method="post" id="helpUserCommand">
    <input type="hidden" name="command" value="helpUser"/>
</form>

<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">Select "Confirm" below if you are ready to end your current session.</div>
            <div class="modal-footer">
                <form method="post" id="logout">
                    <input type="hidden" name="command" value="logout"/>
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <button class="btn btn-primary" type="submit" form="logout">Confirm</button>
                </form>
            </div>
        </div>
    </div>
</div>
