alter table ccr_rule_to_authority drop constraint fkkbmlpt19qtncbstk9pko4emnx;

alter table ccr_rule_to_authority
    add constraint fkkbmlpt19qtncbstk9pko4emnx
        foreign key (authority_id) references ccr_user_authority
            on update cascade on delete cascade;