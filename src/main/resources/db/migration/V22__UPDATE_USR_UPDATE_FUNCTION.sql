/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2019-3-21 09:50:06                           */
/* Version:        1.1.3                                        */
/* Description:    1.修改用户更新函数，用于批量更新用户,修改更新操作bug */
/* Author:         Ren Yong &Y Geng Jiaxing                                    */
/*==============================================================*/

CREATE OR REPLACE FUNCTION fun_ccr_update_usr("_usr_code" varchar, "_usr_group" varchar, "_usr_rule" varchar, "_usr_password" varchar)
  RETURNS "pg_catalog"."void" AS $BODY$
        DECLARE
            _usr_code_array varchar[]; --用户编码数组
            _usr_group_array varchar[]; --用户组数组
						_usr_rule_array varchar[]; --用户权限数组
            _array_length int; --数组长度

            _user_code varchar; --单个用户编码
            _user_group varchar; --单个用户的用户组
						_user_rule int8; --单个用户的角色
            _exist_user boolean; --用户是否存在
						_exist_rule boolean; --用户权限是否存在
						_user_id int8; --单个用户主键
        BEGIN
            select regexp_split_to_array(_usr_code, ',') into _usr_code_array;
            select regexp_split_to_array(_usr_group, ',') into _usr_group_array;
						select regexp_split_to_array(_usr_rule, ',') into _usr_rule_array;
            select array_length(_usr_code_array, 1) into _array_length;
            --raise notice 'array size: %', array_length(_usr_code_array, 1);
            FOR _index IN 1.._array_length LOOP
                _user_code = _usr_code_array[_index];
                _user_group = _usr_group_array[_index];
                _user_rule = _usr_rule_array[_index];
                select count(*) = 1 from ccr_user where user_code = _user_code into _exist_user;
                --raise notice 'exist: %', _exist_user;
                IF _exist_user = false THEN
								    _user_id = nextval('ccr_user_user_id_seq'::regclass);
                    insert into ccr_user (user_id, user_code, pass_word, user_group, update_time, is_admin)
                    values(_user_id, _user_code, _usr_password, _user_group, now(), 't');
                ELSE
                    select user_id from ccr_user where user_code = _user_code into _user_id;
                    update ccr_user set user_group = _user_group where user_code = _user_code;
                END IF;

								select count(*) = 1 from ccr_user_to_rule where user_id = _user_id and rule_id = _user_rule into _exist_rule;

								IF _exist_rule = false THEN
                    insert into ccr_user_to_rule (user_id, rule_id)
                    values(_user_id, _user_rule);
                ELSE
                END IF;
            END LOOP;
            RETURN;
            END
    $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  COMMENT ON FUNCTION fun_ccr_update_usr("_usr_code" varchar, "_usr_group" varchar, "_usr_rule" varchar, "_usr_password" varchar) IS '用户批量更新函数：fun_ccr_update_usr';