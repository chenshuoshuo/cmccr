/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2019-3-21 09:50:06                           */
/* Version:        1.1.3                                        */
/* Description:    1.增加用户更新函数，用于批量更新用户         */
/* Author:         Ren Yong                                     */
/*==============================================================*/

CREATE OR REPLACE FUNCTION fun_ccr_update_usr(IN _usr_code varchar, IN _usr_group varchar)
RETURNS void AS
$BODY$
  DECLARE
    _usr_code_array varchar[]; --用户编码数组
    _usr_group_array varchar[]; --用户组数组

    _user_code varchar; --单个用户编码
    _user_group varchar; --单个用户的用户组
    _exist_user boolean; --用户是否存在
  BEGIN
    select regexp_split_to_array(_usr_code, ',') into _usr_code_array;
    select regexp_split_to_array(_usr_group, ',') into _usr_group_array;

    FOR _index IN REVERSE 1..array_length(_usr_code_array, 1) LOOP
      _user_code = _usr_code_array[_index];
      _user_group = _usr_group_array[_index];

      select count(*) = 1 from ccr_user where user_code = _user_code into _exist_user;
      IF _exist_user = false THEN
        insert into ccr_user (user_id, user_code, user_group, update_time, is_admin)
        values(nextval('ccr_user_user_id_seq'::regclass), _user_code, _user_group, now(), 'f');
      ELSE
        update ccr_user set user_group = _user_group where user_code = _user_code;
      END IF;
    END LOOP;
    RETURN;
  END
$BODY$
LANGUAGE plpgsql;

COMMENT ON FUNCTION fun_ccr_update_usr(_usr_code varchar, _usr_group varchar) IS '用户批量更新函数：fun_ccr_update_usr';