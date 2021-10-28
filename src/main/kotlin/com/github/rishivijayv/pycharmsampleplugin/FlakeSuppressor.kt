package com.github.rishivijayv.pycharmsampleplugin;

import com.intellij.codeInspection.InspectionSuppressor;
import com.intellij.codeInspection.SuppressQuickFix;
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil


class FlakeSuppressor : InspectionSuppressor {
    override fun isSuppressedFor(element: PsiElement, toolId: String): Boolean {
        if (!toolId.startsWith("Py")) {
            return false
        }

        return isSuppressedByEndOfLine(element) || isSuppressedByLineComment(element)
    }

    private fun isSuppressedByEndOfLine(element: PsiElement): Boolean {
        var leaf: PsiElement? = PsiTreeUtil.nextLeaf(element)

        while (leaf != null) {
            if (leaf is PsiWhiteSpace && leaf.textContains('\n') ) {
                return false
            }

            if (leaf is PsiComment && leaf.text.startsWith("# noqa")) {
                return true
            }
            leaf = PsiTreeUtil.nextLeaf(leaf)
        }

        return false
    }

    private fun isSuppressedByLineComment(element: PsiElement): Boolean {
        var found = false

        val file: PsiFile = element.containingFile
        file.acceptChildren(object: PsiRecursiveElementVisitor() {
            override fun visitComment(comment: PsiComment) {
                if (comment.text.startsWith("# flake8: noqa")) {
                    found = true
                }
            }
        })

        return found
    }

    override fun getSuppressActions(element: PsiElement?, toolId: String): Array<SuppressQuickFix> {
        TODO("Not yet implemented")
    }

}
