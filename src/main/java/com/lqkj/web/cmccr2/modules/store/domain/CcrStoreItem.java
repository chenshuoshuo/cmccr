package com.lqkj.web.cmccr2.modules.store.domain;

import org.springframework.http.MediaType;

import javax.persistence.*;

/**
 * k-v详细储存信息
 */
@Cacheable
@Entity
@Table(name = "ccr_store_item")
public class CcrStoreItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false)
    private String key;

    @Column(columnDefinition = " text", nullable = false)
    private String value;

    @Column(name = "content_type")
    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private CcrStore store;

    public CcrStoreItem() {
    }

    public CcrStoreItem(String key, String value, CcrStore store) {
        this.key = key;
        this.value = value;
        this.store = store;
        this.contentType = MediaType.APPLICATION_JSON_VALUE;
    }

    public CcrStoreItem(String key, String value, String contentType, CcrStore store) {
        this.key = key;
        this.value = value;
        this.contentType = contentType;
        this.store = store;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CcrStore getStore() {
        return store;
    }

    public void setStore(CcrStore store) {
        this.store = store;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
