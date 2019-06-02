<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title><fmt:message key="r.pageTitle"/></title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="../js/common/changelocale.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.min.js"></script>
</head>
<body>

<tag:navbar/>
<main role="main" class="container">
    <div class="card mt-3 shadow">
        <div class="card-header">
            <h6 class="card-text"><fmt:message key="r.cardHeader"/></h6>
        </div>
        <div class="card-body">
            <form id="signupForm" method="post" class="form-horizontal">
                <input type="hidden" name="command"
                       value="${pageContext.request.contextPath}/udacidy/registration"/>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="firstname"><fmt:message key="r.firstName"/></label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="firstname" name="firstname"
                               placeholder="<fmt:message key="r.firstName"/>"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="lastname"><fmt:message key="r.lastName"/></label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="lastname" name="lastname"
                               placeholder="<fmt:message key="r.lastName"/>"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="email"><fmt:message key="r.email"/></label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="email" name="email"
                               placeholder="<fmt:message key="r.email"/>"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="password"><fmt:message key="r.password"/></label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="<fmt:message key="r.password"/>"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="confirmedPassword"><fmt:message key="r.confirmPassword"/></label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="confirmedPassword"
                               name="confirmedPassword" placeholder="<fmt:message key="r.confirmPassword"/>"/>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-9 offset-sm-4">
                        <button type="submit" class="btn btn-primary" name="signup" value="Sign up">
                            <fmt:message key="r.signUpButton"/>
                        </button>
                    </div>
                </div>
            </form>
            <c:if test="${not empty errorRegistrationPassMessage}">
                <div class="alert alert-danger text-center" role="alert">
                    <strong>${errorRegistrationPassMessage}</strong> <fmt:message key="r.alert"/>
                </div>
            </c:if>
        </div>
        <div class="card-footer">
            <div class="text-center">
                <h6 class="card-text ml-4">
                    <a href="${pageContext.request.contextPath}/udacidy/login">
                        <fmt:message key="r.loginPage"/>
                    </a>
                </h6>
            </div>
        </div>
    </div>
</main>
<tag:footer/>

<div id="localeMessagesToJs">
    <input type="hidden" id="firstNameRequired" name="firstNameRequired" value="<fmt:message key="r.firstNameRequired"/>"/>
    <input type="hidden" id="lastNameRequired" name="lastNameRequired" value="<fmt:message key="r.lastNameRequeired"/>"/>
    <input type="hidden" id="passwordRequired" name="passwordRequired" value="<fmt:message key="r.passwordRequeired"/>"/>
    <input type="hidden" id="confirmPasswordRequired" name="confirmPasswordRequired" value="<fmt:message key="r.confirmPasswordRequired"/>"/>
    <input type="hidden" id="confirmPasswordEquals" name="confirmPasswordEquals" value="<fmt:message key="r.confirmPasswordEquals"/>"/>
    <input type="hidden" id="emailRequired" name="emailRequired" value="<fmt:message key="r.emailRequired"/>"/>
    <input type="hidden" id="agreePolicy" name="agreePolicy" value="<fmt:message key="r.agreeRequired"/>"/>
    <input type="hidden" id="passwordPattern" name="passwordPattern" value="<fmt:message key="r.passwordPattern"/>">
</div>

<script src="../js/registrationValidation.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>