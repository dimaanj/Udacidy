<%@ tag description="ChangePasswordModalTag" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<%--Change password modal--%>
<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="ch.modalHeader"/>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" id="change-password-modal-body">
                <div class="form-group">
                    <label for="previousPassword"><fmt:message key="ch.previousPassword"/></label>
                    <input type="password" name="password" class="form-control" id="previousPassword"
                           placeholder="<fmt:message key="ch.previousPassword"/>">
                </div>
                <div class="form-group">
                    <label for="newPassword"><fmt:message key="ch.newPassword"/></label>
                    <input type="password" name="password" class="form-control" id="newPassword" placeholder="<fmt:message key="ch.newPassword"/>">
                </div>
                <div class="form-group">
                    <label for="confirmedPassword"><fmt:message key="ch.confirmPassword"/></label>
                    <input type="password" name="password" class="form-control" id="confirmedPassword"
                           placeholder="<fmt:message key="ch.confirmPassword"/>">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="ch.close"/></button>
                <button type="button" class="btn btn-primary" id="changePasswordButton"><fmt:message key="ch.saveChanges"/></button>
            </div>
        </div>
    </div>
</div>

<div id="localMessagesToJs">
    <input type="hidden" id="passwordRequiredLength" name="passwordRequiredLength" value="<fmt:message key="ch.passwordRequiredLength"/>"/>
    <input type="hidden" id="equalsPasswords" name="equalsPasswords" value="<fmt:message key="ch.equalsPasswords"/>"/>
    <input type="hidden" id="prevPasswordIsNotValid" name="prevPasswordIsNotValid" value="<fmt:message key="ch.prevPasswordIsNotValid"/>"/>
    <input type="hidden" id="updatePasswordSuccess" name="updatePasswordSuccess" value="<fmt:message key="ch.updatePasswordSuccess"/>"/>
</div>