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



    // функция возвращает длину стека фрагментов
    fun getLenghtOfStack():Int{
        val activity = weakActivity.get()

        activity?.run {
            return supportFragmentManager.backStackEntryCount
        }
    return 0
    }

// Эта функция незаметно удаляет все фрагменты из стека до фрагмента уровня level: (level 1 соответствует Mainfragment, 2 - cледующий в стеке и тд)
    fun clearStackUntillLevel(level:Int):Boolean{
        val activity = weakActivity.get()

        activity?.run {
            var count =  supportFragmentManager.backStackEntryCount
            while (level < count){
                supportFragmentManager.popBackStackImmediate()
                count--
            }

        }
        return true

    }

}