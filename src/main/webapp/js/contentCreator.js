var nextStepButton = document.getElementById('nextStepButton');
nextStepButton.addEventListener('click', function (event) {
    var alertContainer = $('#alert-message');

    alertContainer.innerHTML = "";
    document.getElementById("data-container").innerHTML = "";

    var content = tinymce.get("mytextarea").getContent();
    var sections = tinymce.get("mytextarea").$(".tox-checklist--checked");

    console.log("1");
    var alert;
    if(content !== ""){
        console.log("2");
        if(isEmpty(sections) || sections.length === 0){
            console.log("3");
            alert = createAlertWithTextAndType('Your conference should contain at least one checked element.' +
                'You can find it in toolbar menu. And try to submit again!', 'alert-danger');
            alertContainer.append(alert);
            $(window).scrollTop(0);
        }else {
            console.log("4");
            console.log(tinymce.get("mytextarea").getContent());
            document.getElementById('data-container').innerHTML = content;
            $('#previewModal').modal('show');
        }
    }else {
        console.log("5");
        alert = createAlertWithTextAndType('Content empty! Fill in your conference!', 'alert-danger');
        alertContainer.append(alert);
        $(window).scrollTop(0);
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

function isEmpty(obj) {
    for(var key in obj) {
        if(obj.hasOwnProperty(key)) {
            return false;
        }
    }
    return true;
}

var createConference = document.getElementById('addConferenceButton');
createConference.addEventListener('click', function () {
    $('#previewModal').modal('hide');
    this.setAttribute('disabled', 'true');

    var content = tinymce.get("mytextarea").getContent();
    var sections = tinymce.get("mytextarea").$(".tox-checklist--checked");

    var sectionsArray = new Array(sections.length);
    for(var i=0; i<sections.length; i++){
        console.log(sections[i].innerHTML);
        sectionsArray[i] = sections[i].innerHTML;
    }

    const formData = new FormData();
    formData.append('command', 'createConference');
    formData.append('content', content);
    formData.append('sections', JSON.stringify(sectionsArray));

    var url = '/udacidy/';
    var fetchOptions = {
        method: 'POST',
        body: formData
    };
    var responsePromise = fetch(url, fetchOptions);
    responsePromise
        .then(function (response) {
            return response.text();
        })
        .then(function (text) {
            console.log(text);
            createAlertWithTextAndType(text, 'alert-success')
            $('#alert-message').append("<div class=\"alert alert-success alert-dismissible fade show shadow\" role=\"alert\">\n" +
                "  <strong>"+text+"!</strong> Aww yeah, you successfully read this important alert message. \n" +
                "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                "    <span aria-hidden=\"true\">&times;</span>\n" +
                "  </button>\n" +
                "</div>");
            $(window).scrollTop(0);
        });
    this.removeAttribute('disabled');
    event.preventDefault();
});