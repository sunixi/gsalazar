package com.angel.code.generator.plugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.activator.Activator;
import com.angel.code.generator.dialogs.CodeGeneratorDialog;
import com.angel.code.generator.factories.codesGenerators.CodesGeneratorFactory;
import com.angel.code.generator.loader.ClientProjectClassLoader;
import com.angel.code.generator.plugin.preferences.PreferenceConstants;

/**
 * The activator class controls the plug-in life cycle
 */
public class CodeGeneratorCommand extends AbstractHandler {

	private String fullPath;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();

		try {
			List<Class<?>> beanClasses = this.getClassesSelected(event);

			CodeGeneratorDialog dirDialog = new CodeGeneratorDialog(shell, beanClasses);
			int returnCode = dirDialog.open();

			if(CodeGeneratorDialog.GENERATE_CODE == returnCode){
				String[] selected = dirDialog.getItemsToOpen();
				this.generateCode(beanClasses, event);				
			}
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
		codesGenerator.setProjectPath(this.getFullPath());
		for(Class<?> beanClass : beanClasses){
			codesGenerator.addDomain(beanClass);
			codesGenerator.addGlobalImport("com.daos", beanClass.getSimpleName() + "DAO");
		}
		codesGenerator.generateCode();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	protected List<Class<?>> getClassesSelected(ExecutionEvent event) throws Exception {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
		Object[] selectionArray = selection.toArray();
		List<Class<?>> beanClasses = new ArrayList<Class<?>>();
		for(Object object: selectionArray){
			ICompilationUnit cu = (ICompilationUnit) object;
			String projectPath = cu.getJavaProject().getProject().getLocation().toOSString();
			this.setFullPath(projectPath);
			/*
			IFile file = (IFile) cu.getResource();

			String osString = file.getFullPath().toOSString();
			file.getLocationURI();
			String localtion = file.getFullPath().toOSString();
			IPath path = file.getFullPath();
			String p = System.getProperty("user.dir") + "" + file.getFullPath().toString();*/

			/**Output Project Folder.*/
			/*IPath selectedProjectPath = cu.getJavaProject().getOutputLocation();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IFolder folder = root.getFolder(selectedProjectPath);
			IPath locationOutputPath = folder.getLocation();*/
			IPath outputPath = ResourcesPlugin.getWorkspace().getRoot().findMember(cu.getJavaProject().getOutputLocation()).getLocation();
			/**/

			/*Source Project Folder.*/
			/*
			 for (IClasspathEntry entry : cu.getJavaProject().getResolvedClasspath(true)){
		        if(entry.getContentKind() == IPackageFragmentRoot.K_SOURCE ) {
		          // now we have IPath, which contains path in format /{projectName}/src/java and it is changed to c:\\proj\\projectdir\\src\\java
		          String relativePath = entry.getPath().toString();  
		          String javadir = ResourcesPlugin.getWorkspace().getRoot().findMember(relativePath).getLocation().toString(); // gives out system file path
		          if(javadir.contains("java")){
		        	  this.setFullPath(javadir);
		        	  break;
		          }
		          System.out.println("Source Folder: " + javadir);
		        } 
			}*/
			
			System.out.println("Java Project name : " + cu.getJavaProject().getElementName());

			IPackageDeclaration[] packages =  cu.getPackageDeclarations();
			for(IPackageDeclaration packageDeclaration: packages){
				System.out.println("Package Declaration [" + packageDeclaration.getElementName() + "].");
				String className = cu.getTypes()[0].getFullyQualifiedParameterizedName();
				
				int index = outputPath.toOSString().indexOf(cu.getJavaProject().getProject().getName());
				String osOutputDirectory = outputPath.toOSString().substring(index).replace(cu.getJavaProject().getProject().getName(), "").concat("\\");
				ClientProjectClassLoader cpcl = new ClientProjectClassLoader(cu,
						Activator.getDefault().getDescriptor().getPluginClassLoader(), osOutputDirectory);
				
				Class myClass = cpcl.loadClass(className);
				System.out.println(myClass.getCanonicalName());
				/*Class<?> domainClass = Activator.getDefault().getBundle().loadClass(className);
				*/
				beanClasses.add(myClass);
			}
			System.out.println("Unit selected [" + cu.getElementName() + "] with type [" + cu.getElementType() + "].");
		}
		return beanClasses;
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
}
