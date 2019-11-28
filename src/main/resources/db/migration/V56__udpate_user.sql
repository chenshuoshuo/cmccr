ALTER TABLE public.ccr_user ADD COLUMN if not EXISTS user_name VARCHAR(64) null;
UPDATE public.ccr_user set update_time = now(),is_admin = true,user_name='系统管理员' WHERE user_code = 'B716B90DEC4A57F5'