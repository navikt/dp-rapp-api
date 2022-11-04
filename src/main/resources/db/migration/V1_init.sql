CREATE TABLE rapportering
(
    id serial primary key,
    fnr VARCHAR(20),
    periodeFra TIMESTAMP,
    periodeTil TIMESTAMP
)