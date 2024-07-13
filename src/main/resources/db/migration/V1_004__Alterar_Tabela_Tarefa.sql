ALTER TABLE public.tarefa
    ADD id_grupo int4 NOT NULL;
ALTER TABLE public.tarefa
    ADD CONSTRAINT tarefa_grupo_fk FOREIGN KEY (id_grupo) REFERENCES public.grupo (id);
