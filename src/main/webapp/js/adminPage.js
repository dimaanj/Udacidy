
var body = $("body");

body.on('click', "button[name='removeConversation']", function (event) {
   event.preventDefault();

   let thisButton = this;

   let conversationId = this.getAttribute('id');

    const formData = new FormData();
    formData.append('command', 'removeQuestionConversation');
    formData.append('conversationId', conversationId);

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
            let row = thisButton.parentElement.parentElement.
                parentElement.parentElement.
                parentElement.parentElement.
                parentElement.parentElement;
            let alert = createAlertWithTextAndType(obj.message, 'alert-success');
            alert.classList.add('mt-3');
            alert.classList.add('row');
            row.replaceChild(alert, row);
        })
        .catch(function(error) {
            console.log('There has been a problem with your fetch operation: ', error.message);
        });
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