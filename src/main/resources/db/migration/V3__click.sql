CREATE TABLE public.ccr_click_record
(
  record_id uuid PRIMARY KEY NOT NULL,
  way_id bigint,
  node_id bigint,
  name text
);