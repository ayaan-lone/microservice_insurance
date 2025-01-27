-- Table: public.insurance

-- DROP TABLE IF EXISTS public.insurance;

CREATE TABLE IF NOT EXISTS public.insurance
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    amount double precision NOT NULL,
    is_issued boolean NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT insurance_pkey PRIMARY KEY (id),
    CONSTRAINT ukerrqpou9u3x4k1schpdvw55q0 UNIQUE (user_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.insurance
    OWNER to postgres;