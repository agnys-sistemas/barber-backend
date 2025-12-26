package br.com.barberbackend.empresas;

import br.com.barberbackend.compartilhado.validacao.CPFOuCNPJ;
import br.com.barberbackend.compartilhado.validacao.CampoUnico;
import br.com.barberbackend.compartilhado.validacao.Telefone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.util.StringUtils;

public record NovaEmpresaRequest(
    @NotBlank String nome,
    String razaoSocial,
    @NotBlank
        @CPFOuCNPJ
        @Pattern(regexp = "(\\d{11}|\\d{14})")
        @CampoUnico(classeDominio = Empresa.class, campo = "documento")
        String documento,
    @NotBlank @Email @CampoUnico(classeDominio = Empresa.class, campo = "email") String email,
    @NotBlank @Telefone String telefone,
    @NotNull @Valid NovoEnderecoRequest enderecoRequest,
    @URL String site) {

  public Empresa toModel() {
    var endereco = enderecoRequest.toModel();
    var empresa = new Empresa(nome, documento, email, telefone, endereco);
    if (StringUtils.hasText(razaoSocial)) {
      empresa.adicionaRazaoSocial(razaoSocial);
    }
    if (StringUtils.hasText(site)) {
      empresa.adicionaSite(site);
    }
    return empresa;
  }
}
