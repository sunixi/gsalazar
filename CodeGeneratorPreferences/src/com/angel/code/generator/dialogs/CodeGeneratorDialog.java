/**
 * 
 */
package com.angel.code.generator.dialogs;

//import java.util.List;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.angel.code.generator.beans.CodeGeneratorInfo;
import com.angel.code.generator.repository.GeneratorClassesRepository;

/**
 * 
 * @author Guillermo Salazar.
 * @since 29/Noviembre/2009.
 * 
 */
public class CodeGeneratorDialog extends TitleAreaDialog {

	/**
	 * IDs for MailDialog buttons We use large integers because we don't want to
	 * conflict with system constants.
	 */
	public static final int OPEN = 9999;

	public static final int DELETE = 9998;

	// List widget
	private List list;
	
	private Label codeGeneratorDescription;
	
	private Collection<Class<?>> domainClasses;

	// Selected items
	private String[] itemsToOpen;

	/**
	 * Constructor for MailDialog.
	 * 
	 * @param shell
	 *            - Containing shell
	 * @param items
	 *            - Mail messages passed to the dialog
	 */
	public CodeGeneratorDialog(Shell shell, Collection<Class<?>> domainClasses) {
		super(shell);
		this.setDomainClasses(new ArrayList<Class<?>>());
		this.getDomainClasses().addAll(domainClasses);
	}

	/**
	 * @see org.eclipse.jface.window.Window#create() We complete the dialog with
	 *      a title and a message
	 */
	@Override
	public void create() {
		super.create();
		super.setDialogHelpAvailable(false);
		this.setTitle("Code Generator Options");
		this
				.setMessage("In this windows, you can select some code generator classes to use in your domain object classes selected. "
						+ "At the following list these are some code generators classes to use to generate code...");
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#
	 *      createDialogArea(org.eclipse.swt.widgets.Composite) Here we fill the
	 *      center area of the dialog
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setRedraw(true);

		final Composite area = this.buildAreaCompositeContainer(parent);
		
		this.buildCodeGeneratorList(area);
		
		this.buildDomainClassesList(area);
		
		this.buildClassGeneratorDescription(parent);
		
		return area;
	}

	protected void buildClassGeneratorDescription(Composite parent) {
		Label classGeneratorDescription = new Label(parent, SWT.BOLD);
		classGeneratorDescription.setText("Class generator description.");
		GridData gridData = this.createGridData(500, 150, 100, 100);
		classGeneratorDescription.setLayoutData(gridData);
		this.setCodeGeneratorDescription(classGeneratorDescription);
	}

	protected void buildDomainClassesList(Composite area) {
		List domainClassesList = new List(area, SWT.BORDER | SWT.MULTI);
		domainClassesList.setLayoutData(this.createGridData(250, 250, 100,  100));
		for(Class<?> domainClass : this.getDomainClasses()){
			domainClassesList.add(domainClass.getCanonicalName());
		}
	}

	protected GridData createGridData(int widthHint, int heightHint, int minimumWidth, int minimumHeight){
		final GridData gridData = new GridData();
		gridData.widthHint = widthHint;
		gridData.minimumHeight = minimumHeight;
		gridData.heightHint = heightHint;
		return gridData;
	}

	protected void buildCodeGeneratorList(Composite area) {
		// Now we create the list widget
		list = new List(area, SWT.BORDER | SWT.MULTI);
		// We define a minimum width for the list
		list.setLayoutData(this.createGridData(250, 250, 100, 100));
		
		final GeneratorClassesRepository generatorClassesRepository = GeneratorClassesRepository.getInstance();
		
		// We add a SelectionListener
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// When the selection changes, we re-validate the list
				validate();
				if(list.getSelectionCount() > 0){
					String simpleName = list.getSelection()[0];
					CodeGeneratorInfo codeGeneratorInfo = generatorClassesRepository.getCodeGeneratorInfo(simpleName);	
					codeGeneratorDescription.setText(codeGeneratorInfo.getDescription());
				}
			}
		});
		for(CodeGeneratorInfo generatorInfo: generatorClassesRepository.getGeneratorClasses()){
			list.add(generatorInfo.getSimpleName());
		}
	}

	protected Composite buildAreaCompositeContainer(Composite parent) {
		/** Create new composite as container.*/
		final Composite area = new Composite(parent, SWT.NULL);
		// We use a grid layout and set the size of the margins
		final GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginWidth = 10;
		gridLayout.marginHeight = 10;
		area.setLayout(gridLayout);
		return area;
	}

	private void validate() {
		// We select the number of selected list entries
		boolean selected = (list.getSelectionCount() > 0);
		// We enable/disable the Open and Delete buttons
		getButton(OPEN).setEnabled(selected);
		//getButton(DELETE).setEnabled(selected);
		if (!selected)
			// If nothing was selected, we set an error message
			setErrorMessage("Select at least one entry!");
		else
			// Otherwise we set the error message to null
			// to show the intial content of the message area
			setErrorMessage(null);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#
	 *      createButtonsForButtonBar(org.eclipse.swt.widgets.Composite) We
	 *      replace the OK and Cancel buttons by our own creations We use the
	 *      method createButton() (from Dialog), to create the new buttons
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		// Create Open button
		Button openButton = createButton(parent, OPEN, "Generate Code", true);
		// Initially deactivate it
		openButton.setEnabled(false);
		// Add a SelectionListener
		openButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Retrieve selected entries from list
				itemsToOpen = list.getSelection();
				// Set return code
				setReturnCode(OPEN);
				// Close dialog
				close();
			}
		});/*
		// Create Delete button
		Button deleteButton = createButton(parent, DELETE, "Delete", false);
		deleteButton.setEnabled(false);
		// Add a SelectionListener
		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Get the indices of the selected entries
				int selectedItems[] = list.getSelectionIndices();
				// Remove all these entries
				list.remove(selectedItems);
				// Now re-validate the list because it has changed
				validate();
			}
		});*/
		// Create Cancel button
		Button cancelButton = createButton(parent, CANCEL, "Cancel", false);
		// Add a SelectionListener
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setReturnCode(CANCEL);
				close();
			}
		});
	}

	/**
	 * Method getItemsToOpen.
	 * 
	 * @return String[] - the selected items
	 */
	public String[] getItemsToOpen() {
		return itemsToOpen;
	}

	/**
	 * @return the domainClasses
	 */
	public Collection<Class<?>> getDomainClasses() {
		return domainClasses;
	}

	/**
	 * @param domainClasses the domainClasses to set
	 */
	public void setDomainClasses(Collection<Class<?>> domainClasses) {
		this.domainClasses = domainClasses;
	}

	/**
	 * @return the codeGeneratorDescription
	 */
	public Label getCodeGeneratorDescription() {
		return codeGeneratorDescription;
	}

	/**
	 * @param codeGeneratorDescription the codeGeneratorDescription to set
	 */
	public void setCodeGeneratorDescription(Label codeGeneratorDescription) {
		this.codeGeneratorDescription = codeGeneratorDescription;
	}
}
