/**
 * 
 */
package com.angel.code.generator.data.types;

import java.util.List;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface DataComment extends CodeConvertible, Importable {
	
	public String getComment();
	
	public void addComment(String comment);
	
	public void addTODOComment(String comment);
	
	public void addTag(String tag, String value);
	
	public List<String> getTagsNames();
	
	public String getTagValue(String tag);
	
	public List<String> getTagsValue();
}
