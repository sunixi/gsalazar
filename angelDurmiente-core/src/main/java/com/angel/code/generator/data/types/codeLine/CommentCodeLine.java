/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import java.util.List;

import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class CommentCodeLine extends CodeLine {

	private String comment;
	
	public CommentCodeLine(){
		super();
		this.setComment(StringHelper.EMPTY_STRING);
	}
	
	public CommentCodeLine(String comment){
		super();
		this.setComment(comment);
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	public CommentCodeLine addNewLine(){
		this.comment += "\n";
		return this;
	}
	
	public CommentCodeLine setTODOComment(String comment){
		this.comment += "\n\tTODO " + comment;
		return this;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String convertCode() {
		return "\t/*\n\t" + this.getComment() + "\n\t*/\n";
	}

	@Override
	protected void completeImportsType(List<String> importsType) {
		//Do nothing.
	}
}
