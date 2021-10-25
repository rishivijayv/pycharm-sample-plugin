package com.github.rishivijayv.pycharmsampleplugin.services

import com.intellij.openapi.project.Project
import com.github.rishivijayv.pycharmsampleplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
