/**
 * 
 */
package com.angel.dao.generic.query;



/**
 * @author Guille Salazar
 * @since 21/Jul/2009
 *
 */
public enum EnumIndexName {
	
	JURISPRUDENCIA_INDEX_NAME("juri"),
	DOCTRINA_INDEX_NAME("doct");

	private String name;

	public String getName() {
		return name;
	}

	private EnumIndexName(String name) {
		this.name = name;
	}
	
	public static boolean hasIndexName(String indexName){
		for(EnumIndexName index : values()){
			if(index.getName().equalsIgnoreCase(indexName)){
				return true;				
			}
		}
		return false;
	}
	
	public static EnumIndexName getEnumIndexName(String indexName){
		for(EnumIndexName index : values()){
			if(index.getName().equalsIgnoreCase(indexName)){
				return index;
			}
		}
		return null;
	}
}
