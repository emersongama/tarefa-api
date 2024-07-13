CREATE TABLE alunos_grupos
(
    id       serial NOT NULL,
    id_aluno int4   NOT NULL,
    id_grupo int4   NOT NULL,
    CONSTRAINT alunos_grupos_pk PRIMARY KEY (id),
    CONSTRAINT alunos_grupos_aluno_fk FOREIGN KEY (id_aluno) REFERENCES aluno (id),
    CONSTRAINT alunos_grupos_grupo_fk FOREIGN KEY (id_grupo) REFERENCES grupo (id)
);
