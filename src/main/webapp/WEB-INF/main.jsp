<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>Checkout example · Bootstrap</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <c:if test="${not empty sessionScope.user and sessionScope.user.isAdmin()}">
        <script src="https://cloud.tinymce.com/5/tinymce.min.js?apiKey=n6pc28z5n87xrwjz2invt1y20ws32djsc2jyd67as953ymf6"></script>
        <script>
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
        </script>
        <style>
            .demo-dfree {
                position: relative;
                color: #626262;
                font-size: 14px;
            }

            .demo-dfree *[contentEditable="true"]:hover {
                outline: 1px solid #2276d2;
            }

            .demo-dfree h1 {
                color: #1976d2;
                font-size: 2em;
                font-weight: bold;
            }

            .demo-dfree h2 {
                color: #1976d2;
                font-size: 2em;
                font-weight: bold;
            }

            .demo-dfree h3 {
                font-size: 1.5em;
                color: #403f42;
                font-weight: bold;
                color: inherit;
            }

            .demo-dfree h2,
            .demo-dfree h3 {
                margin-bottom: 0px;
                margin-top: 0px;
                line-height: 40px;
            }

            .demo-dfree ul,
            .demo-dfree ol {
                padding-left: 20px;
            }

            .demo-dfree ul {
                list-style: disc;
            }

            .demo-dfree ol {
                list-style: decimal;
            }

            .demo-dfree a {
                text-decoration: underline;
            }

            .demo-dfree textarea {
                display: none;
            }

            .demo-dfree .dfree-header {
                font-size: 2.4em;
                text-align: center;
                margin: 10px;
            }

            .demo-dfree .dfree-body {
                padding: 20px;
                padding-top: 0px;
            }

            .demo-dfree .dfree-body p {
                margin-bottom: 10px;
            }

            .demo-dfree .dfree-body blockquote {
                margin-left: 30px;
                padding-left: 10px;
                margin-right: 40px;
                font-style: italic;
                border-left: 1px solid #696969;
            }

            .demo-dfree .dfree-body a {
                text-decoration: underline;
            }

            .demo-dfree .dfree-body td,
            .demo-dfree .dfree-body tr {
                border: 1px solid #696969;
            }


        </style>
    </c:if>

    <c:if test="${not empty sessionScope.user and not sessionScope.user.isAdmin()}">
        <style>
            .tox-checklist > li:not(.tox-checklist--hidden) {
                list-style: none;
                margin: .25em 0;
                position: relative;
            }

            .tox-checklist > li:not(.tox-checklist--hidden)::before {
                background-image: url("data:image/svg+xml;charset=UTF-8,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%2216%22%20height%3D%2216%22%20viewBox%3D%220%200%2016%2016%22%3E%3Cg%20id%3D%22checklist-unchecked%22%20fill%3D%22none%22%20fill-rule%3D%22evenodd%22%3E%3Crect%20id%3D%22Rectangle%22%20width%3D%2215%22%20height%3D%2215%22%20x%3D%22.5%22%20y%3D%22.5%22%20fill-rule%3D%22nonzero%22%20stroke%3D%22%234C4C4C%22%20rx%3D%222%22%2F%3E%3C%2Fg%3E%3C%2Fsvg%3E%0A");
                background-size: 100%;
                content: '';
                cursor: pointer;
                height: 1em;
                left: -1.5em;
                position: absolute;
                top: .125em;
                width: 1em;
            }

            .tox-checklist li:not(.tox-checklist--hidden).tox-checklist--checked::before {
                background-image: url("data:image/svg+xml;charset=UTF-8,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%2216%22%20height%3D%2216%22%20viewBox%3D%220%200%2016%2016%22%3E%3Cg%20id%3D%22checklist-checked%22%20fill%3D%22none%22%20fill-rule%3D%22evenodd%22%3E%3Crect%20id%3D%22Rectangle%22%20width%3D%2216%22%20height%3D%2216%22%20fill%3D%22%234099FF%22%20fill-rule%3D%22nonzero%22%20rx%3D%222%22%2F%3E%3Cpath%20id%3D%22Path%22%20fill%3D%22%23FFF%22%20fill-rule%3D%22nonzero%22%20d%3D%22M11.5703186%2C3.14417309%20C11.8516238%2C2.73724603%2012.4164781%2C2.62829933%2012.83558%2C2.89774797%20C13.260121%2C3.17069355%2013.3759736%2C3.72932262%2013.0909105%2C4.14168582%20L7.7580587%2C11.8560195%20C7.43776896%2C12.3193404%206.76483983%2C12.3852142%206.35607322%2C11.9948725%20L3.02491697%2C8.8138662%20C2.66090143%2C8.46625845%202.65798871%2C7.89594698%203.01850234%2C7.54483354%20C3.373942%2C7.19866177%203.94940006%2C7.19592841%204.30829608%2C7.5386474%20L6.85276923%2C9.9684299%20L11.5703186%2C3.14417309%20Z%22%2F%3E%3C%2Fg%3E%3C%2Fsvg%3E%0A");
            }
        </style>
    </c:if>

