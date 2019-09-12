--
--添加房间预约管理员角色
INSERT INTO public.ccr_user_rule (rule_id,content,name,update_time)
VALUES (104,'room-manager', '房间预约管理员', now());

--添加菜单权限
INSERT INTO public.ccr_rule_to_authority
select 104, generate_series(97,105);

-- 初始化房间管理员，并赋予房间预约管理权限
--
INSERT INTO public.ccr_user (user_id,pass_word,user_code,update_time)
VALUES (155477,'$2a$10$22X8d2.oc6padLOao/r0OOqhWgBnDZpI4Ru49rpj7PhcwFVUI6iJe', 'room', now());

INSERT INTO public.ccr_user_to_rule VALUES (155477,104);

