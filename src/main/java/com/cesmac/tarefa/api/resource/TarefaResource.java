package com.cesmac.tarefa.api.resource;

import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.service.TarefaService;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api/tarefa")
@RequiredArgsConstructor
public class TarefaResource implements Serializable {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Tarefa> salvar(@RequestBody TarefaDTO dto) {
        Tarefa tarefaSalva = this.tarefaService.salvar(dto);
        return ResponseEntity.ok(tarefaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> alterar(@RequestBody TarefaDTO dto, @PathVariable(name = "id") Long id) {
        Tarefa tarefaAlterada = this.tarefaService.alterar(dto, id);
        return ResponseEntity.ok(tarefaAlterada);
    }

    @PostMapping("/concluir/{id}")
    public ResponseEntity<Void> concluir(@PathVariable(name = "id") Long id) {
        this.tarefaService.concluir(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable(name = "id") Long id) {
        this.tarefaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> excluir() {
        List<TarefaDTO> tarefas = this.tarefaService.consultarTodas();
        return ResponseEntity.ok(tarefas);
    }
}
