/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.exceptions.CodeGeneratorException;
import com.angel.common.helpers.StringHelper;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ExecutableMultipleReturnCodeLine extends ExecutableReturnCodeLine {

	private List<ExecutableReturnCodeLine> executablesReturn;

	public ExecutableMultipleReturnCodeLine(ExecutableReturnCodeLine executableReturnCodeLine){
		super(StringHelper.EMPTY_STRING, StringHelper.EMPTY_STRING);
		this.setExecutablesReturn(new ArrayList<ExecutableReturnCodeLine>());
		this.addExecutablesReturnCodeLine(executableReturnCodeLine);
	}

	/**
	 * Add an executable return code line to be executed.
	 * 
	 * @param executableReturnCodeLine to be added.
	 */
	public void addExecutableReturnCodeLine(ExecutableReturnCodeLine executableReturnCodeLine){
		if(executableReturnCodeLine == null){
			throw new CodeGeneratorException("Executable return code line cannot be null.");
		}
		if(this.getExecutablesReturn().size() > 0){
			executableReturnCodeLine.setVariableName(StringHelper.EMPTY_STRING);
		}
		this.getExecutablesReturn().add(executableReturnCodeLine);
	}

	/**
	 * Add several executables return code line to be executed.
	 * 
	 * @param executablesReturnCodeLine to be added.
	 */
	public void addExecutablesReturnCodeLine(ExecutableReturnCodeLine ...executablesReturnCodeLine){
		if(executablesReturnCodeLine != null && executablesReturnCodeLine.length > 0){
			for(ExecutableReturnCodeLine ercl: executablesReturnCodeLine){
				this.addExecutableReturnCodeLine(ercl);
			}
		}
	}

	@Override
	public boolean needsCast(String variableCanonicalName, String returnCollectionCanonicalName) {
		ExecutableReturnCodeLine lastExecutableReturnCodeLine = this.getLastExecutableReturnCodeLine();
		return lastExecutableReturnCodeLine.needsCast(variableCanonicalName, returnCollectionCanonicalName);
	}

	@Override
	public String convertCode() {
		String convertedCode = "";
		for(ExecutableReturnCodeLine ercl: this.getExecutablesReturn()){
			convertedCode += ercl.convertCode();
		}
		return convertedCode;
	}

	protected boolean isLastExecutableReturnCodeLine(ExecutableReturnCodeLine executableReturnCodeLine){
		return this.getExecutablesReturn().indexOf(executableReturnCodeLine) == this.getExecutablesReturn().size() - 1;
	}

	/**
	 * Get last executable return code line added.
	 * 
	 * @return last executable return code line added.
	 */
	protected ExecutableReturnCodeLine getLastExecutableReturnCodeLine(){
		int executablesSize = this.getExecutablesReturn().size();
		if(executablesSize > 0){
			return this.getExecutablesReturn().get(executablesSize - 1);
		}
		throw new CodeGeneratorException("Executable multiples return code line hasn't added any executables return code line.");
	}

	@SuppressWarnings("unchecked")
	public <T extends ExecutableMultipleReturnCodeLine> T addParameterName(String parameterName) {
		this.getParametersNames().add(parameterName);
		return (T) this;
	}


	/**
	 * @return the executablesReturn
	 */
	protected List<ExecutableReturnCodeLine> getExecutablesReturn() {
		return executablesReturn;
	}


	/**
	 * @param executablesReturn the executablesReturn to set
	 */
	protected void setExecutablesReturn(
			List<ExecutableReturnCodeLine> executablesReturn) {
		this.executablesReturn = executablesReturn;
	}
}
