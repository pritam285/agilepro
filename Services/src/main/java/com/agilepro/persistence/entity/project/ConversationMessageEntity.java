package com.agilepro.persistence.entity.project;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.agilepro.commons.models.project.ConversationMessageModel;
import com.yukthi.utils.annotations.PropertyMapping;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.WebutilsEntity;

/**
 * The Class ConversationMessageEntity.
 * 
 * @author Pritam
 */
@Table(name = "CONVERSATION_MESSAGE")
public class ConversationMessageEntity extends WebutilsEntity
{
	/**
	 * The conversation title entity.
	 */
	@Column(name = "CONVERSATION_TITLE_ID", nullable = false)
	@ManyToOne
	@PropertyMapping(type = ConversationMessageModel.class, from = "conversationTitleId", subproperty = "id")
	private ConversationTitleEntity conversationTitleEntity;

	/**
	 * The user entity.
	 **/
	@Column(name = "USER_ID")
	@ManyToOne
	@PropertyMapping(type = ConversationMessageModel.class, from = "userId", subproperty = "id")
	private UserEntity userEntity;

	/**
	 * The message.
	 */
	@Column(name = "MESSAGE", length = 2000)
	private String message;

	/**
	 * The time.
	 **/
	@Column(name = "DATE", nullable = false)
	private Date date;

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date
	 *            the new date
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * Gets the conversation title entity.
	 *
	 * @return the conversation title entity
	 */
	public ConversationTitleEntity getConversationTitleEntity()
	{
		return conversationTitleEntity;
	}

	/**
	 * Sets the conversation title entity.
	 *
	 * @param conversationTitleEntity
	 *            the new conversation title entity
	 */
	public void setConversationTitleEntity(ConversationTitleEntity conversationTitleEntity)
	{
		this.conversationTitleEntity = conversationTitleEntity;
	}

	/**
	 * Gets the user entity.
	 *
	 * @return the user entity
	 */
	public UserEntity getUserEntity()
	{
		return userEntity;
	}

	/**
	 * Sets the user entity.
	 *
	 * @param userEntity
	 *            the new user entity
	 */
	public void setUserEntity(UserEntity userEntity)
	{
		this.userEntity = userEntity;
	}
}
