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
    for(var k=0; k<images.length; k++){
        images[k].setAttribute('id', 'responsive-image');
    }

    col.appendChild(bodyData);

    var flexR = document.createElement('div');
    flexR.classList.add('d-flex');
    flexR.classList.add('flex-row');
    flexR.classList.add('bd-highlight');
    flexR.classList.add('mb-3');
    flexR.setAttribute('id', 'flex-row'+conferenceId);

    var flexItem = document.createElement('div');
    flexItem.classList.add('p-2');
    flexItem.classList.add('bd-highlight');

    if(!jsonConference.isRequestAlreadySent){
        var chooseSectionsButton = selectSectionsButtonBuilder(conferenceId);
        flexItem.appendChild(chooseSectionsButton);
    }else{

    }

    flexR.appendChild(flexItem);
    col.appendChild(flexR);
    var collapse = chooseSectionsCollapseElementBuilder(conferenceSections);
    col.appendChild(collapse);

    row.appendChild(col);
    return row;
}

var body = $("body");

body.on('click', "li[name='section']", function (event) {
    event.preventDefault();
    if(this.classList.contains('tox-checklist--checked')){
        this.classList.remove('tox-checklist--checked');
    }else {
        this.classList.add('tox-checklist--checked');
    }
});

body.on('click', "button[name='submitRequestButton']", function (event) {
    event.preventDefault();
    console.log('1');

    var thisButton = this;

    var sectionsUl = this.parentElement.parentElement.firstElementChild;
    var sections = sectionsUl.firstElementChild.children;

    var isCorrectForm = false;

    for(var i=0; i<sections.length; i++){
        console.log(sections[i]);
        if(sections[i].classList.contains('tox-checklist--checked')){
            isCorrectForm = true;
            console.log('true');
            break;
        }
    }
    if(!isCorrectForm){
        console.log('incorrectForm');
        var previoseAlert = document.getElementById('singleAlert');
        if(previoseAlert !== null){
            previoseAlert.remove();
        }
        var alert = createAlertWithTextAndType('You should choose ' +
            'at least one section of the conference to submit request!', 'alert-danger');
        alert.setAttribute('id', 'singleAlert');
        alert.classList.add('mt-3');
        thisButton.parentElement.appendChild(alert);
    }else {
        console.log('will be submited');
        var sectionsLength = 0;
        for(var z=0; z<sections.length; z++){
            if(sections[z].classList.contains('tox-checklist--checked')) {
                sectionsLength++;
            }
        }
        var sectionsIds = new Array(sectionsLength);
        var k = 0;
        for(var j=0; j< sections.length; j++){
            if(sections[j].classList.contains('tox-checklist--checked')) {
                sectionsIds[k] = sections[j].getAttribute('id');
                k++;
            }
        }

        const formData = new FormData();
        formData.append('command', 'sendRequest');
        formData.append('sectionsIds', JSON.stringify(sectionsIds));

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
                if (jsonObj.isSectionExists) {

                    alert = createAlertWithTextAndType(jsonObj.message, 'alert-success');
                    alert.firstElementChild.append(' Go to profile page to look through all your requests.');

                    var col = thisButton.parentNode.parentNode.parentNode.parentNode;

                    var dFlex = col.children[1];
                    dFlex.firstElementChild.innerHTML = "";

                    $("#collapseSections"+col.parentElement.getAttribute('id')).remove();
                    col.appendChild(alert);

                    window.location.replace('/udacidy/conferences');

                } else {
                    var row = thisButton.parentNode.parentNode.parentNode.parentNode;

                    alert = createAlertWithTextAndType(jsonObj.message, 'alert-danger');
                    alert.classList.add('mx-auto');
                    alert.classList.add('col-sm-8');
                    alert.classList.add('shadow-lg');
                    alert.classList.add('rounded-lg');
                    alert.classList.add('mt-3');
                    alert.setAttribute('id', row.getAttribute('id'));

                    row.parentNode.replaceChild(alert, row);
                    document.getElementById(alert.getAttribute('id')).scrollIntoView();
                }
            });
    }
});

