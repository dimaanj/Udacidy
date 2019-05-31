<%@ tag description="Header" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

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
                <a class="navbar-brand" href="${pageContext.request.contextPath}/udacidy/contentediting">
                    <fmt:message key="nav.company"/>
                </a>
            </h6>
        </c:when>
        <c:when test="${not empty sessionScope.user and not sessionScope.user.isAdmin()}">
            <h6 class="mt-1">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/udacidy/conferences">
                    <fmt:message key="nav.company"/>
                </a>
            </h6>
        </c:when>
        <c:otherwise>
            <h6 class="mt-1">
                <a class="navbar-brand" href="${pageContext.request.contextPath}">
                    <fmt:message key="nav.company"/>
                </a>
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
                            <fmt:message key="nav.addConference"/>
                        </a>
                    </h6>
                </c:when>
                <c:when test="${not empty sessionScope.user and not sessionScope.user.isAdmin()}">
                    <h6 class="mt-1">
                        <a class="p-2 text-dark" href="${pageContext.request.contextPath}/udacidy/help">
                            <fmt:message key="nav.help"/>
                        </a>
                    </h6>
                </c:when>
            </c:choose>
            <div class="dropdown">
                <button class="btn btn-primary btn-sm dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <fmt:message key="nav.language"/>
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <c:choose>
                        <c:when test="${sessionScope.locale eq 'ru'}">
                            <button class="dropdown-item" type="submit" form="changeToEn"><fmt:message key="nav.eng"/>
                            </button>
                        </c:when>
                        <c:when test="${sessionScope.locale eq 'en'}">
                            <button class="dropdown-item" type="submit" form="changeToRu"><fmt:message key="nav.rus"/>
                            </button>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </ul>

        <c:choose>
            <c:when test="${not empty sessionScope.user and not sessionScope.user.isAdmin()}">
                <h6 class="mt-1">
                    <a class="p-2 text-dark mr-3" href="${pageContext.request.contextPath}/udacidy/profile">
                        <c:out value="${sessionScope.user.getFirstName()}"/>
                    </a>
                </h6>
            </c:when>
            <c:when test="${not empty sessionScope.user and sessionScope.user.isAdmin()}">
                <h6 class="mt-1">
                    <a class="p-2 text-dark mr-3" href="${pageContext.request.contextPath}/udacidy/admin">
                        <c:out value="${sessionScope.user.getFirstName()}"/>
                        <fmt:message key="nav.ifAdminLabel"/>
                    </a>
                </h6>
            </c:when>
            <c:otherwise>
                <a class="btn btn-outline-secondary mr-2"
                   href="${pageContext.request.contextPath}/udacidy/registration">
                    <fmt:message key="nav.signUp"/>
                </a>
                <a class="btn btn-outline-primary mr-2" href="${pageContext.request.contextPath}/udacidy/login">
                    <fmt:message key="nav.singIn"/>
                </a>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty sessionScope.user}">
            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#logoutModal">
                <fmt:message key="nav.logout"/>
            </button>
        </c:if>
    </div>
</nav>
<form method="post" id="changeToEn">
    <input type="hidden" name="locale" value="en"/>
    <input type="hidden" name="command" value="changeLocale"/>
</form>
<form method="post" id="changeToRu">
    <input type="hidden" name="locale" value="ru"/>
    <input type="hidden" name="command" value="changeLocale"/>
</form>

<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="lm.question"/></h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body"><fmt:message key="lm.body"/></div>
            <div class="modal-footer">
                <form method="post" id="logout">
                    <input type="hidden" name="command" value="logout"/>
                    <button class="btn btn-secondary" type="button" data-dismiss="modal"><fmt:message key="lm.cancel"/></button>
                    <button class="btn btn-primary" type="submit" form="logout"><fmt:message key="lm.confirm"/></button>
                </form>
            </div>
        </div>
    </div>
</div>
