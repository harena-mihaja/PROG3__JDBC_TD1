INSERT INTO product (id, name, price, creation_datetime) VALUES
(1, 'Laptop Dell XPS', 4500.00, '2024-01-15T09:30:00Z'),
(2, 'iPhone 13', 5200.00, '2024-02-01T14:10:00Z'),
(3, 'Casque Sonny WH1000', 890.50, '2024-02-10T16:45:00Z'),
(4, 'Clavier Logitech', 180.00, '2024-03-05T11:20:00Z'),
(5, 'Écran Samsung 27"', 1200.00, '2024-03-18T08:00:00Z');

INSERT INTO product_category (id, name, product_id) VALUES
(1, 'Informatique', 1),
(2, 'Téléphonie', 2),
(3, 'Audio', 3),
(4, 'Accessoires', 1),
(5, 'Informatique', 5),
(6, 'Bureau', 5),
(7, 'Mobile', 2);
