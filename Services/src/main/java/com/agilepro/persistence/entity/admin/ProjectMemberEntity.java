package com.agilepro.persistence.entity.admin;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.agilepro.commons.ProjectMemberRole;
import com.agilepro.commons.models.customer.ProjectMemberModel;
import com.yukthi.persistence.annotations.DataType;
import com.yukthi.persistence.annotations.DataTypeMapping;
import com.yukthi.persistence.annotations.UniqueConstraint;
import com.yukthi.persistence.annotations.UniqueConstraints;
import com.yukthi.persistence.conversion.impl.JsonConverter;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ProjectMember.
 * 
 * @author Pritam
 */
@Table(name = "PROJECT_MEMBER")
@UniqueConstraints({ @UniqueConstraint(name = "SPACE_ID_NAME", fields = { "spaceIdentity", "projectEntity", "employeeEntity" }) })
public class ProjectMemberEntity extends WebutilsEntity
{
	/**
	 * The projects entity.
	 **/
	@ManyToOne
	@Column(name = "PROJECT_ID", nullable = false)
	@PropertyMapping(type = ProjectMemberModel.class, from = "projectId", subproperty = "id")
	private ProjectEntity projectEntity;

	/**
	 * The employee entity.
	 **/
	@ManyToOne
	@Column(name = "EMPLOYEE_ID", nullable = false)
	@PropertyMapping(type = ProjectMemberModel.class, from = "employeeId", subproperty = "id")
	private EmployeeEntity employeeEntity;

	/**
	 * The user role.
	 **/
	@Column(name = "ROLE", length = 200)
	@DataTypeMapping(type = DataType.STRING, converterType = JsonConverter.class)
	private ProjectMemberRole projectMemberRole;

	/**
	 * Gets the employee entity.
	 *
	 * @return the employee entity
	 */
	public EmployeeEntity getEmployeeEntity()
	{
		return employeeEntity;
	}

	/**
	 * Gets the project entity.
	 *
	 * @return the project entity
	 */
	public ProjectEntity getProjectEntity()
	{
		return projectEntity;
	}

	/**
	 * Sets the project entity.
	 *
	 * @param projectEntity
	 *            the new project entity
	 */
	public void setProjectEntity(ProjectEntity projectEntity)
	{
		this.projectEntity = projectEntity;
	}

	/**
	 * Sets the employee entity.
	 *
	 * @param employeeEntity
	 *            the new employee entity
	 */
	public void setEmployeeEntity(EmployeeEntity employeeEntity)
	{
		this.employeeEntity = employeeEntity;
	}

	/**
	 * Gets the project member role.
	 *
	 * @return the project member role
	 */
	public ProjectMemberRole getProjectMemberRole()
	{
		return projectMemberRole;
	}

	/**
	 * Sets the project member role.
	 *
	 * @param projectMemberRole
	 *            the new project member role
	 */
	public void setProjectMemberRole(ProjectMemberRole projectMemberRole)
	{
		this.projectMemberRole = projectMemberRole;
	}
}
