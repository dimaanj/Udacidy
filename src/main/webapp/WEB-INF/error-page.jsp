<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:template>
    <div>
        <h5>
            Request from ${pageContext.errorData.requestURI} is failed
            <br/>
            Servlet name or type: ${pageContext.errorData.servletName}
            <br/>
            Status code: ${pageContext.errorData.statusCode}
            <br/>
            Exception: ${pageContext.errorData.throwable}
        </h5>
    </div>
</tag:template>