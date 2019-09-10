--
-- 默认系统用户，并赋予所有管理权限
--
INSERT INTO public.ccr_user (user_id,pass_word,user_code,update_time)
VALUES (155476,'$2a$10$dvicvhiLS/mGtgcOb32LO.a89crKviAfRLZpTy.yelcVR/mOsbUEC', 'B716B90DEC4A57F5', now());

INSERT INTO public.ccr_user_to_rule VALUES (155476,1);