
INSERT INTO public.ccr_user_authority VALUES (97, 'CM-S2654', '房间预约', '/cm-s2654', NULL, 'menu', NULL, true, NULL);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (98, 'roomManger', '预约审核记录', '/roomManger', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (99, 'locked', '房间锁定记录', '/locked', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (100, 'notice', '通知公告管理', '/notice', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (101, 'reservationSetting', '预约设置', '/reservationSetting', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (102, 'mangerConfig', '房间管理员配置', '/mangerConfig', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (103, 'auditor', '审核人员管理', '/auditor', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (104, 'auditProcess', '审核流程管理', '/auditProcess', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (105, 'operationCenter', '运维中心', '/operationCenter', 97, 'm2654_menu', null, true, null);

INSERT INTO public.ccr_rule_to_authority
select 1, generate_series(97,105);