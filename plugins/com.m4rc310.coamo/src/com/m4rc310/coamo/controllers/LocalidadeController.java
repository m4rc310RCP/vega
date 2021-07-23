package com.m4rc310.coamo.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m4rc310.coamo.models.Localidade;
import com.m4rc310.rcp.ui.phonetics.PhoneticUtils;

@Creatable
@Singleton
public class LocalidadeController {


	private List<Localidade> localidades = new ArrayList<>();
	private List<String> ufs = new ArrayList<>();

	Shell shell = new Shell(); 
	
	@Inject
	public LocalidadeController() {
//		init();
	}

	public void init() {
		try {
			URL bundle = Platform.getBundle("com.m4rc310.coamo").getResource("database/cities.json");
			String path = FileLocator.toFileURL(bundle).getFile();
//			
			ObjectMapper mapper = new ObjectMapper();
			localidades.addAll(Arrays.asList(mapper.readValue(new File(path), Localidade[].class)));
			
			localidades.stream().forEach(local->{
				String uf = local.getUf();
				if(!ufs.contains(uf)) {
					ufs.add(uf);
				}
			});
//			
//			MessageDialog.openInformation(shell, "Teste", localidades.size() + "");
		} catch (Exception e) {
			MessageDialog.openInformation(shell, "Teste", e.getMessage());
		}
	}
	
	public void test() {
		
		
		try {
			URL bundle = Platform.getBundle("com.m4rc310.coamo").getResource("database/cities.json");
			String path = FileLocator.toFileURL(bundle).getFile();
			
			MessageDialog.openInformation(shell, "Teste", path);
		} catch (Exception e) {
			MessageDialog.openInformation(shell, "Error", e.getMessage());
		}
		
		
	}
	
	
	public List<Localidade> findLocalidade(String distrito){
		String phone = PhoneticUtils.toString(distrito);
		return localidades.stream().filter(loc -> loc.getPhonetc().contains(phone)).collect(Collectors.toList());
	}
	
	public List<String> listUfs(){
		return ufs;
	}
	

//	private <T> List<T> listFronJson(String path, Class<T> type) {
//		
//		System.out.println(type);
//		
//		try {
//			List<T> ret = new ArrayList<T>();
//			
//			ObjectMapper mapper = new ObjectMapper();
//			List<?> list = mapper.readValue(new File(path), List.class);
//			
//			for (Object object : list) {
//				try {
//					ret.add(type.cast(object));
//				} catch (Exception e) {
//				}
//			}
//			
//			return ret;
//		} catch (Exception e) {
//			throw new UnsupportedOperationException(e);
//		}
//	}
}
