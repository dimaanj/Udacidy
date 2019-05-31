<%@ tag description="userInfoTag" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<div class="card-body mx-auto rounded-lg shadow-lg mt-3" id="profileBody">
    <h5 class="card-title">
        <c:out value="${sessionScope.user.getFirstName()}"/> <c:out
            value="${sessionScope.user.getLastName()}"/>
    </h5>
    <h6><fmt:message key="ut.email"/>: ${sessionScope.user.getEmail()}</h6>
    <h6 class="card-subtitle mb-2 text-muted">
        <fmt:message key="ut.status"/>:
        <c:choose>
            <c:when test="${sessionScope.user.isAdmin()}"><fmt:message key="ut.admin"/></c:when>
            <c:otherwise><fmt:message key="ut.client"/></c:otherwise>
        </c:choose>
    </h6>
    <button class="btn btn-primary" data-toggle="modal" data-target="#changePasswordModal"><fmt:message key="ut.changePasswordButton"/>
    </button>
</div>