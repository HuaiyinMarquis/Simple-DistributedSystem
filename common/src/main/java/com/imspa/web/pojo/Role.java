package com.imspa.web.pojo;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role.id
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role.role_name
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role.description
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role.created_time
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    private Date createdTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role.created_by
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    private String createdBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role.updated_time
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    private Date updatedTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role.updated_by
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    private String updatedBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role.permissions
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    private String permissions;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_role
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role.id
     *
     * @return the value of t_role.id
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public Role withId(String id) {
        this.setId(id);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role.id
     *
     * @param id the value for t_role.id
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role.role_name
     *
     * @return the value of t_role.role_name
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public Role withRoleName(String roleName) {
        this.setRoleName(roleName);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role.role_name
     *
     * @param roleName the value for t_role.role_name
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role.description
     *
     * @return the value of t_role.description
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public Role withDescription(String description) {
        this.setDescription(description);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role.description
     *
     * @param description the value for t_role.description
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role.created_time
     *
     * @return the value of t_role.created_time
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public Role withCreatedTime(Date createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role.created_time
     *
     * @param createdTime the value for t_role.created_time
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role.created_by
     *
     * @return the value of t_role.created_by
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public Role withCreatedBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role.created_by
     *
     * @param createdBy the value for t_role.created_by
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role.updated_time
     *
     * @return the value of t_role.updated_time
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public Role withUpdatedTime(Date updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role.updated_time
     *
     * @param updatedTime the value for t_role.updated_time
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role.updated_by
     *
     * @return the value of t_role.updated_by
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public Role withUpdatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role.updated_by
     *
     * @param updatedBy the value for t_role.updated_by
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role.permissions
     *
     * @return the value of t_role.permissions
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public String getPermissions() {
        return permissions;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public Role withPermissions(String permissions) {
        this.setPermissions(permissions);
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role.permissions
     *
     * @param permissions the value for t_role.permissions
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    public void setPermissions(String permissions) {
        this.permissions = permissions == null ? null : permissions.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_role
     *
     * @mbg.generated Mon Aug 12 21:49:44 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleName=").append(roleName);
        sb.append(", description=").append(description);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", permissions=").append(permissions);
        sb.append("]");
        return sb.toString();
    }
}