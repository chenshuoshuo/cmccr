ALTER TABLE public.ccr_access_log ADD COLUMN if not EXISTS create_month VARCHAR(64) null;
ALTER TABLE public.ccr_access_log ADD COLUMN if not EXISTS create_date VARCHAR(64) null;
ALTER TABLE public.ccr_access_log ADD COLUMN if not EXISTS create_hour VARCHAR(64) null;
comment on column ccr_access_log.create_month is
'创建时间月份，用于统计：create_month';
comment on column ccr_access_log.create_month is
'创建时间日期，用于统计：create_date';
comment on column ccr_access_log.create_hour is
'创建时间小时，用于统计：create_hour';