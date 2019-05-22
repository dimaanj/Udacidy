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
</head>
<body>

<tag:navbar/>

<c:if test="${not empty firstUserEnter}">
    <h3 class="display-4 font-weight-normal text-center" id="firstUserEnter">Here you can ask our admins...</h3>
</c:if>
<main role="main" class="container-fluid" id="main-tag">
    <input type="hidden" id="conversation-id" name="conversationId" value="${conversationId}">
    <c:if test="${not empty showViewMoreButton}">
        <button class="btn btn-primary btn-lg btn-block w-50 mx-auto shadow" type="submit" id="view-more-button">
            View more
        </button>
    </c:if>
</main>

<style>
    html {
        position: relative;
        min-height: 100%;
    }

    body {
        margin-bottom: 75px; /* Margin bottom by footer height */
    }

    .footer {
        position: fixed;
        bottom: 0;
        width: 100%;
        height: 60px; /* Set the fixed height of the footer here */
        line-height: 60px; /* Vertically center the text there */
        background-color: #f5f5f5;
    }


    /* Custom page CSS
    -------------------------------------------------- */
    /* Not required for template or sticky footer method. */

    .container {
        width: auto;
        max-width: 680px;
        padding: 0 15px;
    }
</style>
<style>
    #responsive-image {
        width: 50%;
        height: auto;
    }
</style>
<footer class="footer" id="footer">
    <div class="container">
        <form class="input-group w-auto p-3" id="send-message-form" enctype="multipart/form-data">
            <div class="input-group">
                <input id="message" name="message" type="text" class="form-control" placeholder="Input message"
                       aria-label="Recipient's username" aria-describedby="button-addon2">
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="inputGroupFile04"
                           aria-describedby="inputGroupFileAddon04" accept="image/x-png">
                    <label class="custom-file-label" for="inputGroupFile04">Choose image</label>
                </div>
                <div class="input-group-append">
                    <button class="btn btn-primary" type="submit" id="button-addon2">
                        Send
                    </button>
                </div>
            </div>
        </form>
    </div>
</footer>

