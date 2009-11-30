/**
 * 
 */
package com.angel.object.generator.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.types.CodeConvertible;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaTypeComment implements CodeConvertible {

	private Map<String, String> tagsComment;
	private String comment;
	
	public JavaTypeComment(){
		super();
		this.setComment("");
		this.setTagsComment(new HashMap<String, String>());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.addTagComment("since", dateFormat.format(new Date()));
	}

	public JavaTypeComment(String comment){
		this();
		this.setComment(comment);
	}
	
	/**
	 * @return the tagsComment
	 */
	public Map<String, String> getTagsComment() {
		return tagsComment;
	}

	/**
	 * @param tagsComment the tagsComment to set
	 */
	public void setTagsComment(Map<String, String> tagsComment) {
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

	public void addTagComment(String tag, String comment){
		this.getTagsComment().put("@" + tag, comment);
	}

	public String convert() {
		String fullCommentClass = "/**\n";
		fullCommentClass += " *\n";
		fullCommentClass += " * " + (this.hasComments() ? this.getComment() + "\n" : "TODO Comentar clase.\n");
		fullCommentClass += " *\n";
		for(String keyTag: this.getTagsComment().keySet()){
			fullCommentClass += " * " + keyTag + "\t" + this.getTagsComment().get(keyTag) + ".\n";	
		}
		fullCommentClass += " *\n";
		fullCommentClass += " */";
		return fullCommentClass;
	}
	
	public boolean hasComments(){
		return StringHelper.isNotEmpty(this.getComment());
	}

	public int getQuantityTags(){
		return this.getTagsComment().size();
	}
}
