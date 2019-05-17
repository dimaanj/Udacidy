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

    <script src="https://cloud.tinymce.com/5/tinymce.min.js?apiKey=n6pc28z5n87xrwjz2invt1y20ws32djsc2jyd67as953ymf6"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <script>
        var mainArea = {
            selector: '#mytextarea',
            height: 700,
            plugins: [
                "advlist autolink lists link image charmap print preview anchor",
                "searchreplace visualblocks code fullscreen",
                "insertdatetime media table paste imagetools wordcount checklist"
            ],
            content_css: [
                '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
                '//www.tiny.cloud/css/codepen.min.css'
            ],
            mobile: {
                theme: 'mobile',
                plugins: ['autosave', 'lists', 'autolink'],
                toolbar: ['undo', 'bold', 'italic', 'styleselect']
            },
            toolbar: ' insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | checklist',
        };

        tinymce.init(mainArea);
    </script>
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
</head>
<body>

<tag:navbar/>

<main role="main" class="container-fluid">

    <div class="row justify-content-md-center">
        <div class="col-sm-9">
            <div id="alert-message">

            </div>
            <div class="py-2">
                <h2>Add conference form</h2>
            </div>

            <div class="card shadow-lg rounded-lg">
                <textarea id="mytextarea" name="mytextarea">
                    <h1 style="text-align: center;">9<sup>th</sup>&nbsp;International Colloids Conference</h1>
                    <p><img style="display: block; margin-left: auto; margin-right: auto;" src="https://premc.org/wp-content/uploads/2019/05/ICMF_2019_Banner.jpg" width="643" height="127" /></p>
                    <p>16-19 June 2019 | Melia Hotel, Sitges, Barcelona, Spain</p>
                    <p>Colloid and Interface Science is the foundation for today&rsquo;s advanced material science and engineering, nano-science, soft matter science and catalysis. As such, Colloid and Interface Sciences underpin new advances in energy efficiency, generation and storage, nano-medicine and drug delivery, sensing and diagnostics, amongst other applications.</p>
                    <p>This meeting aims to attract international researchers to communicate and share the latest developments in these dynamic and ever-expanding fields.</p>
                    <p>The conference will be held in the&nbsp;<a href="https://www.elsevier.com/events/conferences/international-colloids-conference/location">popular and attractive seaside resort of Sitges</a>, near Barcelona. Our conference venue provides first class facilities and offers Attendees many opportunities to meet and learn from each other, to forge new scientific relationships and international collaborations.</p>
                    <h3>Sections:</h3>
                    <ul class="tox-checklist">
                    <li class="tox-checklist--checked">Advanced functional polymers, surfactants, gels, biocolloids and soft matter systems</li>
                    <li class="tox-checklist--checked">Engineered functional interfaces, surfaces, films, membranes and composites</li>
                    <li class="tox-checklist--checked">Materials for catalysis, separations, energy generation and storage</li>
                    <li class="tox-checklist--checked">Bio materials, nano-medicines and diagnostics</li>
                    <li class="tox-checklist--checked">New theory, novel phenomena and advanced experimental technique</li>
                    </ul>
                    <h2>Reasons to attend:</h2>
                    <h3>High quality presentations</h3>
                    <p>Our attendees continuously rate the quality of the presentations among the highest in the field.</p>
                    <h3>Gender policy</h3>
                    <p>The conference series has developed and published a&nbsp;<a href="https://www.elsevier.com/editors-update/story/publishing-trends/why-gender-balance-at-conferences-should-become-the-new-normal" target="_blank" rel="noopener">gender equality policy</a>.</p>
                    <h3>Young Investigator Perspectives</h3>
                    <p>Oral slots are available specifically for Young Investigators (PhD students or Post-docs). Young Investigator Perspectives talks should include clear scientific justifications for the research, new results and explanations of how the research has advanced the field compared to existing literature. There will be opportunities for these Presenters to contribute Young Investigator Perspectives articles to the&nbsp;<em><a href="https://www.elsevier.com/events/conferences/international-colloids-conference/about/supporting-publication">Journal of Colloid and Interface Science</a></em>, in consultation with the Editors.</p>
                    <h3>Win the best poster prize</h3>
                    <p>The best posters will be selected by the Scientific Committee: prize winners will receive a certificate and free registration to the 2020 conference.</p>
                    <h3>Post-conference publication</h3>
                    <p>After the conference selected articles will be published in the&nbsp;<em><a href="https://www.elsevier.com/events/conferences/international-colloids-conference/about/supporting-publication">Journal of Colloid and Interface Science</a></em>, in consultation with the Editors and subject to the journal&rsquo;s peer review processes.</p>
                </textarea>
            </div>

            <div class="d-flex justify-content-between">
                <button type="button"
                        class="btn btn-primary btn-lg mt-3 w-50 mx-auto shadow-lg"
                        id="nextStepButton">
                    Next step
                </button>
            </div>
        </div>
    </div>
</main>


<style>
    .shadow-textarea textarea.form-control::placeholder {
        font-weight: 300;
    }

    .shadow-textarea textarea.form-control {
        padding-left: 0.8rem;
    }
</style>
<div class="modal fade" id="previewModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Preview</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">
                <div class="row justify-content-md-center">
                    <div class="col-sm-8 shadow-lg rounded-lg p-5">
                        <div id="data-container"></div>
                        <button class="btn btn-primary mb-3" disabled>Go somewhere</button>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" id="addConferenceButton">Save
                    changes
                </button>
            </div>
        </div>
    </div>
</div>

<script>

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
                $('#previewModal').modal('hide');
                createAlertWithTextAndType(text, 'alert-success')
                $('#alert-message').append("<div class=\"alert alert-success alert-dismissible fade show shadow\" role=\"alert\">\n" +
                    "  <strong>"+text+"!</strong> Aww yeah, you successfully read this important alert message. \n" +
                    "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "    <span aria-hidden=\"true\">&times;</span>\n" +
                    "  </button>\n" +
                    "</div>");
                $(window).scrollTop(0);
            });
        event.preventDefault();
    });

</script>

<tag:footer/>

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
