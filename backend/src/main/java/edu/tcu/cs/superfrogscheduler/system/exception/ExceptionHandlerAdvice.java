package edu.tcu.cs.superfrogscheduler.system.exception;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;
import edu.tcu.cs.superfrogscheduler.system.HttpStatusCode;
import edu.tcu.cs.superfrogscheduler.system.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleObjectNotFoundException(ObjectNotFoundException ex){
        return new Result(false, HttpStatusCode.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleAuthenticationException(Exception ex) {
        return new Result(false, HttpStatusCode.UNAUTHORIZED, "username or password is incorrect.", ex.getMessage());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return new Result(false, HttpStatusCode.UNAUTHORIZED, "Login credentials are missing.", ex.getMessage());
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleAccountStatusException(AccountStatusException ex) {
        return new Result(false, HttpStatusCode.UNAUTHORIZED, "User account is abnormal.", ex.getMessage());
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleInvalidBearerTokenException(InvalidBearerTokenException ex) {
        return new Result(false, HttpStatusCode.UNAUTHORIZED, "The access token provided is expired, revoked, malformed, or invalid for other reasons.", ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    Result handleAccessDeniedException(AccessDeniedException ex) {
        return new Result(false, HttpStatusCode.FORBIDDEN, "No permission.", ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new Result(false, HttpStatusCode.NOT_FOUND, "This API endpoint is not found.", ex.getMessage());
    }

    @ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class})
    ResponseEntity<Result> handleRestClientException(HttpStatusCodeException ex) throws JsonProcessingException {

        String exceptionMessage = ex.getMessage();

        // Replace <EOL> with actual newlines.
        exceptionMessage = exceptionMessage.replace("<EOL>", "\n");

        // Extract the JSON part from the string.
        String jsonPart = exceptionMessage.substring(exceptionMessage.indexOf("{"), exceptionMessage.lastIndexOf("}") + 1);

        // Create an ObjectMapper instance.
        ObjectMapper mapper = new ObjectMapper();

        // Parse the JSON string to a JsonNode.
        JsonNode rootNode = mapper.readTree(jsonPart);

        // Extract the message.
        String formattedExceptionMessage = rootNode.path("error").path("message").asText();

        return new ResponseEntity<>(
                new Result(false,
                        ex.getStatusCode().value(),
                        "A rest client error occurs, see data for details.",
                        formattedExceptionMessage),
                ex.getStatusCode());
    }

    /**
     * Fallback handles any unhandled exceptions.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Result handleOtherException(Exception ex) {
        return new Result(false, HttpStatusCode.INTERNAL_SERVER_ERROR, "A server internal error occurs.", ex.getMessage());
    }
}