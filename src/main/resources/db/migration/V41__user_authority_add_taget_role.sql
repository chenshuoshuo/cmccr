ALTER TABLE public.ccr_user_authority ADD target_user_role  VARCHAR(64)[]  null;
ALTER TABLE public.ccr_user_authority ADD specify_user_id  VARCHAR(64)[]  null;
comment on column ccr_user_authority.target_user_role is
'面向用户角色：target_user_role';
comment on column ccr_user_authority.specify_user_id is
'指定用户ID：specify_user_id';