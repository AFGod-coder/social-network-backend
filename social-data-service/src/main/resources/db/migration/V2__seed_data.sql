-- V2__seed_data.sql
INSERT INTO social.users (first_name, last_name, alias, email, date_of_birth)
VALUES
    ('Admin', 'User', 'admin', 'admin@example.com', '1990-01-01')
    ON CONFLICT (email) DO NOTHING;

INSERT INTO social.posts (message, author_id)
VALUES
    ('Primer post de admin', 1)
    ON CONFLICT DO NOTHING;
