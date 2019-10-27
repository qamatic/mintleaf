-- <ChangeSet id="create tables" delimiter=";" />




CREATE TABLE categories (
    category_id smallint NOT NULL,
    category_name character varying(15) NOT NULL,
    description text,
    picture bytea
);



-- <ChangeSet id="load categories" delimiter=";" />

INSERT INTO categories VALUES (1, 'Beverages', 'Soft drinks, coffees, teas, beers, and ales', '\x');
INSERT INTO categories VALUES (2, 'Condiments', 'Sweet and savory sauces, relishes, spreads, and seasonings', '\x');
INSERT INTO categories VALUES (3, 'Confections', 'Desserts, candies, and sweet breads', '\x');
INSERT INTO categories VALUES (4, 'Dairy Products', 'Cheeses', '\x');
INSERT INTO categories VALUES (5, 'Grains/Cereals', 'Breads, crackers, pasta, and cereal', '\x');
INSERT INTO categories VALUES (6, 'Organic Veg', 'Organic', '\x');
INSERT INTO categories VALUES (7, 'Produce', 'Dried fruit and bean curd', '\x');




-- <ChangeSet id="book and authors" delimiter=";" />

DROP TABLE IF EXISTS books, authors, testing, images;

CREATE TABLE IF NOT EXISTS authors (
    id serial PRIMARY KEY,
    name VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS books (
    id serial PRIMARY KEY,
    author_id INT references authors(id), title VARCHAR(100)
);


INSERT INTO authors(id, name) VALUES(1, 'Jack London');
INSERT INTO authors(id, name) VALUES(2, 'Honore de Balzac');
INSERT INTO authors(id, name) VALUES(3, 'Lion Feuchtwanger');
INSERT INTO authors(id, name) VALUES(4, 'Emile Zola');
INSERT INTO authors(id, name) VALUES(5, 'Truman Capote');

INSERT INTO books(id, author_id, title) VALUES(1, 1, 'Call of the Wild');
INSERT INTO books(id, author_id, title) VALUES(2, 1, 'Martin Eden');
INSERT INTO books(id, author_id, title) VALUES(3, 2, 'Old Goriot');
INSERT INTO books(id, author_id, title) VALUES(4, 2, 'Cousin Bette');
INSERT INTO books(id, author_id, title) VALUES(5, 3, 'Jew Suess');
INSERT INTO books(id, author_id, title) VALUES(6, 4, 'Nana');
INSERT INTO books(id, author_id, title) VALUES(7, 4, 'The Belly of Paris');
INSERT INTO books(id, author_id, title) VALUES(8, 5, 'In Cold blood');
INSERT INTO books(id, author_id, title) VALUES(9, 5, 'Breakfast at Tiffany');
