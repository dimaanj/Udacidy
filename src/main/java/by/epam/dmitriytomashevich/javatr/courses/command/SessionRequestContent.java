package by.epam.dmitriytomashevich.javatr.courses.command;

import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SessionRequestContent {
    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;
    private boolean validSession = true;
    private ActionType actionType;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String action;

    public SessionRequestContent(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.actionType = request.getMethod().equals(Parameter.METHOD_GET) ? ActionType.FORWARD : ActionType.REDIRECT;
        this.requestAttributes = new HashMap<>();
        this.sessionAttributes = new HashMap<>();
        this.requestParameters = new HashMap<>(request.getParameterMap());
        extractValues();
    }

    private void extractValues() {
        Enumeration<String> requestAttrNames = request.getAttributeNames();
        while(requestAttrNames.hasMoreElements()){
            String name = requestAttrNames.nextElement();
            this.requestAttributes.put(name, request.getAttribute(name));
        }
        HttpSession session = request.getSession(false);
        if(session != null){
            Enumeration<String> sessionNames = session.getAttributeNames();
            while(sessionNames.hasMoreElements()){
                String name = sessionNames.nextElement();
                this.sessionAttributes.put(name, session.getAttribute(name));
            }
        }
    }

    public void insertAttributes() {
        requestAttributes.forEach(this.request::setAttribute);
        sessionAttributes.forEach(this.request.getSession()::setAttribute);
        if(!validSession){
            this.request.getSession().invalidate();
        }
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public ServletContext getServletContext(){
        return request.getServletContext();
    }

    public HttpServletRequest getRequest(){
        return request;
    }

    public String getParameter(String name){
        return requestParameters.get(name) == null ? null : requestParameters.get(name)[0];
    }

    public void setRequestAttribute(String attr, Object value){
        requestAttributes.put(attr, value);
    }

    public void setSessionAttribute(String attr, Object value){
        sessionAttributes.put(attr, value);
    }

    public boolean isValidSession() {
        return validSession;
    }

    public void invalidateSession() {
        this.validSession = false;
    }

    public HttpSession getSession(){
        return this.request.getSession();
    }

    public HttpSession getSession(boolean arg){
        return this.request.getSession(arg);
    }

    public Object getSessionAttributeByName(String arg){
        return sessionAttributes.get(arg);
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public enum ActionType{
        REDIRECT, FORWARD, AJAX_CONTEXT
    }
}
