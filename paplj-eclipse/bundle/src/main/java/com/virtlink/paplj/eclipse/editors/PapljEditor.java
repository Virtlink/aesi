package com.virtlink.paplj.eclipse.editors;

import com.virtlink.aesi.eclipse.editors.AesiEditor;
import com.virtlink.aesi.eclipse.editors.AesiSourceViewerConfiguration;
import com.virtlink.aesi.eclipse.editors.ColorManager;
import com.virtlink.paplj.eclipse.PapljPlugin;

public class PapljEditor extends AesiEditor {

	public PapljEditor(ColorManager colorManager, AesiSourceViewerConfiguration sourceViewerConfiguration) {
		super(
				PapljPlugin.getInjector().getInstance(ColorManager.class),
				PapljPlugin.getInjector().getInstance(AesiSourceViewerConfiguration.class));
	}

}
