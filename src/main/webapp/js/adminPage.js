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
            return response.blob();
        })
        .then(function (obj) {
            console.log(obj);
            let element = document.getElementById('userQuestionItem' + conversationId);
            let alert = createAlertWithTextAndType(
                document.getElementById('actionWasSubmitted').value, 'alert-success');
            alert.classList.add('mt-3');
            alert.classList.add('mx-auto');
            element.innerHTML = "";
            element.appendChild(alert);

            $('#confirmationModal').modal('hide');
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
            return response.blob();
        })
        .then(function (obj) {

            let alert = createAlertWithTextAndType(
                document.getElementById('actionWasSubmitted').value,'alert-success');
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

body.on('click', "button[name='viewBody']", function (event) {
    event.preventDefault();
    console.log('1');

    var thisButton = this;
    thisButton.setAttribute('disabled', 'true');

    let conferenceId = thisButton.getAttribute('id').replace('viewBody', '');
    let requestId = thisButton.getAttribute('requestId');
    const formData = new FormData();
    formData.append('command', 'getConferenceContent');
    formData.append('conferenceId', conferenceId);
    formData.append('requestId', requestId);

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
    for (let i = 0; i < conferenceSections.length; i++) {
        let li = document.createElement('li');
        li.classList.add('list-group-item');
        if (requestSectionsIds.includes(conferenceSections[i].id)) {
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



