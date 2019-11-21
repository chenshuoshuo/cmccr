ALTER TABLE public.ccr_menu ADD url_open_type VARCHAR(64) null;
comment on column ccr_menu.url_open_type is
'url链接打开方式 跳转：jump，弹框：iframe';