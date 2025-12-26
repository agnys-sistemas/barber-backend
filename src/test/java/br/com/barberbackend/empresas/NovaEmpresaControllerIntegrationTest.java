package br.com.barberbackend.empresas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.barberbackend.TestcontainersConfiguration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@Transactional
@DisplayName("Testes de Integração - NovaEmpresaController")
class NovaEmpresaControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired private EmpresaRepository empresaRepository;

  @BeforeEach
  void setUp() {
    empresaRepository.deleteAll();
  }

  @Test
  @DisplayName("Deve criar empresa com sucesso, retornar Location header, persistir no banco e retornar todos os dados corretos")
  void teste1() throws Exception {
    var request = criarRequestCompleto();
    var countAntes = empresaRepository.count();

    MvcResult result =
        mockMvc
            .perform(
                post("/empresas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.nome").value("Barbearia X"))
            .andExpect(jsonPath("$.razaoSocial").value("Barbearia X Ltda"))
            .andExpect(jsonPath("$.documento").value("12345678000195"))
            .andExpect(jsonPath("$.email").value("contato@barbeariax.com"))
            .andExpect(jsonPath("$.telefone").value("31999999999"))
            .andExpect(jsonPath("$.site").value("https://www.barbeariax.com"))
            .andExpect(jsonPath("$.endereco.logradouro").value("Rua A"))
            .andExpect(jsonPath("$.endereco.numero").value("123"))
            .andExpect(jsonPath("$.endereco.complemento").value("Apto 101"))
            .andExpect(jsonPath("$.endereco.bairro").value("Centro"))
            .andExpect(jsonPath("$.endereco.cidade").value("Uberlandia"))
            .andExpect(jsonPath("$.endereco.estado").value("MG"))
            .andExpect(jsonPath("$.endereco.cep").value("38400-000"))
            .andReturn();

    // Verificar Location header
    var locationHeader = result.getResponse().getHeader("Location");
    assertThat(locationHeader).isNotNull();
    // Aceita tanto URL completa quanto apenas o path
    assertThat(locationHeader).matches(".*/empresas/\\d+");

    // Verificar persistência no banco
    var responseBody =
        objectMapper.readValue(
            result.getResponse().getContentAsString(), NovaEmpresaResponse.class);
    var countDepois = empresaRepository.count();
    assertThat(countDepois).isEqualTo(countAntes + 1);

    // Verificar dados salvos no banco
    var empresaSalva = empresaRepository.findById(responseBody.id());
    assertThat(empresaSalva).isPresent();
    var empresa = empresaSalva.get();
    assertThat(empresa.getNome()).isEqualTo("Barbearia X");
    assertThat(empresa.getRazaoSocial()).isEqualTo("Barbearia X Ltda");
    assertThat(empresa.getDocumento()).isEqualTo("12345678000195");
    assertThat(empresa.getEmail()).isEqualTo("contato@barbeariax.com");
    assertThat(empresa.getTelefone()).isEqualTo("31999999999");
    assertThat(empresa.getSite()).isEqualTo("https://www.barbeariax.com");
    assertThat(empresa.getEndereco().getLogradouro()).isEqualTo("Rua A");
    assertThat(empresa.getEndereco().getNumero()).isEqualTo("123");
    assertThat(empresa.getEndereco().getComplemento()).isEqualTo("Apto 101");
    assertThat(empresa.getEndereco().getBairro()).isEqualTo("Centro");
    assertThat(empresa.getEndereco().getCidade()).isEqualTo("Uberlandia");
    assertThat(empresa.getEndereco().getEstado()).isEqualTo("MG");
    assertThat(empresa.getEndereco().getCep()).isEqualTo("38400-000");
  }

  @Test
  @DisplayName("Deve retornar erro 400 com múltiplas validações, verificar quantidade de erros, quais campos têm erro e que não salvou no banco")
  void teste2() throws Exception {
    var countAntes = empresaRepository.count();

    // Criar uma empresa válida primeiro para testar unicidade
    var empresaValida = criarRequestBasico();
    mockMvc
        .perform(
            post("/empresas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empresaValida)))
        .andExpect(status().isCreated());

    // Requisição com múltiplos erros de validação
    var requestComErros =
        new NovaEmpresaRequest(
            null, // nome ausente
            null,
            "123", // documento com tamanho inválido
            "email-invalido", // email sem @
            "999", // telefone inválido
            new NovoEnderecoRequest(
                null, // logradouro ausente
                null, // numero ausente
                null,
                null, // bairro ausente
                null, // cidade ausente
                "ABC", // estado com mais de 2 caracteres
                "12345"), // CEP inválido
            "nao-e-url"); // site não é URL

    MvcResult result =
        mockMvc
            .perform(
                post("/empresas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestComErros)))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.erro").value("Erro de Validação"))
            .andExpect(jsonPath("$.detalhes").isArray())
            .andReturn();

    // Verificar que não salvou no banco
    var countDepois = empresaRepository.count();
    assertThat(countDepois).isEqualTo(countAntes + 1); // Apenas a empresa válida criada antes

    // Verificar quantidade e tipos de erros
    var jsonResponse = result.getResponse().getContentAsString();
    assertThat(jsonResponse).isNotNull();
    
    // Verificar quantidade de erros usando JsonNode
    JsonNode jsonNode = objectMapper.readTree(jsonResponse);
    JsonNode detalhesArray = jsonNode.get("detalhes");
    assertThat(detalhesArray).isNotNull();
    assertThat(detalhesArray.isArray()).isTrue();
    assertThat(detalhesArray.size()).isGreaterThanOrEqualTo(8);
    
    // Verificar que todos os campos esperados estão presentes no JSON
    var detalhesStr = jsonResponse.toLowerCase();
    assertThat(detalhesStr).contains("nome");
    assertThat(detalhesStr).contains("documento");
    assertThat(detalhesStr).contains("email");
    assertThat(detalhesStr).contains("telefone");
    assertThat(detalhesStr).contains("logradouro");
    assertThat(detalhesStr).contains("numero");
    assertThat(detalhesStr).contains("bairro");
    assertThat(detalhesStr).contains("cidade");
    assertThat(detalhesStr).contains("estado");
    assertThat(detalhesStr).contains("cep");
    assertThat(detalhesStr).contains("site");

    // Testar duplicação de documento
    var requestDocumentoDuplicado =
        new NovaEmpresaRequest(
            "Barbearia Y",
            null,
            "12345678000195", // Mesmo documento da empresa criada anteriormente
            "outro@email.com",
            "31988888888",
            criarEnderecoPadrao(),
            null);

    result =
        mockMvc
            .perform(
                post("/empresas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDocumentoDuplicado)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.detalhes").isArray())
            .andReturn();

    // Verificar que não salvou no banco
    var countAposDuplicacao = empresaRepository.count();
    assertThat(countAposDuplicacao).isEqualTo(countAntes + 1); // Ainda apenas a primeira empresa

    // Verificar que o erro contém informação sobre documento
    var jsonResponseDocumento = result.getResponse().getContentAsString();
    assertThat(jsonResponseDocumento.toLowerCase()).contains("documento");

    // Testar duplicação de email
    var requestEmailDuplicado =
        new NovaEmpresaRequest(
            "Barbearia Z",
            null,
            "98765432000111", // Documento diferente
            "contato@barbeariax.com", // Mesmo email da empresa criada anteriormente
            "31977777777",
            criarEnderecoPadrao(),
            null);

    result =
        mockMvc
            .perform(
                post("/empresas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestEmailDuplicado)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.detalhes").isArray())
            .andReturn();

    // Verificar que não salvou no banco
    var countAposEmailDuplicado = empresaRepository.count();
    assertThat(countAposEmailDuplicado).isEqualTo(countAntes + 1); // Ainda apenas a primeira empresa

    // Verificar que o erro contém informação sobre email
    var jsonResponseEmail = result.getResponse().getContentAsString();
    assertThat(jsonResponseEmail.toLowerCase()).contains("email");
  }

  // Métodos auxiliares
  private NovaEmpresaRequest criarRequestBasico() {
    return new NovaEmpresaRequest(
        "Barbearia X",
        null,
        "12345678000195",
        "contato@barbeariax.com",
        "31999999999",
        criarEnderecoPadrao(),
        null);
  }

  private NovaEmpresaRequest criarRequestCompleto() {
    return new NovaEmpresaRequest(
        "Barbearia X",
        "Barbearia X Ltda",
        "12345678000195",
        "contato@barbeariax.com",
        "31999999999",
        criarEnderecoComComplemento(),
        "https://www.barbeariax.com");
  }

  private NovoEnderecoRequest criarEnderecoPadrao() {
    return new NovoEnderecoRequest(
        "Rua A", "123", null, "Centro", "Uberlandia", "MG", "38400-000");
  }

  private NovoEnderecoRequest criarEnderecoComComplemento() {
    return new NovoEnderecoRequest(
        "Rua A", "123", "Apto 101", "Centro", "Uberlandia", "MG", "38400-000");
  }
}
