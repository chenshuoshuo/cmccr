alter table ccr_multi_application alter column android_url type text using android_url::text;

alter table ccr_multi_application alter column name type text using name::text;

alter table ccr_multi_application alter column web_url type text using web_url::text;