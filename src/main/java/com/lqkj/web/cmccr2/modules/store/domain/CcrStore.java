package com.lqkj.web.cmccr2.modules.store.domain;

import javax.persistence.*;
import java.util.List;

/**
 * k-v储存
 */
//@Cacheable
@Entity
@Table(name = "ccr_store")
public class CcrStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, unique = true, nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(name = "store_id")
    private List<CcrStoreItem> items;

    public CcrStore() {
    }

    public CcrStore(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CcrStoreItem> getItems() {
        return items;
    }

    public void setItems(List<CcrStoreItem> items) {
        this.items = items;
    }
}
