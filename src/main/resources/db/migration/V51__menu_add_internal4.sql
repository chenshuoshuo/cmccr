
--增加空间教室菜单
INSERT INTO public.ccr_menu (menu_id,name, sort, status, type, update_time, url, target_user_role, specify_user_id, parent_id, ename, menu_mode, app_type, has_qr_code, mobile_sort, open, icon) VALUES (10026,'空闲教室', 110, 'f', 'builtInMenu', '2019-11-21 10:57:06.439894', NULL, NULL, NULL, 10003, 'CM-S2653', 'right', 'pc', 'f', NULL, 'f', NULL);
--增加空间教室系统管理后台菜单
INSERT INTO public.ccr_user_authority VALUES (119, 'CM-S2653', '空闲教室', '/cm-s2653', NULL, 'home_menu', NULL, false, NULL,NULL,NULL,NULL,false);