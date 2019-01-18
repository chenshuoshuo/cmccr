ALTER TABLE public.ccr_request_record ALTER COLUMN exception TYPE text USING exception::text;
ALTER TABLE public.ccr_request_record ALTER COLUMN url TYPE text USING url::text;