package br.com.barberbackend.empresas;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/empresas")
public class NovaEmpresaController {

  private final EmpresaRepository empresaRepository;

  @Autowired
  public NovaEmpresaController(EmpresaRepository empresaRepository) {
    this.empresaRepository = empresaRepository;
  }

  @PostMapping
  public ResponseEntity<NovaEmpresaResponse> criarEmpresa(
      @RequestBody @Valid NovaEmpresaRequest request) {
    var novaEmpresa = request.toModel();

    empresaRepository.save(novaEmpresa);

    var response = new NovaEmpresaResponse(novaEmpresa);
    var locationUri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(novaEmpresa.getId())
            .toUri();
    return ResponseEntity.created(locationUri).body(response);
  }
}
