ALTER TABLE public.ccr_user_authority ADD manage bool null;
comment on column ccr_user_authority.manage is
'是否有后台管理系统';

update public.ccr_user_authority set manage = true where type = 'home_menu';