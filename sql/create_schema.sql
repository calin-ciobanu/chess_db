
    create table chess_game (
       id  bigserial not null,
        created_at timestamp,
        created_by varchar(255),
        updated_at timestamp,
        updated_by varchar(255),
        compressed_moves text,
        date timestamp,
        location varchar(255),
        number_of_moves integer,
        black_player_id bigint not null,
        chess_game_result_id bigint not null,
        chess_time_control_id bigint not null,
        pgn_game_id bigint not null,
        white_player_id bigint not null,
        primary key (id)
    )

    create table chess_game_result (
       id  bigserial not null,
        result_type varchar(255),
        primary key (id)
    )

    create table chess_move (
       id  bigserial not null,
        description varchar(10) not null,
        index_in_game integer not null,
        side varchar(10) not null,
        chess_game_id bigint not null,
        primary key (id)
    )

    create table chess_position (
       id  bigserial not null,
        description bytea not null,
        hash varchar(255) not null,
        chess_game_id bigint not null,
        chess_move_id bigint not null,
        primary key (id)
    )

    create table chess_time_control (
       id  bigserial not null,
        game_time_in_seconds integer not null,
        game_type varchar(255) not null,
        increment_per_move_in_seconds integer not null,
        primary key (id)
    )

    create table chess_user (
       id  bigserial not null,
        created_at timestamp,
        created_by varchar(255),
        updated_at timestamp,
        updated_by varchar(255),
        email varchar(100) not null,
        first_name varchar(100) not null,
        last_name varchar(100) not null,
        password varchar(255) not null,
        role varchar(255) not null,
        username varchar(100) not null,
        chess_user_detail_id bigint not null,
        primary key (id)
    )

    create table chess_user_detail (
       id  bigserial not null,
        created_at timestamp,
        created_by varchar(255),
        updated_at timestamp,
        updated_by varchar(255),
        about text,
        rating integer,
        primary key (id)
    )

    create table invalid_session (
       id  bigserial not null,
        created_at timestamp,
        created_by varchar(255),
        updated_at timestamp,
        updated_by varchar(255),
        expires_at int8,
        session_uuid varchar(255),
        username varchar(255),
        primary key (id)
    )

    create table pgn_game (
       id  bigserial not null,
        created_at timestamp,
        created_by varchar(255),
        updated_at timestamp,
        updated_by varchar(255),
        last_name varchar(255) not null,
        moves text not null,
        pgn_text text not null,
        processed boolean,
        primary key (id)
    )
create index idx_chess_game_players on chess_game (white_player_id, black_player_id)
create index idx_chess_game_chess_time_control_id on chess_game (chess_time_control_id)
create index idx_chess_game_chess_game_result_id on chess_game (chess_game_result_id)
create index idx_chess_game_pgn_game_id on chess_game (pgn_game_id)
create index idx_chess_move_chess_game_id on chess_move (chess_game_id)
create index idx_chess_position_description on chess_position (description)
create index idx_chess_position_hash on chess_position (hash)
create index idx_chess_position_game_id on chess_position (chess_game_id)
create index idx_chess_position_move_id on chess_position (chess_move_id)

    alter table if exists chess_time_control 
       add constraint unique_chess_time_control_game_setup unique (game_type, game_time_in_seconds, increment_per_move_in_seconds)
create index idx_chess_user_name on chess_user (first_name, last_name, username)
create index idx_chess_user_chess_user_detail_id on chess_user (chess_user_detail_id)

    alter table if exists chess_user 
       add constraint unique_chess_user_username unique (username)

    alter table if exists chess_user 
       add constraint idx_chess_user_email unique (email)
create index idx_chess_user_detail_rating on chess_user_detail (rating)
create index idx_invalid_session_username_session_uuid on invalid_session (username, session_uuid)
create index idx_pgn_game on pgn_game (last_name, moves)
create index idx_pgn_game_processed on pgn_game (processed)

    alter table if exists chess_game 
       add constraint fk__game_black__game_result 
       foreign key (black_player_id) 
       references chess_user

    alter table if exists chess_game 
       add constraint fk__chess_game__chess_game_result 
       foreign key (chess_game_result_id) 
       references chess_game_result

    alter table if exists chess_game 
       add constraint fk__chess_game__chess_time_control 
       foreign key (chess_time_control_id) 
       references chess_time_control

    alter table if exists chess_game 
       add constraint fk__chess_game__pgn_game 
       foreign key (pgn_game_id) 
       references pgn_game

    alter table if exists chess_game 
       add constraint fk__game_white__user 
       foreign key (white_player_id) 
       references chess_user

    alter table if exists chess_move 
       add constraint fk__chess_move__chess_game 
       foreign key (chess_game_id) 
       references chess_game

    alter table if exists chess_position 
       add constraint fk__chess_position__chess_game 
       foreign key (chess_game_id) 
       references chess_game

    alter table if exists chess_position 
       add constraint fk__chess_position__chess_move 
       foreign key (chess_move_id) 
       references chess_move

    alter table if exists chess_user 
       add constraint fk__chess_user__chess_user_detail 
       foreign key (chess_user_detail_id) 
       references chess_user_detail
