window.onload = function (event) {
    event.preventDefault();
    var images = document.getElementsByTagName('img');
    for(var i=0; i<images.length; i++){
        console.log(images[i]);
        images[i].setAttribute('id', 'responsive-image');
    }

    let pageContent = document.getElementById('page-content');
    if(pageContent.innerHTML === ""){
        let div = document.createElement('div');
        div.classList.add('mt-4');
        div.classList.add('mx-auto');
        div.classList.add('w-75');
        div.classList.add('rounded-lg');
        div.classList.add('shadow-lg');
        div.classList.add('p-5');
        div.classList.add('rounded');
        let h5 = document.createElement('h5');
        h5.classList.add('mx-auto');
        h5.appendChild(document.createTextNode('Sorry, none conferences presented now. ' +
            'But our admins have been already working on content!'));
        div.appendChild(h5);
        pageContent.appendChild(div);
    }
};

function createConference(jsonConference, requestConferenceIds) {
    let conferenceId = jsonConference.id;
    let conferenceContent = jsonConference.content;
    let conferenceSections = jsonConference.jsonSections;

    let confItem = document.createElement('div');
    confItem.classList.add('mx-auto');
    confItem.classList.add('w-75');
    confItem.classList.add('rounded-lg');
    confItem.classList.add('shadow-lg');
    confItem.classList.add('p-5');
    confItem.classList.add('mt-4');
    confItem.classList.add('rounded');

    let bodyData = document.createElement('div');
    bodyData.setAttribute('id', 'bodyData' + conferenceId);
    bodyData.innerHTML = conferenceContent;
    let images = bodyData.getElementsByTagName('img');
    for (var k = 0; k < images.length; k++) {
        images[k].setAttribute('id', 'responsive-image');
    }
    confItem.appendChild(bodyData);

    if(requestConferenceIds.includes(conferenceId)){
        let span = document.createElement('span');
        let a = document.createElement('a');
        a.setAttribute('href', '/udacidy/profile');
        a.appendChild(document.createTextNode('Go profile'));
        span.appendChild(a);
        span.appendChild(document.createTextNode(' to check details of your request'));
        confItem.appendChild(span);
    }else {
        let button = document.createElement('button');
        button.setAttribute('id', 'chooseSectionsButton' + conferenceId);
        button.classList.add('btn');
        button.classList.add('btn-primary');
        button.setAttribute('name', 'chooseSectionsButton');
        button.setAttribute('type', 'button');
        button.setAttribute('data-toggle', 'collapse');
        button.setAttribute('data-target', '#collapseSections' + conferenceId);
        button.setAttribute('aria-expanded', 'false');
        button.setAttribute('aria-controls', 'collapseSections' + conferenceId);
        button.appendChild(document.createTextNode('Choose sections'));
        confItem.appendChild(button);
    }

    let collapse = document.createElement('div');
    collapse.classList.add('collapse');
    collapse.classList.add('mt-3');
    collapse.setAttribute('id', 'collapseSections' + conferenceId);
    let card = document.createElement('div');
    card.classList.add('card');
    let cardBody = document.createElement('card-body');
    let ul = document.createElement('ul');
    ul.classList.add('tox-checklist');
    ul.setAttribute('id','sectionsUl'+conferenceId);
    for(let i=0;i<conferenceSections.length;i++){
        let li = document.createElement('li');
        li.setAttribute('name', 'section');
        li.setAttribute('id', conferenceSections[i].id);
        li.innerHTML = conferenceSections[i].content;
        ul.appendChild(li);
    }
    cardBody.appendChild(ul);

    let cardFooter = document.createElement('div');
    cardFooter.classList.add('card-footer');
    cardFooter.classList.add('border-white');
    let submitRequestButton = document.createElement('button');
    submitRequestButton.classList.add('btn');
    submitRequestButton.classList.add('btn-primary');
    submitRequestButton.setAttribute('name', 'submitRequestButton');
    submitRequestButton.setAttribute('type', 'button');
    submitRequestButton.setAttribute('id', 'submitRequestButton' + conferenceId);
    submitRequestButton.appendChild(document.createTextNode('Submit request'));
    cardFooter.appendChild(submitRequestButton);

    card.appendChild(cardBody);
    card.appendChild(cardFooter);
    collapse.appendChild(card);

    confItem.appendChild(collapse);
    console.log(confItem);
    return confItem;
}

$('#pagination-demo').twbsPagination({
    totalPages: Math.ceil($('#conferencesNumber').val() / 3),
    visiblePages: 6,
    next: 'Next',
    prev: 'Prev',
    last: 'last',
    first: 'First',
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

            let pageContent = document.getElementById('page-content');
            pageContent.innerHTML = "";

            let conferenceIds = obj.requestConferenceIds;
            let conferences = obj.conferences;

            if(isEmpty(conferenceIds)){
                conferenceIds = new Array(1);
                conferenceIds[0] = -1;
            }

            console.log('conferenceIds' + conferenceIds);
            for(let i=0;i<conferences.length;i++){
                pageContent.append(createConference(conferences[i], conferenceIds));
            }
            body.animate({ scrollTop: 0 }, "slow");
        })
        .catch(function (error) {
            console.log('There has been a problem with your fetch operation: ', error.message);
        });
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
    let conferenceItem = thisButton.parentNode.parentNode.parentNode.parentNode;
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
        let previousAlert = document.getElementById('singleAlert');
        if (previousAlert !== null) {
            previousAlert.remove();
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

        const formData = new FormData();
        formData.append('command', 'sendClientRequest');
        formData.append('sectionsIds', JSON.stringify(sectionsIds));

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

                var modal = $("#viewDetails");
                modal.modal('show');

                let conferenceId = thisButton.getAttribute('id').replace('submitRequestButton', '');
                let collapseElement = document.getElementById('collapseSections' + conferenceId);
                collapseElement.remove();

                let alert = createAlertWithTextAndType(jsonObj.message, 'alert-success');
                alert.classList.add('mt-3');
                alert.classList.add('w-75');
                alert.classList.add('mx-auto');

                let span = document.createElement('span');
                let a = document.createElement('a');
                a.setAttribute('href', '/udacidy/profile');
                a.appendChild(document.createTextNode('Go profile'));
                span.appendChild(a);
                span.appendChild(document.createTextNode('to check details of your request'));

                let collapseButton = document.getElementById('chooseSectionsButton'+conferenceId);
                collapseButton.remove();

                conferenceItem.appendChild(span);
                conferenceItem.appendChild(alert);
            });
    }
});

function isEmpty(obj) {
    for (var key in obj) {
        if (obj.hasOwnProperty(key))
            return false;
    }
    return true;
}




