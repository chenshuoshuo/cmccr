--
--添加房间预约管理员角色
INSERT INTO public.ccr_user_rule (content,name,update_time)
VALUES ('room-manager', '房间预约管理员', now());

--添加菜单权限
INSERT INTO public.ccr_rule_to_authority
select (select last_value from ccr_user_rule_rule_id_seq), generate_series(97,107);

-- 初始化房间管理员，并赋予房间预约管理权限
--
INSERT INTO public.ccr_user (pass_word,user_code,update_time)
VALUES ('$2a$10$22X8d2.oc6padLOao/r0OOqhWgBnDZpI4Ru49rpj7PhcwFVUI6iJe', 'room', now());

INSERT INTO public.ccr_user_to_rule VALUES ((select last_value from ccr_user_user_id_seq),(select last_value from ccr_user_rule_rule_id_seq));

