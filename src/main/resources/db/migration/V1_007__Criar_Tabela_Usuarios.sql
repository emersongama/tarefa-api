CREATE TABLE public.usuario
(
    id                     serial4   NOT NULL,
    nome                   varchar   NOT NULL,
    login                  varchar   NOT NULL,
    senha                  varchar   NOT NULL,
    ativo                  boolean   NOT NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (id)
);

INSERT INTO public.usuario(nome, login, senha, ativo) VALUES ('Emerson Gama', 'gama', '$2a$12$DCNEalF32PbWaC8I4VaXDOfeZ.0nqAxeaJisbFvBmA2Yvcf7CVucm', true);