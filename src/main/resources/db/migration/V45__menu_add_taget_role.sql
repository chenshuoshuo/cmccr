ALTER TABLE public.ccr_menu ADD target_user_role  VARCHAR(64)[]  null;
ALTER TABLE public.ccr_menu ADD specify_user_id  VARCHAR(64)[]  null;
ALTER TABLE public.ccr_menu ADD parent_id INT8 null;
ALTER TABLE public.ccr_menu ADD ename VARCHAR(64) null;
ALTER TABLE public.ccr_menu ADD menu_mode VARCHAR(64) null;
comment on column ccr_menu.target_user_role is
'面向用户角色：target_user_role';
comment on column ccr_menu.specify_user_id is
'指定用户ID：specify_user_id';
comment on column ccr_menu.parent_id is
'父菜单ID：parent_id';
comment on column ccr_menu.ename is
'英文名：ename';
comment on column ccr_menu.menu_mode is
'菜单模式：menu_mode，right,left';
comment on column ccr_menu.type is
'菜单类别：type:sysMenu,';
