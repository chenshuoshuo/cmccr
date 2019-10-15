
INSERT INTO public.ccr_user_authority VALUES (97, 'CM-S2654', '房间预约', '/cm-s2654', NULL, 'menu', NULL, true, NULL);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (98, 'roomManger', '预约审核记录', 'room-reservation/roomManger', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (99, 'locked', '房间锁定记录', 'room-locked/locked', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (100, 'notice', '通知公告管理', 'notification-notice/notice', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (101, 'reservation-setting', '产品配置', '', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (102, 'reservationSetting', '预约规则设置', 'reservation-setting/reservationSetting', 101, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (103, 'sendMessageConfig', '消息发送配置', 'reservation-setting/sendMessageConfig', 101, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (104, 'mangerConfig', '房间管理员配置', 'manger-config/mangerConfig', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (105, 'auditor', '审核人员管理', 'auditors-manger/auditor', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (106, 'auditProcess', '审核流程管理', 'audit-process/auditProcess', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (107, 'operationCenter', '运维中心', 'operation-center/operationCenter', 97, 'm2654_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (109, 'otherConfig', '其他配置', 'reservation-setting/otherConfig', 101, 'm2654_menu', null, true, null);



INSERT INTO public.ccr_rule_to_authority
select 1, generate_series(97,105);

INSERT INTO public.ccr_rule_to_authority (rule_id,authority_id) VALUES (1, 109);