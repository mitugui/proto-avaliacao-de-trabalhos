CREATE TABLE estudante (
    usuario_id INT NOT NULL,
    curso_id INT,
    ano_ingresso INT NOT NULL,
    matricula VARCHAR(20) UNIQUE NOT NULL,

    PRIMARY KEY(usuario_id),

    CONSTRAINT fk_estudante_usuario_id
        FOREIGN KEY (usuario_id) REFERENCES usuario(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_estudante_curso_id
        FOREIGN KEY (curso_id) REFERENCES curso(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) engine = InnoDB;

CREATE TABLE curso (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL UNIQUE,
    ativo TINYINT DEFAULT 1,

    PRIMARY KEY(id)
) engine = InnoDB;