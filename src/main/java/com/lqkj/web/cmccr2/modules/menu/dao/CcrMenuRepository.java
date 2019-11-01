package com.lqkj.web.cmccr2.modules.menu.dao;

import com.lqkj.web.cmccr2.modules.menu.domain.CcrMenu;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface CcrMenuRepository extends JpaRepository<CcrMenu, Long> {

    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true")
    })
    @Override
    <S extends CcrMenu> Page<S> findAll(Example<S> example, Pageable pageable);

    @Query(nativeQuery = true, value = "select m.* from ccr_menu as m where m.name=:name")
    List<CcrMenu> nameByMenu(@Param("name") String menuName);

    @Query(nativeQuery = true, value = "select m.* from ccr_menu as m where m.parent_id=:parentId")
    List<CcrMenu> childMenus(@Param("parentId") Long parentId);

    List<CcrMenu> findAllByType(CcrMenu.IpsMenuType type);

    @Query("select m from CcrMenu m where m.ename LIKE CONCAT('%',:ename,'%')")
    List<CcrMenu> queryEname(@Param("ename") String ename);

    @Modifying
    @Query("update CcrMenu a set a.status=:status where a.menuId=:menuId")
    void updateChildState(@Param("menuId") Long menuId,

                          @Param("status") Boolean status);
    @Modifying
    @Query("update CcrMenu a set a.open=:status,a.status=:status where a.menuId=:menuId")
    void updateChildOpen(Long menuId, Boolean status);
}
