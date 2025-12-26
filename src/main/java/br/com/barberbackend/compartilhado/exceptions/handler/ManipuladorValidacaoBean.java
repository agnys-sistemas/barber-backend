package br.com.barberbackend.compartilhado.exceptions.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Manipulador específico para erros de validação do Bean Validation.
 */
public class ManipuladorValidacaoBean {

    private static final Logger logger = LoggerFactory.getLogger(ManipuladorValidacaoBean.class);

    /**
     * Manipula erros de validação de argumentos do método (@Valid).
     * 
     * @param ex Exceção de validação de argumentos
     * @param caminho Caminho da requisição
     * @return Resposta de erro formatada
     */
    public static RespostaErro tratarMethodArgumentNotValid(MethodArgumentNotValidException ex, String caminho) {
        List<String> detalhes = new ArrayList<>();
        
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String nomeCampo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            detalhes.add(nomeCampo + ": " + mensagem);
        });
        
        logger.info("Tratando MethodArgumentNotValidException no caminho {} com {} erro(s) de validação", 
            caminho, detalhes.size());
        
        return new RespostaErro(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de Validação",
            "Dados inválidos fornecidos na requisição",
            caminho,
            detalhes
        );
    }

    /**
     * Manipula erros de validação de binding.
     * 
     * @param ex Exceção de binding
     * @param caminho Caminho da requisição
     * @return Resposta de erro formatada
     */
    public static RespostaErro tratarBindException(BindException ex, String caminho) {
        List<String> detalhes = new ArrayList<>();
        
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String nomeCampo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            detalhes.add(nomeCampo + ": " + mensagem);
        });
        
        logger.info("Tratando BindException no caminho {} com {} erro(s) de validação", 
            caminho, detalhes.size());
        
        return new RespostaErro(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de Validação",
            "Erro ao processar os dados da requisição",
            caminho,
            detalhes
        );
    }

    /**
     * Manipula erros de violação de constraints.
     * 
     * @param ex Exceção de violação de constraint
     * @param caminho Caminho da requisição
     * @return Resposta de erro formatada
     */
    public static RespostaErro tratarConstraintViolation(ConstraintViolationException ex, String caminho) {
        List<String> detalhes = new ArrayList<>();
        Set<ConstraintViolation<?>> violacoes = ex.getConstraintViolations();
        
        violacoes.forEach(violacao -> {
            String propriedade = violacao.getPropertyPath().toString();
            String mensagem = violacao.getMessage();
            detalhes.add(propriedade + ": " + mensagem);
        });
        
        logger.info("Tratando ConstraintViolationException no caminho {} com {} violação(ões) de constraint", 
            caminho, detalhes.size());
        
        return new RespostaErro(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Erro de Validação",
            "Violação de constraints de validação",
            caminho,
            detalhes
        );
    }
}

