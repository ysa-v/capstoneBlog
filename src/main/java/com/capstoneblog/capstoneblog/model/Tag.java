package com.capstoneblog.capstoneblog.model;

import java.util.Objects;

public class Tag {

    private int tagID;
    private String tagName;

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return getTagID() == tag.getTagID() && getTagName().equals(tag.getTagName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTagID(), getTagName());
    }
}
