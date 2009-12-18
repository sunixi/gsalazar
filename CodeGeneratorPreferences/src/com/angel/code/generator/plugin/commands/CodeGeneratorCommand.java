package com.angel.code.generator.plugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.activator.Activator;
import com.angel.code.generator.factories.codesGenerators.CodesGeneratorFactory;
import com.angel.code.generator.loader.ClientProjectClassLoader;
import com.angel.code.generator.plugin.preferences.PreferenceConstants;

/**
 * The activator class controls the plug-in life cycle
 */
public class CodeGeneratorCommand extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		try {
			List<Class<?>> beanClasses = this.getClassesSelected(event);
			this.generateCode(beanClasses, event);
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(HandlerUtil
					.getActiveShell(event), "Code Generator", "Error: " + e.getMessage());
			throw new ExecutionException("Error executing command.", e);
		}
		return null;
	}

	protected void generateCode(List<Class<?>> beanClasses, ExecutionEvent event) throws Exception {
		Activator activator = Activator.getDefault();
		IPreferenceStore preferenceStore = activator.getPreferenceStore();
		String codeGeneratorFactoryClassName = preferenceStore.getString(PreferenceConstants.P_STRING);

		String basePackageName = preferenceStore.getString(PreferenceConstants.BASE_PACKAGE_NAME_STRING);
		Class<?> codeGeneratorFactoryClass = Class.forName(codeGeneratorFactoryClassName);

		CodesGeneratorFactory generatorFactory = (CodesGeneratorFactory) codeGeneratorFactoryClass.newInstance();
		CodesGenerator codesGenerator = generatorFactory.createClassesGenerator(basePackageName);

		for(Class<?> beanClass : beanClasses){
			codesGenerator.addDomain(beanClass);
			codesGenerator.addGlobalImport("com.daos", beanClass.getSimpleName() + "DAO");
		}
		codesGenerator.generateCode();
	}

	protected List<Class<?>> getClassesSelected(ExecutionEvent event) throws Exception {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
		Object[] selectionArray = selection.toArray();
		List<Class<?>> beanClasses = new ArrayList<Class<?>>();
		for(Object object: selectionArray){
			ICompilationUnit cu = (ICompilationUnit) object;
			
			
			IFile file = (IFile) cu.getResource();

			IPath path = file.getFullPath();
			String p = System.getProperty("user.dir") + "" + file.getFullPath().toString();
			
			System.out.println("Java Project name : " + cu.getJavaProject().getElementName());
			try {
				IPackageDeclaration[] packages =  cu.getPackageDeclarations();
				for(IPackageDeclaration packageDeclaration: packages){
					System.out.println("Package Declaration [" + packageDeclaration.getElementName() + "].");
					String className = cu.getTypes()[0].getFullyQualifiedParameterizedName();
					
					
					
					ClientProjectClassLoader cpcl = new ClientProjectClassLoader(cu,
							Activator.getDefault().getDescriptor().getPluginClassLoader());
					
					Class myClass = cpcl.loadClass(className);
					System.out.println(myClass.getCanonicalName());
					/*Class<?> domainClass = Activator.getDefault().getBundle().loadClass(className);
					*/
					beanClasses.add(myClass);
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
			System.out.println("Unit selected [" + cu.getElementName() + "] with type [" + cu.getElementType() + "].");
		}
		return beanClasses;
	}

}
