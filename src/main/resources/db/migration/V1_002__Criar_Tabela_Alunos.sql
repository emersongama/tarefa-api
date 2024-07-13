CREATE TABLE public.aluno
(
    id        serial       NOT NULL,
    nome      varchar(100) NOT NULL,
    idade     int4         NOT NULL,
    matricula varchar(6)   NOT NULL,
    genero    char         NOT NULL,
    CONSTRAINT aluno_pk PRIMARY KEY (id)
);
