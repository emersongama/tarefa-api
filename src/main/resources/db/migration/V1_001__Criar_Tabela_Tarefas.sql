CREATE TABLE public.tarefa
(
    id                         serial4   NOT NULL,
    titulo                     varchar   NOT NULL,
    descricao                  varchar   NOT NULL,
    data_hora_conclusao        timestamp NULL,
    data_hora_exclusao         timestamp NULL,
    data_hora_ultima_alteracao timestamp NOT NULL DEFAULT now(),
    CONSTRAINT tarefa_pkey PRIMARY KEY (id)
);