package br.com.barberbackend.compartilhado.exceptions.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Manipulador específico para exceções comuns.
 */
public class ManipuladorExcecoesComuns {

    private static final Logger logger = LoggerFactory.getLogger(ManipuladorExcecoesComuns.class);

    /**
     * Manipula IllegalArgumentException.
     * 
     * @param ex Exceção de argumento ilegal
     * @param caminho Caminho da requisição
     * @return Resposta de erro formatada
     */
    public static RespostaErro tratarIllegalArgument(IllegalArgumentException ex, String caminho) {
        logger.info("Tratando IllegalArgumentException no caminho {}: {}", caminho, ex.getMessage());
        
        return new RespostaErro(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Argumento Inválido",
            ex.getMessage() != null ? ex.getMessage() : "Argumento inválido fornecido",
            caminho,
            null
        );
    }

    /**
     * Manipula IllegalStateException.
     * 
     * @param ex Exceção de estado ilegal
     * @param caminho Caminho da requisição
     * @return Resposta de erro formatada
     */
    public static RespostaErro tratarIllegalState(IllegalStateException ex, String caminho) {
        logger.info("Tratando IllegalStateException no caminho {}: {}", caminho, ex.getMessage());
        
        return new RespostaErro(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Estado Inválido",
            ex.getMessage() != null ? ex.getMessage() : "Estado inválido da operação",
            caminho,
            null
        );
    }

    /**
     * Manipula NullPointerException.
     * 
     * @param ex Exceção de ponteiro nulo
     * @param caminho Caminho da requisição
     * @return Resposta de erro formatada
     */
    public static RespostaErro tratarNullPointer(NullPointerException ex, String caminho) {
        logger.info("Tratando NullPointerException no caminho {}: {}", caminho, ex.getMessage(), ex);
        
        return new RespostaErro(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro Interno",
            "Ocorreu um erro interno no servidor",
            caminho,
            null
        );
    }

    /**
     * Manipula RuntimeException genérica.
     * 
     * @param ex Exceção de runtime
     * @param caminho Caminho da requisição
     * @return Resposta de erro formatada
     */
    public static RespostaErro tratarRuntimeException(RuntimeException ex, String caminho) {
        logger.info("Tratando RuntimeException no caminho {}: {}", caminho, ex.getMessage(), ex);
        
        return new RespostaErro(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro Interno",
            ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro inesperado",
            caminho,
            null
        );
    }

    /**
     * Manipula Exception genérica (fallback).
     * 
     * @param ex Exceção genérica
     * @param caminho Caminho da requisição
     * @return Resposta de erro formatada
     */
    public static RespostaErro tratarException(Exception ex, String caminho) {
        logger.info("Tratando Exception genérica no caminho {}: {}", caminho, ex.getMessage(), ex);
        
        return new RespostaErro(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro Interno",
            "Ocorreu um erro inesperado no servidor",
            caminho,
            null
        );
    }
}

