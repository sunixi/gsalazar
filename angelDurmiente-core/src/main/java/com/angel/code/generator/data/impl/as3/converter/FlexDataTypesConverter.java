/**
 * 
 */
package com.angel.code.generator.data.impl.as3.converter;

import java.util.HashMap;
import java.util.Map;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class FlexDataTypesConverter {
	
	private static FlexDataTypesConverter INSTANCE;
	
	private Map<String, FlexTypeImport> javaFlexTypesMapping;

	private FlexDataTypesConverter(){
		super();
		this.setJavaFlexTypesMapping(new HashMap<String, FlexTypeImport>());
		this.addMapping("java.lang.String"	,"String");
		this.addMapping("java.lang.Boolean"	,"Boolean");
		this.addMapping("boolean"			,"Boolean");
		this.addMapping("java.lang.Integer"	,"Number");
		this.addMapping("int"				,"Number");
		this.addMapping("java.lang.Short"	,"Number");
		this.addMapping("short"				,"Number");
		this.addMapping("java.lang.Byte"	,"Number");
		this.addMapping("byte"				,"Number");
		this.addMapping("java.lang.Byte[]"	,"flash.utils.ByteArray", true);
		this.addMapping("java.lang.Double"	,"Number");
		this.addMapping("double"			,"Number");
		this.addMapping("java.lang.Long"	,"Number");
		this.addMapping("long"				,"Number");
		this.addMapping("java.lang.Float"	,"Number");
		this.addMapping("float"				,"Number");
		this.addMapping("java.lang.Character"	,"String");
		this.addMapping("char"					,"String");
		this.addMapping("java.lang.Character[]"	,"String");
		this.addMapping("char[]"				,"String");
		this.addMapping("java.math.BigDecimal"	,"String");
		this.addMapping("java.util.Calendar"	,"Date");
		this.addMapping("java.util.Date"		,"Date");
		this.addMapping("java.util.Collection"	,"mx.collections.ArrayCollection", true);
		this.addMapping("java.util.ArrayList"	,"mx.collections.ArrayCollection", true);
		this.addMapping("java.util.List"		,"mx.collections.IList", true);
		this.addMapping("java.lang.Object[]"	,"Array");
		this.addMapping("java.util.Map"			,"Object");
		this.addMapping("java.util.Dictionary"	,"Object");
		this.addMapping("org.w3c.dom.Document"	,"XML");
		this.addMapping("java.util.Dictionary"	,"Object");
		this.addMapping("java.lang.Object"		,"Object");
		this.addMapping("java.sql.Blob"			,"flash.utils.ByteArray", true);
	}

	public void addMapping(String javaCanonicalType, String flexCanonicalType, boolean importType){
		this.getJavaFlexTypesMapping().put(javaCanonicalType, new FlexTypeImport(flexCanonicalType, importType));
	}
	
	public void addMapping(String javaCanonicalType, String flexCanonicalType){
		this.getJavaFlexTypesMapping().put(javaCanonicalType, new FlexTypeImport(flexCanonicalType));
	}

	public static synchronized FlexDataTypesConverter getInstance(){
		if(INSTANCE == null){
			INSTANCE = new FlexDataTypesConverter();
		}
		return INSTANCE;
	}

	/**
	 * @return the javaFlexTypesMapping
	 */
	protected Map<String, FlexTypeImport> getJavaFlexTypesMapping() {
		return javaFlexTypesMapping;
	}

	/**
	 * @param javaFlexTypesMapping the javaFlexTypesMapping to set
	 */
	protected void setJavaFlexTypesMapping(Map<String, FlexTypeImport> javaFlexTypesMapping) {
		this.javaFlexTypesMapping = javaFlexTypesMapping;
	}

	/**
	 * Get a flex type from a java type.
	 * 
	 * @param javaType to find.
	 * @return a flex type.
	 */
	public FlexTypeImport getFlexTypeFrom(String javaType){
		FlexTypeImport flexType = this.getJavaFlexTypesMapping().get(javaType);
		if(flexType != null){
			return flexType;
		}
		return null;
	}

	public class FlexTypeImport {
		private String canonicalType;
		private boolean importType;
		
		public FlexTypeImport(String canonicalType, boolean importType){
			super();
			this.setCanonicalType(canonicalType);
			this.setImportType(importType);
		}

		public FlexTypeImport(String canonicalType){
			this(canonicalType, false);
		}

		/**
		 * @return the canonicalType
		 */
		public String getCanonicalType() {
			return canonicalType;
		}

		/**
		 * @param canonicalType the canonicalType to set
		 */
		protected void setCanonicalType(String canonicalType) {
			this.canonicalType = canonicalType;
		}

		/**
		 * @return the importType
		 */
		public boolean isImportType() {
			return importType;
		}

		/**
		 * @param importType the importType to set
		 */
		protected void setImportType(boolean importType) {
			this.importType = importType;
		}
	}
}
