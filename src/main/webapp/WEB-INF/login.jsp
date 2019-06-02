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
    <title><fmt:message key="l.pageTitle"/></title>

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
            <h6 class="card-text"><fmt:message key="l.cardHeader"/></h6>
        </div>
        <div class="card-body">
            <form id="loginForm" method="post" class="form-horizontal">
                <input type="hidden" name="command" value="${pageContext.request.contextPath}/udacidy/login"/>

                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="email"><fmt:message key="l.email"/></label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="email" name="email"
                               placeholder="<fmt:message key="l.email"/>"/>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="password"><fmt:message key="l.password"/></label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="<fmt:message key="l.password"/>"/>
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-sm-9 offset-sm-4">
                        <button type="submit" class="btn btn-primary" name="signin" value="Sign in">
                            <fmt:message key="l.singInButton"/>
                        </button>
                    </div>
                </div>
            </form>
            <c:if test="${not empty errorLoginPassMessage}">
                <div class="alert alert-danger text-center" role="alert">
                    <strong>${errorLoginPassMessage}</strong> <fmt:message key="l.alert"/>
                </div>
            </c:if>
        </div>

        <div class="card-footer">
            <div class="text-center">
                <h6 class="card-text ml-4">
                    <a href="${pageContext.request.contextPath}/udacidy/registration">
                        <fmt:message key="l.registerAnAccount"/>
                    </a>
                </h6>
            </div>
        </div>
    </div>
</main>
<tag:footer/>

<div id="localeMessagesToJs">
    <input type="hidden" id="passwordRequired" name="passwordRequired" value="<fmt:message key="l.providePassword"/>"/>
    <input type="hidden" id="passwordMinlength" name="passwordMinlength" value="<fmt:message key="l.minlengthPassword"/>"/>
    <input type="hidden" id="emailRequired" name="emailRequired" value="<fmt:message key="l.emailValidate"/>"/>
    <input type="hidden" id="agree" name="agree" value="<fmt:message key="l.agree"/>"/>
    <input type="hidden" id="passwordPattern" name="passwordPattern" value="<fmt:message key="l.passwordPattern"/>"/>
</div>

<script src="../js/loginValidation.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
