drop table if exists ccr_notification_read;

/*==============================================================*/
/* Table: ccr_notification_read                                 */
/*==============================================================*/
create table ccr_notification_read (
   read_id              SERIAL8              not null,
   info_id              INT4                 null,
   user_code            VARCHAR(255)         null,
   read_time            TIMESTAMP            null,
   order_id             INT8                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_CCR_NOTIFICATION_READ primary key (read_id)
);

comment on table ccr_notification_read is
'通知已读记录：ccr_notification_read';

comment on column ccr_notification_read.read_id is
'记录ID：read_id';

comment on column ccr_notification_read.info_id is
'信息ID：info_id';

comment on column ccr_notification_read.user_code is
'用户账号：user_code';

comment on column ccr_notification_read.read_time is
'阅读时间：read_time';

comment on column ccr_notification_read.order_id is
'排序：order_id';

comment on column ccr_notification_read.memo is
'备注：memo';

alter table ccr_notification_read
   add constraint FK_CCR_NOTIFY_READ_REF_NOTIFY foreign key (info_id)
      references ccr_notification (info_id)
      on delete cascade on update cascade;
