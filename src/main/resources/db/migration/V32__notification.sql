drop table if exists ccr_notification;

/*==============================================================*/
/* Table: ccr_notification                                      */
/*==============================================================*/
create table ccr_notification (
   info_id              SERIAL               not null,
   title                VARCHAR(255)         null,
   content              VARCHAR(255)         null,
   target_user_role     VARCHAR(64)[]        null,
   specify_user_id      VARCHAR(64)[]        null,
   author_id            VARCHAR(64)          null,
   post_time            TIMESTAMP            null,
   constraint PK_CCR_NOTIFICATION primary key (info_id)
);

comment on table ccr_notification is
'系统通知：ccr_notification';

comment on column ccr_notification.info_id is
'信息ID：info_id';

comment on column ccr_notification.title is
'信息标题：title';

comment on column ccr_notification.content is
'内容：content';

comment on column ccr_notification.target_user_role is
'面向用户角色：target_user_role';

comment on column ccr_notification.specify_user_id is
'指定用户ID：specify_user_id';

comment on column ccr_notification.author_id is
'发布用户：author_id';

comment on column ccr_notification.post_time is
'发布时间：post_time';
