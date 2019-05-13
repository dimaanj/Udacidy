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
            var dfreeHeaderConfig = {
                selector: '.dfree-header',
                menubar: false,
                inline: true,
                toolbar: false,
                plugins: ['quickbars'],
                quickbars_insert_toolbar: 'undo redo',
                quickbars_selection_toolbar: 'italic underline'
            };

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
</head>
<body>

<tag:navbar/>
<main role="main" class="container-fluid" id="main-tag">
    <div id="data-container"></div>

    <c:if test="${not hideViewMoreButton}">
        <button class="btn btn-primary btn-lg btn-block w-50 mx-auto shadow-lg mt-3" type="button" id="view-more-button">View
            more
        </button>
    </c:if>
</main>

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
<tag:footer/>

<script>
    window.onload = function () {
        <c:if test="${not empty conferences}">
            <c:forEach var="i" begin="0" end="${conferences.size()-1}">
                var jsonConference = ${conferences.get(i)};
                createConference(jsonConference);
            </c:forEach>
        </c:if>
    };

    function createConference(jsonConference) {
        var conferenceId = jsonConference.id;
        var conferenceTitle = jsonConference.title;
        var conferenceAuthorFirstName = jsonConference.authorFirstName;
        var conferenceAuthorLastName = jsonConference.authorLastName;
        var conferenceContent = jsonConference.content;

        var row = document.createElement('div');
        row.classList.add('row');
        row.classList.add('mt-3');
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

        var userButton = document.createElement('button');
        userButton.classList.add('btn');
        userButton.classList.add('btn-primary');
        userButton.classList.add('mb-3');
        userButton.classList.add('mr-auto');
        userButton.classList.add('mt-2');
        userButton.appendChild(document.createTextNode('Go somewhere'));
        col.appendChild(userButton);
        </c:when>
        </c:choose>


        row.appendChild(col);
        document.getElementById('data-container').append(row);
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

    