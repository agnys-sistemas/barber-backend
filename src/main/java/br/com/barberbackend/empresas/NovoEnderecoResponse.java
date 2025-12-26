package br.com.barberbackend.empresas;

public record NovoEnderecoResponse(
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String estado,
    String cep) {
  public NovoEnderecoResponse(Endereco endereco) {
    this(
        endereco.getLogradouro(),
        endereco.getNumero(),
        endereco.getComplemento(),
        endereco.getBairro(),
        endereco.getCidade(),
        endereco.getEstado(),
        endereco.getCep());
  }
}
