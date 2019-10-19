ALTER TABLE public.ccr_menu ADD target_user_role  VARCHAR(64)[]  null;
ALTER TABLE public.ccr_menu ADD specify_user_id  VARCHAR(64)[]  null;
ALTER TABLE public.ccr_menu ADD parent_id INT8 null;
ALTER TABLE public.ccr_menu ADD ename VARCHAR(64) null;
ALTER TABLE public.ccr_menu ADD menu_mode VARCHAR(64) null;
ALTER TABLE public.ccr_menu ADD app_type VARCHAR(64) null;
ALTER TABLE public.ccr_menu ADD has_qr_code BOOL null;
ALTER TABLE public.ccr_menu ADD mobile_sort INT8 null;

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
'菜单类别：type:sysMenu,builtInMenu,builtInApp,extMenu,urlApp,mobApp';
comment on column ccr_menu.app_type is
'应用类别：app_type:pc,h5';
comment on column ccr_menu.has_qr_code is
'二维码应用：has_qr_code:是,否';
comment on column ccr_menu.mobile_sort is
'移动端排序';


