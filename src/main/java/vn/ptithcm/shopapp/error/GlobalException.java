package vn.ptithcm.shopapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.ptithcm.shopapp.model.response.RestResponse;


import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = {
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            IdInvalidException.class,
            StorageException.class,
            Exception.class
    })
    public ResponseEntity<RestResponse<Object>> handleUsernameNotFoundException(Exception ex){
        RestResponse<Object> res = new RestResponse<>();
        res.setMessage(ex.getMessage());
        res.setError("Exception occur ....");
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        RestResponse<Object> res = new RestResponse<>();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(it -> it.getDefaultMessage())
                .collect(Collectors.toList());

        res.setMessage(errors.size() > 1 ? errors : errors.get(0));
//        res.setError(HttpStatus.BAD_REQUEST.name());
        res.setError(ex.getBody().getDetail());
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(UserNoLongerException.class)
    public ResponseEntity<RestResponse<Object>> handleUserNoLonerException(UserNoLongerException ex){
        RestResponse<Object> res = new RestResponse<>();
        res.setMessage("Exception occur ....");
        res.setError(ex.getMessage());
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestResponse<Object>> handleAccessDeniedException(AccessDeniedException ex){
        RestResponse<Object> res = new RestResponse<>();
        res.setMessage("Exception occur ....");
        res.setError(ex.getMessage());
        res.setStatusCode(HttpStatus.FORBIDDEN.value());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<RestResponse<Object>> handleOutOfStockException(OutOfStockException ex){
        RestResponse<Object> res = new RestResponse<>();
        res.setMessage("Exception occur ....");
        res.setError(ex.getMessage());
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setData(ex.getOutOfStockItems());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex){
        RestResponse<Object> res = new RestResponse<>();
        res.setMessage(ex.getMessage());
        res.setError("Exception occur ....");
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

}
