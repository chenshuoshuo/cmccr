drop table if exists ccr_access_log;

/*==============================================================*/
/* Table: ccr_access_log                                        */
/*==============================================================*/
create table ccr_access_log (
   log_id               SERIAL               not null,
   user_id              VARCHAR(64)          null,
   user_group           VARCHAR(32)          null,
   ip_address           VARCHAR(64)          null,
   log_time             TIMESTAMP            null,
   constraint PK_CCR_ACCESS_LOG primary key (log_id)
);

comment on table ccr_access_log is
'用户访问记录：ccr_access_log';

comment on column ccr_access_log.log_id is
'记录ID：log_id';

comment on column ccr_access_log.user_id is
'访问用户ID：user_id';

comment on column ccr_access_log.user_group is
'访问用户用户组：user_group';

comment on column ccr_access_log.ip_address is
'访问IP地址：ip_address';

comment on column ccr_access_log.log_time is
'访问时间：log_time';
