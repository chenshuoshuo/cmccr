ALTER TABLE public.ccr_recommended_application ADD application_type VARCHAR(255) null;
comment on column ccr_recommended_application.application_type is
    '应用类型';
ALTER TABLE public.ccr_recommended_application ADD support_jump VARCHAR(10) null;
comment on column ccr_recommended_application.support_jump is
    '是否支持跳转';