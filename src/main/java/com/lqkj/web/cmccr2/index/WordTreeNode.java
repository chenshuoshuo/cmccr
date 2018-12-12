package com.lqkj.web.cmccr2.index;

import java.io.Serializable;
import java.util.*;

/**
 * 分词树节点
 */
public class WordTreeNode implements Serializable {
    /**
     * 节点id
     */
    private String id;

    /**
     * 节点内容
     */
    private char content;

    /**
     * 父节点
     */
    private WordTreeNode parent;

    /**
     * 子节点
     */
    private Set<WordTreeNode> children;

    /**
     * 是否是结束字符
     */
    private boolean isEnd = false;

    public WordTreeNode() {
        this.id = UUID.randomUUID().toString();
        this.children = new LinkedHashSet<>();
    }

    public WordTreeNode(char content) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.children = new LinkedHashSet<>();
    }

    public WordTreeNode(char content, boolean isEnd) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.children = new LinkedHashSet<>();
        this.isEnd = isEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getContent() {
        return content;
    }

    public void setContent(char content) {
        this.content = content;
    }

    public WordTreeNode getParent() {
        return parent;
    }

    public void setParent(WordTreeNode parent) {
        this.parent = parent;
    }

    public void addChild(WordTreeNode node) {
        this.children.add(node);
    }

    public void removeChild(WordTreeNode node) {
        this.children.remove(node);
    }

    public Set<WordTreeNode> getChildren() {
        return children;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordTreeNode that = (WordTreeNode) o;
        return content == that.content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "WordTreeNode{" +
                "id='" + id + '\'' +
                ", content=" + content +
                ", parent=" + parent +
                ", children=" + children +
                '}';
    }
}
