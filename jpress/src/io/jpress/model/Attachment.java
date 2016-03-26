package io.jpress.model;

import io.jpress.core.annotation.Table;
import io.jpress.model.base.BaseAttachment;

/**
 * Generated by JPress.
 */
@Table(tableName="attachment",primaryKey="id")
public class Attachment extends BaseAttachment<Attachment> {
	private static final long serialVersionUID = 1L;

	public static final Attachment DAO = new Attachment();
}
