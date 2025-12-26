package br.com.barberbackend.empresas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.util.StringUtils;

public record NovoEnderecoRequest(
    @NotBlank String logradouro,
    @NotBlank String numero,
    String complemento,
    @NotBlank String bairro,
    @NotBlank String cidade,
    @NotBlank @Size(max = 2) String estado,
    @NotBlank String cep) {
  public Endereco toModel() {
    var endereco = new Endereco(logradouro, numero, bairro, cidade, estado, cep);
    if (StringUtils.hasText(complemento)) {
      endereco.adicionaComplemento(complemento);
    }
    return endereco;
  }
}
