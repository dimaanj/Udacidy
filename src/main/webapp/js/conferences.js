window.onload = function (event) {
    event.preventDefault();
    var images = document.getElementsByTagName('img');
    for(var i=0; i<images.length; i++){
        console.log(images[i]);
        images[i].setAttribute('id', 'responsive-image');
    }
};

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



