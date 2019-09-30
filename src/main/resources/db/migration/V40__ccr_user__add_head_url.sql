ALTER TABLE public.ccr_user ADD head_path  VARCHAR(255)  null;
comment on column ccr_user.head_path is
'个人头像保存地址：head_path';

ALTER TABLE public.ccr_user ADD head_url  VARCHAR(255)  null;
comment on column ccr_user.head_url is
'个人头像访问路径：head_url';