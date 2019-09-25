INSERT INTO public.ccr_user_authority VALUES (96, 'CMCCR', '中控发布系统', '/cmccr', NULL, 'menu', NULL, true, NULL);


update ccr_user_authority set parent_id = 96 where authority_id = 14;
update ccr_user_authority set parent_id = 96 where authority_id = 1;
update ccr_user_authority set parent_id = 96 where authority_id = 3;
update ccr_user_authority set parent_id = 96 where authority_id = 5;
update ccr_user_authority set parent_id = 96 where authority_id = 8;
update ccr_user_authority set parent_id = 96 where authority_id = 10;
update ccr_user_authority set parent_id = 96 where authority_id = 16;
update ccr_user_authority set parent_id = 96 where authority_id = 24;

INSERT INTO public.ccr_rule_to_authority VALUES (1,96);
