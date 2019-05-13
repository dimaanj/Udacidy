<%@ tag description="Footer" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
<footer class="footer">
    <div class="container">
        <form class="input-group w-auto p-3" id="send-message-form">
            <input type="hidden" id="command-id" name="command" value="sendMessage"/>
            <div class="input-group">
                <input id="message" name="message" type="text" class="form-control" placeholder="Input message"
                       aria-label="Recipient's username" aria-describedby="button-addon2">

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
    var form = document.getElementById('send-message-form');

    form.addEventListener('submit', function (ev) {
        var headers = new Headers();
        // Tell the server we want JSON back
        headers.set('Accept', 'application/json');

        var formData = new FormData();
        for (var i = 0; i < form.length; ++i) {
            formData.append(form[i].name, formEl[i].value);
        }

        // This is for the purpose of this demo using jsFiddle AJAX Request endpoint
        formData.append('json', JSON.stringify({example: 'return value'}));

        // 2. Make the request
        // ================================
        var url = 'http://localhost:8080/udacidy/';
        var fetchOptions = {
            method: 'POST',
            body: formData
        };

        var responsePromise = fetch(url, fetchOptions);

        responsePromise
        // 3.1 Convert the response into JSON-JS object.
            .then(function(response) {
                return response.json();
            })
            // 3.2 Do something with the JSON data
            .then(function(jsonData) {
                console.log(jsonData);
                document.getElementById('results').innerText =
                    JSON.stringify(jsonData);
            });


        event.preventDefault();
    })
</script>