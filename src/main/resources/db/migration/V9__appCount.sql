ALTER TABLE public.ccr_ios_application DROP download_count;

ALTER TABLE public.ccr_android_application DROP download_count;

ALTER TABLE public.ccr_version_application ADD download_count bigint DEFAULT 0 NULL;