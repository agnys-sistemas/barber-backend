package br.com.barberbackend.empresas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NovoEnderecoRequestTest {
    @Nested
    class toModelTests {
        private NovoEnderecoRequest criarNovoEnderecoRequest(String complemento) {
            return new NovoEnderecoRequest(
                    "Rua A", "123", complemento, "Centro", "Uberlandia", "MG", "38400-000");
        }

        private NovoEnderecoRequest criarNovoEnderecoRequestComTodosCampos() {
            return new NovoEnderecoRequest(
                    "Rua A", "123", "Apto 101", "Centro", "Uberlandia", "MG", "38400-000");
        }

        @Test
        @DisplayName("Deve converter NovoEnderecoRequest para Endereco corretamente com todos os campos")
        void teste1() {
            var novoEnderecoRequest = criarNovoEnderecoRequestComTodosCampos();
            var endereco = novoEnderecoRequest.toModel();

            assertEquals("Rua A", endereco.getLogradouro());
            assertEquals("123", endereco.getNumero());
            assertEquals("Apto 101", endereco.getComplemento());
            assertEquals("Centro", endereco.getBairro());
            assertEquals("Uberlandia", endereco.getCidade());
            assertEquals("MG", endereco.getEstado());
            assertEquals("38400-000", endereco.getCep());
        }

        @Test
        @DisplayName("Deve converter sem complemento quando for null")
        void teste2() {
            var novoEnderecoRequest = criarNovoEnderecoRequest(null);
            var endereco = novoEnderecoRequest.toModel();

            assertEquals("Rua A", endereco.getLogradouro());
            assertEquals("123", endereco.getNumero());
            assertNull(endereco.getComplemento());
            assertEquals("Centro", endereco.getBairro());
            assertEquals("Uberlandia", endereco.getCidade());
            assertEquals("MG", endereco.getEstado());
            assertEquals("38400-000", endereco.getCep());
        }

        @Test
        @DisplayName("Deve converter sem complemento quando for vazio")
        void teste3() {
            var novoEnderecoRequest = criarNovoEnderecoRequest("");
            var endereco = novoEnderecoRequest.toModel();

            assertEquals("Rua A", endereco.getLogradouro());
            assertEquals("123", endereco.getNumero());
            assertNull(endereco.getComplemento());
            assertEquals("Centro", endereco.getBairro());
            assertEquals("Uberlandia", endereco.getCidade());
            assertEquals("MG", endereco.getEstado());
            assertEquals("38400-000", endereco.getCep());
        }

        @Test
        @DisplayName("Deve converter sem complemento quando for apenas espaços em branco")
        void teste4() {
            var novoEnderecoRequest = criarNovoEnderecoRequest("   ");
            var endereco = novoEnderecoRequest.toModel();

            assertEquals("Rua A", endereco.getLogradouro());
            assertEquals("123", endereco.getNumero());
            assertNull(endereco.getComplemento());
            assertEquals("Centro", endereco.getBairro());
            assertEquals("Uberlandia", endereco.getCidade());
            assertEquals("MG", endereco.getEstado());
            assertEquals("38400-000", endereco.getCep());
        }
    }
}

