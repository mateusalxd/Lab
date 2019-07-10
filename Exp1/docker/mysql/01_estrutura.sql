use Exp1;

create table if not exists tbl_produto (
    id_produto integer auto_increment primary key,
    ds_produto varchar(50) not null,
    vl_produto numeric(6,2) not null
)
character set utf8
collate utf8_general_ci;