package org.metaborg.paplj.debugger

import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.runners.GenericProgramRunner

class AesiDebugRunner: GenericProgramRunner<AesiDebugRunnerSettings>() {

    override fun canRun(executorId: String, profile: RunProfile): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRunnerId(): String {
        return AesiDebugRunner::class.java.simpleName
    }
}