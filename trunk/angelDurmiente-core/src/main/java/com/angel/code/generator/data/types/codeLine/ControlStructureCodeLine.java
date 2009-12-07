/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import java.util.List;

import com.angel.code.generator.data.types.CodeBlock;
import com.angel.common.helpers.StringHelper;


/**
 * 
 * 
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ControlStructureCodeLine extends CodeLine {

	private String name;
	private String content;
	private CodeBlock codeBlock;

	/**
	 * Create a control structure code line with a name and content.
	 * <pre>
	 * 	name(content) {
	 * 		codeBlock
	 *	}
	 * </pre>
	 * @param name to assign to this control structure.
	 * @param content of control structure.
	 */
	public ControlStructureCodeLine(String name, String content){
		super();
		this.setName(name);
		this.setContent(content);
		this.setCodeBlock(new CodeBlock());
	}

	/**
	 * Create a control structure code line with a name.
	 * <pre>
	 * 	name {
	 * 		codeBlock
	 * 	}
	 * 
	 * @param name to assign to this control structure.
	 */
	public ControlStructureCodeLine(String name){
		this(name, StringHelper.EMPTY_STRING);
	}

	public String convertCode() {
		String convertedCode = this.getName();
		convertedCode += this.convertCodeControlStructure();
		convertedCode += " {\n";
		convertedCode += this.convertCodeBlockCode();
		convertedCode += "\n\t}\n";
		return convertedCode;
	}

	protected String convertCodeBlockCode() {
		return this.getCodeBlock().convertCode();
	}

	@Override
	public boolean hasEndOfLine(){
		return false;
	}

	protected String convertCodeControlStructure(){
		return this.hasContent() ? "(" + this.getContent() + ")" : "";
	}
	
	public boolean hasContent(){
		return StringHelper.isNotEmpty(this.getContent());
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the codeBlock
	 */
	public CodeBlock getCodeBlock() {
		return codeBlock;
	}

	/**
	 * @param codeBlock the codeBlock to set
	 */
	public void setCodeBlock(CodeBlock codeBlock) {
		this.codeBlock = codeBlock;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public void addCodeLines(CodeLine ...codeLines) {
		if(codeLines != null && codeLines.length > 0){
			for(CodeLine codeLine: codeLines){
				this.getCodeBlock().addCodeLine(codeLine);
			}
		}
	}

	@Override
	protected void completeImportsType(List<String> importsType) {
		this.addImportsType(importsType, this.getCodeBlock().getImportsType());		
	}
}
