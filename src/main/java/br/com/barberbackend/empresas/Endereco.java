package br.com.barberbackend.empresas;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.util.Assert;

@Embeddable
public class Endereco {
  @NotBlank private String logradouro;
  @NotBlank private String numero;
  private String complemento;
  @NotBlank private String bairro;
  @NotBlank private String cidade;

  @NotBlank
  @Size(max = 2)
  private String estado;

  @NotBlank private String cep;

  @Deprecated
  /**
   * @deprecated Construtor padrão para uso do JPA
   */
  public Endereco() {}

  public Endereco(
      String logradouro, String numero, String bairro, String cidade, String estado, String cep) {
    Assert.hasText(logradouro, "Logradouro é obrigatório");
    Assert.hasText(numero, "Número é obrigatório");
    Assert.hasText(bairro, "Bairro é obrigatório");
    Assert.hasText(cidade, "Cidade é obrigatória");
    Assert.hasText(estado, "Estado é obrigatório");
    Assert.hasText(cep, "CEP é obrigatório");

    this.logradouro = logradouro;
    this.numero = numero;
    this.bairro = bairro;
    this.cidade = cidade;
    this.estado = estado;
    this.cep = cep;
  }

  public void adicionaComplemento(String complemento) {
    Assert.hasText(complemento, "Complemento não pode ser vazio");
    this.complemento = complemento;
  }

  public String getLogradouro() {
    return logradouro;
  }

  public String getNumero() {
    return numero;
  }

  public String getComplemento() {
    return complemento;
  }

  public String getBairro() {
    return bairro;
  }

  public String getCidade() {
    return cidade;
  }

  public String getEstado() {
    return estado;
  }

  public String getCep() {
    return cep;
  }
}
