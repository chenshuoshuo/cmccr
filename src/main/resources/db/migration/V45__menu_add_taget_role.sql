ALTER TABLE public.ccr_menu ADD target_user_role  VARCHAR(64)[]  null;
ALTER TABLE public.ccr_menu ADD specify_user_id  VARCHAR(64)[]  null;
comment on column ccr_menu.target_user_role is
'面向用户角色：target_user_role';
comment on column ccr_menu.specify_user_id is
'指定用户ID：specify_user_id';