create table game (id  bigserial not null, created_at timestamp, created_by varchar(255), updated_at timestamp, updated_by varchar(255), compressed_moves text, date timestamp, location varchar(255), number_of_moves integer, winning_side varchar(255) not null, black_player_id bigint not null, game_result_id bigint not null, time_control_id bigint not null, white_player_id bigint not null, primary key (id))
create table game_result (id  bigserial not null, result_type varchar(255), primary key (id))
create table move (id  bigserial not null, description varchar(10) not null, index_in_game integer not null, side varchar(10) not null, game_id bigint not null, primary key (id))
create table position (id  bigserial not null, description bytea not null, hash varchar(255) not null, game_id bigint not null, move_id bigint not null, primary key (id))
create table time_control (id  bigserial not null, game_time_in_seconds integer not null, game_type varchar(10) not null, increment_per_move_in_seconds integer not null, name varchar(10) not null, primary key (id))
create table user (id  bigserial not null, created_at timestamp, created_by varchar(255), updated_at timestamp, updated_by varchar(255), email varchar(100) not null, first_name varchar(100) not null, last_name varchar(100) not null, username varchar(100) not null, user_detail_id bigint not null, primary key (id))
create table user_detail (id  bigserial not null, created_at timestamp, created_by varchar(255), updated_at timestamp, updated_by varchar(255), about text, rating integer, primary key (id))
create index idx_game_players on game (white_player_id, black_player_id)
create index idx_game_time_control_id on game (time_control_id)
create index idx_game_game_result_id on game (game_result_id)
create index idx_move_game_id on move (game_id)
create index idx_position_description on position (description)
create index idx_position_hash on position (hash)
create index idx_position_game_id on position (game_id)
create index idx_position_move_id on position (move_id)
alter table if exists time_control add constraint unique_time_control_game_setup unique (game_type, game_time_in_seconds, increment_per_move_in_seconds)
alter table if exists time_control add constraint unique_time_control_name unique (name)
create index idx_user_name on user (first_name, last_name, username)
create index idx_user_user_detail_id on user (user_detail_id)
alter table if exists user add constraint unique_user_username unique (username)
alter table if exists user add constraint idx_user_email unique (email)
create index idx_user_detail_rating on user_detail (rating)
alter table if exists game add constraint fk__game_black__game_result foreign key (black_player_id) references user
alter table if exists game add constraint fk__game__game_result foreign key (game_result_id) references game_result
alter table if exists game add constraint fk__game__time_control foreign key (time_control_id) references time_control
alter table if exists game add constraint fk__game_white__user foreign key (white_player_id) references user
alter table if exists move add constraint fk__move__game foreign key (game_id) references game
alter table if exists position add constraint fk__position__game foreign key (game_id) references game
alter table if exists position add constraint fk__position__move foreign key (move_id) references move
alter table if exists user add constraint fk__user__user_detail foreign key (user_detail_id) references user_detail
