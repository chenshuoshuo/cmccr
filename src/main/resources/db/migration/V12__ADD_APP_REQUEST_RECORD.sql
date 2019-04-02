/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2019-3-20 13:49:49                           */
/* Version:        1.1.1                                        */
/* Description:    门户(portal)数据库                           */
/* Description:    1.增加应用访问记录表                         */
/* Author:         Ren Yong                                     */
/*==============================================================*/

CREATE TABLE ccr_app_request_record (
    record_id uuid NOT NULL,
    create_time TIMESTAMP,
    app_id int8 NOT NULL,
    PRIMARY KEY ( record_id )
);
COMMENT ON COLUMN ccr_app_request_record.record_id IS '访问记录ID';

COMMENT ON COLUMN ccr_app_request_record.create_time IS '访问时间';

COMMENT ON COLUMN ccr_app_request_record.app_id IS '应用ID';

COMMENT ON TABLE ccr_app_request_record IS '应用访问记录：ccr_app_request_record';

ALTER TABLE ccr_app_request_record
  ADD CONSTRAINT fk_arr_ref_app FOREIGN KEY ( app_id )
    REFERENCES ccr_application ( app_id )
    ON DELETE CASCADE ON UPDATE CASCADE
