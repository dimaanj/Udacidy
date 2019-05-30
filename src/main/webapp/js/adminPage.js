var body = $("body");

body.on('click', "button[name='removeConversation']", function (event) {
    let removeConversationId = this.getAttribute('id');
    let conversationId = removeConversationId.replace('removeConversation', '');
    let conversationIdHiddenParam = document.getElementById('conversationIdHiddenParam');
    conversationIdHiddenParam.setAttribute('value', conversationId);
});

body.on('click', "#confirmationRemoveConferenceButton", function (event) {
    event.preventDefault();

    let thisButton = this;
    thisButton.setAttribute('disabled', 'true');

    let conversationIdHiddenParam = document.getElementById('conversationIdHiddenParam');
    let conversationId = conversationIdHiddenParam.getAttribute('value');

    const formData = new FormData();
    formData.append('command', 'removeQuestionConversation');
    formData.append('conversationId', conversationId);

    var url = '/udacidy/';
    var fetchOptions = {
        method: 'POST',
        body: formData,
    };

    var responsePromise = fetch(url, fetchOptions);
    responsePromise
        .then(function (response) {
            return response.json();
        })
        .then(function (obj) {
            console.log(obj);
            let element = document.getElementById('userQuestionItem' + conversationId);
            let alert = createAlertWithTextAndType(obj.message, 'alert-success');
            alert.classList.add('mt-3');
            alert.classList.add('mx-auto');
            element.innerHTML = "";
            element.appendChild(alert);

            $('#confirmationActionModal').modal('hide');
        })
        .catch(function (error) {
            console.log('There has been a problem with your fetch operation: ', error.message);
        });
    thisButton.removeAttribute('disabled');
});


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

function initRequestData(thisButton, command){
    let confirmationActionButton = document.getElementById('confirmationActionButton');

    let conferenceId = thisButton.getAttribute('conferenceId');
    let userId = thisButton.getAttribute('userId');

    confirmationActionButton.setAttribute('conferenceId', conferenceId);
    confirmationActionButton.setAttribute('userId', userId);
    confirmationActionButton.setAttribute('command', command);
}

body.on('click', "button[name='acceptRequest']", function (event) {
    event.preventDefault();
    let thisButton = this;
    initRequestData(thisButton, 'acceptUserRequest');
});
body.on('click', "button[name='rejectRequest']", function (event) {
    event.preventDefault();
    let thisButton = this;
    initRequestData(thisButton, 'rejectUserRequest');
});

body.on('click', "#confirmationActionButton", function (event) {
    event.preventDefault();
    let thisButton = this;
    thisButton.setAttribute('disabled', 'true');

    let conferenceId = thisButton.getAttribute('conferenceId');
    let userId = thisButton.getAttribute('userId');

    const formData = new FormData();
    formData.append('command', thisButton.getAttribute('command'));
    formData.append('conferenceId', conferenceId);
    formData.append('userId', userId);

    var url = '/udacidy/';
    var fetchOptions = {
        method: 'POST',
        body: formData,
    };
    var responsePromise = fetch(url, fetchOptions);
    responsePromise
        .then(function (response) {
            return response.json();
        })
        .then(function (obj) {
            console.log(obj);

            let alert = createAlertWithTextAndType(obj.message, 'alert-success');
            let requestItem = document.getElementById('userRequestItem' + conferenceId);
            requestItem.innerHTML = "";
            requestItem.appendChild(alert);

            $('#confirmationActionModal').modal('hide');
        })
        .catch(function (error) {
            console.log('There has been a problem with your fetch operation: ', error.message);
        });
    thisButton.removeAttribute('disabled');
});