</head>
<body>


<style>
    #responsive-image {
        width: 100%;
        height: auto;
    }
</style>
<tag:navbar/>
<main role="main" class="container-fluid" id="main-tag">
    <div id="data-container"></div>

    <c:if test="${not hideViewMoreButton}">
        <button class="btn btn-primary btn-lg btn-block w-50 mx-auto shadow-lg mt-3" type="button" id="view-more-button">View
            more
        </button>
    </c:if>
</main>

<c:if test="${not empty sessionScope.user and sessionScope.user.isAdmin()}">
    <div class="modal fade" id="deleteConferenceModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Confirmation</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Are you really want delete this conference?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary" id="deleteConferenceButton">Yes</button>
                </div>
            </div>
        </div>
    </div>
</c:if>
<tag:footer/>

<script>
    window.onload = function () {
        <c:if test="${not empty conferences}">
            <c:forEach var="i" begin="0" end="${conferences.size()-1}">
                var jsonConference = ${conferences.get(i)};
                console.log(jsonConference);
                var row = createConference(jsonConference);
                document.getElementById('data-container').append(row);
            </c:forEach>
        </c:if>
    };

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

        <c:choose>
            <c:when test="${not empty sessionScope.user and sessionScope.user.isAdmin()}">

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
        deleteConferenceButton.setAttribute('data-target', '#deleteConferenceModal');
        flexItem2.appendChild(deleteConferenceButton);

        flexRow.appendChild(flexItem1);
        flexRow.appendChild(flexItem2);
        col.appendChild(flexRow);
        </c:when>
            <c:when test="${not empty sessionScope.user and not sessionScope.user.isAdmin()}">

        var flexR = document.createElement('div');
        flexR.classList.add('d-flex');
        flexR.classList.add('flex-row');
        flexR.classList.add('bd-highlight');
        flexR.classList.add('mb-3');

        var flexItem = document.createElement('div');
        flexItem.classList.add('p-2');
        flexItem.classList.add('bd-highlight');

        if(jsonConference.isRequestAlreadySent){
            var removeRequestButton = removeRequestButtonBuilder(conferenceId);
            flexItem.appendChild(removeRequestButton);
        }else {
            var chooseSectionsButton = selectSectionsButtonBuilder(conferenceId);
            flexItem.appendChild(chooseSectionsButton);
        }

        flexR.appendChild(flexItem);

        var collapse = document.createElement('div');
        collapse.classList.add('collapse');
        collapse.setAttribute('id', 'collapseSection' + conferenceId);
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

        col.appendChild(flexR);
        col.appendChild(collapse);
        </c:when>
        </c:choose>

        row.appendChild(col);
        return row;
    }

    var body = $("body");

    <c:if test="${not empty sessionScope.user and sessionScope.user.isAdmin()}">

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
        var rowElement = this.parentElement.parentElement.parentElement.parentElement;
        document.getElementById('deleteConferenceButton').setAttribute('name', rowElement.getAttribute('id'));
    });

    body.on('click', '#deleteConferenceButton', function (event) {
        event.preventDefault();

        var thisButton = this;
        var conferenceId = this.getAttribute('name');
        var rowElement = document.getElementById(conferenceId);

        const formData = new FormData();
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
                return response.text();
            })
            .then(function (text) {
                console.log(text);

                var alert = document.createElement('div');
                alert.classList.add('alert');
                alert.classList.add('alert-info');
                alert.classList.add('alert-dismissible');
                alert.classList.add('fade');
                alert.classList.add('show');
                alert.classList.add('mx-auto');
                alert.classList.add('col-sm-8');
                alert.classList.add('shadow-lg');
                alert.classList.add('rounded-lg');
                alert.classList.add('card');
                alert.classList.add('mt-3');
                alert.setAttribute('role', 'alert');
                alert.setAttribute('id', conferenceId);


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

                rowElement.parentNode.replaceChild(alert, rowElement);
                deletedConferenceId = conferenceId;
                thisButton.removeAttribute('name');
                $('#deleteConferenceModal').modal('hide');
            });
    });

    var deletedConferenceId;

    $('#deleteConferenceModal').on('hidden.bs.modal', function (e) {
        var currentAlert = document.getElementsByName(deletedConferenceId);
        currentAlert.scrollIntoView();
    });

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

        const formData = new FormData();
        formData.append('command', 'editConferenceContent');
        formData.append('conferenceId', dFreeBody.parentElement.parentElement.getAttribute('id'));
        formData.append('content', dFreeBody.innerHTML);

        var url = '/udacidy/';
        var fetchOptions = {
            method: 'POST',
            body: formData,
        };

        var responsePromise = fetch(url, fetchOptions);
        responsePromise
            .then(function (response) {
                return response.text();
            })
            .then(function (text) {
                console.log(text);

                var alert = document.createElement('div');
                alert.classList.add('alert');
                alert.classList.add('alert-success');
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

                var col = dFreeBody.parentElement;
                col.appendChild(alert);

                saveChangesButton.parentElement.remove();
                tinymce.remove();
            });
        event.preventDefault();
    });

    </c:if>

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
                    return response.text();
                })
                .then(function (text) {
                    console.log(text);

                    var alert = createAlertWithTextAndType(text, 'alert-success');
                    alert.firstElementChild.append(' Go to profile page to look through all your requests.');

                    var col = thisButton.parentNode.parentNode.parentNode.parentNode;
                    var row = col.parentNode;
                    var removeRequestButton = removeRequestButtonBuilder(row.getAttribute('id'));


                    var dFlex = col.children[1];
                    dFlex.innerHTML = "";
                    dFlex.appendChild(removeRequestButton);

                    col.lastChild.remove();
                    col.appendChild(alert);
                });
        }
    });

    body.on('click', "button[name='removeRequest']", function (event) {
        var thisButton = this;

        const formData = new FormData();
        formData.append('command', 'removeRequest');
        formData.append('conferenceId', thisButton.getAttribute('id'));

        var url = '/udacidy/';
        var fetchOptions = {
            method: 'POST',
            body: formData,
        };
        var responsePromise = fetch(url, fetchOptions);
        responsePromise
            .then(function (response) {
                return response.text();
            })
            .then(function (text) {
                console.log(text);
                var alert = createAlertWithTextAndType(text, 'alert-success');

                var col = thisButton.parentNode.parentNode.parentNode;
                var selectSectionsButton = selectSectionsButtonBuilder(thisButton.getAttribute('id'));

                var dFlex = col.children[1];
                dFlex.innerHTML = "";
                dFlex.appendChild(selectSectionsButton);
                dFlex.appendChild();

                col.lastChild.remove();
                col.appendChild(alert);
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
                    createConference(conferences[i]);
                }

                if (hideViewMoreButton === true) {
                    $("#view-more-button").hide();
                }
            });
        event.preventDefault();
    });

    var removeRequestButtonBuilder = function (conferenceId) {
        var removeRequestButton = document.createElement('button');
        removeRequestButton.classList.add('btn');
        removeRequestButton.classList.add('btn-dark');
        removeRequestButton.setAttribute('name', 'removeRequest');
        removeRequestButton.setAttribute('id', conferenceId);
        removeRequestButton.appendChild(document.createTextNode('Remove request'));
        return removeRequestButton;
    };

    var selectSectionsButtonBuilder = function (conferenceId) {
        var chooseSectionsButton = document.createElement('button');
        chooseSectionsButton.classList.add('btn');
        chooseSectionsButton.classList.add('btn-primary');
        chooseSectionsButton.setAttribute('name', 'chooseSectionsButton');
        chooseSectionsButton.setAttribute('type', 'button');
        chooseSectionsButton.setAttribute('data-toggle', 'collapse');
        chooseSectionsButton.setAttribute('data-target', '#collapseSection' + conferenceId);
        chooseSectionsButton.setAttribute('aria-expanded', 'false');
        chooseSectionsButton.setAttribute('aria-controls', 'collapseSection' + conferenceId);
        chooseSectionsButton.appendChild(document.createTextNode('Choose sections'));
        return chooseSectionsButton;
    };


</script>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>

    