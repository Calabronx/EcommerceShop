DELETE FROM products_tbl;
DELETE FROM cart_tbl;
ALTER TABLE products_tbl DROP id;
ALTER TABLE products_tbl AUTO_INCREMENT = 1;
ALTER TABLE products_tbl ADD id int
UNSIGNED NOT NULL auto_increment primary KEY FIRST;
ALTER TABLE cart_tbl AUTO_INCREMENT = 1;
COMMIT;