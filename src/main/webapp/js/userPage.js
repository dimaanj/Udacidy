var body = $("body");

body.on('click', "button[name='removeRequest']", function () {
    var rowElement = this.parentElement.parentElement.parentElement.parentElement;
    var confirmationButton = document.getElementById('confirmationButton');
    confirmationButton.setAttribute('name', rowElement.getAttribute('id'));
});

body.on('click', '#confirmationButton', function (event) {
    var thisButton = this;
    var conferenceId = this.getAttribute('name');
    var rowElement = document.getElementById(conferenceId);
    const formData = new FormData();
    formData.append('command', 'removeRequest');
    formData.append('conferenceId', conferenceId);

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
        .then(function (jsonObj) {
            var alert;
            if (jsonObj.didSectionsExists) {
                alert = createAlertWithTextAndType(jsonObj.message, 'alert-info');
            } else {
                alert = createAlertWithTextAndType(jsonObj.message, 'alert-danger');
            }
            alert.classList.add('mx-auto');
            alert.classList.add('col-sm-8');
            alert.classList.add('shadow-lg');
            alert.classList.add('rounded-lg');
            alert.classList.add('mt-3');
            alert.setAttribute('id', rowElement.getAttribute('id'));

            rowElement.parentNode.replaceChild(alert, rowElement);
            document.getElementById(alert.getAttribute('id')).scrollIntoView();

            thisButton.removeAttribute('name');
            $('#confirmationModal').modal('hide');
        });
});

function createConference(jsonConference) {
    var conferenceId = jsonConference.id;
    var conferenceContent = jsonConference.content;
    var conferenceSections = jsonConference.jsonSections;

    var row = document.createElement('div');
    row.classList.add('row');
    row.classList.add('mt-4');
    row.classList.add('justify-content-md-center');
    row.id = conferenceId;

    var col = document.createElement('div');
    col.classList.add('col-sm-7');
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

    var flexR = document.createElement('div');
    flexR.classList.add('d-flex');
    flexR.classList.add('flex-row');
    flexR.classList.add('bd-highlight');
    flexR.classList.add('mb-3');

    var flexItem = document.createElement('div');
    flexItem.classList.add('p-2');
    flexItem.classList.add('bd-highlight');

    var removeRequestButton = removeRequestButtonBuilder();
    flexItem.appendChild(removeRequestButton);

    flexR.appendChild(flexItem);
    col.appendChild(flexR);

    row.appendChild(col);
    return row;
}

var removeRequestButtonBuilder = function () {
    var removeRequestButton = document.createElement('button');
    removeRequestButton.classList.add('btn');
    removeRequestButton.classList.add('btn-dark');
    removeRequestButton.setAttribute('name', 'removeRequest');
    removeRequestButton.appendChild(document.createTextNode('Remove request'));
    removeRequestButton.setAttribute('data-toggle', 'modal');
    removeRequestButton.setAttribute('data-target', '#confirmationModal');
    return removeRequestButton;
};

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
