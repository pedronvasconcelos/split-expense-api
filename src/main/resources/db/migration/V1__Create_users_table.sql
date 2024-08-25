-- src/main/resources/db/migration/V1__Create_users_table.sql

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    birth_date DATE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE,
    deleted_at TIMESTAMP WITH TIME ZONE,
    role VARCHAR(50) NOT NULL
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_users_role ON users(role);

COMMENT ON TABLE users IS 'Table to store user information';
COMMENT ON COLUMN users.id IS 'Unique identifier for the user';
COMMENT ON COLUMN users.first_name IS 'User''s first name';
COMMENT ON COLUMN users.last_name IS 'User''s last name';
COMMENT ON COLUMN users.email IS 'User''s email address (unique)';
COMMENT ON COLUMN users.status IS 'Current status of the user (e.g., ACTIVE, INACTIVE)';
COMMENT ON COLUMN users.birth_date IS 'User''s date of birth';
COMMENT ON COLUMN users.created_at IS 'Timestamp of record creation';
COMMENT ON COLUMN users.updated_at IS 'Timestamp of last record update';
COMMENT ON COLUMN users.deleted_at IS 'Timestamp of logical deletion (if applicable)';
COMMENT ON COLUMN users.role IS 'User''s role in the system (e.g., USER, ADMIN)';