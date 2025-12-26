package br.com.barberbackend.empresas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NovaEmpresaRequestTest {
  @Nested
  class toModelTests {
    private NovoEnderecoRequest criarEnderecoPadrao() {
      return new NovoEnderecoRequest(
              "Rua A", "123", null, "Centro", "Uberlandia", "MG", "38400-000");
    }

    private NovoEnderecoRequest criarEnderecoComComplemento() {
      return new NovoEnderecoRequest(
              "Rua A", "123", "complemento", "CCentro", "Uberlandia", "MG", "38400-000");
    }

    private NovaEmpresaRequest criarNovaEmpresaRequest(
            String razaoSocial, String site) {
      return new NovaEmpresaRequest(
              "Barbearia X",
              razaoSocial,
              "12345678000195",
              "cliente@email.com",
              "31999999999",
              criarEnderecoPadrao(),
              site);
    }

    private NovaEmpresaRequest criarNovaEmpresaRequestComTodosCampos() {
      return new NovaEmpresaRequest(
              "Barbearia X",
              "Barbearia X Ltda",
              "12345678000195",
              "cliente@email.com",
              "31999999999",
              criarEnderecoComComplemento(),
              "https://www.barbeariax.com");
    }

    @Test
    @DisplayName("Deve converter NovaEmpresaRequest para Empresa corretamente com todos os campos")
    void teste1() {
      var novaEmpresaRequest = criarNovaEmpresaRequestComTodosCampos();
      var empresa = novaEmpresaRequest.toModel();

      assertEquals("Barbearia X", empresa.getNome());
      assertEquals("Barbearia X Ltda", empresa.getRazaoSocial());
      assertEquals("12345678000195", empresa.getDocumento());
      assertEquals("cliente@email.com", empresa.getEmail());
      assertEquals("31999999999", empresa.getTelefone());
      assertEquals("https://www.barbeariax.com", empresa.getSite());

      var endereco = empresa.getEndereco();
      assertNotNull(endereco);
      assertEquals("Rua A", endereco.getLogradouro());
      assertEquals("123", endereco.getNumero());
      assertEquals("complemento", endereco.getComplemento());
      assertEquals("CCentro", endereco.getBairro());
      assertEquals("Uberlandia", endereco.getCidade());
      assertEquals("MG", endereco.getEstado());
      assertEquals("38400-000", endereco.getCep());
    }

    @Test
    @DisplayName("Deve converter sem razaoSocial quando for null")
    void teste2() {
      var novaEmpresaRequest = criarNovaEmpresaRequest(null, "https://www.barbeariax.com");
      var empresa = novaEmpresaRequest.toModel();

      assertEquals("Barbearia X", empresa.getNome());
      assertNull(empresa.getRazaoSocial());
      assertEquals("12345678000195", empresa.getDocumento());
      assertEquals("cliente@email.com", empresa.getEmail());
      assertEquals("31999999999", empresa.getTelefone());
      assertEquals("https://www.barbeariax.com", empresa.getSite());
    }

    @Test
    @DisplayName("Deve converter sem razaoSocial quando for vazio")
    void teste3() {
      var novaEmpresaRequest = criarNovaEmpresaRequest("", "https://www.barbeariax.com");
      var empresa = novaEmpresaRequest.toModel();

      assertEquals("Barbearia X", empresa.getNome());
      assertNull(empresa.getRazaoSocial());
      assertEquals("12345678000195", empresa.getDocumento());
      assertEquals("cliente@email.com", empresa.getEmail());
      assertEquals("31999999999", empresa.getTelefone());
    }

    @Test
    @DisplayName("Deve converter sem razaoSocial quando for apenas espaços em branco")
    void teste4() {
      var novaEmpresaRequest = criarNovaEmpresaRequest("   ", "https://www.barbeariax.com");
      var empresa = novaEmpresaRequest.toModel();

      assertEquals("Barbearia X", empresa.getNome());
      assertNull(empresa.getRazaoSocial());
      assertEquals("12345678000195", empresa.getDocumento());
    }

    @Test
    @DisplayName("Deve converter sem site quando for null")
    void teste5() {
      var novaEmpresaRequest = criarNovaEmpresaRequest("Barbearia X Ltda", null);
      var empresa = novaEmpresaRequest.toModel();

      assertEquals("Barbearia X", empresa.getNome());
      assertEquals("Barbearia X Ltda", empresa.getRazaoSocial());
      assertEquals("12345678000195", empresa.getDocumento());
      assertEquals("cliente@email.com", empresa.getEmail());
      assertEquals("31999999999", empresa.getTelefone());
      assertNull(empresa.getSite());
    }

    @Test
    @DisplayName("Deve converter sem site quando for vazio")
    void teste6() {
      var novaEmpresaRequest = criarNovaEmpresaRequest("Barbearia X Ltda", "");
      var empresa = novaEmpresaRequest.toModel();

      assertEquals("Barbearia X", empresa.getNome());
      assertEquals("Barbearia X Ltda", empresa.getRazaoSocial());
      assertEquals("12345678000195", empresa.getDocumento());
      assertNull(empresa.getSite());
    }

    @Test
    @DisplayName("Deve converter sem site quando for apenas espaços em branco")
    void teste7() {
      var novaEmpresaRequest = criarNovaEmpresaRequest("Barbearia X Ltda", "   ");
      var empresa = novaEmpresaRequest.toModel();

      assertEquals("Barbearia X", empresa.getNome());
      assertEquals("Barbearia X Ltda", empresa.getRazaoSocial());
      assertNull(empresa.getSite());
    }

    @Test
    @DisplayName("Deve converter sem razaoSocial e sem site quando ambos forem null")
    void teste8() {
      var novaEmpresaRequest = criarNovaEmpresaRequest(null, null);
      var empresa = novaEmpresaRequest.toModel();

      assertEquals("Barbearia X", empresa.getNome());
      assertNull(empresa.getRazaoSocial());
      assertEquals("12345678000195", empresa.getDocumento());
      assertEquals("cliente@email.com", empresa.getEmail());
      assertEquals("31999999999", empresa.getTelefone());
      assertNull(empresa.getSite());
    }

    @Test
    @DisplayName("Deve converter sem razaoSocial e sem site quando ambos forem vazios")
    void teste9() {
      var novaEmpresaRequest = criarNovaEmpresaRequest("", "");
      var empresa = novaEmpresaRequest.toModel();

      assertEquals("Barbearia X", empresa.getNome());
      assertNull(empresa.getRazaoSocial());
      assertEquals("12345678000195", empresa.getDocumento());
      assertNull(empresa.getSite());
    }
  }
}
