INSERT INTO contact
       (id, first_name, last_name, phone, email, company)
VALUES
       (1, 'John', 'Dow', '1111', 'email@mail.com', 'aaa'),
       (2, 'James', 'Dow', '2222', 'email2@mail.com', 'bbb'),
       (3, 'John', 'Smith', '3333', 'email3@mail.com', 'ccc'),
       (4, 'James', 'Smith', '4444', 'email4@mail.com', 'ddd');

SELECT setval('hibernate_sequence', 4);