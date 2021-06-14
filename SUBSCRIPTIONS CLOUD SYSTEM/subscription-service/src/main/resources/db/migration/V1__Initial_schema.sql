CREATE TABLE subscription (
      id                  BIGSERIAL PRIMARY KEY NOT NULL,
      created_date        bigint NOT NULL,
      last_modified_date  bigint NOT NULL,
      version             integer NOT NULL,
      gender              varchar(255),
      email               varchar(255) UNIQUE NOT NULL,
      flagconsent         float8 NOT NULL,
      dateofbirth         integer,
      firstname           varchar(255)
);