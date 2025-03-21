
INSERT INTO users (id, role, name, password, username)
VALUES (2, 'ROLE_CLIENT', 'Cliente Teste', '$2a$10$0lczZCG0N9MAdeW7PzX9TuGRbDPgOTo8t8qVTtMBq0v48lAYVWerG', 'clientetest')
    ON DUPLICATE KEY UPDATE id = id;

INSERT INTO appointment (id, date_time, status, client_id) VALUES
                                                               (6, NOW() - INTERVAL 1 DAY, 'CONFIRMADO', 2),
                                                               (7, NOW() - INTERVAL 2 DAY, 'CONFIRMADO', 2),
                                                               (8, NOW() - INTERVAL 3 DAY, 'CONFIRMADO', 2);


INSERT INTO appointment_service (appointment_id, service_id) VALUES
                                                                 (6, 1), (6, 3),
                                                                 (7, 2), (7, 5),
                                                                 (8, 4), (8, 3);
