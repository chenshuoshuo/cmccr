package com.lqkj.web.cmccr2.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 分词树
 */
public class WordTree {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private WordTreeNode rootNode;

    public WordTree() {
        this.rootNode = new WordTreeNode();
    }

    /**
     * 插入单词，构建树
     *
     * @param text
     */
    public void insertText(String text) {
        char[] cs = text.toCharArray();

        insertChar(rootNode, cs[0], cs, 0);
    }

    /**
     * 递归插入字符，构建分词树
     */
    private void insertChar(WordTreeNode node, char c, char[] cs, int index) {
        WordTreeNode createdNode = new WordTreeNode(c, index == cs.length - 1);

        Set<WordTreeNode> childrenNode = node.getChildren();

        boolean hasNode = false;

        for (WordTreeNode childNode : childrenNode) {
            if (childNode.equals(createdNode)) {
                createdNode = childNode;
                hasNode = true;
            }
        }

        if (!hasNode) {
            childrenNode.add(createdNode);
        }

        if (index < cs.length - 1) {
            int nextIndex = index + 1;

            char nextChar = cs[nextIndex];

            insertChar(createdNode, nextChar, cs, nextIndex);
        }
    }

    /**
     * 得带根节点
     *
     * @return
     */
    public WordTreeNode getRootNode() {
        return rootNode;
    }

    /**
     * 根据带个字符搜索节点
     *
     * @param c
     * @return
     */
    public WordTreeNode getNodeByChar(char c) {
        WordTreeNode node = getNodeByChar(this.rootNode, c);
        return node;
    }

    /**
     * 在指定节点搜索字符
     *
     * @param searchNode
     * @param c
     * @return
     */
    public WordTreeNode getNodeByChar(WordTreeNode searchNode, char c) {
        for (WordTreeNode node : searchNode.getChildren()) {
            if (node.getContent() == c) {
                return node;
            } else {
                return getNodeByChar(node, c);
            }
        }

        return null;
    }

    /**
     * 检查是否有匹配的文字
     *
     * @param checkText 待检测的文字
     */
    public List<String> check(String checkText) {
        List<String> results = new LinkedList<>();

        char[] cs = checkText.toCharArray();

        for (int i = 0; i < cs.length; i++) {
            List<WordTreeNode> checkedNodes = new LinkedList<>();

            checkChar(this.rootNode, i, cs, checkedNodes);

            if (checkedNodes.size() < 2) {
                continue;
            }
            WordTreeNode lastNode = ((LinkedList<WordTreeNode>) checkedNodes).getLast();
            if (lastNode.getChildren().size() != 0 && !lastNode.isEnd()) {
                continue;
            }

            results.add(nodesToString(checkedNodes));
        }

        return results;
    }

    private String nodesToString(List<WordTreeNode> nodes) {
        StringBuilder builder = new StringBuilder();
        nodes.forEach(node -> builder.append(node.getContent()));
        return builder.toString();
    }

    /**
     * 检查字符
     */
    private void checkChar(WordTreeNode node, Integer index, char[] cs, List<WordTreeNode> checkedNodes) {
        Set<WordTreeNode> childrenNode = node.getChildren();

        char checkedChar = cs[index];

        for (WordTreeNode childNode : childrenNode) {
            if (childNode.getContent() == checkedChar) {
                checkedNodes.add(childNode);
                if (index + 1 < cs.length) {
                    index++;
                    checkChar(childNode, index, cs, checkedNodes);
                }
            }
        }
    }

    @Override
    public String toString() {
        return rootNode.toString();
    }
}
