package com.angel.code.generator.plugin.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.angel.code.generator.activator.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class CodeGeneratorPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public CodeGeneratorPreferencePage() {
		super(GRID);
		super.setPreferenceStore(Activator.getDefault().getPreferenceStore());
		super.setDescription("Panel de configuracion del generador de c�digo java / flex.\n" +
				"Aca se agregan los generadores de codigo disponibles para luego ser utilizados por " +
				"las clases de dominio.\n" +
				"Para poder generar codigo a partir de las clases de dominio, solamente hacer click derecho sobre " +
				"la(s) clase(s), en la vista Navigator, y luego Generar codigo...");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		org.eclipse.swt.widgets.Composite composite = getFieldEditorParent();
		composite.setData("com.angel.code.generator.factories.codesGenerators.impl.CodesGeneratorFactoryImpl");
		addField(
				new StringFieldEditor(
						PreferenceConstants.P_STRING, 
						"Code Generator Factory Class:",
						composite						
						)
				);
		addField(
				new StringFieldEditor(
						PreferenceConstants.BASE_PACKAGE_NAME_STRING, 
						"Base package name:",
						getFieldEditorParent()						
						)
				);
		/*
		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
				"&Directory preference:", getFieldEditorParent()));
		addField(
			new BooleanFieldEditor(
				PreferenceConstants.P_BOOLEAN,
				"&An example of a boolean preference",
				getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(
				PreferenceConstants.P_CHOICE,
			"An example of a multiple-choice preference",
			1,
			new String[][] { { "&Choice 1", "choice1" }, {
				"C&hoice 2", "choice2" }
		}, getFieldEditorParent()));
		addField(
			new StringFieldEditor(PreferenceConstants.P_STRING, "A &text preference:", getFieldEditorParent()));
		*/
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}