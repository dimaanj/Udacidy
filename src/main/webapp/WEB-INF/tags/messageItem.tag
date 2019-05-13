<%@ tag description="messageItem" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-md-4">
    <img src="..." class="rounded float-left" alt="...">
</div>
<div class="card mb-4" style="max-width: 540px; max-height: 200px;">
    <div class="row no-gutters">
        <div class="col-md-8">
            <div class="card-body">
                <h5 class="card-title">by ${message.getCreator().getFirstName()} ${message.getCreator().getLastName()}</h5>
                <p class="card-text">last message: ${message.getText()}</p>
            </div>
        </div>
    </div>
</div>
