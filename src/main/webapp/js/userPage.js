var body = $("body");

function createConference(jsonConference, requestSectionsIds) {
    var conferenceId = jsonConference.id;
    var conferenceContent = jsonConference.content;
    var conferenceSections = jsonConference.jsonSections;

    var row = document.createElement('div');
    row.classList.add('row');
    row.classList.add('mt-4');
    row.classList.add('justify-content-md-center');
    row.classList.add('mb-3');
    row.id = conferenceId;

    var col = document.createElement('div');
    col.classList.add('col-sm-8');
    col.classList.add('shadow-lg');
    col.classList.add('rounded-lg');
    col.classList.add('p-5');

    var bodyData = document.createElement('div');
    bodyData.setAttribute('id', 'bodyData');
    bodyData.innerHTML = conferenceContent;
    var images = bodyData.getElementsByTagName('img');
    for (var k = 0; k < images.length; k++) {
        images[k].setAttribute('id', 'responsive-image');
    }

    col.appendChild(bodyData);

    let h5 = document.createElement('h5');
    h5.classList.add('mt-3');
    h5.appendChild(document.createTextNode('Your choice'));
    col.appendChild(h5);

    let ul = document.createElement('ul');
    ul.classList.add('list-group');
    for(let i=0; i<conferenceSections.length; i++){
        let li = document.createElement('li');
        li.classList.add('list-group-item');
        if(requestSectionsIds.includes(conferenceSections[i].id)){
            li.classList.add('active');
            console.log('include section id = ' + conferenceSections[i].id);
        }
        li.innerHTML = conferenceSections[i].content;
        ul.appendChild(li);
    }
    col.appendChild(ul);
    row.appendChild(col);
    return row;
}

function createAlertWithTextAndType(text, type) {
    var alert = document.createElement('div');
    alert.classList.add('alert');
    alert.classList.add(type);
    alert.classList.add('alert-dismissible');
    alert.classList.add('fade');
    alert.classList.add('show');
    alert.setAttribute('role', 'alert');

    var strong = document.createElement('strong');
    strong.appendChild(document.createTextNode(text));

    var button = document.createElement('button');
    button.setAttribute('type', 'button');
    button.classList.add('close');
    button.setAttribute('data-dismiss', 'alert');
    button.setAttribute('aria-label', 'Close');

    var span = document.createElement('span');
    span.setAttribute('aria-hidden', 'true');
    span.innerHTML = '&times;';

    button.appendChild(span);
    alert.appendChild(strong);
    alert.appendChild(button);
    return alert;
}

function isEmpty(obj) {
    for (var key in obj) {
        if (obj.hasOwnProperty(key))
            return false;
    }
    return true;
}

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
        alert = createAlertWithTextAndType('Sorry, passwords should contain at least 5 characters!', 'alert-danger');
        alert.classList.add('mt-3');
        modalBody.appendChild(alert);
    } else if (newPassword.value !== confirmedPassword.value) {
        alert = createAlertWithTextAndType('Sorry, new password should be equal to confirmed!', 'alert-danger');
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
                    $('#changePasswordModal').modal('hide');
                    alert = createAlertWithTextAndType(jsonObj.message, 'alert-success');
                    alert.classList.add('mt-3');
                    document.getElementById('profileBody').appendChild(alert);
                } else {
                    alert = createAlertWithTextAndType(jsonObj.message, 'alert-danger');
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

body.on('click', "button[name='viewDetailsButton']", function (event) {
    event.preventDefault();
    console.log('1');

    var thisButton = this;
    thisButton.setAttribute('disabled', 'true');

    let conferenceId= thisButton.getAttribute('id').replace('viewDetailsButton', '');
    const formData = new FormData();
    formData.append('command', 'getConferenceContent');
    formData.append('conferenceId', conferenceId);

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
            console.log(jsonObj);
            let conference = createConference(jsonObj.conference, jsonObj.requestSectionsIds);
            var dataContainer = document.getElementById('data-container');
            dataContainer.innerHTML = "";
            dataContainer.appendChild(conference);
            $('#viewDetails').modal('show');
            thisButton.removeAttribute('disabled');
        });
});

body.on('click', "button[name='removeRequestButton']", function (event) {
    event.preventDefault();
    console.log('1');
    let removeRequestForm = document.getElementById('removeRequestForm');

    let previousConfIdParam = document.getElementsByName('conferenceId');
    if(!isEmpty(previousConfIdParam)){
        previousConfIdParam.remove();
    }

    let conferenceId = this.getAttribute('id').replace('removeRequestButton', '');

    let inputParam = document.createElement('input');
    inputParam.setAttribute('type', 'hidden');
    inputParam.setAttribute('name', 'conferenceId');
    inputParam.setAttribute('value', conferenceId);

    removeRequestForm.appendChild(inputParam);
});



