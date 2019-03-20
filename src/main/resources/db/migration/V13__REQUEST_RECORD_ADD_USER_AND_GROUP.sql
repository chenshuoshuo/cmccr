/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2019-3-20 14:51:41                           */
/* Version:        1.1.2                                        */
/* Description:    1.访问记录增加访问用户、用户组信息           */
/* Description:    用于统计                                     */
/* Author:         Ren Yong                                     */
/*==============================================================*/

--增加字段
ALTER TABLE ccr_request_record
  ADD COLUMN user_code VARCHAR ( 255 ) ,
  ADD COLUMN user_group VARCHAR ( 255 );

COMMENT ON COLUMN ccr_request_record.user_code IS '访问用户：user_code';

COMMENT ON COLUMN ccr_request_record.user_group IS '访问用户所属用户组：user_group';
