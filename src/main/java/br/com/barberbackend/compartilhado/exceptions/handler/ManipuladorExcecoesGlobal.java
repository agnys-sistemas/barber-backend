package br.com.barberbackend.compartilhado.exceptions.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manipulador global de exceções da aplicação.
 * Centraliza o tratamento de todas as exceções lançadas pelos controllers.
 */
@ControllerAdvice
public class ManipuladorExcecoesGlobal {

    private static final Logger logger = LoggerFactory.getLogger(ManipuladorExcecoesGlobal.class);

    /**
     * Manipula erros de validação de argumentos do método (@Valid).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaErro> tratarMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        
        logger.info("Erro de validação de argumentos na requisição {}: {}", 
            request.getRequestURI(), ex.getMessage());
        
        RespostaErro respostaErro = ManipuladorValidacaoBean.tratarMethodArgumentNotValid(
            ex, 
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaErro);
    }

    /**
     * Manipula erros de validação de binding.
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<RespostaErro> tratarBindException(
            BindException ex,
            HttpServletRequest request) {
        
        logger.info("Erro de binding na requisição {}: {}", 
            request.getRequestURI(), ex.getMessage());
        
        RespostaErro respostaErro = ManipuladorValidacaoBean.tratarBindException(
            ex, 
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaErro);
    }

    /**
     * Manipula erros de violação de constraints.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RespostaErro> tratarConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {
        
        logger.info("Violação de constraint na requisição {}: {}", 
            request.getRequestURI(), ex.getMessage());
        
        RespostaErro respostaErro = ManipuladorValidacaoBean.tratarConstraintViolation(
            ex, 
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaErro);
    }

    /**
     * Manipula IllegalArgumentException.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespostaErro> tratarIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {
        
        logger.info("IllegalArgumentException na requisição {}: {}", 
            request.getRequestURI(), ex.getMessage());
        
        RespostaErro respostaErro = ManipuladorExcecoesComuns.tratarIllegalArgument(
            ex, 
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaErro);
    }

    /**
     * Manipula IllegalStateException.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<RespostaErro> tratarIllegalState(
            IllegalStateException ex,
            HttpServletRequest request) {
        
        logger.info("IllegalStateException na requisição {}: {}", 
            request.getRequestURI(), ex.getMessage());
        
        RespostaErro respostaErro = ManipuladorExcecoesComuns.tratarIllegalState(
            ex, 
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaErro);
    }

    /**
     * Manipula NullPointerException.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<RespostaErro> tratarNullPointer(
            NullPointerException ex,
            HttpServletRequest request) {
        
        logger.info("NullPointerException na requisição {}: {}", 
            request.getRequestURI(), ex.getMessage(), ex);
        
        RespostaErro respostaErro = ManipuladorExcecoesComuns.tratarNullPointer(
            ex, 
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respostaErro);
    }

    /**
     * Manipula RuntimeException genérica.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RespostaErro> tratarRuntimeException(
            RuntimeException ex,
            HttpServletRequest request) {
        
        logger.info("RuntimeException na requisição {}: {}", 
            request.getRequestURI(), ex.getMessage(), ex);
        
        RespostaErro respostaErro = ManipuladorExcecoesComuns.tratarRuntimeException(
            ex, 
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respostaErro);
    }

    /**
     * Manipula Exception genérica (fallback para todas as outras exceções).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErro> tratarException(
            Exception ex,
            HttpServletRequest request) {
        
        logger.info("Exception não tratada na requisição {}: {}", 
            request.getRequestURI(), ex.getMessage(), ex);
        
        RespostaErro respostaErro = ManipuladorExcecoesComuns.tratarException(
            ex, 
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respostaErro);
    }
}

