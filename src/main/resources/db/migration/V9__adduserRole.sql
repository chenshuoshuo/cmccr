--
-- 默认学生和教职工角色，并赋予个人中心管理权限
--
INSERT INTO public.ccr_user_rule (rule_id, content, name, update_time)
VALUES (2, 'teacher', '教职工', now());
INSERT INTO public.ccr_user_rule (rule_id, content, name, update_time)
VALUES (3, 'student', '学生', now());


INSERT INTO public.ccr_rule_to_authority
select 2, generate_series(14, 15);

INSERT INTO public.ccr_rule_to_authority
select 3, generate_series(14, 15);