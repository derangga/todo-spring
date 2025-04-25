CREATE TABLE todo (
    id          BIGSERIAL PRIMARY KEY,
    title       TEXT        NOT NULL,
    description TEXT        NOT NULL,
    completed   BOOLEAN     NOT NULL DEFAULT FALSE,
    user_id     BIGINT         NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    deleted_at  TIMESTAMPTZ  NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_todo_title ON todo(title) WHERE deleted_at IS NULL;
CREATE INDEX idx_todo_active ON todo(id) WHERE deleted_at IS NULL;
