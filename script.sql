-- Criação do banco de dados
CREATE DATABASE merito;
\c merito; 

-- Criação da tabela tb_pessoas
CREATE TABLE tb_pessoas (
    tb_pessoa_id BIGINT PRIMARY KEY,
    tb_name VARCHAR(100),
    tb_idade INT,
    tb_telefone BIGINT,
    tb_email VARCHAR(100),
    tb_tipo_pessoa INT,
    tb_documento BIGINT
);

-- Criação da tabela users
CREATE TABLE users (
    usu_id INT PRIMARY KEY,
    usu_name VARCHAR,
    usu_pass VARCHAR,
    tb_pessoa_id BIGINT,
    FOREIGN KEY (tb_pessoa_id) REFERENCES tb_pessoas(tb_pessoa_id)
);

-- Criação da tabela tb_combustivel
CREATE TABLE tb_combustivel (
    com_id BIGINT PRIMARY KEY,
    com_nome VARCHAR,
    com_preco NUMERIC
);

-- Criação da tabela tb_bomba
CREATE TABLE tb_bomba (
    bb_id BIGINT PRIMARY KEY,
    bb_combustivel BIGINT,
    FOREIGN KEY (bb_combustivel) REFERENCES tb_combustivel(com_id)
);

-- Criação da tabela tb_mov_pdv
CREATE TABLE tb_mov_pdv (
    mov_id BIGINT PRIMARY KEY,
    mov_bomba BIGINT,
    mov_qtd FLOAT,
    mov_cliente BIGINT,
    mov_obs VARCHAR,
    mov_valor_total FLOAT,
    mov_desc FLOAT,
    mov_time TIMESTAMP,
    mov_valor FLOAT
);

-- Inserção de dados na tabela tb_pessoas
INSERT INTO tb_pessoas (
    tb_pessoa_id, tb_name, tb_idade, tb_telefone, tb_email, tb_tipo_pessoa, tb_documento
) VALUES (
    1, 'admin', 20, 55999999999, 'admin@admin.com', 1, 11111111111
);

-- Inserção de dados na tabela users
INSERT INTO users (
    usu_id, usu_name, usu_pass, tb_pessoa_id
) VALUES (
    1, 'admin', 'ÃÆÏËÐ', 1
);
