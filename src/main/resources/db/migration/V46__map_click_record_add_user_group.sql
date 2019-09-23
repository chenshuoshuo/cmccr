ALTER TABLE public.ccr_click_record ADD user_group  VARCHAR(32) null;
comment on column ccr_click_record.user_group is
'访问用户用户组：user_group';