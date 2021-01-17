alter table if exists chess_game drop constraint if exists fk__game_black__game_result
alter table if exists chess_game drop constraint if exists fk__chess_game__chess_game_result
alter table if exists chess_game drop constraint if exists fk__chess_game__chess_time_control
alter table if exists chess_game drop constraint if exists fk__chess_game__pgn_game
alter table if exists chess_game drop constraint if exists fk__game_white__user
alter table if exists chess_move drop constraint if exists fk__chess_move__chess_game
alter table if exists chess_position drop constraint if exists fk__chess_position__chess_game
alter table if exists chess_position drop constraint if exists fk__chess_position__chess_move
alter table if exists "user" drop constraint if exists fk__user__user_detail
drop table if exists chess_game cascade
drop table if exists chess_game_result cascade
drop table if exists chess_move cascade
drop table if exists chess_position cascade
drop table if exists chess_time_control cascade
drop table if exists pgn_game cascade
drop table if exists "user" cascade
drop table if exists user_detail cascade
