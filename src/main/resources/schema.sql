DROP TABLE cursos IF EXISTS;

CREATE TABLE cursos(id INTEGER PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(100));

CREATE TABLE cursos_usuarios(id INTEGER PRIMARY KEY AUTO_INCREMENT, usuario_id INTEGER, curso_id INTEGER );

ALTER TABLE cursos_usuarios ADD CONSTRAINT abc UNIQUE (usuario_id);
ALTER TABLE cursos_usuarios ADD CONSTRAINT def FOREIGN KEY (curso_id) REFERENCES cursos(id);

--CREATE TABLE cursos(id SERIAL PRIMARY KEY, nombre TEXT);
--CREATE TABLE cursos_usuarios(id SERIAL PRIMARY KEY, usuario_id INT, curso_id INT );
