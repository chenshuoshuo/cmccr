/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2019-3-22 17:03:55                           */
/* Version:        1.1.6                                        */
/* Description:    1.更新用户批量更新函数                       */
/* Author:         Ren Yong                                     */
/*==============================================================*/

CREATE OR REPLACE FUNCTION fun_ccr_update_usr(_usr_code varchar, _usr_group varchar)
    RETURNS pg_catalog.void AS 
    $BODY$
        DECLARE
            _usr_code_array varchar[]; --用户编码数组
            _usr_group_array varchar[]; --用户组数组
            _array_length int; --数组长度
            
            _user_code varchar; --单个用户编码
            _user_group varchar; --单个用户的用户组
            _exist_user boolean; --用户是否存在
        BEGIN
            select regexp_split_to_array(_usr_code, ',') into _usr_code_array;
            select regexp_split_to_array(_usr_group, ',') into _usr_group_array;
            select array_length(_usr_code_array, 1) into _array_length;
            --raise notice 'array size: %', array_length(_usr_code_array, 1);
            FOR _index IN 1.._array_length LOOP
                _user_code = _usr_code_array[_index];
                _user_group = _usr_group_array[_index];
                
                select count(*) = 1 from ccr_user where user_code = _user_code into _exist_user;
                --raise notice 'exist: %', _exist_user;
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
LANGUAGE plpgsql VOLATILE
COST 100;

COMMENT ON FUNCTION fun_ccr_update_usr(_usr_code varchar, _usr_group varchar) IS '用户批量更新函数：fun_ccr_update_usr';