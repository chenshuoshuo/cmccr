/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2019-3-20 09:36:12                           */
/* Version:        1.1.0                                        */
/* Description:    门户(portal)数据库                           */
/* Description:    1.访问记录增加访问月份、日期、小时           */
/* Description:    用于统计                                     */
/* Author:         Ren Yong                                     */
/*==============================================================*/

--增加字段
ALTER TABLE ccr_request_record
  ADD COLUMN create_month VARCHAR ( 7 ) DEFAULT to_char( now(), 'yyyy-mm' ),
  ADD COLUMN create_date VARCHAR ( 10 ) DEFAULT to_char( now(), 'yyyy-mm-dd' ),
  ADD COLUMN create_hour VARCHAR ( 15 ) DEFAULT to_char( now(), 'yyyy-mm-dd hh24' );

COMMENT ON COLUMN ccr_request_record.create_month IS '创建时间月份，用于统计：create_month';

COMMENT ON COLUMN ccr_request_record.create_date IS '创建时间日期，用于统计：create_date';

COMMENT ON COLUMN ccr_request_record.create_hour IS '创建时间小时，用于统计：create_hour';

--修改已有数据的值
UPDATE ccr_request_record
  SET create_month = to_char( create_time, 'yyyy-mm' ),
      create_date = to_char( create_time, 'yyyy-mm-dd' ),
      create_hour = to_char( create_time, 'yyyy-mm-dd hh24' );
