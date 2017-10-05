package com.virtlink.aesi.eclipse;

import com.virtlink.editorservices.IProject;

public class ProjectManager {
	public IProject getProject() {
		return new EclipseProject();
	}
}
