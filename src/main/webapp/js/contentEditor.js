var dfreeBodyConfig = {
    selector: '.dfree-body',
    menubar: false,
    inline: true,
    plugins: [
        'autolink',
        'codesample',
        'link',
        'lists',
        'media',
        'powerpaste',
        'table',
        'image',
        'quickbars'
    ],
    toolbar: false,
    quickbars_insert_toolbar: 'quicktable image',
    quickbars_selection_toolbar: 'bold italic | h2 h3 | blockquote quicklink',
    contextmenu: 'inserttable | cell row column deletetable',
    powerpaste_word_import: 'clean',
    powerpaste_html_import: 'clean'
};

function createConference(jsonConference) {
    var conferenceId = jsonConference.id;
    var conferenceContent = jsonConference.content;

    let div = document.createElement('div');
    div.classList.add('mx-auto');
    div.classList.add('w-75');
    div.classList.add('shadow-lg');
    div.classList.add('p-5');
    div.classList.add('mt-4');
    div.classList.add('rounded-lg');
    div.id = conferenceId;

    var bodyData = document.createElement('div');
    bodyData.setAttribute('id', 'bodyData');
    bodyData.innerHTML = conferenceContent;
    var images = bodyData.getElementsByTagName('img');
    for (var k = 0; k < images.length; k++) {
        images[k].setAttribute('id', 'responsive-image');
    }

    div.appendChild(bodyData);

    var flexRow = document.createElement('div');
    flexRow.classList.add('d-flex');
    flexRow.classList.add('flex-row');
    flexRow.classList.add('bd-highlight');
    flexRow.classList.add('mb-3');

    var flexItem1 = document.createElement('div');
    flexItem1.classList.add('p-2');
    flexItem1.classList.add('bd-highlight');
    var editButton = document.createElement('button');
    editButton.classList.add('btn');
    editButton.classList.add('btn-primary');
    editButton.setAttribute('type', 'button');
    editButton.setAttribute('name', 'edit');
    editButton.appendChild(document.createTextNode('Edit'));
    flexItem1.appendChild(editButton);

    var flexItem2 = document.createElement('div');
    flexItem2.classList.add('p-2');
    flexItem2.classList.add('bd-highlight');
    var deleteConferenceButton = document.createElement('button');
    deleteConferenceButton.classList.add('btn');
    deleteConferenceButton.classList.add('btn-dark');
    deleteConferenceButton.appendChild(document.createTextNode('Delete conference'));
    deleteConferenceButton.setAttribute('name', 'deleteButton');
    deleteConferenceButton.setAttribute('data-toggle', 'modal');
    deleteConferenceButton.setAttribute('data-target', '#confirmationModal');
    flexItem2.appendChild(deleteConferenceButton);

    flexRow.appendChild(flexItem1);
    flexRow.appendChild(flexItem2);
    div.appendChild(flexRow);

    return div;
}

$('#pagination-demo').twbsPagination({
    totalPages: Math.ceil($('#conferencesNumber').val() / 3),
    visiblePages: 6,
    next: 'Next',
    prev: 'Prev',
    onPageClick: function (event, page) {
        console.log(page);
        changePage(page);
    }
});

function changePage(page) {
    const formData = new FormData();
    formData.append('command', 'getPageContent');
    formData.append('page', page);
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
            console.log(obj.conferences);
            console.log(obj.requestConferenceIds);

            let pageContent = document.getElementById('data-container');
            pageContent.innerHTML = "";

            let conferenceIds = obj.requestConferenceIds;
            let conferences = obj.conferences;

            if(isEmpty(conferenceIds)){
                conferenceIds = new Array(1);
                conferenceIds[0] = -1;
            }

            console.log('conferenceIds' + conferenceIds);
            for(let i=0;i<conferences.length;i++){
                pageContent.append(createConference(conferences[i]));
            }
            body.animate({ scrollTop: 0 }, "slow");
        })
        .catch(function (error) {
            console.log('There has been a problem with your fetch operation: ', error.message);
        });
}

var body = $("body");

body.on('click', "button[name='edit']", function (event) {
    event.preventDefault();

    var dFreeBody = this.parentElement.parentElement.parentElement.firstElementChild;
    dFreeBody.classList.add('dfree-body');
    dFreeBody.classList.add('mce-content-body');
    dFreeBody.setAttribute('contenteditable', 'true');
    dFreeBody.setAttribute('style', 'position: relative;');
    dFreeBody.setAttribute('spellcheck', 'false');

    dFreeBody.parentElement.classList.add('demo-dfree');

    tinymce.init(dfreeBodyConfig);

    this.setAttribute('disabled', 'true');
    this.parentElement.parentElement.lastElementChild.firstElementChild.setAttribute('disabled', 'true');

    var flexItem = document.createElement('div');
    flexItem.classList.add('p-2');
    flexItem.classList.add('bd-highlight');

    var button = document.createElement('button');
    button.classList.add('btn');
    button.classList.add('btn-primary');
    button.setAttribute('type', 'button');
    button.setAttribute('name', 'saveChanges');
    button.appendChild(document.createTextNode('Submit changes'));

    flexItem.appendChild(button);

    this.parentElement.parentElement.append(flexItem);
});

