insert into aluno (data_nascimento, nome, endereco, matricula, telefone) values ('2019-01-01', 'Aluno 1', 'Rua 1, do Aluno 1', 'A0000001', '19-11111-1111');
insert into aluno (data_nascimento, nome, endereco, matricula, telefone) values ('2019-01-02', 'Aluno 2', 'Rua 2, do Aluno 2', 'A0000002', '19-22222-2222');
insert into aluno (data_nascimento, nome, endereco, matricula, telefone) values ('2019-01-03', 'Aluno 3', 'Rua 3, do Aluno 3', 'A0000003', '19-33333-3333');

insert into perfil (nome) values('alunos_delete');
insert into perfil (nome) values('alunos_get');
insert into perfil (nome) values('alunos_post');
insert into perfil (nome) values('alunos_put');
insert into perfil (nome) values('perfis_delete');
insert into perfil (nome) values('perfis_get');
insert into perfil (nome) values('perfis_post');
insert into perfil (nome) values('perfis_put');

insert into curso (nome) values('Ciência da Computação');
insert into curso (nome) values('Direito');

insert into professor (data_nascimento, nome, cargo, codigo, salario) values ('1980-01-03', 'Professor 1', 'Cargo 1', 'C0000001', 1000.00);
insert into professor (data_nascimento, nome, cargo, codigo, salario) values ('1990-07-25', 'Professor 2', 'Cargo 2', 'C0000002', 1999.99);
insert into professor (data_nascimento, nome, cargo, codigo, salario) values ('2000-01-03', 'Professor 3', 'Cargo 3', 'C0000003', 1000.00);

insert into disciplina (nome) values('Calculo I');
insert into disciplina (nome) values('Calculo II');
insert into disciplina (nome) values('Ética');

insert into usuario (ativo, bloqueado, data_cadastro, nome, senha) values (1, 0, NOW(), 'A0000001', '$2a$10$HYL3W7b1W2BfbW3ZEJrTke.Fe4GbwoZMqCT.c2C.luutbTIJyryc2');
insert into usuario (ativo, bloqueado, data_cadastro, nome, senha) values (1, 0, NOW(), 'A0000002', '$2a$10$HYL3W7b1W2BfbW3ZEJrTke.Fe4GbwoZMqCT.c2C.luutbTIJyryc2');
insert into usuario (ativo, bloqueado, data_cadastro, nome, senha) values (1, 0, NOW(), 'A0000003', '$2a$10$HYL3W7b1W2BfbW3ZEJrTke.Fe4GbwoZMqCT.c2C.luutbTIJyryc2');

insert into usuario_perfis (usuario_id, perfis_id) values (1, 2);
insert into usuario_perfis (usuario_id, perfis_id) values (2, 2);
insert into usuario_perfis (usuario_id, perfis_id) values (3, 2);