package br.com.barberbackend.empresas;

import br.com.barberbackend.compartilhado.validacao.CPFOuCNPJ;
import br.com.barberbackend.compartilhado.validacao.Telefone;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.Instant;
import java.util.Objects;
import org.hibernate.validator.constraints.URL;
import org.springframework.util.Assert;

@Entity
@Table(name = "empresas")
public class Empresa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank private String nome;
  private String razaoSocial;

  @NotBlank
  @CPFOuCNPJ
  @Column(unique = true)
  @Pattern(regexp = "(\\d{11}|\\d{14})")
  private String documento;

  @Column(unique = true)
  @NotBlank
  @Email
  private String email;

  @NotBlank @Telefone private String telefone;
  @Embedded private Endereco endereco;
  @URL private String site;

  private final Instant dataCriacao = Instant.now();

  @Deprecated
  /**
   * @deprecated Construtor padrão para uso do JPA
   */
  public Empresa() {}

  public Empresa(String nome, String documento, String email, String telefone, Endereco endereco) {
    Assert.hasText(nome, "Nome é obrigatório");
    Assert.hasText(documento, "Documento é obrigatório");
    Assert.hasText(email, "Email é obrigatório");
    Assert.hasText(telefone, "Telefone é obrigatório");
    Assert.notNull(endereco, "Endereço é obrigatório");

    this.nome = nome;
    this.documento = documento;
    this.email = email;
    this.telefone = telefone;
    this.endereco = endereco;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Empresa empresa = (Empresa) o;
    return Objects.equals(documento, empresa.documento);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(documento);
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getRazaoSocial() {
    return razaoSocial;
  }

  public String getDocumento() {
    return documento;
  }

  public String getEmail() {
    return email;
  }

  public String getTelefone() {
    return telefone;
  }

  public Endereco getEndereco() {
    return endereco;
  }

  public String getSite() {
    return site;
  }

  public void adicionaRazaoSocial(String razaoSocial) {
    Assert.hasText(razaoSocial, "Razão Social é obrigatória");
    this.razaoSocial = razaoSocial;
  }

  public void adicionaSite(String site) {
    Assert.hasText(site, "Site é obrigatório");
    this.site = site;
  }
}
