package com.cesmac.tarefa.api.resource;

import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.service.TarefaService;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.cesmac.tarefa.api.shared.Constantes.URI.URI_TAREFA;

@RestController
@RequestMapping(URI_TAREFA)
@RequiredArgsConstructor
public class TarefaResource implements Serializable {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Tarefa> salvar(@Valid @RequestBody TarefaDTO dto) throws URISyntaxException {
        Tarefa tarefaSalva = this.tarefaService.salvar(dto);
        return ResponseEntity
                .created(new URI(URI_TAREFA + "/" + tarefaSalva.getId()))
                .body(tarefaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> alterar(@Valid @RequestBody TarefaDTO dto, @PathVariable Long id) {
        Tarefa tarefaAlterada = this.tarefaService.alterar(dto, id);
        return ResponseEntity.ok(tarefaAlterada);
    }

    @PatchMapping("/concluir/{id}")
    public ResponseEntity<Void> concluir(@PathVariable Long id) {
        this.tarefaService.concluir(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        this.tarefaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> consultarTodas() {
        return ResponseEntity.ok(this.tarefaService.consultarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscar(id));
    }
}