<script>
    window.onload = function () {
        loadMessages();
        setInterval(updateMessages, 1000);
        window.scrollTo(0, document.body.scrollHeight);
    };

    function waitFirstResponse () {
        var row = $('.row');
        if(row !== null){
            updateMessages();
        }
    }

    function getMessagesRequestForm(url) {
        var fetchOptions = {
            method: 'GET',
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
            .then(function (jsonData) {
                console.log(jsonData);
                for (var i = 0; i < jsonData.length; i++) {
                    var messageElement = jsonMessageToDom(jsonData[i]);
                    document.getElementById('main-tag').appendChild(messageElement);
                }
                if(!isEmpty(jsonData)){
                    window.scrollTo(0, document.body.scrollHeight);
                }
            })
            .catch(function(error) {
                console.log('There has been a problem with your fetch operation: ', error.message);
            });
    }

    function isEmpty(obj) {
        for(var key in obj) {
            if(obj.hasOwnProperty(key))
                return false;
        }
        return true;
    }

    function loadMessages() {
        var url = '/udacidy/loadMessages?' +
            'conversationId=' + ${conversationId};
        getMessagesRequestForm(url);
    }

    function updateMessages() {
        var row = $('.row');
        var lastMessageElementId = row.last().attr('id');
        var url = '/udacidy/updateMessages?' +
                'lastMessageId=' + lastMessageElementId;
        getMessagesRequestForm(url);
    }

    function jsonMessageToDom(obj) {
        var row = document.createElement('div');
        row.classList.add('row');
        row.id = obj.id;

        var col = document.createElement('div');
        var card = document.createElement('div');
        var cardBody = document.createElement('div');

        col.classList.add('col-md-8');
        card.classList.add('card');
        card.classList.add('shadow');
        card.classList.add('w-75');
        card.classList.add('mx-auto');
        card.classList.add('mt-3');
        card.classList.add('border-white');
        card.classList.add('rounded-lg');
        cardBody.classList.add('card-body');
        if (obj.creatorId === ${sessionScope.user.getId()}) {
            col.classList.add('ml-auto');
            cardBody.classList.add('text-primary');
        } else {
            cardBody.classList.add('text-success');
        }
        card.setAttribute('style', 'width: 18rem;');

        var h6 = document.createElement('h6');
        h6.classList.add('card-text');

        console.log(obj.text);
        h6.appendChild(document.createTextNode(obj.text));
        cardBody.appendChild(h6);

        var cardFooter = document.createElement('div');
        cardFooter.classList.add('card-footer');
        cardFooter.classList.add('bg-white');
        cardFooter.classList.add('rounded');
        var p = document.createElement('p');
        p.classList.add('card-title');

        var authorSettings;
        var timeNotify = ', at ' + obj.formattedCreationDateTime;
        if (obj.creatorId === ${sessionScope.user.getId()}) {
            authorSettings = document.createTextNode('by me' + timeNotify);
        } else {
            var adminNotify = '';
            if (obj.creatorRole === 'ADMIN') {
                adminNotify = ' (ADMIN)';
            }
            authorSettings = document.createTextNode(
                'by ' + obj.creatorFirstName + ' ' + obj.creatorLastName + adminNotify + timeNotify);
        }
        p.appendChild(authorSettings);
        cardFooter.appendChild(p);

        if ("undefined" !== typeof (obj["imageUrl"]) && obj.imageUrl !== null) {
            var img = document.createElement('img');
            img.classList.add('card-img-top');
            img.src = obj.imageUrl;
            img.classList.add('mx-auto');
            img.id = 'responsive-image';
            img.alt = '';
            card.appendChild(img);
        }

        card.appendChild(cardBody);
        card.appendChild(cardFooter);
        col.appendChild(card);
        row.appendChild(col);
        return row;
    }

    var form = document.getElementById('send-message-form');
    form.addEventListener('submit', function (event) {
        var message = document.getElementById("message");
        var conversationId = document.getElementById("conversation-id");
        var fileInput = document.getElementById("inputGroupFile04");

        if (message.value !== "" || fileInput.files.length !== 0) {
            const formData = new FormData();
            formData.append('file', fileInput.files[0]);
            formData.append('command', 'ajaxSendMessage');
            formData.append('message', message.value);
            formData.append('conversationId', conversationId.value);

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
                    console.log(obj);
                    var message = jsonMessageToDom(obj);
                    $('#main-tag').append(message);

                    document.getElementById("send-message-form").reset();
                    window.scrollTo(0, document.body.scrollHeight);
                })
                .catch(function(error) {
                    console.log('There has been a problem with your fetch operation: ', error.message);
                });
            event.preventDefault();
        }
    });

    <c:if test="${empty firstUserEnter}">
    var viewMore = document.getElementById('view-more-button');
    viewMore.addEventListener('click', function (event) {
        var url = '/udacidy/';
        var earliestMessage = $('#view-more-button').next();
        var fetchOptions = {
            method: 'POST',
            headers: {
                'Content-type': 'application/x-www-form-urlencoded; charset=UTF-8'
            },
            body: 'command=viewMore&' +
                'earliestMessageId=' + earliestMessage.attr('id'),
        };

        var responsePromise = fetch(url, fetchOptions);
        responsePromise
            .then(function (response) {
                return response.json();
            })
            .then(function (jsonData) {
                console.log(jsonData);
                var messages = jsonData.messages;
                var hideViewMoreButton = jsonData.hideViewMoreButton;
                for (var i = 0; i < messages.length; i++) {
                    $('#view-more-button').after(jsonMessageToDom(messages[i]));
                }

                if (hideViewMoreButton === true) {
                    $("#view-more-button").hide();
                }

                document.getElementById(earliestMessage.attr('id')).scrollIntoView();
            });
        event.preventDefault();
    });
    </c:if>
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
