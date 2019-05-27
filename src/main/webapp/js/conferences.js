function createConference(jsonConference) {
    var conferenceId = jsonConference.id;
    var conferenceContent = jsonConference.content;
    var conferenceSections = jsonConference.jsonSections;
    var requestStatus = jsonConference.requestStatus;

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
    flexR.setAttribute('id', 'flex-row' + conferenceId);

    var flexItem = document.createElement('div');
    flexItem.classList.add('p-2');
    flexItem.classList.add('bd-highlight');

    if(requestStatus !== null && requestStatus !== undefined) {
        // var removeButton = removeRequestButtonBuilder();
        // flexItem.appendChild(removeButton);
        // flexR.appendChild(flexItem);
        //
        // var flexItem2 = document.createElement('div');
        // flexItem2.classList.add('p-3');
        // flexItem2.classList.add('bd-highlight');
        // flexItem2.setAttribute('id', 'requestStatus' + conferenceId);
        // var h6 = document.createElement('h6');
        // h6.classList.add('card-title');
        //
        // if (requestStatus === 'SHIPPED') {
        //     h6.classList.add('text-primary');
        //     h6.appendChild(document.createTextNode("shipped"));
        // } else if (requestStatus === 'ACCEPTED') {
        //     h6.classList.add('text-success');
        //     h6.appendChild(document.createTextNode("accepted"));
        // } else {
        //     h6.classList.add('text-secondary');
        //     h6.appendChild(document.createTextNode("ejected"));
        // }
        // flexItem2.appendChild(h6);
        // flexR.appendChild(flexItem2);

        let span = document.createElement('span');

        let linkToProfile = document.createElement('a');
        linkToProfile.setAttribute('href', '/udacidy/profile');
        linkToProfile.appendChild(document.createTextNode('Go profile'));
        span.appendChild(linkToProfile);
        span.appendChild(document.createTextNode(' to view request details.'));
        col.appendChild(span);
    }else {
        var chooseSectionsButton = selectSectionsButtonBuilder(conferenceId);
        flexItem.appendChild(chooseSectionsButton);
        flexR.appendChild(flexItem);
    }

    col.appendChild(flexR);
    var collapse = chooseSectionsCollapseElementBuilder(conferenceSections);
    col.appendChild(collapse);

    row.appendChild(col);
    return row;
}

var body = $("body");

body.on('click', "li[name='section']", function (event) {
    event.preventDefault();
    if (this.classList.contains('tox-checklist--checked')) {
        this.classList.remove('tox-checklist--checked');
    } else {
        this.classList.add('tox-checklist--checked');
    }
});

body.on('click', "button[id='view-more-button']", function (event) {
    var lastRowElement = $('.row').last();
    var lastConferenceElementId = lastRowElement.attr('id');

    const formData = new FormData();
    formData.append('command', 'viewMoreConferences');
    formData.append('lastConferenceId', lastConferenceElementId);

    var url = '/udacidy/?t='+new Date().getTime();
    var fetchOptions = {
        method: 'GET',
        cache: 'no-store',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
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

body.on('click', "button[name='submitRequestButton']", function (event) {
   event.preventDefault();
   console.log('1');

    let thisButton = this;
    let col = thisButton.parentNode.parentNode.parentNode.parentNode;
    let sectionsUl = this.parentElement.parentElement.firstElementChild;
    let sections = sectionsUl.firstElementChild.children;

    let isCorrectForm = false;

    for (var i = 0; i < sections.length; i++) {
        console.log(sections[i]);
        if (sections[i].classList.contains('tox-checklist--checked')) {
            isCorrectForm = true;
            console.log('true');
            break;
        }
    }
    if (!isCorrectForm) {
        console.log('incorrectForm');
        let previoseAlert = document.getElementById('singleAlert');
        if (previoseAlert !== null) {
            previoseAlert.remove();
        }
        let alert = createAlertWithTextAndType('You should choose ' +
            'at least one section of the conference to submit request!', 'alert-danger');
        alert.setAttribute('id', 'singleAlert');
        alert.classList.add('mt-3');
        thisButton.parentElement.appendChild(alert);
    } else {
        console.log('will be submited');
        let sectionsLength = 0;
        for (let z = 0; z < sections.length; z++) {
            if (sections[z].classList.contains('tox-checklist--checked')) {
                sectionsLength++;
            }
        }
        let sectionsIds = new Array(sectionsLength);
        let k = 0;
        for (let j = 0; j < sections.length; j++) {
            if (sections[j].classList.contains('tox-checklist--checked')) {
                sectionsIds[k] = sections[j].getAttribute('id');
                k++;
            }
        }

        let inputParam = document.createElement('input');
        inputParam.setAttribute('type', 'hidden');
        inputParam.setAttribute('name', 'sectionsIds');
        inputParam.setAttribute('value', JSON.stringify(sectionsIds));

        let sendRequestForm = document.getElementById('sendRequestForm');
        sendRequestForm.appendChild(inputParam);

        sendRequestForm.submit();
    }

});

