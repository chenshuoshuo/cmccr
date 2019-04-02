/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2019-3-21 10:52:22                           */
/* Version:        1.1.5                                        */
/* Description:    1.增加百度语音API配置                        */
/* Author:         Ren Yong                                     */
/*==============================================================*/

--增加表
CREATE TABLE ccr_baidu_asr_config (
    config_id int4 NOT NULL,
    client_id varchar(255) NOT NULL,
    client_secret varchar(255) NOT NULL,
    access_token varchar(255),
    update_time timestamp,
    invalid_time timestamp,
    PRIMARY KEY (client_id)
);

COMMENT ON COLUMN ccr_baidu_asr_config.config_id IS '配置ID：config_id';

COMMENT ON COLUMN ccr_baidu_asr_config.client_id IS '应用的API KEY：client_id';

COMMENT ON COLUMN ccr_baidu_asr_config.client_secret IS '应用的Secret Key：client_secret';

COMMENT ON COLUMN ccr_baidu_asr_config.access_token IS '应用的access token：access_token';

COMMENT ON COLUMN ccr_baidu_asr_config.update_time IS '更新时间：update_time';

COMMENT ON COLUMN ccr_baidu_asr_config.invalid_time IS '失效时间：invalide_time';

COMMENT ON TABLE ccr_baidu_asr_config IS '百度语音参数配置：ccr_baidu_asr_config';

--初始一条数据
insert into ccr_baidu_asr_config (config_id, client_id, client_secret)
  values (1, 'rza7sAxOtFpN2eKksUSuyaOV', 'SejtGcGeBTUvrj6gFh6eErXCjiG37g3A');