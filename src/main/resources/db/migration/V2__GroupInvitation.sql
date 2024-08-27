-- src/main/resources/db/migration/V2__GroupInvitation.sql
CREATE TABLE group_invitations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    group_id UUID NOT NULL,
    invited_user_id UUID NOT NULL,
    invited_by_user_id UUID NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    responded_at TIMESTAMP WITH TIME ZONE,
    message TEXT,
    updated_at TIMESTAMP WITH TIME ZONE
);

-- Add foreign key constraints
ALTER TABLE group_invitations
    ADD CONSTRAINT fk_group_invitations_group
    FOREIGN KEY (group_id)
    REFERENCES groups(id);

ALTER TABLE group_invitations
    ADD CONSTRAINT fk_group_invitations_invited_user
    FOREIGN KEY (invited_user_id)
    REFERENCES users(id);

ALTER TABLE group_invitations
    ADD CONSTRAINT fk_group_invitations_invited_by_user
    FOREIGN KEY (invited_by_user_id)
    REFERENCES users(id);

-- Create indexes for foreign keys to improve query performance
CREATE INDEX idx_group_invitations_group_id ON group_invitations(group_id);
CREATE INDEX idx_group_invitations_invited_user_id ON group_invitations(invited_user_id);
CREATE INDEX idx_group_invitations_invited_by_user_id ON group_invitations(invited_by_user_id);

-- Create index on status for quick lookups
CREATE INDEX idx_group_invitations_status ON group_invitations(status);

-- Add a check constraint to ensure status is valid
ALTER TABLE group_invitations
    ADD CONSTRAINT check_group_invitation_status
    CHECK (status IN ('PENDING', 'ACCEPTED', 'DECLINED', 'CANCELLED'));

-- Optional: Add a unique constraint to prevent duplicate invitations
ALTER TABLE group_invitations
    ADD CONSTRAINT uq_group_invitations_group_invited_user
    UNIQUE (group_id, invited_user_id);