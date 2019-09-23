package com.lqkj.web.cmccr2.modules.record.dao;

import com.lqkj.web.cmccr2.modules.record.doamin.CcrMapClickRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CcrMapClickRecordRepository extends JpaRepository<CcrMapClickRecord, UUID> {

    @Query("select r.name,count(r) from CcrMapClickRecord r group by r.name")
    List<Object[]> clickRecord();

    @Query(value = "select r.name,count(r),r.user_group from ccr_click_record r where 1=1 and " +
            "case when ?1 ='全部' then user_group is not null else 1=1 end and " +
            "case when ?1 ='学生' then user_group like 'student' else 1=1 end and " +
            "case when ?1 ='教师' then user_group like 'teacher_staff' else 1=1 end and " +
            "case when ?1 ='其他' then user_gruop like 'guest' else 1=1 end group by r.name",nativeQuery = true)
    List<Object[]> clickRecordByGroup(String userGroup);

}
