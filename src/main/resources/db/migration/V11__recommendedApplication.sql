drop table if exists ccr_recommended_application;
create table ccr_recommended_application (
   app_id               VARCHAR(50)          not null,
   app_name             VARCHAR(50)          null,
   app_url              VARCHAR(1024)        null,
   start_time           TIMESTAMP            null,
   end_time             TIMESTAMP            null,
   app_logo             TEXT                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_CCR_RECOMMENDED_APPLICATION primary key (app_id)
);

comment on table ccr_recommended_application is
'推荐应用信息：ccr_recommended_application';

comment on column ccr_recommended_application.app_id is
'应用ID：app_id';

comment on column ccr_recommended_application.app_name is
'应用名称：app_name';

comment on column ccr_recommended_application.app_url is
'应用地址：app_url';

comment on column ccr_recommended_application.start_time is
'推荐开始时间：start_time';

comment on column ccr_recommended_application.end_time is
'推荐结束时间：end_time';

comment on column ccr_recommended_application.app_logo is
'应用主题图片：app_logo';

comment on column ccr_recommended_application.order_id is
'排序：order_id';

comment on column ccr_recommended_application.memo is
'备注：memo';