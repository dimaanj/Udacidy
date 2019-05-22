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

    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.min.js"></script>
</head>
<body>

<tag:navbar/>
<main role="main" class="container">
    <div class="card mt-3 shadow">
        <div class="card-header">
            <h6 class="card-text">Sign up Form</h6>
        </div>
        <div class="card-body">
            <form id="signupForm" method="post" class="form-horizontal">
                <input type="hidden" name="command"
                       value="${pageContext.request.contextPath}/udacidy/registration"/>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="firstname">First name</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="firstname" name="firstname"
                               placeholder="First name"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="lastname">Last name</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="lastname" name="lastname"
                               placeholder="Last name"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="email">Email</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="email" name="email"
                               placeholder="Email"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="password">Password</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="Password"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label" for="confirm_password">Confirm password</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="confirm_password"
                               name="confirm_password" placeholder="Confirm password"/>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-6 offset-sm-4">
                        <div class="form-check">
                            <input type="checkbox" id="agree" name="agree" value="agree"
                                   class="form-check-input"/>
                            <label class="form-check-label">Please agree to our policy</label>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-9 offset-sm-4">
                        <button type="submit" class="btn btn-primary" name="signup" value="Sign up">Sign up
                        </button>
                    </div>
                </div>
            </form>
            <c:if test="${not empty errorRegistrationPassMessage}">
                <div class="alert alert-danger text-center" role="alert">
                    <strong>${errorRegistrationPassMessage}</strong> Try submitting again.
                </div>
            </c:if>
        </div>
        <div class="card-footer">
            <div class="text-center">
                <h6 class="card-text ml-4">
                    <a href="${pageContext.request.contextPath}/udacidy/login">
                        Login Page
                    </a>
                </h6>
            </div>
        </div>
    </div>
</main>
<tag:footer/>

<script src="../js/registrationValidation.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>