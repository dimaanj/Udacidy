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

body.on('click', "button[name='viewDetailsButton']", function (event) {
    event.preventDefault();
    console.log('1');

    var thisButton = this;
    thisButton.setAttribute('disabled', 'true');

    let conferenceId = thisButton.getAttribute('id').replace('viewDetailsButton', '');
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

body.on('click', "button[name='removeRequestButton']", function (event) {
    event.preventDefault();
    console.log('1');
    let conferenceId = this.getAttribute('id').replace('removeRequestButton', '');
    let confirmationButton = document.getElementById('confirmationButton');
    confirmationButton.setAttribute('conferenceId', conferenceId);
});

body.on('click', '#confirmationButton', function (event) {
    event.preventDefault();

    let thisButton = this;
    thisButton.setAttribute('disabled', 'true');
    let conferenceId = thisButton.getAttribute('conferenceId');
    console.log(conferenceId);
    console.log(conferenceId.value);

    const formData = new FormData();
    formData.append('command', 'removeRequest');
    formData.append('conferenceId', conferenceId);

    var url = '/udacidy/';
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
            let requestItem = document.getElementById('requestItem' + conferenceId);
            requestItem.innerHTML = "";
            let alert;
            if (jsonObj.isPositiveResult) {
                alert = createAlertWithTextAndType(
                    document.getElementById('itemWasSuccessfullyRemoved').value, 'alert-success');
            } else {
                alert = createAlertWithTextAndType(
                    document.getElementById('sorry').value, 'alert-danger');
            }
            requestItem.appendChild(alert);

            $("#confirmationModal").modal('hide');
        });
    thisButton.removeAttribute('disabled');
});