body.on('click', "button[name='removeRequest']", function () {
    var rowElement = this.parentElement.parentElement.parentElement.parentElement;
    var confirmationButton = document.getElementById('confirmationButton');
    confirmationButton.setAttribute('name', rowElement.getAttribute('id'));
});

var chooseSectionsCollapseElementBuilder = function (conferenceSections) {
    var collapse = document.createElement('div');
    collapse.classList.add('collapse');
    collapse.setAttribute('id', 'collapseSections' + conferenceSections[0].conferenceId);
    var cardCollapse = document.createElement('div');
    cardCollapse.classList.add('card');

    var cardBodyCollapse = document.createElement('div');
    cardBodyCollapse.classList.add('card-body');
    var ul = document.createElement('ul');
    ul.classList.add('tox-checklist');
    for(var i=0; i<conferenceSections.length; i++){
        var li = document.createElement('li');
        li.setAttribute('name', 'section');
        li.setAttribute('id', conferenceSections[i].id);
        li.appendChild(document.createTextNode(conferenceSections[i].content));
        ul.appendChild(li);
    }
    cardBodyCollapse.appendChild(ul);

    var cardFooter = document.createElement('div');
    cardFooter.classList.add('card-footer');
    cardFooter.classList.add('border-white');
    var submitRequestButton = document.createElement('button');
    submitRequestButton.classList.add('btn');
    submitRequestButton.classList.add('btn-primary');
    submitRequestButton.setAttribute('name', 'submitRequestButton');
    submitRequestButton.appendChild(document.createTextNode('Submit request'));
    cardFooter.appendChild(submitRequestButton);

    cardCollapse.appendChild(cardBodyCollapse);
    cardCollapse.appendChild(cardFooter);

    collapse.appendChild(cardCollapse);
    return collapse;
};

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

var selectSectionsButtonBuilder = function (conferenceId) {
    var chooseSectionsButton = document.createElement('button');
    chooseSectionsButton.classList.add('btn');
    chooseSectionsButton.classList.add('btn-primary');
    chooseSectionsButton.setAttribute('name', 'chooseSectionsButton');
    chooseSectionsButton.setAttribute('type', 'button');
    chooseSectionsButton.setAttribute('data-toggle', 'collapse');
    chooseSectionsButton.setAttribute('data-target', '#collapseSections' + conferenceId);
    chooseSectionsButton.setAttribute('aria-expanded', 'false');
    chooseSectionsButton.setAttribute('aria-controls', 'collapseSections' + conferenceId);
    chooseSectionsButton.appendChild(document.createTextNode('Choose sections'));
    return chooseSectionsButton;
};

body.on('click', '#confirmationButton', function (event) {
    event.preventDefault();
    $('#confirmationModal').modal('hide');

    var thisButton = this;
    var rowElement = document.getElementById(thisButton.getAttribute('name'));
    var conferenceId = rowElement.getAttribute('id');

    var formData = new FormData();
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
            if(jsonObj.didSectionsExists) {
                alert = createAlertWithTextAndType(jsonObj.message, 'alert-info');
                console.log(conferenceId);
                document.getElementById('flex-row' + conferenceId).remove();

                var col = rowElement.firstElementChild;
                col.appendChild(alert);
                window.location.replace('/udacidy/conferences');
            }else {
                alert = createAlertWithTextAndType(jsonObj.message, 'alert-danger');
                alert.classList.add('mx-auto');
                alert.classList.add('col-sm-8');
                alert.classList.add('shadow-lg');
                alert.classList.add('rounded-lg');
                alert.classList.add('mt-3');
                alert.setAttribute('id', rowElement.getAttribute('id'));

                rowElement.parentNode.replaceChild(alert, rowElement);
                document.getElementById(alert.getAttribute('id')).scrollIntoView();
            }
            thisButton.removeAttribute('name');
        });
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