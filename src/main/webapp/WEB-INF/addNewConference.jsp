<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmr" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title><fmt:message key="add.title"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="https://cloud.tinymce.com/5/tinymce.min.js?apiKey=n6pc28z5n87xrwjz2invt1y20ws32djsc2jyd67as953ymf6"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="../js/common/changelocale.js"></script>
    <script src="../js/tinymceCreatorConfig.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/checkList.css">
</head>
<body>

<tag:navbar/>
<main role="main" class="container-fluid">

        <div class="w-75 mx-auto">
            <div id="alert-message">
            </div>
            <div class="py-2">
                <h2><fmt:message key="add.confForm"/></h2>
            </div>

            <div class="card shadow-lg rounded-lg">
                <textarea id="mytextarea" name="mytextarea">
                    <h1 style="text-align: center;">9<sup>th</sup>&nbsp;International Colloids Conference</h1>
                    <p><img style="display: block; margin-left: auto; margin-right: auto;" src="https://rci.bsu.by/images/news/150.jpg" alt="" width="631" height="304" /></p>
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
                    <fmt:message key="add.nextStep"/>
                </button>
            </div>
        </div>
</main>

<div class="modal fade" id="previewModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle"><fmt:message key="add.preview"/></h5>
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
<tag:footer/>
<script src="../js/contentCreator.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
