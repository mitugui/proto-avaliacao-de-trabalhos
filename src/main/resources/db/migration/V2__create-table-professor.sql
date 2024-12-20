CREATE TABLE professor (
    usuario_id INT NOT NULL,
    siape INT NOT NULL UNIQUE,

    PRIMARY KEY(usuario_id),

    CONSTRAINT fk_professor_usuario_id
        FOREIGN KEY (usuario_id) REFERENCES usuario(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) engine = InnoDB;