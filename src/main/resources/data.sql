INSERT INTO users(id, email, user_id, first_name, last_name, holidays, user_type, manager_id, team_id)
    VALUES (1, 'user1@gmail.com', 'user1id', 'Stefan', 'Kąkolek', 10, 'EMPLOYEE', 'manager1id', 'team1');
INSERT INTO users(id, email, user_id, first_name, last_name, holidays, user_type, manager_id, team_id)
    VALUES (2, 'user2@gmail.com', 'user2id', 'Stefano', 'Kąkol', 10, 'EMPLOYEE', 'manager2id', 'team2');
INSERT INTO users(id, email, user_id, first_name, last_name, holidays, user_type, manager_id, team_id)
    VALUES (3, 'user3@gmail.com', 'user3id', 'Stefania', 'Kąkolek', 10, 'EMPLOYEE', 'manager1id', 'team1');

INSERT INTO requests(id, approval_date, comment, end_date, owner_id, registration_date, start_date, status, type, user_id)
    VALUES (1, (DATE '2023-02-01'), '', (DATE '2023-02-11'), 'user1id', (DATE '2023-02-01'), (DATE '2023-02-02'), 'PENDING', 'HOLIDAY', 1);
INSERT INTO requests(id, approval_date, comment, end_date, owner_id, registration_date, start_date, status, type, user_id)
    VALUES (2, (DATE '2023-02-01'), '', (DATE '2023-02-11'), 'user2id', (DATE '2023-02-01'), (DATE '2023-02-02'), 'PENDING', 'HOLIDAY', 2);
INSERT INTO requests(id, approval_date, comment, end_date, owner_id, registration_date, start_date, status, type, user_id)
    VALUES (3, (DATE '2023-02-01'), '', (DATE '2023-02-11'), 'user3id', (DATE '2023-02-01'), (DATE '2023-02-02'), 'PENDING', 'HOLIDAY', 3);