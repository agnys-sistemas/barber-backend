package br.com.barberbackend.empresas;

public record NovaEmpresaResponse(
    Long id,
    String nome,
    String documento,
    String email,
    String telefone,
    String razaoSocial,
    NovoEnderecoResponse endereco,
    String site) {
  public NovaEmpresaResponse(Empresa empresa) {
    this(
        empresa.getId(),
        empresa.getNome(),
        empresa.getDocumento(),
        empresa.getEmail(),
        empresa.getTelefone(),
        empresa.getRazaoSocial(),
        new NovoEnderecoResponse(empresa.getEndereco()),
        empresa.getSite());
  }
}
