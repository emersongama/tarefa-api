package com.cesmac.tarefa.api.resource;

import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.service.AlunoService;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.cesmac.tarefa.api.shared.Constantes.URI.URI_ALUNO;

@RestController
@RequestMapping(URI_ALUNO)
@RequiredArgsConstructor
public class AlunoResource {

    private final AlunoService alunoService;

    @PostMapping
    public ResponseEntity<Aluno> salvar(@Valid @RequestBody AlunoDTO dto) throws URISyntaxException {
        Aluno alunoSalvo = this.alunoService.salvar(dto);
        return ResponseEntity.created(new URI(URI_ALUNO + "/" + alunoSalvo.getId()))
                .body(alunoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> alterar(@Valid @RequestBody AlunoDTO dto, @PathVariable Long id) {
        Aluno alunoAlterado = this.alunoService.alterar(dto, id);
        return ResponseEntity.ok(alunoAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        this.alunoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> consultarTodas() {
        return ResponseEntity.ok(this.alunoService.consultarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(this.alunoService.buscar(id));
    }
}