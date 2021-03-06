package com.alexandros.mytwitterlogin.RESTApi.response;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FriendsResponse {

    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("next_cursor")
    @Expose
    private Long nextCursor;
    @SerializedName("next_cursor_str")
    @Expose
    private String nextCursorStr;
    @SerializedName("previous_cursor")
    @Expose
    private Integer previousCursor;
    @SerializedName("previous_cursor_str")
    @Expose
    private String previousCursorStr;
    @SerializedName("total_count")
    @Expose
    private Object totalCount;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(Long nextCursor) {
        this.nextCursor = nextCursor;
    }

    public String getNextCursorStr() {
        return nextCursorStr;
    }

    public void setNextCursorStr(String nextCursorStr) {
        this.nextCursorStr = nextCursorStr;
    }

    public Integer getPreviousCursor() {
        return previousCursor;
    }

    public void setPreviousCursor(Integer previousCursor) {
        this.previousCursor = previousCursor;
    }

    public String getPreviousCursorStr() {
        return previousCursorStr;
    }

    public void setPreviousCursorStr(String previousCursorStr) {
        this.previousCursorStr = previousCursorStr;
    }

    public Object getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Object totalCount) {
        this.totalCount = totalCount;
    }
}
