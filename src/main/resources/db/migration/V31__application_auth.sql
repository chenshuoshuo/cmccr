INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (88, 'thematic-category', '专题图分类管理', '/thematic-category', 31, 'm3820_menu', null, false, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (89, 'thematic-info', '专题图信息管理', '/thematic-info', 31, 'm3820_menu', null, false, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (90, 'model-manager-index', '专题图模块管理', '/model-manager/index', 87, 'm3820_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (91, 'dynamic-thematic', '动态专题图管理', '/dynamic-thematic', 31, 'm3820_menu', null, false, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (92, 'free-classroom', '空闲教室', '/dynamic-thematic/free-classroom', 91, 'm3820_menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (93, 'migration-map', '迁徙图', '/metaAnalysis/migration-map', 9, 'menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (94, 'thermogram-map', '热力图', '/metaAnalysis/thermogram-map', 9, 'menu', null, true, null);
INSERT INTO public.ccr_user_authority (authority_id, content, name, route, parent_id, type, icon, enabled, http_method) VALUES (95, 'migration', '迁徙图区域配置', '/maintenanceCenter/migration', 24, 'menu', null, true, null);

INSERT INTO public.ccr_rule_to_authority
select 1, generate_series(86,95);