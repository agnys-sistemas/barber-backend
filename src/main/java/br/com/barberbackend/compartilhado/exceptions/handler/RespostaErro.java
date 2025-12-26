package br.com.barberbackend.compartilhado.exceptions.handler;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO padrão para respostas de erro da API.
 *
 * @param timestamp Data e hora em que o erro ocorreu
 * @param status Código HTTP do erro
 * @param erro Tipo do erro
 * @param mensagem Mensagem principal do erro
 * @param caminho Caminho da requisição que causou o erro
 * @param detalhes Lista de detalhes adicionais (opcional, para erros de validação)
 */
public record RespostaErro(
    LocalDateTime timestamp,
    Integer status,
    String erro,
    String mensagem,
    String caminho,
    List<String> detalhes) {
  public RespostaErro {
    if (timestamp == null) {
      timestamp = LocalDateTime.now();
    }
  }
}
