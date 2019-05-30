<%@ tag description="userInfoTag" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="card-body mx-auto rounded-lg shadow-lg mt-3" id="profileBody">
    <h5 class="card-title">
        <c:out value="${sessionScope.user.getFirstName()}"/> <c:out
            value="${sessionScope.user.getLastName()}"/>
    </h5>
    <h6>Email address: ${sessionScope.user.getEmail()}</h6>
    <h6 class="card-subtitle mb-2 text-muted">
        Status:
        <c:choose>
            <c:when test="${sessionScope.user.isAdmin()}">admin</c:when>
            <c:otherwise>client</c:otherwise>
        </c:choose>
    </h6>
    <button class="btn btn-primary" data-toggle="modal" data-target="#changePasswordModal">Change password
    </button>
</div>