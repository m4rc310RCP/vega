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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m4rc310.coamo.models.TipoLogradouro;

@Creatable
@Singleton
public class TipoLogradouroController {

	private List<TipoLogradouro> tiposLogradouro = new ArrayList<>();

	@Inject
	public TipoLogradouroController() {
		init();
	}

	private void init() {
		try {
			URL bundle = Platform.getBundle("com.m4rc310.mercado").getResource("database/tipos_logradouro.json");
			String path = FileLocator.toFileURL(bundle).getFile();
			
			ObjectMapper mapper = new ObjectMapper();
			
			tiposLogradouro.addAll(Arrays.asList(mapper.readValue(new File(path), TipoLogradouro[].class)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<TipoLogradouro> findTipoLogradouro(String descricao){
		return tiposLogradouro.stream().filter(tipo->tipo.getDescricao().toUpperCase().contains(descricao.toUpperCase())).collect(Collectors.toList());
	}
	
}
