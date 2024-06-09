package org.example.exceptions;

import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import org.example.dto.ErrorMessage;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorContent(exception.getMessage());
        errorMessage.setErrorCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        errorMessage.setDocumentationUrl("https://example.com/error");

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .build();
    }
}

