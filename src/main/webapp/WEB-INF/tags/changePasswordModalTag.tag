<%@ tag description="ChangePasswordModalTag" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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