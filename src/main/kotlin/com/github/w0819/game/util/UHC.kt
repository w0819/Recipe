package com.github.w0819.game.util

import java.util.jar.JarFile

interface UHC{
    companion object {
        @JvmStatic
        fun registerAll(pkg: String): Array<out UHC> {
            val jarFile = JarFile(this::class.java.protectionDomain.codeSource.location.path)
            val entries = jarFile.entries()
            val uhcList = ArrayList<UHC>()
            while (entries.hasMoreElements()) {
                val fileName = entries.nextElement().name
                if (fileName.startsWith("$pkg.".replace(".", "/")) && fileName.endsWith(".class")) {
                    uhcList.add(
                        Class.forName(fileName.replace("/", ".").removeSuffix(".class"))
                            .getDeclaredConstructor().newInstance() as? UHC ?: continue
                    )
                }
            }

            return uhcList.toTypedArray()
        }
    }
}