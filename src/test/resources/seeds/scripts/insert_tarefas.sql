INSERT INTO public.tarefa (id, titulo, descricao, data_hora_conclusao, data_hora_exclusao, data_hora_ultima_alteracao,
                           id_grupo)
VALUES (1, 'Tarefa 1', 'Desc Teste 1', null, NULL, '2025-06-06 15:04:57.606', 1),
       (2, 'Tarefa 2 - Concluido', 'Desc Teste 2', '2024-06-06 15:05:01.468', NULL, '2024-06-06 15:06:30.062', 1),
       (3, 'Tarefa 3 - Excluida', 'Desc Teste 3', NULL, '2024-06-06 15:05:01.468', '2024-06-06 15:06:30.062', 1);
