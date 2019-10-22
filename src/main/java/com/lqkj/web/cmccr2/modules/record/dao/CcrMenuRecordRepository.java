package com.lqkj.web.cmccr2.modules.record.dao;

import com.lqkj.web.cmccr2.modules.record.doamin.CcrMenuClickRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Repository
public interface CcrMenuRecordRepository extends JpaRepository<CcrMenuClickRecord, UUID> {

    @Query(nativeQuery = true, value = "select m.name, t._count from " +
            "(select menu_id, count(*) _count from ccr_menu_click_record group by menu_id) t, ccr_menu m " +
            "where t.menu_id = m.menu_id and m.parent_id is null order by m.menu_id")
    List<Object[]> oneMenurecord();

    @Query(nativeQuery = true, value = "select m.name, t._count as tatal_count from " +
            "(select menu_id, count(*) _count from ccr_menu_click_record GROUP BY menu_id) t, ccr_menu m " +
            "where t.menu_id = m.menu_id and m.parent_id is not null order by tatal_count desc limit 5 OFFSET 0")
    List<Object[]> appMenurecord();
}