body.on('click', "button[name='deleteButton']", function (event) {
    var rowElement = this.parentElement.parentElement.parentElement;
    document.getElementById('confirmationButton').setAttribute('name', rowElement.getAttribute('id'));
});

var deletedConferenceId;

body.on('click', "button[name='saveChanges']", function (event) {
    event.preventDefault();

    var saveChangesButton = this;
    saveChangesButton.setAttribute('disabled', 'true');

    var dFreeBody = saveChangesButton.parentElement.parentElement.parentElement.firstElementChild;
    dFreeBody.removeAttribute('spellcheck');
    dFreeBody.removeAttribute('style');
    dFreeBody.removeAttribute('contenteditable');
    dFreeBody.classList.remove('mce-content-body');
    dFreeBody.classList.remove('dfree-body');

    dFreeBody.parentElement.classList.remove('demo-dfree');

    var editButton = saveChangesButton.parentElement.parentElement.children[0].firstElementChild;
    editButton.removeAttribute('disabled');

    var deleteButton = saveChangesButton.parentElement.parentElement.children[1].firstElementChild;
    deleteButton.removeAttribute('disabled');

    let conferenceId = dFreeBody.parentElement.getAttribute('id');
    const formData = new FormData();
    formData.append('command', 'editConferenceContent');
    formData.append('conferenceId', conferenceId);
    formData.append('content', dFreeBody.innerHTML);

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
            console.log(jsonObj);
            if (jsonObj.isConferenceExists) {

                var alert = document.createElement('div');
                alert.classList.add('alert');
                alert.classList.add('alert-success');
                alert.classList.add('alert-dismissible');
                alert.classList.add('fade');
                alert.classList.add('show');
                alert.setAttribute('role', 'alert');

                var strong = document.createElement('strong');
                strong.appendChild(document.createTextNode(jsonObj.message));

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

                var col = dFreeBody.parentElement;
                col.appendChild(alert);
                saveChangesButton.parentElement.remove();

            } else {
                var dfBody = document.getElementById(conferenceId);
                var dangerAlert = createAlertWithTextAndType(jsonObj.message, 'alert-danger');

                dangerAlert.classList.add('mx-auto');
                dangerAlert.classList.add('col-sm-8');
                dangerAlert.classList.add('shadow-lg');
                dangerAlert.classList.add('rounded-lg');
                dangerAlert.classList.add('mt-3');

                dfBody.parentNode.replaceChild(dangerAlert, dfBody);
                dangerAlert.scrollIntoView();
            }
            tinymce.remove();
        });
});

body.on('click', '#confirmationButton', function (event) {
    event.preventDefault();
    $('#confirmationModal').modal('hide');

    var thisButton = this;
    var conferenceId = this.getAttribute('name');
    var rowElement = document.getElementById(conferenceId);

    var formData = new FormData();
    formData.append('command', 'deleteConference');
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
            console.log(jsonObj);
            var alert;
            if (jsonObj.didConferenceExist) {
                alert = createAlertWithTextAndType(jsonObj.message, 'alert-info');
            } else {
                alert = createAlertWithTextAndType(jsonObj.message, 'alert-danger');
            }
            alert.setAttribute('id', conferenceId);
            alert.classList.add('mx-auto');
            alert.classList.add('col-sm-8');
            alert.classList.add('shadow-lg');
            alert.classList.add('rounded-lg');
            alert.classList.add('mt-3');
            rowElement.parentNode.replaceChild(alert, rowElement);
            deletedConferenceId = conferenceId;
            thisButton.removeAttribute('name');
        });
});


$('#confirmationModal').on('hidden.bs.modal', function (e) {
    var currentAlert = document.getElementsByName(deletedConferenceId);
    currentAlert.scrollIntoView();
});

body.on('click', "button[id='view-more-button']", function (event) {
    var lastRowElement = $('.row').last();
    var lastConferenceElementId = lastRowElement.attr('id');

    const formData = new FormData();
    formData.append('command', 'viewMoreConferences');
    formData.append('lastConferenceId', lastConferenceElementId);

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
            console.log(jsonObj);

            var conferences = jsonObj.conferences;
            var hideViewMoreButton = jsonObj.hideViewMoreButton;

            for (var i = 0; i < conferences.length; i++) {
                var row = createConference(conferences[i]);
                document.getElementById('data-container').append(row);
            }

            if (hideViewMoreButton === true) {
                $("#view-more-button").hide();
            }
        });
    event.preventDefault();
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


function isEmpty(obj) {
    for (var key in obj) {
        if (obj.hasOwnProperty(key))
            return false;
    }
    return true;
}