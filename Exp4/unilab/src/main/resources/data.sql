insert into perfil (nome) values('*_*');
insert into perfil (nome) values('actuator_*');
insert into perfil (nome) values('alunos_delete');
insert into perfil (nome) values('alunos_get');
insert into perfil (nome) values('alunos_post');
insert into perfil (nome) values('alunos_put');
insert into perfil (nome) values('cursos_delete');
insert into perfil (nome) values('cursos_get');
insert into perfil (nome) values('cursos_post');
insert into perfil (nome) values('cursos_put');
insert into perfil (nome) values('disciplinas_delete');
insert into perfil (nome) values('disciplinas_get');
insert into perfil (nome) values('disciplinas_post');
insert into perfil (nome) values('disciplinas_put');
insert into perfil (nome) values('perfis_delete');
insert into perfil (nome) values('perfis_get');
insert into perfil (nome) values('perfis_post');
insert into perfil (nome) values('perfis_put');
insert into perfil (nome) values('professores_delete');
insert into perfil (nome) values('professores_get');
insert into perfil (nome) values('professores_post');
insert into perfil (nome) values('professores_put');
insert into perfil (nome) values('usuarios_delete');
insert into perfil (nome) values('usuarios_get');
insert into perfil (nome) values('usuarios_post');
insert into perfil (nome) values('usuarios_put');

insert into professor (data_nascimento, nome, cargo, codigo, salario) values ('2019-01-01', 'Administrador', 'Administrador do Sistema', 'admin', 0.0);

insert into usuario (ativo, bloqueado, data_cadastro, nome, senha) values (1, 0, NOW(), 'admin', '$2a$10$HYL3W7b1W2BfbW3ZEJrTke.Fe4GbwoZMqCT.c2C.luutbTIJyryc2');

insert into usuario_perfis (usuario_id, perfis_id) values (1, 1);
