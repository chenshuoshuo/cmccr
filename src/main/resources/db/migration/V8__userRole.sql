INSERT INTO public.ccr_user_rule (rule_id, content, name, update_time)
VALUES (1, 'admin', '超级管理员', now());

INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (34, 'geostatistics', '网关地理统计', '/gatewayStats/geostatistics', 5, 'menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (35, 'openfunc', '开放功能', '/maintenanceCenter/openfuc', 24, 'menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (36, 'MTApp', '编辑移动端应用', '/appManager/addMT', 3, 'menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (37, 'PCApp', '编辑PC应用', '/appManager/addPC', 3, 'menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (38, 'information-manager', '属性信息管理', '/information-manager', 30, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (39, 'information-organization', '机构信息管理', '/information-manager/organization', 38, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (40, 'create-organization', '编辑机构信息', '/information-manager/createOrganization', 38, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (41, 'information-label', '标注信息管理', '/information-manager/label', 38, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (42, 'create-label', '编辑标注信息', '/information-manager/createLabel', 38, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (43, 'information-building', '大楼信息管理', '/information-manager/building', 38, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (44, 'create-building', '编辑大楼信息', '/information-manager/createBuilding', 38, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (45, 'information-room', '房间信息管理', '/information-manager/room', 38, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (46, 'edit-room', '编辑房间信息', '/information-manager/createRoom', 38, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (47, 'information-other', '其他信息', '/information-manager/other', 38, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (48, 'edit-other', '编辑其他信息', '/information-manager/createOthers', 38, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (49, 'category-manager', '属性类别管理', '/category-manager', 30, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (50, 'category-organization', '机构类别管理', '/category-manager/organization', 49, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (51, 'category-label', '标注类别管理', '/category-manager/label', 49, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (52, 'category-building', '大楼类别管理', '/category-manager/building', 49, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (53, 'category-room', '房间类别管理', '/category-manager/room', 49, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (54, 'category-other', '其他类别管理', '/category-manager/other', 49, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (55, 'panoramic-roaming', '全景漫游管理', '/panoramic-roaming', 30, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (56, 'aerial-photo', '校园航拍管理', '/panoramic-roaming/aerialPhoto', 55, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (57, 'ground-panorama', '地面全景', '/panoramic-roaming/groundPanorama', 55, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (58, 'add-panorama', '编辑地面全景', '/panoramic-roaming/groundPanorama', 55, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (59, 'map-correction', '地图纠错', '/map-correction', 30, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (60, 'correction', '地图纠错', '/map-correction/correction', 59, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (61, 'suggestions-manager', '意见反馈', '/suggestions-manager', 30, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (62, 'suggestions', '意见反馈', '/suggestions-manager/suggestions', 61, 'ips_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (23, 'create-role', '编辑角色', '/configuration/createRole', 16, 'menu', null, true, null);

INSERT INTO public.ccr_rule_to_authority
select 1, generate_series(1, 62);