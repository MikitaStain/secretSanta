create table if not exists application.messages
(
    id bigserial primary key,
    text_message varchar(500) not null,
    time_message timestamp with time zone not null,
    type_message varchar(255) not null,
    id_account bigint
        references application.accounts(id) not null
)