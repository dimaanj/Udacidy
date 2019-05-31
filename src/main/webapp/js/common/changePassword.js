body.on('click', '#changePasswordButton', function (event) {
    event.preventDefault();

    var thisButton = this;
    var modalBody = document.getElementById('change-password-modal-body');

    var previousPassword = document.getElementById('previousPassword');
    var newPassword = document.getElementById('newPassword');
    var confirmedPassword = document.getElementById('confirmedPassword');

    var isPasswordsCorrect = previousPassword.value !== "" && previousPassword.value.length > 4 &&
        newPassword.value !== "" && newPassword.value.length > 4 &&
        confirmedPassword.value !== "" && confirmedPassword.value.length > 4;

    var alert;
    if (!isPasswordsCorrect) {
        alert = createAlertWithTextAndType(
            document.getElementById('passwordRequiredLength').value, 'alert-danger');
        alert.classList.add('mt-3');
        modalBody.appendChild(alert);
    } else if (newPassword.value !== confirmedPassword.value) {
        alert = createAlertWithTextAndType(
            document.getElementById('equalsPasswords').value, 'alert-danger');
        alert.classList.add('mt-3');
        modalBody.appendChild(alert);
    } else {
        thisButton.setAttribute('disabled', 'true');

        const formData = new FormData();
        formData.append('command', 'changePassword');
        formData.append('previousPassword', previousPassword.value);
        formData.append('newPassword', newPassword.value);

        var url = '/udacidy/?n=' + new Date().getTime();
        var fetchOptions = {
            method: 'POST',
            cache: 'no-store',
            body: formData,
        };
        var responsePromise = fetch(url, fetchOptions);
        responsePromise
            .then(function (response) {
                return response.json();
            })
            .then(function (jsonObj) {
                var alert;
                if (jsonObj.isPreviousPasswordCorrect) {
                    previousPassword.value = '';
                    newPassword.value = '';
                    confirmedPassword.value = '';
                    $('#changePasswordModal').modal('hide');
                    alert = createAlertWithTextAndType(
                        document.getElementById('updatePasswordSuccess').value, 'alert-success');
                    alert.classList.add('mt-3');
                    document.getElementById('profileBody').appendChild(alert);
                } else {
                    alert = createAlertWithTextAndType(
                        document.getElementById('prevPasswordIsNotValid').value, 'alert-danger');
                    if (modalBody.children[3] !== null && !isEmpty(modalBody.children[3])) {
                        modalBody.children[3].remove();
                    }
                    modalBody.appendChild(alert);
                }
                thisButton.removeAttribute('disabled');
            })
            .catch(e => {
                $('#changePasswordModal').modal('hide');
                alert = createAlertWithTextAndType('Something was wrong! Sorry. We have already working on this problem.', 'alert-danger');
                alert.classList.add('mt-3');
                document.getElementById('profileBody').appendChild(alert);
                thisButton.removeAttribute('disabled');
                console.log(e);
            });
        previousPassword.innerText = "";
        newPassword.innerText = "";
        confirmedPassword.innerText = "";
    }
});