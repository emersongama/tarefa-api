package com.cesmac.tarefa.api.resource;

import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.entity.Grupo;
import com.cesmac.tarefa.api.service.GrupoService;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;
import com.cesmac.tarefa.api.shared.dto.GrupoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.cesmac.tarefa.api.shared.Constantes.URI.URI_GRUPO;

@RestController
@RequestMapping(URI_GRUPO)
@RequiredArgsConstructor
public class GrupoResource implements Serializable {

    private final GrupoService grupoService;

    @PostMapping
    public ResponseEntity<Grupo> salvar(@Valid @RequestBody GrupoDTO dto)
            throws URISyntaxException {
        Grupo grupoCadastrado = this.grupoService.salvar(dto);
        return ResponseEntity.created(new URI(URI_GRUPO + "/" + grupoCadastrado.getId()))
                .body(grupoCadastrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grupo> alterar(
            @Valid @RequestBody GrupoDTO dto, @PathVariable Long id) {
        Grupo grupoAlterado = this.grupoService.alterar(dto, id);
        return ResponseEntity.ok(grupoAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        this.grupoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> consultarTodas() {
        return ResponseEntity.ok(this.grupoService.consultarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(grupoService.buscar(id));
    }

    @GetMapping("/alunos-grupo/{idGrupo}")
    public ResponseEntity<List<AlunoDTO>> consultarAlunosDoGrupo(@PathVariable Long idGrupo) {
        return ResponseEntity.ok(grupoService.consultarAlunosDoGrupo(idGrupo));
    }
}
