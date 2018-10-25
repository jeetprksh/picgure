package com.picgure.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "data",
    "success",
    "status"
})
public class ImgurSubredditObjectsResponse {

    @JsonProperty("data")
    private List<ImgurObjectAttrs> data;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("status")
    private Integer status;

    /**
     * 
     * @return
     *     The data
     */
    @JsonProperty("data")
    public List<ImgurObjectAttrs> getData() {
    	if (data == null){
    		data = Collections.emptyList();
    	}
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    @JsonProperty("data")
    public void setData(List<ImgurObjectAttrs> data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The success
     */
    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

}
