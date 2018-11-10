package com.bignerdranch.android.fiztehradio

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference
// TODO Класс был взят из примера по 4 лекции. Скринкаст https://cloud.mail.ru/public/KWkJ/3C1iZD5vy

class Router(activity : FragmentActivity, container: Int) {
    private val weakActivity = WeakReference(activity)
    private val fragmentContainer = container

    /* Метод navigateTo позволяет перейти к фрагменту.
       Параметр addToBack - добавить ли фрагмент в стек фрагментов.
       Второй параметр - лямбда, возврящающает фрагмент, который нужно добавить.
     */
    fun navigateTo(addToBack : Boolean = true, fragmentFactory: () -> Fragment) {
        val activity = weakActivity.get()

        activity?.run {
            val fragment = fragmentFactory()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(fragmentContainer, fragment)
            if (addToBack) transaction.addToBackStack(fragment::class.java.simpleName)
            transaction.commit()
        }
    }

// Метод для перехода к предыдущему фрагменту.
    fun navigateBack() : Boolean {
        val activity = weakActivity.get()

        activity?.run {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                return true
            }
        }

        return false
    }
}