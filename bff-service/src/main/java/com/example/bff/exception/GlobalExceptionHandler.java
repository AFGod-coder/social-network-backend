package com.example.bff.exception;

import com.example.bff.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Recurso no encontrado: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(getCurrentPath())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        log.error("No autorizado: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Unauthorized")
                .message(ex.getMessage())
                .path(getCurrentPath())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponse> handleExternalServiceException(ExternalServiceException ex) {
        log.error("Error en servicio externo: {}", ex.getMessage());
        
        String friendlyMessage = parseFriendlyMessage(ex.getMessage());
        HttpStatus status = determineStatusFromMessage(ex.getMessage());
        
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(friendlyMessage)
                .path(getCurrentPath())
                .build();
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.error("Usuario ya existe: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
                .message(ex.getMessage())
                .path(getCurrentPath())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        log.error("Error de validación: {}", ex.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(getCurrentPath())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Error de validación: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message("Error de validación en los datos de entrada")
                .path(getCurrentPath())
                .validationErrors(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("Error interno del servidor")
                .path(getCurrentPath())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private String getCurrentPath() {
        return "BFF Service";
    }

    private String parseFriendlyMessage(String errorMessage) {
        if (errorMessage.contains("409") && errorMessage.contains("correo electrónico ya existe")) {
            return "El correo electrónico ya está registrado. Por favor, usa otro correo o inicia sesión.";
        }
        if (errorMessage.contains("409") && errorMessage.contains("alias ya existe")) {
            return "El alias ya está en uso. Por favor, elige otro alias.";
        }
        if (errorMessage.contains("401") && errorMessage.contains("Credenciales inválidas")) {
            return "Correo electrónico o contraseña incorrectos.";
        }
        if (errorMessage.contains("401") && errorMessage.contains("Refresh token")) {
            return "Tu sesión ha expirado. Por favor, inicia sesión nuevamente.";
        }
        if (errorMessage.contains("404") && errorMessage.contains("Usuario no encontrado")) {
            return "Usuario no encontrado.";
        }
        if (errorMessage.contains("404") && errorMessage.contains("Post no encontrado")) {
            return "Publicación no encontrada.";
        }
        if (errorMessage.contains("400") && errorMessage.contains("Error de validación")) {
            return "Los datos proporcionados no son válidos. Por favor, revisa la información.";
        }
        return "Ha ocurrido un error. Por favor, intenta nuevamente.";
    }

    private HttpStatus determineStatusFromMessage(String errorMessage) {
        if (errorMessage.contains("409")) {
            return HttpStatus.CONFLICT;
        }
        if (errorMessage.contains("401")) {
            return HttpStatus.UNAUTHORIZED;
        }
        if (errorMessage.contains("404")) {
            return HttpStatus.NOT_FOUND;
        }
        if (errorMessage.contains("400")) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.BAD_GATEWAY;
    }
}