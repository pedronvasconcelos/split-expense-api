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




-- Create groups table
CREATE TABLE groups (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    owner_id UUID NOT NULL REFERENCES users(id),
    deleted_at TIMESTAMP WITH TIME ZONE,
    finished_at TIMESTAMP WITH TIME ZONE,
    category varchar(50) NOT NULL
);

-- Create expenses table
CREATE TABLE expenses (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    group_id UUID NOT NULL REFERENCES groups(id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    deleted_at TIMESTAMP WITH TIME ZONE
);

-- Create group_members junction table
CREATE TABLE group_members (
    group_id UUID REFERENCES groups(id),
    user_id UUID REFERENCES users(id),
    PRIMARY KEY (group_id, user_id)
);

-- Create expense_participants junction table
CREATE TABLE expense_participants (
    expense_id UUID REFERENCES expenses(id),
    user_id UUID REFERENCES users(id),
    PRIMARY KEY (expense_id, user_id)
);

CREATE TABLE expense_group_entity_user_entity (
  expense_group_entity_id UUID,
  user_entity_id UUID,
  PRIMARY KEY (expense_group_entity_id, user_entity_id),
  FOREIGN KEY (expense_group_entity_id) REFERENCES groups(id),
  FOREIGN KEY (user_entity_id) REFERENCES users(id)
);

-- Add indexes for better query performance
CREATE INDEX idx_groups_owner_id ON groups(owner_id);
CREATE INDEX idx_expenses_group_id ON expenses(group_id);
CREATE INDEX idx_group_members_user_id ON group_members(user_id);
CREATE INDEX idx_expense_participants_user_id ON expense_participants(user_id);