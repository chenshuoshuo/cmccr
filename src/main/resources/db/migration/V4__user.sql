ALTER TABLE public.ccr_application_has_users ADD has_users_user_code varchar(255) NOT NULL;
ALTER TABLE public.ccr_application_has_users
  ADD CONSTRAINT ccr_application_has_users_code_fk
FOREIGN KEY (has_users_user_code) REFERENCES public.ccr_user (user_code);
ALTER TABLE public.ccr_application_has_users DROP CONSTRAINT fkphp5uudl9sn30trc7l7wba7sh;
ALTER TABLE public.ccr_application_has_users DROP has_users_user_id;
ALTER TABLE public.ccr_application_has_users ADD CONSTRAINT ccr_application_has_users_pk PRIMARY KEY (ccr_pc_application_app_id, has_users_user_code);