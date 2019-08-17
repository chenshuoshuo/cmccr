delete from ccr_rule_to_authority where authority_id
  in (26, 27, 31, 32, 33);

delete
from ccr_user_authority
where authority_id in (26, 27, 31, 32, 33);

update ccr_user_authority a set route = '/cmdbe' where a.authority_id = 28;
update ccr_user_authority a set route = '/cmgis' where a.authority_id = 29;
update ccr_user_authority a set route = '/cmips-manager' where a.authority_id = 30;