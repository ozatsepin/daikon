package org.talend.daikon.spring.audit.logs.service;

public interface ResponseExtractor {

    int getStatusCode(Object responseObject);

    Object getResponseBody(Object responseObject);
}
