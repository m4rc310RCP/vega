package com.m4rc310.coamo.dialogs.pessoa.fisica;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.coamo.dialogs.FragmentComposite;
import com.m4rc310.coamo.models.PessoaFisica;
import com.m4rc310.coamo.models.Sexo;
import com.m4rc310.rcp.ui.utils.MAction;

@Creatable
public class FragmentPFBasicsData extends FragmentComposite {
	
	@Inject
	@Named("list_sexo")
	List<Sexo> listSexo;
	

	@Override
	public Composite make(MAction action, Composite parent_) {

		ActionPF actionPF = (ActionPF) action;

		Composite parent = pc.getComposite(parent_);

		parent.setLayout(new GridLayout(3, false));

		Composite c = getComposite(parent, 1);
		Label label = pc.getLabel(c, "Nome:");
		Text textName = pc.getText(c, "");
		textName.setMessage("Informe o nome completo");
		pc.configureUpperCase(textName);
		pc.setWidth(textName, 38);
		pc.groupControl(textName, label);
		
		c = getComposite(parent, 2);
		label = pc.getLabel(c, "Data de Nascimento:");
		GridData gd = new GridData();
		gd.horizontalSpan =2;
		label.setLayoutData(gd);
		Text textBirthDate = pc.getText(c, "", SWT.BORDER|SWT.CENTER);
		textBirthDate.setMessage("dd/MM/aaaa");
		pc.configureDateField("dd/MM/yyyy", textBirthDate);
		pc.setWidth(textBirthDate, 10);
		pc.groupControl(textBirthDate, label);
		textBirthDate.addListener(SWT.FocusOut, e->{
			Text txt = (Text) e.widget;
			actionPF.changeBirthDate(txt.getText());
		});
		
		Label labelAge = pc.getLabel(c, "Idade não definida!");
//		pc.setWidth(labelAge, 12);
		
		c = getComposite(parent, 1);
		label = pc.getLabel(c, "Sexo:");
		ComboViewer comboViewerGender = pc.getComboViewer(c, SWT.READ_ONLY);
		pc.groupControl(comboViewerGender.getCombo(), label);
		pc.setWidth(comboViewerGender.getControl(), 12); 
		
		comboViewerGender.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerGender.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Sexo) {
					Sexo sexo = (Sexo) element;
					return sexo.getDescricao();
				}
				return super.getText(element);
			}
			
			
		});
		comboViewerGender.setInput(listSexo);
		
		
		actionPF.addListener(this, FIRE$inform_age, e->{
			String age = e.getValue(String.class);
			labelAge.setText(age);
		});
		
		actionPF.addListener(this, FIRE$load_pf, e->{
			PessoaFisica pf = e.getValue(PessoaFisica.class);
			pc.observeWidget(textName, "nome", pf);
			pc.observeWidget(textBirthDate, "nascimento", pf,"dd/MM/yyyy");
			pc.observeComboList(comboViewerGender, "sexo", pf);

			pc.enabled(pf != null, textName, textBirthDate, labelAge, comboViewerGender.getCombo());
			if(pf==null) {
				labelAge.setText("Idade não definida!");
			}else {
				actionPF.changeBirthDate(textBirthDate.getText());
			}
		});

		
		
		return parent;
	}
	
	
}
