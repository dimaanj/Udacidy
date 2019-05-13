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
        position: absolute;
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
        <span class="text-center">
            © Udacidy 2019-2020. Powered by Dmitriy, <a href="#">diaamnj@mail.ru</a>
        </span>
    </div>
</footer>