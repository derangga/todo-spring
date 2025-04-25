CREATE TABLE users (
    id          BIGSERIAL       PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    email       VARCHAR(255)    UNIQUE NOT NULL,
    password    TEXT            NOT NULL,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    deleted_at  TIMESTAMPTZ     NULL
);

CREATE INDEX idx_user_email ON users(email) WHERE deleted_at IS NULL;
