package com.m4rc310.coamo.parts.funcionario;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.coamo.actions.IActionCoamoConsts;
import com.m4rc310.coamo.models.Funcionario;
import com.m4rc310.coamo.parts.IComposite;
import com.m4rc310.rcp.ui.utils.MAction;
import com.m4rc310.rcp.ui.utils.PartControl;

@Creatable
public class MCompositeDadosFuncionario implements IComposite, IActionCoamoConsts{

//	private ViewerComposite viewerComposite;
	
	@Inject PartControl pc;

	@Override
	public Composite make(Composite parent) {
		return null;
	}

	@Override
	public Composite make(MAction action, Composite parent_) {
		Composite parent = pc.getComposite(parent_);
		parent.setLayout(new GridLayout(5,false));
		
		
		Group group = pc.getGroup(parent);
		group.setLayout(new GridLayout(3, false));
		
		pc.getLabel(group, "Matrícula:");
		
		Text textMatricula = pc.getText(group, "",SWT.BORDER | SWT.CENTER);
		pc.setWidth(textMatricula, 8);
		
		Text textNome = pc.getText(group, "");
		pc.setWidth(textNome, 30);
		
		group = pc.getGroup(parent);
		group.setLayout(new GridLayout(4, false));
		
		pc.getLabel(group, "Vinculo:");
		Text textVinculo = pc.getText(group, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textVinculo, 5);
		
		Text textDataVinculo = pc.getText(group, "", SWT.BORDER | SWT.CENTER);
		pc.setWidth(textDataVinculo, 10);
		
		Button checkAdmin = pc.getButton(group, "Administrados", SWT.CHECK);
		
		
		pc.editable(false, textMatricula, textNome);
		
		action.addListener(action, FIRE$load_funcionario, e->{
			Funcionario funcionario = e.getValue(Funcionario.class);
			
//			String md5Original = MD5Utils.getChecksum(funcionario);
//			
//			pc.addObserverListener(event->{
//				String md5 = MD5Utils.getChecksum(funcionario);
////				System.out.println(md5Original.equals(md5));
//				
//			});
			
			pc.observeWidget(textNome, "nome", funcionario.getDados());
			pc.observeWidget(textVinculo, "id", funcionario.getVinculo(),"%03d");
			pc.observeWidget(textDataVinculo, "inicio", funcionario.getVinculo(),"dd/MM/yyyy");
			pc.observeWidget(textMatricula, "matricula", funcionario);
			pc.observeWidget(checkAdmin, "isAdmin", funcionario);
//			
//			VinculoEmprego vinculo = funcionario.getVinculo();
//			
//			if(vinculo!=null) {
//				pc.observeTextString(textVinculo, "id", vinculo);
//				pc.observeTextDate(textDataVinculo, "inicio", vinculo, "dd/MM/yyyy");
//			}
			
//			pc.observeTextString(textDataVinculo, "inicio", funcionario.getVinculo().getInicio());
		});
		
		return parent;
	}
	
	
	
}
