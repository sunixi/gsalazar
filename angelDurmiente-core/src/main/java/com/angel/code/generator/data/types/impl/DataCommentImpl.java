/**
 * 
 */
package com.angel.code.generator.data.types.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.angel.code.generator.data.types.DataComment;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class DataCommentImpl implements DataComment {

	private Map<String, String> tagsComment;
	private String comment;
	
	public DataCommentImpl(){
		super();
		this.setComment("");
		this.setTagsComment(new HashMap<String, String>());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.addTag("since", dateFormat.format(new Date()));
	}

	public DataCommentImpl(String comment){
		this();
		this.setComment(comment);
	}
	
	/**
	 * @return the tagsComment
	 */
	private Map<String, String> getTagsComment() {
		return tagsComment;
	}

	/**
	 * @param tagsComment the tagsComment to set
	 */
	private void setTagsComment(Map<String, String> tagsComment) {
		this.tagsComment = tagsComment;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String convertCode() {
		String fullCommentClass = "/**\n";
		fullCommentClass += " *\n";
		fullCommentClass += " * " + (this.hasComments() ? this.getComment() + "\n" : "TODO Comentar.\n");
		fullCommentClass += " *\n";
		for(String keyTag: this.getTagsComment().keySet()){
			fullCommentClass += " * " + keyTag + "\t" + this.getTagsComment().get(keyTag) + ".\n";	
		}
		fullCommentClass += " *\n";
		fullCommentClass += " */\n";
		return fullCommentClass;
	}
	
	public boolean hasComments(){
		return StringHelper.isNotEmpty(this.getComment());
	}

	public int getQuantityTags(){
		return this.getTagsComment().size();
	}
	
	public void clearTags(){
		this.getTagsComment().clear();
	}

	public void addComment(String comment) {
		this.comment += "\n" + comment;
	}

	public void addTODOComment(String comment) {
		this.addComment("TODO " + comment);
	}

	public void addTag(String tag, String value) {
		this.getTagsComment().put("@" + tag, value);
	}

	public String getTagValue(String tag) {
		return this.getTagsComment().get("@" + tag);
	}

	public List<String> getTagsNames() {
		List<String> tagsNames = new ArrayList<String>();
		tagsNames.addAll(this.getTagsComment().keySet());
		return tagsNames;
	}

	public List<String> getTagsValues() {
		List<String> tagsValues = new ArrayList<String>();
		tagsValues.addAll(this.getTagsComment().values());
		return tagsValues;
	}

	public List<String> getImportsType() {
		return new ArrayList<String>();
	}
}
