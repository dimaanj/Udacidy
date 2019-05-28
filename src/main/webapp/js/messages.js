window.onload = function () {
    loadMessages();
    setInterval(updateMessages, 700);
    window.scrollTo(0, document.body.scrollHeight);
};

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
            if (!isEmpty(jsonData)) {
                window.scrollTo(0, document.body.scrollHeight);
            }
        })
        .catch(function (error) {
            console.log('There has been a problem with your fetch operation: ', error.message);
        });
}

function isEmpty(obj) {
    for (var key in obj) {
        if (obj.hasOwnProperty(key))
            return false;
    }
    return true;
}

function loadMessages() {
    var url = '/udacidy/loadMessages?' +
        'conversationId=' + document.getElementById('conversationId').value;
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
    var userId = document.getElementById('userId').value;
    console.log(userId);
    if (obj.creatorId.toString() === userId.toString()) {
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
    if (obj.creatorId.toString() === userId.toString()) {
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
    var conversationId = document.getElementById("conversationId");
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
                // if(obj.isFirstMessage) {
                //     var messageElement = jsonMessageToDom(jsonData.message);
                //     document.getElementById('main-tag').appendChild(messageElement);
                //     window.scrollTo(0, document.body.scrollHeight);
                // }
            })
            .catch(function (error) {
                console.log('There has been a problem with your fetch operation: ', error.message);
            });
        event.preventDefault();
    }
});

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